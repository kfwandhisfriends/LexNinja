package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ZombieAction extends AbstractGameAction {
    public void update(){
        this.isDone=true;
        AbstractPower zombie = AbstractDungeon.player.getPower("Zombie");

        if( zombie != null) {
            this.addToTop(new GainBlockAction(AbstractDungeon.player,2*zombie.amount));
        }
    }
}
