package cards.special;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import patches.AbstractCardEnum;
import patches.CardTagsEnum;

public class MosquitoHand extends CustomCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("MosquitoHand");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Ninja/MosquitoHand.png";
    public static final String ID = "MosquitoHand";

    public MosquitoHand(){
        super (ID, NAME, IMG_PATH, -2, DESCRIPTION, CardType.CURSE, CardColor.CURSE, CardRarity.CURSE, CardTarget.SELF);
        this.tags.add(CardTagsEnum.HAND);
    }

    public void use(AbstractPlayer p, AbstractMonster m ){

    }

    public void triggerOnOtherCardPlayed(AbstractCard c) {
        CardCrawlGame.sound.play("Mosquito");
    }

    public void triggerWhenDrawn() {
        CardCrawlGame.sound.play("MosquitoHand");
    }

    public void upgrade(){
    }

    public AbstractCard makeCopy(){
        return new MosquitoHand();
    }
}
