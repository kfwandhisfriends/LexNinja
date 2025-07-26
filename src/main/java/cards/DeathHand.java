package cards;

import actions.NinjutsuAction;
import actions.TakeYourHeartAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Beta;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import patches.AbstractCardEnum;
import patches.CardTagsEnum;

public class DeathHand extends CustomCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("DeathHand");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Ninja/TakeYourHeart.png";
    public static final String ID = "DeathHand";

    public DeathHand(){
        super (ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.NONE);
        this.tags.add(CardTagsEnum.HAND);
        this.tags.add(CardTagsEnum.NINJUTSU);
        this.exhaust = true;
        this.baseMagicNumber = 6;
        this.magicNumber = this.baseMagicNumber;
        this.cardsToPreview = new TakeYourHeart();
    }

    public void use(AbstractPlayer p, AbstractMonster m ){
        this.addToBot(new NinjutsuAction(p,new MakeTempCardInDrawPileAction(this.cardsToPreview.makeStatEquivalentCopy(), 1, true, true),this.magicNumber,"DeathHand"));
    }

    public void upgrade(){
        if(!this.upgraded){
            this.upgradeName();
            this.upgradeMagicNumber(-1);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    public AbstractCard makeCopy(){
        return new DeathHand();
    }
}
