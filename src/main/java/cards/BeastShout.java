package cards;

import actions.BeastShoutAction;
import actions.NinjutsuAction;
import actions.PlaySoundAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.ReaperEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import patches.AbstractCardEnum;
import patches.CardTagsEnum;

public class BeastShout extends CustomCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("BeastShout");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Ninja/BeastShout.png";
    public static final String ID = "BeastShout";

    public BeastShout(){
        super (ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.ATTACK, AbstractCardEnum.Ninja_COLOR, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
        this.baseDamage = 12;
        this.tags.add(CardTagsEnum.NINJUTSU);
        this.isMultiDamage = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m ){
        this.addToBot(new SFXAction("ATTACK_PIERCING_WAIL"));
        if (Settings.FAST_MODE) {
            this.addToBot(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, Settings.GREEN_TEXT_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC), 0.3F));
        } else {
            this.addToBot(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, Settings.GREEN_TEXT_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC), 1.5F));
        }
        CardCrawlGame.sound.play("BeastVoice");
        this.addToBot(new VFXAction(new ReaperEffect()));
        this.addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, this.multiDamage , DamageInfo.DamageType.NORMAL , AbstractGameAction.AttackEffect.NONE));
        this.addToBot(new NinjutsuAction( p , new BeastShoutAction( this.magicNumber ), 1 , "BeastShout"));
    }


    public void upgrade(){
        if(!this.upgraded){
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.upgradeDamage(3);
        }
    }

    public AbstractCard makeCopy(){
        return new BeastShout();
    }
}
