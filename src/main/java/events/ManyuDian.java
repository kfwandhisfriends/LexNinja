package events;

import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.CombatPhase;
import basemod.abstracts.events.phases.TextPhase;
import basemod.devcommands.gold.Gold;
import cards.special.MosquitoHand;
import com.megacrit.cardcrawl.cards.curses.Shame;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import relics.ToiletWater;

import java.util.function.Supplier;

public class ManyuDian extends PhasedEvent {
    public static final String ID = "ManyuDian";
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    public static final String NAME = eventStrings.NAME;
    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    public static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String title = eventStrings.NAME;
    private boolean isGoldEnough = false;
    private static final String IMG_PATH = "img/event_Ninja/ManyuDian.png";
    private TextPhase.OptionInfo buy;

    public ManyuDian() {
        super(ID, title, IMG_PATH);
        if (AbstractDungeon.player.gold>=220){
            this.isGoldEnough = true;
        }
        CardCrawlGame.sound.play("ManyuDian");
        /*this.buy = new TextPhase.OptionInfo(OPTIONS[0],new ToiletWater());
        this.buy.enabledCondition(() -> Boolean.valueOf(this.isGoldEnough),OPTIONS[3]);*/
        registerPhase("start", (new TextPhase(DESCRIPTIONS[0]))
                .addOption(new TextPhase.OptionInfo(OPTIONS[0],new ToiletWater()).enabledCondition(() -> isGoldEnough,OPTIONS[4]), this::buy)
                .addOption(new TextPhase.OptionInfo(OPTIONS[1],new MosquitoHand()), this::leave)
                .addOption(new TextPhase.OptionInfo(OPTIONS[2]),this::fight));
        registerPhase("leave1", (new TextPhase(DESCRIPTIONS[1]))
                .addOption(OPTIONS[3], integer -> openMap()));
        registerPhase("leave2", (new TextPhase(DESCRIPTIONS[2]))
                .addOption(OPTIONS[3], integer1 -> openMap()));
        registerPhase("fight",(new CombatPhase("2 Thieves")
                .addRewards(true,room -> room.addRelicToRewards((AbstractRelic)new ToiletWater()))));
        transitionKey("start");
    }


    private void buy(Integer integer){
        CardCrawlGame.sound.play("ToiletWater");
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(((float) Settings.WIDTH / 2), ((float) Settings.HEIGHT / 2),new ToiletWater());
        AbstractDungeon.player.loseGold(220);
        transitionKey("leave1");
    }

    private void leave(Integer integer) {
        CardCrawlGame.sound.play("MosquitoHand");
        CardCrawlGame.sound.play("Mosquito");
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new MosquitoHand(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
        transitionKey("leave2");
    }

    private void fight(Integer integer){
        CardCrawlGame.sound.play("NoNoNo");
        AbstractRoom room = AbstractDungeon.getCurrRoom();
        room.rewards.add(new RewardItem(AbstractDungeon.miscRng.random(25, 35)));
        transitionKey("fight");
    }
}
