package cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import patches.AbstractCardEnum;
import patches.CardTagsEnum;
import powers.LexKela;

public class ICantImagine extends CustomCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("ICantImagine");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Ninja/ICantImagine.png";
    public static final String ID = "ICantImagine";

    public ICantImagine(){
        super (ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.SKILL, AbstractCardEnum.Ninja_COLOR, CardRarity.COMMON, CardTarget.SELF);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
        this.baseBlock = 6;
    }

    public void use(AbstractPlayer p , AbstractMonster m){
        CardCrawlGame.sound.play("ICantImagine");
        AbstractPower lexkela = p.getPower("LexKela");
        if (lexkela == null) {
            return;
        }
        int amount = this.magicNumber*lexkela.amount;
        this.addToBot(new ApplyPowerAction(p , p ,new LexKela(p , -lexkela.amount),-lexkela.amount));
        for(int i=0;i<amount;i++){
            this.addToBot(new GainBlockAction(p,this.block));

        }
        this.addToBot(new GainEnergyAction(amount));
    }

    public void upgrade(){
        if(!this.upgraded){
            this.upgradeName();
            this.upgradeBaseCost(0);
        }
    }

    public AbstractCard makeCopy(){
        return new ICantImagine();
    }
}
