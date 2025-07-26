package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import powers.LexKela;

public class ScienceAction extends AbstractGameAction {
    private final AbstractPlayer p;
    AbstractGameAction abstractAction;
    private int ninjutsuKela =1;

    public ScienceAction(AbstractPlayer p) {
        this.p = p;
    }

    @Override
    public void update() {
        //判断是否有蕾克拉
        AbstractPower lexKela = p.getPower("LexKela");

        AbstractPower amoxilin = AbstractDungeon.player.getPower("AmoXilin");
        if (amoxilin != null){
            this.ninjutsuKela -= amoxilin.amount;
            CardCrawlGame.sound.play("AmoXilin");
            if (this.ninjutsuKela<=0){
                this.ninjutsuKela = 0;
            }
        }
        if(lexKela != null &&  lexKela.amount > 0 || this.ninjutsuKela == 0) {
            this.addToTop(new ApplyPowerAction(p,p,new LexKela(p,-this.ninjutsuKela)));
            this.isDone = true;
        }
        else {
            this.isDone = true;
        }

    }
}
