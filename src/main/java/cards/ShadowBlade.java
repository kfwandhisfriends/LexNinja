package cards;

import actions.NinjutsuAction;
import actions.NinjutsuActionBot;
import actions.PlaySoundAction;
import actions.ShadowBladeAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.combat.SmokeBombEffect;
import patches.AbstractCardEnum;
import patches.CardTagsEnum;

public class ShadowBlade extends CustomCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("ShadowBlade");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Ninja/ShadowBlade.png";
    public static final String ID = "ShadowBlade";

    public ShadowBlade(){
        super (ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, AbstractCardEnum.Ninja_COLOR, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage = 12;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(CardTagsEnum.BLADE);
    }

    public void use(AbstractPlayer p, AbstractMonster m ){
        this.addToBot(new ShadowBladeAction(1,false,false,false));
        this.addToBot(new PlaySoundAction("ShadowBlade"));
        this.addToBot(new DamageAction(m,new DamageInfo(p,this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        this.addToBot(new ApplyPowerAction(m,p,new VulnerablePower(m,this.magicNumber,false),this.magicNumber));
        this.addToBot(new VFXAction(new SmokeBombEffect(p.hb.cX, p.hb.cY)));
    }

    public void upgrade(){
        if(!this.upgraded){
            this.upgradeName();
            this.upgradeDamage(2);
            this.upgradeMagicNumber(1);
        }
    }

    public AbstractCard makeCopy(){
        return new ShadowBlade();
    }
}
