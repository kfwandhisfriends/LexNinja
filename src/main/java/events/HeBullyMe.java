package events;

import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.CombatPhase;
import basemod.abstracts.events.phases.EventPhase;
import basemod.abstracts.events.phases.TextPhase;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Normality;
import com.megacrit.cardcrawl.cards.curses.Shame;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.FrozenEye;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import java.util.ArrayList;

public class HeBullyMe extends PhasedEvent {
    public static final String ID = "HeBullyMe";
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    public static final String NAME = eventStrings.NAME;
    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    public static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String title = eventStrings.NAME;
    private static final String IMG_PATH = "img/event_Ninja/HeBullyMe.png"; // 自定义事件图片

    public HeBullyMe() {
    super(ID, title, IMG_PATH);
        registerPhase("start", (new TextPhase(DESCRIPTIONS[0]))
                .addOption(OPTIONS[0], this::fight)
                .addOption(new TextPhase.OptionInfo(OPTIONS[1],new Shame()), this::leave));
        registerPhase("fight", (new CombatPhase("Gremlin Nob"))
                .addRewards(true,room -> room.addRelicToRewards(AbstractDungeon.returnRandomScreenlessRelic(AbstractDungeon.returnRandomRelicTier()))));
        registerPhase("leave", (new TextPhase(DESCRIPTIONS[1]))
                .addOption(OPTIONS[2], integer -> openMap()));
        CardCrawlGame.sound.play("HeBullyMe");
        transitionKey("start");
    }

    private void fight(Integer integer) {
        CardCrawlGame.sound.play("SoCool");
        AbstractRoom room = AbstractDungeon.getCurrRoom();
        room.rewards.add(new RewardItem(AbstractDungeon.miscRng.random(25, 35)));
        transitionKey("fight");
    }

    private void leave(Integer integer) {
        CardCrawlGame.sound.play("BLUNT_HEAVY");
        CardCrawlGame.sound.play("KillFor500");
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Shame(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
        AbstractDungeon.player.gainGold(200);
        AbstractDungeon.effectList.add(new RainingGoldEffect(200));
        transitionKey("leave");
    }


}
