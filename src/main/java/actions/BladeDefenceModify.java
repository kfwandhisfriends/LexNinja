package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import patches.CardTagsEnum;

public class BladeDefenceModify extends AbstractGameAction {
    private AbstractCard thisCard;
    private int magicNumber;

    public BladeDefenceModify( AbstractCard thisCard,int magicNumber){
        this.thisCard = thisCard;
        this.magicNumber = magicNumber;
    }

    public void update(){

        for(AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisTurn) {
            if (c.hasTag(CardTagsEnum.BLADE)) {
                thisCard.baseDamage+=this.magicNumber;
                if (thisCard.baseDamage < 0) {
                    thisCard.baseDamage = 0;
                }
            }
        }

        this.isDone = true;
    }
}
