package actions;

import cards.WaterSandStorm;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.BlizzardEffect;

import java.util.Optional;

public class WaterSandAction extends AbstractGameAction {
    private AbstractPlayer p;

    public WaterSandAction(AbstractPlayer p){
        this.p = p;
    }

    @Override
    public void update(){
        int count = 0;
        AbstractPower Sand = p.getPower("SandWall");
        this.addToTop(new DamageAllEnemiesAction(p, Sand.amount, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        this.addToTop(new GainBlockAction(p,Sand.amount));
        this.addToTop(new VFXAction(new BlizzardEffect(0, AbstractDungeon.getMonsters().shouldFlipVfx()), 0.20F));
        this.isDone=true;
    }
}
