package events;

import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.CombatPhase;
import basemod.abstracts.events.phases.TextPhase;
import cards.special.MosquitoHand;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.AscendersBane;
import com.megacrit.cardcrawl.cards.curses.CurseOfTheBell;
import com.megacrit.cardcrawl.cards.curses.Necronomicurse;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import patches.CardTagsEnum;
import relics.ToiletWater;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class Thirsty extends PhasedEvent {
    public static final String ID = "Thirsty";
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    public static final String NAME = eventStrings.NAME;
    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    public static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String title = eventStrings.NAME;
    private static final String IMG_PATH = "images/events/fountain.jpg";
    private int healAmount = 0;
    private int NoMoreWaterChance = 25;
    private boolean anyCard = false;
    private AbstractCard cardToRemove;

    public Thirsty() {
        super(ID, title, IMG_PATH);
        this.healAmount = MathUtils.round( AbstractDungeon.player.maxHealth * 0.10F);
        if (this.getRandomCard()!=null){
            this.cardToRemove = this.getRandomCard();
            this.anyCard = true;
        }
        else {
            this.anyCard = false;
        }
        /*0 发现了一处泉水*/
        registerPhase("start", (new TextPhase(DESCRIPTIONS[0]))
                .addOption(new TextPhase.OptionInfo(OPTIONS[0]), this::continued));
        /*1 啊渴了渴了渴了渴了渴了，你突发恶疾，感到特别口渴*/
        registerPhase("continued",(new TextPhase(DESCRIPTIONS[1]))
                .addOption(new TextPhase.OptionInfo(OPTIONS[1]+2*this.healAmount+OPTIONS[6]), this::drink1));
        /*2 喝了一口水，身体的疲惫有所缓解。 NL NL 仔细一看，泉水还有余，要不......*/
        registerPhase("drink1",(new TextPhase(DESCRIPTIONS[2]))
                .addOption(new TextPhase.OptionInfo(OPTIONS[2] + this.healAmount + OPTIONS[7] + this.cardToRemove.name,this.cardToRemove).enabledCondition(()->this.anyCard,OPTIONS[8]), this::drink2)
                .addOption(new TextPhase.OptionInfo(OPTIONS[3]), this::leave1));
        /*4 喝完水，你感到特别的满足，继续上路*/
        registerPhase("leave1", (new TextPhase(DESCRIPTIONS[4]))
                .addOption(OPTIONS[4], integer -> openMap()));
        /*5 泉水都被喝完了，没东西继续喝了，只好继续上路*/
        registerPhase("leave2", (new TextPhase(DESCRIPTIONS[5]))
                .addOption(OPTIONS[4], integer1 -> openMap()));
        transitionKey("start");
    }


    private void continued(Integer integer){
        CardCrawlGame.sound.play("Thirsty");
        this.imageEventText.loadImage("img/event_Ninja/Thirsty.png");
        transitionKey("continued");
    }

    private void drink1(Integer integer){
        CardCrawlGame.sound.play("Drink");
        AbstractDungeon.player.heal(2*this.healAmount);
        transitionKey("drink1");
    }

    private void drink2(Integer integer){
        CardCrawlGame.sound.play("MoreDrink");
        AbstractDungeon.player.heal(this.healAmount,true);
        AbstractDungeon.effectList.add(new PurgeCardEffect(this.cardToRemove));
        AbstractDungeon.player.masterDeck.removeCard(this.cardToRemove);
        if (this.getRandomCard()!=null){
            this.cardToRemove = this.getRandomCard();
            this.anyCard = true;
        }
        else {
            this.anyCard = false;
        }
        /*3 还不够！你又喝了一口*/
        registerPhase("drink2",(new TextPhase(DESCRIPTIONS[3]))
                .addOption(new TextPhase.OptionInfo(OPTIONS[5] + this.healAmount + OPTIONS[7] + this.cardToRemove.name+"。 有"+ this.NoMoreWaterChance +"％的可能喝完。",this.cardToRemove).enabledCondition(()->this.anyCard,OPTIONS[8]), this::drink3));
        transitionKey("drink2");
    }

    private void drink3(Integer integer){
        CardCrawlGame.sound.play("MORE!!!");
        int random = AbstractDungeon.miscRng.random(0, 99);
        if (random < 99 - this.NoMoreWaterChance) {
            AbstractDungeon.player.heal(this.healAmount,true);
            AbstractDungeon.effectList.add(new PurgeCardEffect(this.cardToRemove));
            AbstractDungeon.player.masterDeck.removeCard(this.cardToRemove);
            if (this.getRandomCard()!=null){
                this.cardToRemove = this.getRandomCard();
                this.anyCard = true;
            }
            else {
                this.anyCard = false;
            }
            this.NoMoreWaterChance += 10;
            registerPhase("drink3",(new TextPhase(DESCRIPTIONS[3]))
                    .addOption(new TextPhase.OptionInfo(OPTIONS[5] + this.healAmount + OPTIONS[7] + this.cardToRemove.name+"。 有"+this.NoMoreWaterChance +"％的可能喝完。" ,this.cardToRemove).enabledCondition(()->this.anyCard,OPTIONS[8]), this::drink3));
            transitionKey("drink3");
        }
        else {
            AbstractDungeon.player.heal(this.healAmount,true);
            AbstractDungeon.effectList.add(new PurgeCardEffect(this.cardToRemove));
            AbstractDungeon.player.masterDeck.removeCard(this.cardToRemove);
            if (this.getRandomCard()!=null){
                this.cardToRemove = this.getRandomCard();
                this.anyCard = true;
            }
            else {
                this.anyCard = false;
            }
            transitionKey("leave2");
        }
    }
    private void leave1(Integer integer){
        transitionKey("leave1");
    }

    private AbstractCard getRandomCard() {
        ArrayList<AbstractCard> list = new ArrayList();

        for(AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.cardID != AscendersBane.ID && c.cardID != CurseOfTheBell.ID && c.cardID != Necronomicurse.ID)
                list.add(c);
        }

        if (list.isEmpty()) {
            return null;
        } else {
            Collections.shuffle(list, new Random(AbstractDungeon.miscRng.randomLong()));
            return (AbstractCard)list.get(0);
        }
    }
}
