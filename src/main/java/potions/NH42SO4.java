package potions;

import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import powers.LexKela;
import powers.PeaShooter;

public class NH42SO4 extends CustomPotion {
    public static final String POTION_ID = "NH42SO4";
    private static final PotionStrings potionStrings;

    public NH42SO4() {
        super(potionStrings.NAME, "NH42SO4", PotionRarity.UNCOMMON, PotionSize.M, PotionColor.WEAK);
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
            CardCrawlGame.sound.play("NH42SO4");
            this.addToBot(new ApplyPowerAction(var2, AbstractDungeon.player, new PeaShooter(var2, this.potency), this.potency));
        }

    }

    public int getPotency(int ascensionLevel) {
        return 4;
    }

    public AbstractPotion makeCopy() {
        return new NH42SO4();
    }

    static {
        potionStrings = CardCrawlGame.languagePack.getPotionString("NH42SO4");
    }
}
