package relics;

import actions.PlaySoundAction;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import patches.CardTagsEnum;
import powers.LexKela;

import java.util.ArrayList;
import java.util.List;

public class KFC extends CustomRelic {
    public static final String ID = "KFC";
    private static final String IMG = "img/relics_Ninja/KFC.png";
    private static final String IMG_OTL = "img/relics_Ninja/outline/KFC.png";

    //调用父类的构造方法，传参为super(遗物ID,遗物全图，遗物白底图，遗物稀有度，获得遗物时的音效)
    public KFC() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.RARE, LandingSound.HEAVY);
    }

    @Override
    public void onEquip() {
        this.counter = 0;
    }

    @Override
    public void atTurnStart() {
        if (this.counter == -1) {
            this.counter += 2;
        } else {
            ++this.counter;
        }

        if (this.counter == 3) {
            this.counter = 0;
            this.flash();
            this.pulse = false;
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            List<AbstractCard> foodCards = new ArrayList<>();
            for (AbstractCard card : CardLibrary.getCardList(CardLibrary.LibraryType.COLORLESS)) { // 替换为角色卡池类型
                if (card.hasTag(CardTagsEnum.FOOD)) {
                    foodCards.add(card);
                }
            }

            if (!foodCards.isEmpty()) {
                // 生成随机索引（修正范围）
                int randomIndex = AbstractDungeon.cardRandomRng.random(foodCards.size() - 1);
                AbstractCard randomFood = foodCards.get(randomIndex).makeCopy();
                this.addToBot(new PlaySoundAction("KFC"));
                this.addToBot(new MakeTempCardInHandAction(randomFood,  1));
            }
        }else if (this.counter == 2) {
            this.beginPulse();
            this.pulse = true;
        }

    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] ;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new KFC();
    }
}
