package relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import patches.CardTagsEnum;
import powers.AmoXilin;
import powers.LexKela;
import powers.PeaShooter;

public class MachineNinja extends CustomRelic {
    public static final String ID = "MachineNinja";
    private static final String IMG = "img/relics_Ninja/MachineNinja.png";
    private static final String IMG_OTL = "img/relics_Ninja/outline/MachineNinja.png";
    private static final int AMOUNT = 3;

    public MachineNinja() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.BOSS, LandingSound.SOLID);
        this.counter = 0;
    }

    public void onEquip() {
        ++AbstractDungeon.player.energy.energyMaster;
    }

    public void onUnequip() {
        --AbstractDungeon.player.energy.energyMaster;
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void onUseCard(AbstractCard card, UseCardAction action){
        AbstractPower lexkela = AbstractDungeon.player.getPower("LexKela");
        AbstractPower amoxilin = AbstractDungeon.player.getPower(AmoXilin.POWER_ID);

        ++counter;
        if (this.counter == 6) {
            this.counter = 0;
            this.flash();
            this.pulse = false;
            if (amoxilin != null){
                CardCrawlGame.sound.play("AmoXilin");
            }
            else if(lexkela!=null) {
                this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LexKela(AbstractDungeon.player, -1), -1));
            }
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            CardCrawlGame.sound.play("machine");
        } else if (this.counter == 5) {
            this.beginPulse();
            this.pulse = true;
        }
    }

    public AbstractRelic makeCopy() {
        return new MachineNinja();
    }
}
