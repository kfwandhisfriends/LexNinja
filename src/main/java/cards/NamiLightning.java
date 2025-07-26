package cards;

import actions.ScienceAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import patches.AbstractCardEnum;
import patches.CardTagsEnum;
import powers.SciencePower;

public class NamiLightning extends CustomCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("NamiLightning");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Ninja/NamiLightning.png";
    public static final String ID = "NamiLightning";

    public NamiLightning() {
        super(ID, NAME, IMG_PATH, 4, DESCRIPTION, CardType.SKILL, AbstractCardEnum.Ninja_COLOR, CardRarity.COMMON, CardTarget.SELF);
        this.baseMagicNumber =2;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(CardTagsEnum.SCIENCE);
    }

    public void use(AbstractPlayer p, AbstractMonster m){

        CardCrawlGame.sound.play("NamiLightning");
        this.addToBot(new DrawCardAction(this.magicNumber));
        this.addToBot(new ScienceAction(p));
    }

    public void upgrade(){
        if(!this.upgraded){
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }

}
