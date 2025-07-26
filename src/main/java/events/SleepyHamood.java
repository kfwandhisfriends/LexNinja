package events;

import basemod.BaseMod;
import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.TextPhase;
import cards.special.KillAll;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.backends.lwjgl.audio.Mp3;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.colorless.DeepBreath;
import com.megacrit.cardcrawl.cards.curses.Doubt;
import com.megacrit.cardcrawl.cards.curses.Regret;
import com.megacrit.cardcrawl.cards.green.Dash;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import java.awt.event.PaintEvent;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class SleepyHamood extends PhasedEvent {
    public static final String ID = "SleepyHamood";
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    public static final String NAME = eventStrings.NAME;
    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    public static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String title = eventStrings.NAME;
    private static final String IMG_PATH = "img/event_Ninja/SleepyHamood01.png";
    private AbstractCard db;
    private Music customBGM;
    public SleepyHamood(){
        super(ID, title, IMG_PATH);
        CardCrawlGame.music.silenceBGMInstantly();

        customBGM = Gdx.audio.newMusic(Gdx.files.internal("audio/Sleep,sleep.mp3"));
        customBGM.setLooping(true);
        customBGM.setVolume(Settings.MUSIC_VOLUME * Settings.MASTER_VOLUME);
        customBGM.play();

        CardCrawlGame.sound.play("Hamood");
        this.db= new DeepBreath();
        this.db.upgrade();
        registerPhase("start",new TextPhase(DESCRIPTIONS[0])
                .addOption(new TextPhase.OptionInfo(OPTIONS[5]),this::Start));
        registerPhase("start1",new TextPhase(DESCRIPTIONS[8])
                .addOption(new TextPhase.OptionInfo(OPTIONS[0]+MathUtils.round(this.hurt())+OPTIONS[4],this.db),this::Threat)
                .addOption(new TextPhase.OptionInfo(OPTIONS[1],new Doubt()),this::Help)
                .addOption(new TextPhase.OptionInfo(OPTIONS[2],new KillAll()),this::Leave0));
        registerPhase("threat",new TextPhase(DESCRIPTIONS[1])
                .addOption(new TextPhase.OptionInfo(OPTIONS[5]),this::Threat1));
        registerPhase("help",new TextPhase(DESCRIPTIONS[2])
                .addOption(new TextPhase.OptionInfo(OPTIONS[5]),this::Help1));
        registerPhase("killall",new TextPhase(DESCRIPTIONS[3])
                .addOption(OPTIONS[3],integer -> openMap()));

        registerPhase("threat1",new TextPhase(DESCRIPTIONS[4])
                .addOption(new TextPhase.OptionInfo(OPTIONS[5]),this::Threat2));
        registerPhase("threat2",new TextPhase(DESCRIPTIONS[5])
                .addOption(new TextPhase.OptionInfo(OPTIONS[5]),this::Threat3));
        registerPhase("threat3",new TextPhase(DESCRIPTIONS[6])
                .addOption(OPTIONS[3],integer -> openMap()));

        registerPhase("help1",new TextPhase(DESCRIPTIONS[7])
                .addOption(OPTIONS[3],integer -> openMap()));

        transitionKey("start");
    }

    public void Start(Integer integer){
        transitionKey("start1");
    }
    public void Threat(Integer integer){
        CardCrawlGame.sound.play("KillYourGrandpa");
        transitionKey("threat");
    }

    public void Threat1(Integer integer){
        CardCrawlGame.sound.play("HamoodKick");
        CardCrawlGame.sound.play("BLUNT_HEAVY");
        player.damage(new DamageInfo(null, MathUtils.round(this.hurt())));
        transitionKey("threat1");
    }

    public void Threat2(Integer integer){
        CardCrawlGame.sound.play("ATTACK_HEAVY");
        CardCrawlGame.sound.play("KillHamood");
        transitionKey("threat2");
    }

    public void Threat3(Integer integer){
        CardCrawlGame.sound.play("SleepyHamood");
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(this.db, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
        transitionKey("threat3");
    }
    public void Help(Integer integer){
        CardCrawlGame.sound.play("YouWillBeFine");
        AbstractDungeon.player.heal(AbstractDungeon.player.maxHealth);
        transitionKey("help");
    }

    public void Help1(Integer integer){
        CardCrawlGame.sound.play("IRegret");
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Doubt(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
        transitionKey("help1");
    }
    public void Leave0(Integer integer){
        CardCrawlGame.sound.play("KillAll");
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new KillAll(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
        transitionKey("killall");
    }

    public float hurt(){
        float hurtNumber;
        if (AbstractDungeon.ascensionLevel >= 15) {
                hurtNumber = player.maxHealth*0.25F;
            }
        else {
                hurtNumber = player.maxHealth*0.15F;
        }


        return hurtNumber;
    }
    @Override
    public void dispose() {
        // 清理时停止音乐
        if (customBGM != null) {
            customBGM.stop();
            customBGM.dispose();
        }
        this.imageEventText.clear();
        this.roomEventText.clear();
    }

}
