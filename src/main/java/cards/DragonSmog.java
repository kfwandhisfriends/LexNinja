package cards;

import actions.DragonSmogAction;
import actions.NinjutsuAction;
import actions.PlaySoundAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.vfx.combat.SmokeBombEffect;
import patches.AbstractCardEnum;
import patches.CardTagsEnum;

public class DragonSmog extends CustomCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("DragonSmog");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Ninja/DragonSmog.png";
    private static final int COST = 3;
    private static final int UPGRADE_PLUS_MAGIC = 2;
    public static final String ID = "DragonSmog";
    private static final int NINJUTSU = 2 ;

    public DragonSmog(){
        super (ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, AbstractCardEnum.Ninja_COLOR, CardRarity.RARE, CardTarget.SELF);
        this.baseMagicNumber = 5;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust=true;
        this.tags.add(CardTagsEnum.NINJUTSU);
    }

    public void use(AbstractPlayer p, AbstractMonster m){
        AbstractCreature var2 = AbstractDungeon.player;
        this.addToBot(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, 1), 1));
        this.addToBot(new VFXAction(new SmokeBombEffect(var2.hb.cX, var2.hb.cY)));
        CardCrawlGame.sound.play("DragonSmog");
        this.addToBot(new NinjutsuAction(p,new  DragonSmogAction(p , m ,this.magicNumber),NINJUTSU,""));
    }

    public void upgrade(){
        if(!this.upgraded){
            this.upgradeName();
            this.upgradeBaseCost(2);
        }
    }

    public AbstractCard makeCopy(){
        return new DragonSmog();
    }
}
