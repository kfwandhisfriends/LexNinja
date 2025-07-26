package cards.special;

import actions.PlaySoundAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import patches.AbstractCardEnum;

public class HengheShui extends CustomCard {

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("HengheShui");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Ninja/HengheShui.png";
    public static final String ID = "HengheShui";

    public HengheShui(){
        super (ID, NAME, IMG_PATH, 0, DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.UNCOMMON, CardTarget.SELF);
        this.tags.add(CardTags.HEALING);
        this.baseMagicNumber = 15;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m ){
        CardCrawlGame.sound.play(HengheShui.ID);
        this.addToBot(new PlaySoundAction("ASan"));
        int randomHeal = AbstractDungeon.miscRng.random(0, this.magicNumber);
        int randomPoison = AbstractDungeon.miscRng.random(0,2);
        this.addToBot(new HealAction(p,p,randomHeal));
        this.addToBot(new ApplyPowerAction(p,p,new PoisonPower(p,p,randomPoison),randomPoison));
    }

    public void upgrade(){
        if(!this.upgraded){
            this.upgradeName();
            this.upgradeMagicNumber(5);
        }
    }

    public AbstractCard makeCopy(){
        return new HengheShui();
    }

}
