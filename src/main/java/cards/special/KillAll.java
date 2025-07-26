package cards.special;

import actions.KillAllAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlyingOrbEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

public class KillAll extends CustomCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("KillAll");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Ninja/KillAll.png";
    public static final String ID = "KillAll";
    private boolean triggerd = true;

    public KillAll(){
        super (ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.CURSE, CardColor.CURSE, CardRarity.SPECIAL, CardTarget.ALL);
    }

    public void use(AbstractPlayer p, AbstractMonster m ){
        if (this.dontTriggerOnUseCard){
            this.addToBot(new KillAllAction());
        }


    }

    public void triggerOnEndOfTurnForPlayingCard() {
        this.dontTriggerOnUseCard = true;
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
    }

    public void upgrade(){
    }

    public AbstractCard makeCopy(){
        return new KillAll();
    }
}
