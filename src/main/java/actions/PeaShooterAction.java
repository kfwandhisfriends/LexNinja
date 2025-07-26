package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class PeaShooterAction extends AbstractGameAction {

    public void update(){
        this.isDone=true;
        AbstractPower pea = AbstractDungeon.player.getPower("PeaShooter");

        if( pea != null) {
            for (int i = 0; i < pea.amount; i++) {
                this.addToTop(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, 2, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            }
        }
    }

}
