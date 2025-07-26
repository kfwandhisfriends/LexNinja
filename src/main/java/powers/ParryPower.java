package powers;

import actions.PlaySoundAction;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.EntanglePower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import patches.CardTagsEnum;


public class ParryPower extends AbstractPower {
    public static final String POWER_ID = "ParryPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("ParryPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private boolean triggerd = false;

    public ParryPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.type = PowerType.BUFF;

        String path128 = "img/powers_Ninja/ParryPower84.png";
        String path48 = "img/powers_Ninja/ParryPower32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        this.updateDescription();
    }
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        this.triggerd = false;
    }
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (target != this.owner && info.type == DamageInfo.DamageType.NORMAL) {
            this.flash();
            this.addToBot(new GainBlockAction(this.owner, this.amount));
        }
    }
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.owner != null  && info.type != DamageInfo.DamageType.HP_LOSS && info.type != DamageInfo.DamageType.THORNS && info.owner != this.owner && damageAmount < info.base) {
            this.flash();
            CardCrawlGame.sound.play("BladeDefence");
            this.addToTop(new ApplyPowerAction(this.owner,this.owner,new LexKela(this.owner,1),1));
        }
        return damageAmount;
    }
    public void atStartOfTurn() {
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "ParryPower"));
    }

    public void updateDescription() {

        this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1];


    }


}
