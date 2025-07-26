package cards;

import actions.ZombieAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import patches.AbstractCardEnum;
import powers.Zombie;

public class OhZombie extends CustomCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("OhZombie");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Ninja/OhZombie.png";
    public static final String ID = "OhZombie";

    public OhZombie(){
        super (ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.SKILL, AbstractCardEnum.Ninja_COLOR, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m ){
        AbstractPower pea = p.getPower("PeaShooter");
        int count = 0;
        if (pea != null) {
            count = pea.amount;
            this.addToBot(new ReducePowerAction(AbstractDungeon.player,AbstractDungeon.player,"PeaShooter",pea.amount));
        }
        CardCrawlGame.sound.play("OhZombie");


        this.addToBot(new ApplyPowerAction(p,p,new Zombie(p,count+this.magicNumber),count+this.magicNumber));
        this.addToBot(new ZombieAction());
    }

    public void upgrade(){
        if(!this.upgraded){
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }

    public AbstractCard makeCopy(){
        return new OhZombie();
    }

}
