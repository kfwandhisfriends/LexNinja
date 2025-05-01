package characters;

import basemod.abstracts.CustomPlayer;
import cards.Defend_Ninja;
import cards.ShakeShakeHands;
import cards.Strike_Ninja;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.beyond.SpireHeart;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.SlaversCollar;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.ui.MultiPageFtue;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import patches.ThmodClassEnum;
import patches.AbstractCardEnum;
import powers.LexKela;
import powers.PainPower;

import java.util.ArrayList;

public class ninja extends CustomPlayer {
    //初始能量
    private static final int ENERGY_PER_TURN = 3;
    //以下图片依次作用为[篝火休息处的角色背影2，篝火休息处的角色背影1，角色死亡后倒下的图片，角色平常站立时的图片]
    private static final String NINJA_SHOULDER_2 = "img/char_Ninja/shoulder2.png";
    private static final String NINJA_SHOULDER_1 = "img/char_Ninja/shoulder1.png";
    private static final String NINJA_CORPSE = "img/char_Ninja/fallen.png";
    private static final String NINJA_STAND = "img/char_Ninja/Ninja.png";
    //各种素材，不是很懂
    private static final String[] ORB_TEXTURES = new String[] { "img/UI_Ninja/EPanel/layer5.png", "img/UI_Ninja/EPanel/layer4.png", "img/UI_Ninja/EPanel/layer3.png", "img/UI_Ninja/EPanel/layer2.png", "img/UI_Ninja/EPanel/layer1.png","img/UI_Ninja/EPanel/layer0.png", "img/UI_Ninja/EPanel/layer5d.png", "img/UI_Ninja/EPanel/layer4d.png", "img/UI_Ninja/EPanel/layer3d.png", "img/UI_Ninja/EPanel/layer2d.png", "img/UI_Ninja/EPanel/layer1d.png" };
    private static final String ORB_VFX = "img/UI_Ninja/energyBlueVFX.png";
    private static final float[] LAYER_SPEED = new float[] { -40.0F, -32.0F, 20.0F, -20.0F, 0.0F, -10.0F, -8.0F, 5.0F, -5.0F, 0.0F };
    //初始生命，最大生命，初始金币,初始的充能球栏位（机器人）,最后一个应该是进阶14时的最大生命值下降
    private static final int STARTING_HP = 68;
    private static final int MAX_HP = 68;
    private static final int STARTING_GOLD = 99;
    private static final int HAND_SIZE = 0;
    private static final int ASCENSION_MAX_HP_LOSS = 5;
    //返回一个颜色
    public static final Color SILVER = CardHelper.getColor(200, 200, 200);

    public ninja(String name) {
        //构造方法，初始化参数
        super(name, ThmodClassEnum.Ninja_CLASS, ORB_TEXTURES, ORB_VFX, LAYER_SPEED, (String)null, (String)null);
        this.dialogX = this.drawX + 0.0F * Settings.scale;
        this.dialogY = this.drawY + 220.0F * Settings.scale;
        initializeClass(NINJA_STAND, NINJA_SHOULDER_2, NINJA_SHOULDER_1, NINJA_CORPSE,
                getLoadout(),
                0.0F, 5.0F, 240.0F, 300.0F,
                new EnergyManager(ENERGY_PER_TURN));
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        //添加初始卡组
        ArrayList<String> retVal = new ArrayList<>();
        for(int x=0; x<4 ; x++) {
            retVal.add("Strike_Ninja");
        }
        for(int x=0; x<4 ; x++) {
            retVal.add("Defend_Ninja");
        }
        retVal.add("ShakeShakeHands");
        retVal.add("YiCut");
        return retVal;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        //添加初始遗物
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add("LotusBox");
        UnlockTracker.markRelicAsSeen("LotusBox");
        return retVal;
    }

    @Override
    public CharSelectInfo getLoadout() {
        //选英雄界面的文字描述
        String title="";
        String flavor="";
        if (Settings.language == Settings.GameLanguage.ZHS) {
            title = "蕾忍";
            flavor = "同古神哈姆战斗至最后一刻启动了伟大封印的忍者 NL 苏醒之时已来到了塔底......";
        } else if (Settings.language == Settings.GameLanguage.ZHT) {
            //当设定为中国台湾省，title和flavor为繁体描述
        } else {
            //其他用英文替代
        }

        return new CharSelectInfo(title, flavor, STARTING_HP, MAX_HP,HAND_SIZE , STARTING_GOLD, ASCENSION_MAX_HP_LOSS, this, getStartingRelics(), getStartingDeck(), false);
    }



