package cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;
import patches.AbstractCardEnum;
import patches.CardTagsEnum;

public class FlashSlash extends CustomCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("FlashSlash");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Ninja/FlashSlash.png";
    public static final String ID = "FlashSlash";

    public FlashSlash(){
        super (ID, NAME, IMG_PATH, 0, DESCRIPTION, CardType.ATTACK, AbstractCardEnum.Ninja_COLOR, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage = 8;
        this.tags.add(CardTagsEnum.BLADE);
        this.isInnate = true;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m ){
        CardCrawlGame.sound.play("Flash");
        CardCrawlGame.sound.play("FlashSlash");
        this.addToBot(new VFXAction(new WhirlwindEffect(), 0.0F));
        this.addToBot(new DamageAction(m,new DamageInfo(p,this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        this.addToBot(new ApplyPowerAction(m, p, new StrengthPower(m, -this.damage), -this.damage, true, AbstractGameAction.AttackEffect.NONE));
        if (!m.hasPower("Artifact")) {
            this.addToBot(new ApplyPowerAction(m, p, new GainStrengthPower(m, this.damage), this.damage, true, AbstractGameAction.AttackEffect.NONE));
        }
    }

    public void upgrade(){
        if(!this.upgraded){
            this.upgradeName();
            this.upgradeDamage(3);
        }
    }

    public AbstractCard makeCopy(){
        return new FlashSlash();
    }
}
