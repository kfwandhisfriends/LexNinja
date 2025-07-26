package actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.ShakeScreenAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.GrandFinalEffect;

public class ShadeCrossSlashAction extends AbstractGameAction {
    private int damage;
    private int magicNumber;

    public ShadeCrossSlashAction( int damage , int magicNumber){
        this.damage = damage;
        this.magicNumber = magicNumber;
    }

    public void update(){
        this.addToBot(new VFXAction(new BorderLongFlashEffect(Color.LIGHT_GRAY)));
        if (Settings.FAST_MODE) {
            this.addToBot(new VFXAction(new GrandFinalEffect(), 0.7F));
        } else {
            this.addToBot(new VFXAction(new GrandFinalEffect(), 1.0F));
        }
        this.addToBot(new ShakeScreenAction(0.1F, ScreenShake.ShakeDur.MED, ScreenShake.ShakeIntensity.HIGH));
        this.addToBot(new VFXAction(AbstractDungeon.player, new CleaveEffect(), 0.1F));
        this.addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, this.damage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        this.addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, this.damage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        this.addToBot(new DrawCardAction(this.magicNumber));
        this.isDone=true;
    }
}
