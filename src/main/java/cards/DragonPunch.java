package cards;

import actions.NinjutsuAction;
import actions.PlaySoundAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.core.appender.rolling.action.AbstractAction;
import patches.AbstractCardEnum;
import patches.CardTagsEnum;
import powers.LexKela;

public class DragonPunch extends CustomCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("DragonPunch");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Ninja/DragonPunch.png";
    private static final int COST = 2;
    private static final int ATTACK_DMG = 18;
    private static final int UPGRADE_PLUS_DMG = 6;
    private static final int UPGRADE_PLUS_MAGIC = 1;
    public static final String ID = "DragonPunch";
    public static final int NINJUTSU = 2;

    public DragonPunch() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, AbstractCardEnum.Ninja_COLOR, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage = ATTACK_DMG;
        this.baseMagicNumber = 2;
        this.magicNumber = baseMagicNumber;
        this.selfRetain=true;
        this.tags.add(CardTagsEnum.NINJUTSU);
        this.tags.add(CardTagsEnum.HAND);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        AbstractPower Lexkela = p.getPower("LexKela");
        CardCrawlGame.sound.play("DragonPunch");
        this.addToBot(new NinjutsuAction(p, new DrawCardAction(p, this.magicNumber), 1, ""));
    }
    
    public void upgrade(){
        if(!upgraded){
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_DMG);
        }
    }

    public AbstractCard makeCopy(){
        return new DragonPunch();
    }
}
