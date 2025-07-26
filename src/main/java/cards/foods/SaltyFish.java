package cards.foods;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.BetterDiscardPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import patches.AbstractCardEnum;
import patches.CardTagsEnum;

public class SaltyFish extends CustomCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("SaltyFish");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Ninja/SaltyFish.png";
    public static final String ID = "SaltyFish";

    public SaltyFish(){
        super (ID, NAME, IMG_PATH, 0, DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.SELF);
        this.exhaust = true;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(CardTagsEnum.FOOD);
    }

    public void use(AbstractPlayer p, AbstractMonster m ){
        CardCrawlGame.sound.play("SaltyFish");
        this.addToBot(new BetterDiscardPileToHandAction(this.magicNumber, 0));
    }

    public void upgrade(){
        if(!this.upgraded){
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }

    public AbstractCard makeCopy(){
        return new SaltyFish();
    }

}
