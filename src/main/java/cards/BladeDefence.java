package cards;

import actions.BladeDefenceAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import patches.AbstractCardEnum;
import patches.CardTagsEnum;
import powers.LexKela;
import powers.ParryPower;

public class BladeDefence extends CustomCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("BladeDefence");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Ninja/BladeDefence.png";
    public static final String ID = "BladeDefence";

    public BladeDefence(){
        super (ID, NAME, IMG_PATH, 0, DESCRIPTION, CardType.SKILL, AbstractCardEnum.Ninja_COLOR, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseDamage = 3;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(CardTagsEnum.BLADE);
    }

    public void use(AbstractPlayer p, AbstractMonster m ){
        CardCrawlGame.sound.play("BladeDefence");
        this.addToBot(new ApplyPowerAction(p,p,new ParryPower(p,this.magicNumber),this.magicNumber));
    }

    public void upgrade(){
        if(!this.upgraded){
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }

    public AbstractCard makeCopy(){
        return new BladeDefence();
    }
}
