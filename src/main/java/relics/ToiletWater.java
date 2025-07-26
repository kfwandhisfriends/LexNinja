package relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.FallingDustEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import powers.LexKela;

public class ToiletWater extends CustomRelic {
    public static final String ID = "ToiletWater";
    private static final String IMG = "img/relics_Ninja/ToiletWater.png";
    private static final String IMG_OTL = "img/relics_Ninja/outline/ToiletWater.png";

    //调用父类的构造方法，传参为super(遗物ID,遗物全图，遗物白底图，遗物稀有度，获得遗物时的音效)
    public ToiletWater() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override
    public void atBattleStart() {
        this.beginPulse();
        this.pulse = true;
        this.counter = 2;
    }

    @Override
    public void onCardDraw(AbstractCard drawnCard) {
        if(counter>0){
            if (drawnCard.type == AbstractCard.CardType.CURSE){
                this.addToTop(new ExhaustSpecificCardAction(drawnCard, AbstractDungeon.player.hand));
                this.addToTop(new DrawCardAction(1));
                CardCrawlGame.sound.play("ToiletWater");
                counter --;
                this.flash();
            }
        }
        else {
            this.pulse = false;
        }

    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 2 + this.DESCRIPTIONS[1];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new ToiletWater();
    }
}
