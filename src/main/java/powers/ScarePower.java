package powers;

import actions.PlaySoundAction;
import actions.scareAction;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import patches.CardTagsEnum;
import patches.LibraryTypeEnum;

import java.util.ArrayList;
import java.util.List;

public class ScarePower extends AbstractPower {
    public static final String POWER_ID = "ScarePower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("ScarePower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public int magicNumber = 0;


    public ScarePower(AbstractCreature owner, int magicNumber , int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.magicNumber = magicNumber;
        this.amount = amount;
        this.updateDescription();
        this.type = PowerType.BUFF;

        String path128 = "img/powers_Ninja/ScarePower84.png";
        String path48 = "img/powers_Ninja/ScarePower32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        this.updateDescription();
    }

    @Override
    public void stackPower(int stackAmount){
        if(stackAmount>0) {
            this.magicNumber += stackAmount;
        }
        else {
            this.amount += stackAmount;
        }

        if (this.amount <= 0) {
            this.amount = 0;
        }
    }

    public void onExhaust(AbstractCard card) {
        if(this.amount>0 && card.hasTag(CardTagsEnum.SCARE)&& card.type != AbstractCard.CardType.POWER){
            this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ScarePower(AbstractDungeon.player, 0, -1), -1));
        }
    }

    public void onUseCard(AbstractCard card, UseCardAction action){
        if(this.amount>0 && card.hasTag(CardTagsEnum.SCARE) && card.type == AbstractCard.CardType.POWER){
            this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ScarePower(AbstractDungeon.player, 0, -1), -1));
        }
    }

    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + this.magicNumber + powerStrings.DESCRIPTIONS[1];
    }

    public void atStartOfTurn(){

        this.amount = this.magicNumber;
        this.flash();
        this.addToTop(new scareAction(this.amount));

    }
}
