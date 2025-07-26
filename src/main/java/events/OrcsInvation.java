package events;

import actions.PlaySoundAction;
import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.CombatPhase;
import basemod.abstracts.events.phases.TextPhase;
import cards.FrzMudSwallow;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.curses.Shame;
import com.megacrit.cardcrawl.cards.green.Dash;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import patches.CardTagsEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class OrcsInvation extends PhasedEvent {
    public static final String ID = "OrcsInvation";
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    public static final String NAME = eventStrings.NAME;
    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    public static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String title = eventStrings.NAME;
    private static final String IMG_PATH = "img/event_Ninja/OrcsInvation.png";
    private boolean anyNinjutsu = false;
    private AbstractCard ninjutsu = getNinjutsu();
    private String ninjutsuName;
    private float playerGold;
    private int healAmt;

    public OrcsInvation() {
        super(ID, title, IMG_PATH);
        if (this.getNinjutsu()!=null){
            this.anyNinjutsu = true;
            this.ninjutsuName = this.ninjutsu.name;
        }
        else {
            this.anyNinjutsu = false;
            this.ninjutsuName = "NOTHING";
        }
        if (player.gold>0){
            this.playerGold = player.gold*0.35F;
        }
        this.healAmt = (int)((float)AbstractDungeon.player.maxHealth * 0.25F);
        registerPhase("start", (new TextPhase(DESCRIPTIONS[0]))
                .addOption(new TextPhase.OptionInfo(OPTIONS[0]+FontHelper.colorString(this.ninjutsuName,"r"),this.ninjutsu).enabledCondition(() -> this.anyNinjutsu,OPTIONS[4]), this::ninjutsu)
                .addOption(new TextPhase.OptionInfo(OPTIONS[1]+MathUtils.round(this.hurt())+" #r点伤害。 "+" #g获得 #g冲刺。",new Dash()), this::hurt)
                .addOption(new TextPhase.OptionInfo(OPTIONS[2]+MathUtils.round(this.playerGold)+" #r金币。 #g恢复" + MathUtils.round(this.healAmt)+"点生命值").enabledCondition(()->player.gold>0,OPTIONS[5]), this::surround));
        registerPhase("ninjutsu", (new TextPhase(DESCRIPTIONS[1]))
                .addOption(OPTIONS[3], integer -> openMap()));
        registerPhase("hurt", (new TextPhase(DESCRIPTIONS[2]))
                .addOption(OPTIONS[3], integer -> openMap()));
        registerPhase("surround",(new TextPhase(DESCRIPTIONS[3]))
                .addOption(OPTIONS[3], integer -> openMap()));
        CardCrawlGame.sound.play("OrcsInvation");
        transitionKey("start");
    }

    private void ninjutsu(Integer integer) {
        CardCrawlGame.sound.play("Pick");
        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
        AbstractDungeon.effectList.add(new PurgeCardEffect(this.ninjutsu));
        AbstractDungeon.player.masterDeck.removeCard(this.ninjutsu);
        transitionKey("ninjutsu");
    }

    private void hurt(Integer integer) {
        CardCrawlGame.sound.play("BLUNT_HEAVY");
        CardCrawlGame.sound.play("OldStep");
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Dash(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
        player.damage(new DamageInfo(null, MathUtils.round(this.hurt())));
        transitionKey("hurt");
    }

    private void surround(Integer integer) {
        CardCrawlGame.sound.play("XiangPiaoPiao");
        player.loseGold(MathUtils.round(this.playerGold));
        player.heal(this.healAmt);
        transitionKey("surround");
    }

    public AbstractCard getNinjutsu(){
        List<AbstractCard> ninjutsuCards = new ArrayList<>();
        //获取卡组里所有忍术牌
        for (AbstractCard card : player.masterDeck.group) {
            if (card.hasTag(CardTagsEnum.NINJUTSU)) {
                ninjutsuCards.add(card);
            }
        }
        if (ninjutsuCards.isEmpty()) {
            return null;
        }
        //随机选取一张忍术牌
        else {
            Random random = new Random();
            int randomIndex = random.nextInt(ninjutsuCards.size());
            return ninjutsuCards.get(randomIndex);
        }
    }

    public boolean anyNinjutsu(){
        if (this.getNinjutsu()!=null){
            return true;
        }
        else {
            return false;
        }
    }

    public float hurt(){
        float hurtNumber;
        int maxLost;
        if (AbstractDungeon.ascensionLevel >= 15) {
            maxLost = 16;
            if (player.maxHealth*0.20F <=maxLost){
                hurtNumber = player.maxHealth*0.20F;
            }
            else {
                hurtNumber = maxLost;
            }
        }
        else {
            maxLost = 12;
            if (player.maxHealth*0.10F <=maxLost){
                hurtNumber = player.maxHealth*0.10F;
            }
            else {
                hurtNumber = maxLost;
            }
        }


        return hurtNumber;
    }
}
