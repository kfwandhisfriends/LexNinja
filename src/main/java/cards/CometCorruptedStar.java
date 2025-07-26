package cards;

import actions.NinjutsuAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import patches.AbstractCardEnum;
import powers.LexKela;

public class CometCorruptedStar extends CustomCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("CometCorruptedStar");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Ninja/CometCorruptedStar.png";
    public static final String ID = "CometCorruptedStar";
    private int ninjutsuKela =1;

    public CometCorruptedStar(){
        super (ID, NAME, IMG_PATH, 0, DESCRIPTION, CardType.SKILL, AbstractCardEnum.Ninja_COLOR, CardRarity.COMMON, CardTarget.SELF);
        this.baseMagicNumber=2;
        this.magicNumber=this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m ){
        CardCrawlGame.sound.play("CometCorruptedStar");
        AbstractPower lexkela = p.getPower("LexKela");
        AbstractPower amoxilin = AbstractDungeon.player.getPower("AmoXilin");

        if(lexkela != null && lexkela.amount <= 2){
            this.addToBot(new ApplyPowerAction(p,p,new LexKela(p,this.magicNumber),this.magicNumber));
        }
        else {
            if (lexkela == null) {
                this.addToBot(new ApplyPowerAction(p, p, new LexKela(p, this.magicNumber), this.magicNumber));
            }
            else {
                if (amoxilin != null){
                    this.ninjutsuKela -= amoxilin.amount;
                    CardCrawlGame.sound.play("AmoXilin");
                    if (this.ninjutsuKela<=0){
                        this.ninjutsuKela = 0;
                    }
                }
                this.addToBot(new ApplyPowerAction(p, p, new LexKela(p, -this.ninjutsuKela)));
                this.addToBot(new GainEnergyAction(1));
            }
        }

    }

    public void upgrade(){
        if(!this.upgraded){
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }

    public AbstractCard makeCopy(){
        return new CometCorruptedStar();
    }
}
