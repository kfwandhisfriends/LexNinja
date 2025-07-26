package powers;

import actions.NinjutsuAction;
import actions.PlaySoundAction;
import actions.ShenWeiAction;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import patches.CardTagsEnum;

public class ShenWeiPower extends AbstractPower {
    public static final String POWER_ID = "ShenWeiPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("ShenWeiPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ShenWeiPower(AbstractCreature owner , int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.type = PowerType.BUFF;

        String path128 = "img/powers_Ninja/ShenWeiPower84.png";
        String path48 = "img/powers_Ninja/ShenWeiPower32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        this.updateDescription();
    }

    public void atStartOfTurn(){
        this.flash();
        for(int i = 0; i <this.amount; i++) {
            this.addToBot(new ShenWeiAction());
        }
    }

    public void onUseCard(AbstractCard card, UseCardAction action){
        if(card.type == AbstractCard.CardType.ATTACK){
            AbstractPower intangible = this.owner.getPower("IntangiblePlayer");
            this.flash();
            if (intangible != null) {
                this.addToBot(new ReducePowerAction(this.owner, this.owner, "IntangiblePlayer", this.amount));
            }
        }
    }

    public void updateDescription() {

        this.description = powerStrings.DESCRIPTIONS[0]+ this.amount +powerStrings.DESCRIPTIONS[1]+ this.amount +powerStrings.DESCRIPTIONS[2];


    }
}
