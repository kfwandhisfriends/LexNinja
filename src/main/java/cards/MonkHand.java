package cards;

import actions.MonkHandAction;
import actions.NinjutsuAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
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

public class MonkHand extends CustomCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("MonkHand");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Ninja/MonkHand.png";
    public static final String ID = "MonkHand";

    public MonkHand() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.ATTACK, AbstractCardEnum.Ninja_COLOR, CardRarity.COMMON, CardTarget.ENEMY);
        this.baseBlock = 6;
        this.magicNumber = baseMagicNumber;
        this.baseDamage = 0;
        this.tags.add(CardTagsEnum.HAND);
        this.tags.add(CardTagsEnum.NINJUTSU);
    }

    public void use(AbstractPlayer p, AbstractMonster m){
        this.addToTop(new GainBlockAction(p,this.block));

        this.baseDamage = this.block + p.currentBlock;

        this.addToBot(new NinjutsuAction(p,new MonkHandAction(p,m,new DamageInfo(p, this.damage , DamageInfo.DamageType.NORMAL)),1,"MonkHand"));

    }

    public void applyPowers() {
        this.baseDamage = this.block + AbstractDungeon.player.currentBlock;
        super.applyPowers();
        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription = this.rawDescription + cardStrings.UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }

    public void upgrade(){
        if(!this.upgraded){
            this.upgradeName();
            this.upgradeBlock(2);
        }
    }

    public AbstractCard makeCopy(){
        return new MonkHand();
    }
}
