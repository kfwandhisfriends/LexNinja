package cards;

import actions.ManTooWeakAction;
import actions.NinjutsuAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import patches.AbstractCardEnum;
import patches.CardTagsEnum;
import powers.LexKela;

public class ManTooWeak extends CustomCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("ManTooWeak");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Ninja/ManTooWeak.png";
    public static final String ID = "ManTooWeak";

    public ManTooWeak() {
        super(ID, NAME, IMG_PATH, 0, DESCRIPTION, CardType.SKILL, AbstractCardEnum.Ninja_COLOR, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(CardTagsEnum.NINJUTSU);
        this.tags.add(CardTagsEnum.HAND);
    }

    public void use(AbstractPlayer p , AbstractMonster m){
        this.addToBot(new NinjutsuAction(p,new ManTooWeakAction(this.magicNumber),1,"ManTooWeak"));
    }

    public void upgrade(){
        if(!this.upgraded){
            this.upgradeName();
            this.exhaust = false;
            this.upgradeMagicNumber(1);
        }
    }

    public AbstractCard makeCopy(){
        return new ManTooWeak();
    }
}
