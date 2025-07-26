package powers;

import actions.PlaySoundAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class NanoExplosion extends AbstractPower {
    public static final String POWER_ID = "NanoExplosion";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public NanoExplosion(AbstractCreature owner) {
        this.name = NAME;
        this.ID = "NanoExplosion";
        this.owner = owner;
        this.amount = 1;
        this.type = PowerType.DEBUFF;
        this.updateDescription();
        this.loadRegion("cExplosion");
    }

    public void onDeath() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead() && this.owner.currentHealth <= 0) {
            this.addToBot(new PlaySoundAction("NanoExplosion"));
            this.addToBot(new DamageAllEnemiesAction((AbstractCreature)null, DamageInfo.createDamageMatrix(this.owner.maxHealth * this.amount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
        }

    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }

    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("NanoExplosion");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
