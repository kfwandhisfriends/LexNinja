package relics;

import basemod.abstracts.CustomRelic;
import com.evacipated.cardcrawl.mod.stslib.relics.OnPlayerDeathRelic;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class Sarira extends CustomRelic implements OnPlayerDeathRelic {
    public static final String ID = "Sarira";
    private static final String IMG = "img/relics_Ninja/Sarira.png";
    private static final String IMG_OTL = "img/relics_Ninja/outline/Sarira.png";
    public boolean triggered ;

    //调用父类的构造方法，传参为super(遗物ID,遗物全图，遗物白底图，遗物稀有度，获得遗物时的音效)
    public Sarira() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.SPECIAL, LandingSound.MAGICAL);
        this.triggered  = false;
        this.grayscale = false;
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public boolean onPlayerDeath(AbstractPlayer abstractPlayer, DamageInfo damageInfo) {
        if(!triggered) {
            AbstractPlayer p = AbstractDungeon.player;
            p.isDying = false;
            p.isDead = false;
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMBAT;
            p.currentHealth = 1;
            CardCrawlGame.sound.play("SariraRevive");
            // 显示遗物特效
            addToTop(new RelicAboveCreatureAction(p, this));

            // 满血复活
            addToTop(new HealAction(p, p, p.maxHealth));
            this.triggered = true;
            this.grayscale = true;
            return false;
        }
        else {
            return true;
        }
    }

    public AbstractRelic makeCopy() {
        return new Sarira();
    }

}
