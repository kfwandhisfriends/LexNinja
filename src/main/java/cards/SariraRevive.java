package cards;

import basemod.abstracts.CustomCard;
import basemod.interfaces.PostUpdateSubscriber;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Normality;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.FairyPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.LizardTail;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import patches.AbstractCardEnum;
import powers.LexKela;
import relics.Sarira;

public class SariraRevive extends CustomCard implements PostUpdateSubscriber {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("SariraRevive");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Ninja/SariraRevive.png";
    public static final String ID = "SariraRevive";

    public SariraRevive(){
        super (ID, NAME, IMG_PATH, 0, DESCRIPTION, CardType.SKILL, AbstractCardEnum.Ninja_COLOR, CardRarity.RARE, CardTarget.SELF);
        this.exhaust = true;
        this.upgraded = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m){
        CardCrawlGame.sound.play("SariraRevive");
        this.addToBot(new ApplyPowerAction(p ,p ,new LexKela(p , 2 ),2));
        this.addToBot(new DrawCardAction(p, 1));
    }

    public void upgrade(){

    }


    public AbstractCard makeCopy(){
        return new SariraRevive();
    }



    private void grantSariraRelic() {
        if (!AbstractDungeon.player.hasRelic("Sarira")) {
            CardCrawlGame.sound.play("SariraRevive");
            AbstractRelic sarira = new Sarira();
            AbstractDungeon.topLevelEffects.add(new AbstractGameEffect(){
                public void render(SpriteBatch sb){

                }
                public void dispose(){

                }
                public void update(){
                    receivePostUpdate();
                    this.isDone = true;
                }
            });
        }
    }

    public void onRemoveFromMasterDeck(){

        grantSariraRelic();
    }

    @Override
    public void receivePostUpdate() {
        AbstractRelic sarira = new Sarira();

                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, sarira);

    }
}

