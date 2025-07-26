package cards.foods;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import patches.AbstractCardEnum;
import patches.CardTagsEnum;

public class Lizhi extends CustomCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Lizhi");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Ninja/Lizhi.png";
    public static final String ID = "Lizhi";

    public Lizhi(){
        super (ID, NAME, IMG_PATH, 0, DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.ENEMY);
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
        this.tags.add(CardTagsEnum.FOOD);
    }

    public void use(AbstractPlayer p, AbstractMonster m ){
        CardCrawlGame.sound.play("Lizhi");
        this.addToBot(new ApplyPowerAction(m,p,new WeakPower(m,this.magicNumber,false),this.magicNumber));
        this.addToBot(new ApplyPowerAction(m,p,new VulnerablePower(m,this.magicNumber,false),this.magicNumber));
    }

    public void upgrade(){
        if(!this.upgraded){
            this.upgradeName();
            this.upgradeMagicNumber(2);
        }
    }

    public AbstractCard makeCopy(){
        return new Lizhi();
    }

}
