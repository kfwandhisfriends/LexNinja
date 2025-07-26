package powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
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
import patches.CardTagsEnum;

public class LexKela extends AbstractPower {
    public static final String POWER_ID = "LexKela";
    private static final PowerStrings powerStrings= CardCrawlGame.languagePack.getPowerStrings("LexKela");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private boolean isUsed = false;

    public LexKela(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount ;

        if (this.amount >= 999) {
            this.amount = 999;
        }

        String path128 = "img/powers_Ninja/LexKela84.png";
        String path48 = "img/powers_Ninja/LexKela32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        this.updateDescription();
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;

        if (this.amount >= 999) {
            this.amount = 999;
        }
        if (this.amount <= 0){
            this.amount = 0;
        }
    }

    public void reducePower(int reduceAmount) {
        this.fontScale = 8.0F;
        this.amount -= reduceAmount;
        if (this.amount == 0) {
            this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, NAME));
        }
    }
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source){
        // 检测是否是蕾克拉能力，且层数减少（消耗）
        if ((power.ID.equals(this.ID) && power.amount < 0)) {
            this.isUsed = true;
        }
    }

    public void atStartOfTurn() {
        this.isUsed = false;
    }

    public void atEndOfTurn(boolean isPlayer){
        if (!this.isUsed){
            this.addToBot(new ApplyPowerAction(this.owner,this.owner,new LexKela(this.owner,1),1));
        }
    }

    public void updateDescription() {

            this.description = DESCRIPTIONS[0]+this.amount+DESCRIPTIONS[1];

    }

}
