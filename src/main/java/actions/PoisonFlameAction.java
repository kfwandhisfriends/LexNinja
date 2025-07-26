package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class PoisonFlameAction extends AbstractGameAction {

    private AbstractPlayer p;
    private int magicNumber;

    public PoisonFlameAction(AbstractPlayer p,int magicNumber){
        this.p=p;
        this.magicNumber=magicNumber;
    }

    public void update(){
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {

            for(AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                if (!monster.isDead && !monster.isDying) {
                    this.addToTop(new ApplyPowerAction(monster, p, new PoisonPower(monster, p, this.magicNumber), this.magicNumber));
                }
            }
        }
        this.isDone=true;
    }
}
