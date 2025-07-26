package actions;

import cards.TakeYourHeart;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import patches.CardTagsEnum;
import patches.LibraryTypeEnum;
import powers.ScarePower;

import java.util.ArrayList;
import java.util.List;

public class scareAction extends AbstractGameAction {

    private int amount;

    public scareAction(int amount) {
        this.amount = amount;
    }

    public void update() {
        //读取忍术卡
        /*List<AbstractCard> ninjutsuCards = new ArrayList<>();
        ninjutsuCards.add(new TakeYourHeart());
        for (AbstractCard c : CardLibrary.getCardList(LibraryTypeEnum.Ninja_COLOR)) {
                if (c.tags.contains(CardTagsEnum.NINJUTSU) && c.rarity != AbstractCard.CardRarity.BASIC && c.cardID !="DeathHand") {
                    ninjutsuCards.add(c);
            }
        }
        for (AbstractCard c : CardLibrary.getCardList(LibraryTypeEnum.Ninja_COLOR)) {
            if (c.tags.contains(CardTagsEnum.NINJUTSU) && c.rarity != AbstractCard.CardRarity.BASIC && c.cardID !="DeathHand") {
                ninjutsuCards.add(c);
            }
        }




        if (!ninjutsuCards.isEmpty()) {*/
            //随机释放忍术
            for (int i = 0; i < this.amount; i++) {

                this.addToTop(new PlaySoundAction("Pick"));
                /*int randomIndex = AbstractDungeon.cardRandomRng.random(ninjutsuCards.size() - 1);
                AbstractCard randomNinjutsu = ninjutsuCards.get(randomIndex).makeCopy();
                AbstractCard card = randomNinjutsu;*/

                AbstractCard card = null;

                while ((card==null)){
                    int roll = AbstractDungeon.cardRandomRng.random(99);
                    AbstractCard.CardRarity cardRarity;
                    if (roll < 55) {
                        cardRarity = AbstractCard.CardRarity.COMMON;
                    } else if (roll < 85) {
                        cardRarity = AbstractCard.CardRarity.UNCOMMON;
                    } else {
                        cardRarity = AbstractCard.CardRarity.RARE;
                    }

                    AbstractCard tmp = CardLibrary.getAnyColorCard(cardRarity);

                    for (AbstractCard c : CardLibrary.getCardList(LibraryTypeEnum.Ninja_COLOR)) {
                        if (c.tags.contains(CardTagsEnum.NINJUTSU) && c.cardID == tmp.cardID) {
                            card = c;
                            break;
                        }
                    }
                }

                card.tags.add(CardTagsEnum.SCARE);
                AbstractDungeon.getCurrRoom().souls.remove(card);
                card.exhaustOnUseOnce = true;
                AbstractDungeon.player.limbo.group.add(card);
                card.current_y = -200.0F * Settings.scale;
                card.target_x = (float) Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
                card.target_y = (float) Settings.HEIGHT / 2.0F;
                card.targetAngle = 0.0F;
                card.lighten(false);
                card.drawScale = 0.12F;
                card.targetDrawScale = 0.75F;
                card.applyPowers();
                this.addToTop(new NewQueueCardAction(card, AbstractDungeon.getCurrRoom().monsters.getRandomMonster((AbstractMonster) null, true, AbstractDungeon.cardRandomRng), true, true));
                this.addToTop(new UnlimboAction(card));
                if (!Settings.FAST_MODE) {
                    this.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
                } else {
                    this.addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
                }

            }
        this.isDone =true;
        }




    }

