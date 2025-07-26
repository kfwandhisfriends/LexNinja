package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class BeastShoutAction extends AbstractGameAction {

    private int magicNumber;

    public BeastShoutAction(int magicNumber ){
        this.magicNumber = magicNumber;
    }

    public void update(){

        for(AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                this.addToTop(new ApplyPowerAction(mo, AbstractDungeon.player, new StrengthPower(mo, -this.magicNumber), -this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
        }
        this.isDone=true;
    }

}
