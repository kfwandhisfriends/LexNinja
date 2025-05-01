package cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.ScreenOnFireEffect;
import patches.AbstractCardEnum;
import powers.LexKela;
import powers.NuclearDragonPower;

public class NuclearDragon extends CustomCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("NuclearDragon");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Ninja/NuclearDragon.png";
    public static final String ID = "NuclearDragon";

    public NuclearDragon() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.POWER, AbstractCardEnum.Ninja_COLOR, CardRarity.RARE, CardTarget.SELF);
        this.exhaust = true;
        this.baseMagicNumber= 3;
        this.baseBlock = 4;
        this.magicNumber = baseMagicNumber;
    }


    public void use(AbstractPlayer p, AbstractMonster m){
        CardCrawlGame.sound.play("NuclearDragon");
        this.addToBot(new ApplyPowerAction(p,p,new NuclearDragonPower(p,this.magicNumber),this.magicNumber));
    }

    public void upgrade(){
        if(!this.upgraded){
            this.upgradeName();
            this.upgradeBlock(2);
            this.upgradeMagicNumber(1);
        }
    }

    public AbstractCard makeCopy(){
        return new NuclearDragon();
    }
}
