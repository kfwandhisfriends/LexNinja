package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import powers.LexKela;
import powers.ScarePower;

public class NinjutsuActionBot extends AbstractGameAction {

    private final AbstractPlayer p;
    private int ninjutsuKela;
    AbstractGameAction abstractAction;
    private String key;

    public NinjutsuActionBot(AbstractPlayer p, AbstractGameAction abstractAction, int ninjutsuKela, String key) {
        this.actionType = AbstractGameAction.ActionType.REDUCE_POWER;
        this.source = p;
        this.p = p;
        this.ninjutsuKela = ninjutsuKela;
        this.abstractAction = abstractAction;
        this.key = key;
    }

    @Override
    public void update() {
        //判断蕾克拉是否足够
        AbstractPower lexKela = p.getPower("LexKela");
        AbstractPower dimdeadtree = p.getPower("DimDeadTreePower");
        AbstractPower scare = p.getPower("ScarePower");
        AbstractPower amoxilin = p.getPower("AmoXilin");
        
        if (scare != null && scare.amount > 0) {
            //受惊形态不消耗蕾克拉初尝试
            CardCrawlGame.sound.play(key);
            this.addToBot(abstractAction);
            this.isDone = true;
        } else {
            if (dimdeadtree != null) {
                //暗寒死树生效则不消耗蕾克拉
                CardCrawlGame.sound.play(key);
                this.addToBot(abstractAction);
                this.isDone = true;
            } else {
                //暗寒死树不生效则判断蕾克拉是否足够
                if (amoxilin != null){
                    this.ninjutsuKela -= amoxilin.amount;
                    if (this.ninjutsuKela<=0){
                        this.ninjutsuKela = 0;
                    }
                }
                if ( this.ninjutsuKela == 0|| lexKela != null && lexKela.amount >= this.ninjutsuKela) {
                    //释放忍术
                    CardCrawlGame.sound.play(key);
                    this.addToBot(abstractAction);
                    this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LexKela(AbstractDungeon.player, -this.ninjutsuKela)));

                    this.isDone = true;
                } else {
                    //不够就无事发生
                    this.isDone = true;
                }
            }
        }
    }
}
