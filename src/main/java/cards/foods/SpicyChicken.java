package cards.foods;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import patches.AbstractCardEnum;
import patches.CardTagsEnum;

public class SpicyChicken extends CustomCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("SpicyChicken");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Ninja/SpicyChicken.png";
    public static final String ID = "SpicyChicken";

    public SpicyChicken(){
        super (ID, NAME, IMG_PATH, 0, DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.SELF);
        this.baseMagicNumber = 5;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
        this.tags.add(CardTagsEnum.FOOD);
    }

    public void use(AbstractPlayer p, AbstractMonster m ){
        CardCrawlGame.sound.play("SpicyChicken");
        this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
        this.addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, this.magicNumber), this.magicNumber));
    }

    public void upgrade(){
        if(!this.upgraded){
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }

    public AbstractCard makeCopy(){
        return new SpicyChicken();
    }

}
