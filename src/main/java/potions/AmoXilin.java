package potions;

import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.shrines.WeMeetAgain;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class AmoXilin extends CustomPotion {
    public static final String POTION_ID = "AmoXilin";
    private static final PotionStrings potionStrings;

    public AmoXilin() {
        super(potionStrings.NAME, "AmoXilin", PotionRarity.RARE, PotionSize.T, PotionColor.POWER);
        this.labOutlineColor = Settings.SHADOW_COLOR;
        this.description = potionStrings.DESCRIPTIONS[0] + this.potency + potionStrings.DESCRIPTIONS[1];
        this.isThrown = false;
    }

    public void initializeData() {
        this.potency = this.getPotency();
        this.description = potionStrings.DESCRIPTIONS[0] + this.potency + potionStrings.DESCRIPTIONS[1];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    public void use(AbstractCreature target) {
        AbstractCreature var2 = AbstractDungeon.player;
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            CardCrawlGame.sound.play("AmoXilin");
            this.addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new powers.AmoXilin(AbstractDungeon.player,this.potency),this.potency));
        }

    }

    public boolean canUse() {
        if (AbstractDungeon.actionManager.turnHasEnded && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            return false;
        } else {
            return AbstractDungeon.getCurrRoom().event == null || !(AbstractDungeon.getCurrRoom().event instanceof WeMeetAgain);
        }
    }

    public int getPotency(int ascensionLevel) {
        return 1;
    }

    public AbstractPotion makeCopy() {
        return new AmoXilin();
    }

    static {
        potionStrings = CardCrawlGame.languagePack.getPotionString("AmoXilin");
    }
}
