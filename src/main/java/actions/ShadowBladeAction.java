package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import patches.CardTagsEnum;

public class ShadowBladeAction extends AbstractGameAction {
    private boolean exhaustCards = false;

    private AbstractPlayer p;

    private boolean isRandom;

    private boolean anyNumber;

    private boolean canPickZero;

    public static int numPlayed;

    private boolean isAutoPlay = true;

    public ShadowBladeAction(int amount, boolean isRandom, boolean anyNumber, boolean canPickZero, boolean exhaustCards) {
        this.anyNumber = anyNumber;
        this.p = AbstractDungeon.player;
        this.canPickZero = canPickZero;
        this.isRandom = isRandom;
        this.amount = amount;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.actionType = AbstractGameAction.ActionType.EXHAUST;
        this.exhaustCards = exhaustCards;
    }

    public ShadowBladeAction(int amount, boolean isRandom, boolean anyNumber, boolean canPickZero) {
        this.anyNumber = anyNumber;
        this.p = AbstractDungeon.player;
        this.canPickZero = canPickZero;
        this.isRandom = isRandom;
        this.amount = amount;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.actionType = AbstractGameAction.ActionType.EXHAUST;
    }

    public ShadowBladeAction(int amount, boolean isRandom, boolean anyNumber, boolean canPickZero, boolean exhaustCards, boolean isAutoPlay) {
        this.anyNumber = anyNumber;
        this.p = AbstractDungeon.player;
        this.canPickZero = canPickZero;
        this.isRandom = isRandom;
        this.amount = amount;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.actionType = AbstractGameAction.ActionType.EXHAUST;
        this.exhaustCards = exhaustCards;
        this.isAutoPlay = isAutoPlay;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            if (this.p.hand.isEmpty()) {
                this.isDone = true;
                return;
            }
            if (!this.anyNumber && this.p.hand.size() <= this.amount) {
                this.amount = this.p.hand.size();
                numPlayed = this.amount;
                int k = this.p.hand.size();
                for (int j = 0; j < k; j++) {
                    AbstractCard card = this.p.hand.getTopCard();
                    playCards(card);
                }
                CardCrawlGame.dungeon.checkForPactAchievement();
                this.isDone = true;
                return;
            }
            if (!this.isRandom) {
                numPlayed = this.amount;
                AbstractDungeon.handCardSelectScreen.open("", this.amount, this.anyNumber, this.canPickZero);
                        tickDuration();
                return;
            }
            for (int i = 0; i < this.amount; i++) {
                AbstractCard card = AbstractDungeon.player.hand.getRandomCard(true);
                playCards(card);
            }
            CardCrawlGame.dungeon.checkForPactAchievement();
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard card : AbstractDungeon.handCardSelectScreen.selectedCards.group)
                playCards(card);
            CardCrawlGame.dungeon.checkForPactAchievement();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        tickDuration();
    }

    private void playCards(AbstractCard card) {
        card.tags.add(CardTagsEnum.BLADE);
        AbstractDungeon.player.hand.group.remove(card);
        (AbstractDungeon.getCurrRoom()).souls.remove(card);
        card.exhaustOnUseOnce = true;
        AbstractDungeon.player.limbo.group.add(card);
        card.current_y = -200.0F * Settings.scale;
        card.target_x = Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
        card.target_y = Settings.HEIGHT / 2.0F;
        card.targetAngle = 0.0F;
        card.lighten(false);
        card.drawScale = 0.12F;
        card.targetDrawScale = 0.75F;
        card.applyPowers();
        addToTop((AbstractGameAction)new NewQueueCardAction(card, true, false, this.isAutoPlay));
        addToTop((AbstractGameAction)new UnlimboAction(card));
        addToTop((AbstractGameAction)new WaitAction(Settings.ACTION_DUR_FASTER));
    }
}
