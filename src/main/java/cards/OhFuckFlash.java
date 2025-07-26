package cards;

import actions.NinjutsuAction;
import actions.OhFuckFlashAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BufferPower;
import patches.AbstractCardEnum;
import patches.CardTagsEnum;

public class OhFuckFlash extends CustomCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("OhFuckFlash");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Ninja/OhFuckFlash.png";
    public static final String ID = "OhFuckFlash";

    public OhFuckFlash(){
        super (ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.SKILL, AbstractCardEnum.Ninja_COLOR, CardRarity.RARE,CardTarget.SELF);
        this.baseMagicNumber = 3;
        this.magicNumber = baseMagicNumber;
        this.tags.add(CardTagsEnum.NINJUTSU);
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m ){
        CardCrawlGame.sound.play("Flash");
        this.addToBot(new ApplyPowerAction(p,p,new BufferPower(p,1),1));
        this.addToBot(new NinjutsuAction(p,new OhFuckFlashAction(), this.magicNumber , "OhFuckFlash"));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(-1);
        }
    }

    public AbstractCard makeCopy(){
        return new OhFuckFlash();
    }
}
