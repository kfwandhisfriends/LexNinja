package cards;

import actions.NinjutsuAction;
import actions.NinjutsuActionBot;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import patches.AbstractCardEnum;
import patches.CardTagsEnum;
import powers.ScarePower;

public class ScareForm extends CustomCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("ScareForm");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Ninja/ScareForm.png";
    public static final String ID = "ScareForm";

    public ScareForm(){
        super (ID, NAME, IMG_PATH, 3, DESCRIPTION, CardType.POWER, AbstractCardEnum.Ninja_COLOR, CardRarity.RARE, CardTarget.SELF);
        this.tags.add(CardTagsEnum.NINJUTSU);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m ){
        this.addToBot(new NinjutsuAction(p,new ApplyPowerAction(p,p,new ScarePower(p,this.magicNumber , 0),this.magicNumber),3,"Pick"));
    }

    public void upgrade(){
        if(!this.upgraded){
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }

    public AbstractCard makeCopy(){
        return new ScareForm();
    }
}
