package powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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

public class AmoXilin extends AbstractPower{
    public static final String POWER_ID = "AmoXilin";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("AmoXilin");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public AmoXilin(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.type = AbstractPower.PowerType.BUFF;

        String path128 = "img/powers_Ninja/AmoXilin84.png";
        String path48 = "img/powers_Ninja/AmoXilin32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        this.updateDescription();
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        AbstractPower scare = AbstractDungeon.player.getPower("ScarePower");
        if (scare != null && scare.amount <= 0) {
            if ((card.hasTag(CardTagsEnum.NINJUTSU) || card.cardID == "YiCut") ) {
                this.flash();
            }
        } else {
            if ((card.hasTag(CardTagsEnum.NINJUTSU) || card.cardID == "YiCut") ) {
                this.flash();
                CardCrawlGame.sound.play("AmoXilin");
            }
        }
    }

    public void updateDescription() {

        this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1];


    }
}
