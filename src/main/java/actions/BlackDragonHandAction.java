package actions;

import cards.BlackDragonHand;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import com.megacrit.cardcrawl.vfx.combat.FlyingOrbEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import com.megacrit.cardcrawl.vfx.combat.ReaperEffect;


public class BlackDragonHandAction extends AbstractGameAction {
    private AbstractCreature target;
    private DamageInfo info;
    private AbstractGameAction.AttackEffect effect;
    private int hp;

    public BlackDragonHandAction(AbstractCreature target , DamageInfo info,int hp,AbstractGameAction.AttackEffect effect){
        this.target = target;
        this.info = info;
        this.hp =hp;
        this.effect = effect;
    }

    public void update(){
        if(target.hasPower("Intangible")){
            this.addToTop(new RemoveSpecificPowerAction(this.target, AbstractDungeon.player,"Intangible"));

        }
        AbstractDungeon.effectsQueue.add(new LightningEffect(this.target.hb.cX, this.target.hb.cY));
        this.addToBot(new VFXAction(new FlyingOrbEffect(target.hb.cX, target.hb.cY)));
        this.addToBot(new DamageAction(this.target, this.info, AttackEffect.NONE));
        AbstractDungeon.player.increaseMaxHp(this.hp, false);
        this.isDone=true;
    }

}
