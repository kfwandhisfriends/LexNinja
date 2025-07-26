package cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import patches.AbstractCardEnum;
import patches.CardTagsEnum;
import powers.LexKela;

public class PastHasGoneHand extends CustomCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("PastHasGoneHand");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Ninja/PastHasGoneHand.png";
    public static final String ID = "PastHasGoneHand";

    public PastHasGoneHand(){
        super (ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.SKILL, AbstractCardEnum.Ninja_COLOR, CardRarity.RARE, CardTarget.SELF);
        this.tags.add(CardTagsEnum.HAND);
        this.baseBlock = 4;
        this.block =this.baseBlock;
        this.exhaust = true;
        this.tags.add(CardTagsEnum.HAND);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m ){
        if (!AbstractDungeon.player.discardPile.isEmpty()) {
            CardCrawlGame.sound.play("PastHasGoneHand");
            for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
                // 为每张卡牌添加消耗动作
                this.addToTop(new GainBlockAction(p, this.block));
                this.addToBot(new ApplyPowerAction(p, p, new LexKela(p, 1), 1));
                this.addToBot(new ExhaustSpecificCardAction(c, AbstractDungeon.player.discardPile));
            }
        }
    }

    public void upgrade(){
        if(!this.upgraded){
            this.upgradeName();
            this.upgradeBlock(2);
        }
    }

    public AbstractCard makeCopy(){
        return new PastHasGoneHand();
    }
}