    @Override
    public String getTitle(PlayerClass playerClass) {
        //应该是进游戏后左上角的角色名
        String title="";
        if (Settings.language == Settings.GameLanguage.ZHS) {
            title = "蕾忍";
        } else if (Settings.language == Settings.GameLanguage.ZHT) {
            title = "蕾忍";
        } else {
            title = "Ninja";
        }

        return title;
    }

    @Override

    public AbstractCard.CardColor getCardColor() {
        //选择卡牌颜色
        return AbstractCardEnum.Ninja_COLOR;
    }

    @Override
    public Color getCardRenderColor() {
        return SILVER;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new ShakeShakeHands();
    }

    @Override
    public Color getCardTrailColor() {
        return SILVER;
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return ASCENSION_MAX_HP_LOSS;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontBlue;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
        CardCrawlGame.sound.play("Pick");
    }
    public void updateOrb(int orbCount) {
        this.energyOrb.updateOrb(orbCount);
    }
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return null;
    }

    @Override
    public String getLocalizedCharacterName() {
        String char_name;
        if (Settings.language == Settings.GameLanguage.ZHS) {
            char_name = "蕾忍";
        } else if (Settings.language == Settings.GameLanguage.ZHT) {
            char_name = "蕾忍";
        } else {
            char_name = "Ninja";
        }
        return char_name;
    }


    @Override
    public AbstractPlayer newInstance() {
        return (AbstractPlayer)new ninja(this.name);
    }

    @Override
    public String getSpireHeartText() {
        return "你将你的蕾克拉聚集到极限......";
    }

    @Override
    public Color getSlashAttackColor() {
        return SILVER;
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[] {AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.BLUNT_HEAVY, AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.LIGHTNING, AbstractGameAction.AttackEffect.BLUNT_HEAVY };
    }



    @Override
    public String getVampireText() {

        return "加入我们吧，忍者......";
    }
    public void applyEndOfTurnTriggers() {
        super.applyEndOfTurnTriggers();
    }

    @Override
    public ArrayList<CutscenePanel> getCutscenePanels(){
        ArrayList<CutscenePanel> panels = new ArrayList<>();

        panels.add(new NinjaPanel("img/char_Ninja/Victory1.png", "TakeYourHeart"));
        panels.add(new NinjaPanel("img/char_Ninja/Victory2.png","DarkSoulCut"));
        panels.add(new NinjaPanel("img/char_Ninja/Victory3.png","Happy~"));

        return panels;
    }



    @Override
    public void preBattlePrep(){

        if (!(Boolean) TipTracker.tips.get("COMBAT_TIP")) {
            AbstractDungeon.ftue = new MultiPageFtue();
            TipTracker.neverShowAgain("COMBAT_TIP");
        }

        AbstractDungeon.actionManager.clear();
        this.damagedThisCombat = 0;
        this.cardsPlayedThisTurn = 0;
        this.maxOrbs = 0;
        this.orbs.clear();
        this.increaseMaxOrbSlots(this.masterMaxOrbs, false);
        this.isBloodied = this.currentHealth <= this.maxHealth / 2;
        poisonKillCount = 0;
        GameActionManager.playerHpLastTurn = this.currentHealth;
        this.endTurnQueued = false;
        this.gameHandSize = this.masterHandSize;
        this.isDraggingCard = false;
        this.isHoveringDropZone = false;
        this.hoveredCard = null;
        this.cardInUse = null;
        this.drawPile.initializeDeck(this.masterDeck);
        AbstractDungeon.overlayMenu.endTurnButton.enabled = false;
        this.hand.clear();
        this.discardPile.clear();
        this.exhaustPile.clear();
        if (AbstractDungeon.player.hasRelic("SlaversCollar")) {
            ((SlaversCollar)AbstractDungeon.player.getRelic("SlaversCollar")).beforeEnergyPrep();
        }

        this.energy.prep();
        this.powers.clear();
        this.isEndingTurn = false;
        this.healthBarUpdatedEvent();
        if (ModHelper.isModEnabled("Lethality")) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 3), 3));
        }

        if (ModHelper.isModEnabled("Terminal")) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new PlatedArmorPower(this, 5), 5));
        }

        AbstractDungeon.getCurrRoom().monsters.usePreBattleAction();
        if (Settings.isFinalActAvailable && AbstractDungeon.getCurrMapNode().hasEmeraldKey) {
            AbstractDungeon.getCurrRoom().applyEmeraldEliteBuff();
        }

        AbstractDungeon.actionManager.addToTop(new WaitAction(1.0F));
        this.applyPreCombatLogic();

        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new PainPower(AbstractDungeon.player))
        );

        CardCrawlGame.sound.play("Painful");
    }

}