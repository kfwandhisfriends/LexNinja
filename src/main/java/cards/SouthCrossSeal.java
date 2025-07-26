package cards;

import actions.NinjutsuAction;
import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.CollectorCurseEffect;
import patches.AbstractCardEnum;
import patches.CardTagsEnum;
import powers.SealPower;

public class SouthCrossSeal extends CustomCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("SouthCrossSeal");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Ninja/SouthCrossSeal.png";
    public static final String ID = "SouthCrossSeal";

    public SouthCrossSeal(){
        super (ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.SKILL, AbstractCardEnum.Ninja_COLOR, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust= true;
        this.tags.add(CardTagsEnum.NINJUTSU);
    }

    public void use(AbstractPlayer p, AbstractMonster m ){
        CardCrawlGame.sound.play("SouthCrossSeal");
        this.addToBot(new NinjutsuAction(p,new ApplyPowerAction(m,p,new SealPower(m,this.magicNumber),this.magicNumber),3,""));
        AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_COLLECTOR_DEBUFF"));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new CollectorCurseEffect(m.hb.cX, m.hb.cY), 2.0F));
        AbstractPower stunned = m.getPower("stslib:Stunned");
        if (stunned == null){
            this.addToBot(new StunMonsterAction(m, p));
            this.addToBot(new GainBlockAction(m,50));
        }

    }

    public void upgrade(){
        if(!this.upgraded){
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }

    public AbstractCard makeCopy(){
        return new SouthCrossSeal();
    }
}
