package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import powers.LexKela;

public class ShenWeiAction extends AbstractGameAction {
    private int ninjutsuKela =1;

    public void update(){
        AbstractPower lexKela = AbstractDungeon.player.getPower("LexKela");
        AbstractPower amoxilin = AbstractDungeon.player.getPower("AmoXilin");
        if (amoxilin != null){
            this.ninjutsuKela -= amoxilin.amount;
            CardCrawlGame.sound.play("AmoXilin");
            if (this.ninjutsuKela<=0){
                this.ninjutsuKela = 0;
            }
        }
        if ( this.ninjutsuKela == 0||lexKela != null && lexKela.amount >= this.ninjutsuKela ) {
            this.addToTop(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new LexKela(AbstractDungeon.player,-this.ninjutsuKela),-this.ninjutsuKela));
            this.addToTop(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new IntangiblePlayerPower(AbstractDungeon.player,1),1));
            this.addToTop(new PlaySoundAction("ShenWei"));

        }
        this.isDone = true;
    }
    
}
