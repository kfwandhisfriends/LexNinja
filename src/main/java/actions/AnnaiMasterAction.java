package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import patches.CardTagsEnum;
import patches.LibraryTypeEnum;

import java.util.ArrayList;
import java.util.List;

public class AnnaiMasterAction extends AbstractGameAction {
    private boolean retrieveCard = false;
    private boolean upgraded;

    public AnnaiMasterAction(boolean upgraded) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.upgraded = upgraded;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractDungeon.cardRewardScreen.customCombatOpen(this.generateCardChoices(), CardRewardScreen.TEXT[1], true);
            this.tickDuration();
        } else {
            if (!this.retrieveCard) {
                if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                    AbstractCard disCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                    disCard.setCostForTurn(0);

                    disCard.current_x = -1000.0F * Settings.xScale;
                    if (AbstractDungeon.player.hand.size() < 10) {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                    } else {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                    }

                    AbstractDungeon.cardRewardScreen.discoveryCard = null;
                }

                this.retrieveCard = true;
            }

            this.tickDuration();
        }
    }

    private ArrayList<AbstractCard> generateCardChoices() {
        ArrayList<AbstractCard> derp = new ArrayList();

        while (derp.size() != 3) {
            boolean dupe = false;
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
            /*System.out.println("[DEBUG] 1.这张牌tmp是" + tmp.cardID);*/

            for (AbstractCard c : CardLibrary.getCardList(LibraryTypeEnum.Ninja_COLOR)) {
                if (c.tags.contains(CardTagsEnum.NINJUTSU) && !c.tags.contains(AbstractCard.CardTags.HEALING) && c.cardID == tmp.cardID) {
                    /*System.out.println("[DEBUG] 2.这张牌tmp是" + tmp.cardID);
                    System.out.println("[DEBUG] 2.这张牌c是" + c.cardID);*/
                    for (AbstractCard card : derp) {
                        if (card.cardID.equals(tmp.cardID)) {
                            /*System.out.println("[DEBUG] 3.这张牌card是" + card.cardID);
                            System.out.println("[DEBUG] 3.这张牌tmp是" + tmp.cardID);*/
                            dupe = true;
                            break;
                        }
                    }

                    if (!dupe) {
                        derp.add(tmp.makeCopy());
                    }
                }
            }



        }

        return derp;
    }
}
