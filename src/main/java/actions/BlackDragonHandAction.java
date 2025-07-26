package actions;

import cards.BlackDragonHand;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.InstantKillAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import com.megacrit.cardcrawl.vfx.combat.FlyingOrbEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import com.megacrit.cardcrawl.vfx.combat.ReaperEffect;


public class BlackDragonHandAction extends AbstractGameAction {
    private AbstractCreature target;
    private AbstractGameAction.AttackEffect effect;
    private int hp;

    public BlackDragonHandAction(AbstractCreature target ,int hp,AbstractGameAction.AttackEffect effect){
        this.target = target;
        this.hp =hp;
        this.effect = effect;
    }

    public void update(){
        this.isDone=true;

        if(target.hasPower("Intangible")){
            this.addToTop(new RemoveSpecificPowerAction(this.target, AbstractDungeon.player,"Intangible"));
        }
        if(target.hasPower("IntangiblePlayer")){
            this.addToTop(new RemoveSpecificPowerAction(this.target, AbstractDungeon.player,"IntangiblePlayer"));
        }

        if(this.target.maxHealth <= this.hp){
            AbstractDungeon.effectsQueue.add(new LightningEffect(this.target.hb.cX, this.target.hb.cY));
            CardCrawlGame.sound.playA("ORB_LIGHTNING_EVOKE", 0.9F);
            CardCrawlGame.sound.playA("ORB_LIGHTNING_PASSIVE", -0.3F);
            this.addToTop(new InstantKillAction(this.target));
            AbstractDungeon.player.increaseMaxHp(this.target.maxHealth, false);
            this.addToTop(new VFXAction(new FlyingOrbEffect(target.hb.cX, target.hb.cY)));
        }
        else {
            AbstractDungeon.effectsQueue.add(new LightningEffect(this.target.hb.cX, this.target.hb.cY));
            CardCrawlGame.sound.playA("ORB_LIGHTNING_EVOKE", 0.9F);
            CardCrawlGame.sound.playA("ORB_LIGHTNING_PASSIVE", -0.3F);
            this.addToTop(new VFXAction(new FlyingOrbEffect(target.hb.cX, target.hb.cY)));
            this.target.decreaseMaxHealth(hp);
            AbstractDungeon.player.increaseMaxHp(this.hp, false);
        }
    }

}
