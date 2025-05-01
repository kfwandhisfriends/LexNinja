package cards;

import actions.NinjutsuAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import patches.AbstractCardEnum;
import patches.CardTagsEnum;

public class RacingAgainstMessi extends CustomCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("RacingAgainstMessi");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Ninja/RacingAgainstMessi.png";
    public static final String ID = "RacingAgainstMessi";

    public RacingAgainstMessi(){
        super (ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.SKILL, AbstractCardEnum.Ninja_COLOR, CardRarity.COMMON, CardTarget.SELF);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(CardTagsEnum.NINJUTSU);
    }

    public void use(AbstractPlayer p, AbstractMonster m ){
        this.addToBot(new DrawCardAction(1));
        this.addToBot(new NinjutsuAction(p,new DrawCardAction(this.magicNumber),1,"RacingAgainstMessi"));
    }

    public void upgrade(){
        if(!this.upgraded){
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }

    public AbstractCard makeCopy(){
        return new RacingAgainstMessi();
    }
}
