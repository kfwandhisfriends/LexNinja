package cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.unique.RandomizeHandCostAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import patches.AbstractCardEnum;
import patches.CardTagsEnum;
import powers.LexKela;

public class DarknessSnakeHand extends CustomCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("DarknessSnakeHand");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Ninja/DarknessSnakeHand.png";
    public static final String ID = "DarknessSnakeHand";

    public DarknessSnakeHand() {
        super(ID, NAME, IMG_PATH, 0, DESCRIPTION, CardType.SKILL, AbstractCardEnum.Ninja_COLOR, CardRarity.COMMON, CardTarget.SELF);
        this.baseMagicNumber = 1 ;
        this.magicNumber = baseMagicNumber;
        this.exhaust = true;
        this.tags.add(CardTagsEnum.HAND);
    }

    public void use(AbstractPlayer p, AbstractMonster m){
        CardCrawlGame.sound.play("DarknessSnakeHand");
        this.addToBot(new ApplyPowerAction(p,p,new LexKela(p,this.magicNumber),this.magicNumber));
        this.addToBot(new DrawCardAction(p,5));
        this.addToBot(new RandomizeHandCostAction());
    }

    public void upgrade(){
        if(!this.upgraded){
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }

    public AbstractCard makeCopy(){
        return new DarknessSnakeHand();
    }
}
