package cards;

import actions.NinjutsuAction;
import actions.WaterSandAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.BlizzardEffect;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;
import patches.AbstractCardEnum;
import patches.CardTagsEnum;
import powers.SandWall;

public class WaterSandStorm extends CustomCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("WaterSandStorm");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Ninja/WaterSandStorm.png";
    public static final String ID = "WaterSandStorm";

    public WaterSandStorm() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, AbstractCardEnum.Ninja_COLOR, CardRarity.RARE, CardTarget.ALL_ENEMY);
        this.baseMagicNumber = 10;
        this.magicNumber = baseMagicNumber;
        this.isMultiDamage = true;
        this.tags.add(CardTagsEnum.NINJUTSU);
    }

    public void use(AbstractPlayer p, AbstractMonster m){
        this.addToBot(new ApplyPowerAction(p,p,new SandWall(p,this.magicNumber),this.magicNumber));
        this.addToBot(new NinjutsuAction(p,new WaterSandAction(p),2,"WaterSandStorm"));
    }

    public void upgrade(){
        if(!this.upgraded){
            this.upgradeName();
            this.upgradeMagicNumber(2);
        }
    }
    public AbstractCard makeCopy(){
        return new WaterSandStorm();
    }
}
