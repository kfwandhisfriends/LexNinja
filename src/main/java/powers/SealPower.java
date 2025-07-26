package powers;

import actions.PlaySoundAction;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.PoisonLoseHpAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.CollectorCurseEffect;

public class SealPower extends AbstractPower {
    public static final String POWER_ID = "SealPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("SealPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public SealPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.type = PowerType.DEBUFF;

        String path128 = "img/powers_Ninja/SealPower84.png";
        String path48 = "img/powers_Ninja/SealPower32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        this.updateDescription();
    }

    public void atEndOfTurn(boolean isPlayer){
        if(!isPlayer){
            if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                this.addToBot(new PlaySoundAction("SouthCrossSeal"));
                AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_COLLECTOR_DEBUFF"));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new CollectorCurseEffect(this.owner.hb.cX, this.owner.hb.cY), 2.0F));
                AbstractMonster mo = (AbstractMonster) this.owner;
                this.addToBot(new StunMonsterAction(mo, AbstractDungeon.player));
                this.addToBot(new GainBlockAction(this.owner,50));
                this.addToBot(new ReducePowerAction(this.owner,this.owner,"SealPower",1));
            }
        }

    }

    public void wasHPLost(DamageInfo info, int damageAmount) {
        if (info.owner != null && info.owner != this.owner && damageAmount > 0) {
            this.flash();
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }

    }

    public void updateDescription() {

        this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1];


    }
}
