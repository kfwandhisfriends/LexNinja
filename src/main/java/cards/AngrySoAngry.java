package cards;

import actions.FrogFrogGoAction;
import actions.NinjutsuAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.WrathStance;
import patches.AbstractCardEnum;

//怒,好怒💢
public class AngrySoAngry extends CustomCard {
    //TODO: cardStrings
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("AngrySoAngry");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Ninja/AngrySoAngry.png";
    public static final String ID = "AngrySoAngry";

    public AngrySoAngry() {
        super(ID,NAME,IMG_PATH,1,DESCRIPTION, CardType.SKILL, AbstractCardEnum.Ninja_COLOR,
                CardRarity.COMMON, CardTarget.SELF);
        this.exhaust = true;
        this.retain = true;
    }

    @Override
    public void upgrade() {
        if(!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        AbstractStance stance = new WrathStance();
        stance.onPlayCard(this);
    }

    public AbstractCard makeCopy() {
        return new AngrySoAngry();
    }

}
