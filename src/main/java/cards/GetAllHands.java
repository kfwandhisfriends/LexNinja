package cards;

import actions.GetAllHandsAction;
import actions.NinjutsuAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.CorruptionPower;
import patches.AbstractCardEnum;
import patches.CardTagsEnum;
import powers.GetAllHandsPower;

public class GetAllHands extends CustomCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("GetAllHands");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Ninja/GetAllHands.png";
    public static final String ID = "GetAllHands";

    public GetAllHands(){
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.SKILL, AbstractCardEnum.Ninja_COLOR, CardRarity.RARE, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(CardTagsEnum.HAND);
        this.tags.add(CardTagsEnum.NINJUTSU);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m){
        this.addToBot(new NinjutsuAction(p,new GetAllHandsAction(p),2,"GetAllHands"));
    }

    public void upgrade(){
        if(!this.upgraded){
            this.upgradeName();
            this.upgradeBaseCost(1);
        }
    }

    public AbstractCard makeCopy(){
        return new GetAllHands();
    }

}
