package cards;

import actions.NinjutsuAction;
import actions.UBWAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import patches.AbstractCardEnum;
import patches.CardTagsEnum;

public class UBW extends CustomCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("UBW");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Ninja/UBW.png";
    public static final String ID = "UBW";

    public UBW(){
        super (ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, AbstractCardEnum.Ninja_COLOR, CardRarity.RARE, CardTarget.ALL_ENEMY);
        this.baseDamage = 8;
        this.tags.add(CardTagsEnum.NINJUTSU);
        this.tags.add(CardTagsEnum.BLADE);
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber ;
    }

    public void use(AbstractPlayer p, AbstractMonster m ){

        this.addToBot(new NinjutsuAction(p,new UBWAction(this.damage),1,""));
    }

    public void applyPowers() {
        super.applyPowers();
        int count = 0;

        for(AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if (c.hasTag(CardTagsEnum.BLADE) || c.cardID=="YiCut" || c.cardID=="Shiv") {
                ++count;
            }
        }

        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[0] + count;
        if (count == 1) {
            this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[1];
        } else {
            this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[2];
        }

        this.initializeDescription();
    }

    public void upgrade(){
        if(!this.upgraded){
            this.upgradeName();
            this.upgradeDamage(3);
        }
    }

    public AbstractCard makeCopy(){
        return new UBW();
    }
}
