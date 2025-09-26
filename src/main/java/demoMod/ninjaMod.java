package demoMod;

import basemod.BaseMod;
import basemod.eventUtil.AddEventParams;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import cards.*;
import cards.foods.*;
import cards.special.*;
import characters.ninja;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import events.*;
import patches.AbstractCardEnum;
import patches.ThmodClassEnum;
import potions.LexKelaPotion;
import potions.AmoXilin;
import potions.NH42SO4;
import relics.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;

import static com.megacrit.cardcrawl.core.Settings.language;

@SpireInitializer
public class ninjaMod implements PotionGetSubscriber,RelicGetSubscriber, PostPowerApplySubscriber, AddAudioSubscriber,PostExhaustSubscriber, PostBattleSubscriber, PostDungeonInitializeSubscriber, EditCharactersSubscriber, PostInitializeSubscriber, EditRelicsSubscriber, EditCardsSubscriber, EditStringsSubscriber, OnCardUseSubscriber, EditKeywordsSubscriber, OnPowersModifiedSubscriber, PostDrawSubscriber, PostEnergyRechargeSubscriber{
    private static final String MOD_BADGE = "img/UI_Ninja/badge.png";
    //攻击、技能、能力牌的图片(512)
    private static final String ATTACK_CC = "img/512/bg_attack_512.png";
    private static final String SKILL_CC = "img/512/bg_skill_512.png";
    private static final String POWER_CC = "img/512/bg_power_512.png";
    private static final String ENERGY_ORB_CC = "img/512/NINJAOrb.png";
    //攻击、技能、能力牌的图片(1024)
    private static final String ATTACK_CC_PORTRAIT = "img/1024/bg_attack.png";
    private static final String SKILL_CC_PORTRAIT = "img/1024/bg_skill.png";
    private static final String POWER_CC_PORTRAIT = "img/1024/bg_power.png";
    private static final String ENERGY_ORB_CC_PORTRAIT = "img/1024/NINJAOrb.png";
    public static final String CARD_ENERGY_ORB = "img/UI_Ninja/energyOrb.png";
    //选英雄界面的角色图标、选英雄时的背景图片
    private static final String MY_CHARACTER_BUTTON = "img/charSelect/NinjaButton.png";
    private static final String MARISA_PORTRAIT = "img/charSelect/NinjaPortrait.png";
    public static final Color SILVER = CardHelper.getColor(200, 200, 200);
    private ArrayList<AbstractCard> cardsToAdd = new ArrayList<>();
    public static ArrayList<AbstractCard> recyclecards = new ArrayList<>();



    public ninjaMod() {
        //构造方法，初始化各种参数
        BaseMod.subscribe((ISubscriber)this);
        BaseMod.addColor(AbstractCardEnum.Ninja_COLOR, SILVER, SILVER, SILVER, SILVER, SILVER, SILVER, SILVER, ATTACK_CC, SKILL_CC, POWER_CC, ENERGY_ORB_CC, ATTACK_CC_PORTRAIT, SKILL_CC_PORTRAIT,POWER_CC_PORTRAIT, ENERGY_ORB_CC_PORTRAIT, CARD_ENERGY_ORB);
    }

    @Override
    public void receiveEditCharacters() {
        //添加角色到MOD中
        BaseMod.addCharacter((AbstractPlayer)new ninja("Ninja"), MY_CHARACTER_BUTTON, MARISA_PORTRAIT, ThmodClassEnum.Ninja_CLASS);
    }
    //初始化整个MOD,一定不能删
    public static void initialize() {
        new ninjaMod();
    }

    @Override
    public void receiveEditCards() {
        //将卡牌批量添加
        loadCardsToAdd();
        Iterator<AbstractCard> var1 = this.cardsToAdd.iterator();
        while (var1.hasNext()) {
            AbstractCard card = var1.next();
            BaseMod.addCard(card);
        }
    }

    @Override
    public void receivePostExhaust(AbstractCard c) {}

    @Override
    public void receivePostPowerApplySubscriber(AbstractPower pow, AbstractCreature target, AbstractCreature owner) {

    }


    @Override
    public void receivePowersModified() {}


    @Override
    public void receivePostDungeonInitialize() {}


    @Override
    public void receivePostDraw(AbstractCard arg0) {}

    private static String loadJson(String jsonPath) {
        return Gdx.files.internal(jsonPath).readString(String.valueOf(StandardCharsets.UTF_8));
    }

    @Override
    public void receiveEditStrings() {
        //读取遗物，卡牌，能力，药水，事件的JSON文本

        String relic="", card="", power="", potion="", event="";
        if (language == Settings.GameLanguage.ZHS || language == Settings.GameLanguage.ZHT) {
            card = "localization/Ninja_cards-zh.json";
            relic = "localization/Ninja_relics-zh.json";
            power = "localization/Ninja_powers-zh.json";
            potion = "localization/Ninja_potions-zh.json";
            event = "localization/Ninja_events-zh.json";
        } else {
            //其他语言配置的JSON
            card = "localization/Ninja_cards-zh.json";
            relic = "localization/Ninja_relics-zh.json";
            power = "localization/Ninja_powers-zh.json";
            potion = "localization/Ninja_potions-zh.json";
            event = "localization/Ninja_events-zh.json";
        }

        String relicStrings = Gdx.files.internal(relic).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
        String cardStrings = Gdx.files.internal(card).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(CardStrings.class, cardStrings);
        String powerStrings = Gdx.files.internal(power).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);
        String potionStrings = Gdx.files.internal(potion).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(PotionStrings.class, potionStrings);
        String eventStrings = Gdx.files.internal(event).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(EventStrings.class, eventStrings);
    }


    //添加一度


    @Override
    public void receiveRelicGet(AbstractRelic relic) {
        //移除遗物,这里移除了小屋子，太垃圾了。

        if (AbstractDungeon.player.name == "Ninja") {
            AbstractDungeon.shopRelicPool.remove("");
        }
    }

    @Override
    public void receiveCardUsed(AbstractCard abstractCard) {

    }

    @Override
    public void receivePostBattle(AbstractRoom r) {

    }

    @Override
    public void receivePostInitialize() {
        BaseMod.addEvent((new AddEventParams.Builder(HeBullyMe.ID, HeBullyMe.class))
                .bonusCondition(() -> (AbstractDungeon.floorNum >= 9)).dungeonIDs("Exordium").endsWithRewardsUI(true).create());
        BaseMod.addEvent(ManyuDian.ID,ManyuDian.class,"TheCity");
        BaseMod.addEvent(OrcsInvation.ID, OrcsInvation.class,"TheCity");
        BaseMod.addEvent(SleepyHamood.ID, SleepyHamood.class,"TheBeyond");
        BaseMod.addEvent(Thirsty.ID,Thirsty.class);
    }

    @Override
    public void receivePostEnergyRecharge() {
        Iterator<AbstractCard> var1 = recyclecards.iterator();

        while (var1.hasNext()) {
            AbstractCard c = var1.next();
            AbstractCard card = c.makeStatEquivalentCopy();
            AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(card, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, false, true, true));
        }
        recyclecards.clear();
    }



    @Override
    public void receiveEditRelics() {
        //将自定义的遗物添加到这里
        BaseMod.addRelicToCustomPool((AbstractRelic)new LotusBox(),AbstractCardEnum.Ninja_COLOR);
        BaseMod.addRelic(new Sarira(), RelicType.SHARED);
        BaseMod.addRelicToCustomPool(new XiangPiaoPiao(),AbstractCardEnum.Ninja_COLOR);
        BaseMod.addRelicToCustomPool(new Salt(),AbstractCardEnum.Ninja_COLOR);
        BaseMod.addRelicToCustomPool(new DimDeadTree(),AbstractCardEnum.Ninja_COLOR);
        BaseMod.addRelicToCustomPool(new LexScroll(),AbstractCardEnum.Ninja_COLOR);
        BaseMod.addRelicToCustomPool(new SpyPeaShooter(),AbstractCardEnum.Ninja_COLOR);
        BaseMod.addRelicToCustomPool(new MachineNinja(),AbstractCardEnum.Ninja_COLOR);
        BaseMod.addRelicToCustomPool(new ThreeDuuz(),AbstractCardEnum.Ninja_COLOR);
        BaseMod.addRelicToCustomPool(new KFC(),AbstractCardEnum.Ninja_COLOR);
        BaseMod.addRelic(new ToiletWater(),RelicType.SHARED);
    }

    //添加音效
    public void receiveAddAudio() {
        BaseMod.addAudio("KillFor500","audio/KillFor500.mp3");
        BaseMod.addAudio("MORE!!!","audio/MORE!!!.mp3");
        BaseMod.addAudio("MoreDrink","audio/MoreDrink.mp3");
        BaseMod.addAudio("Drink","audio/Drink.mp3");
        BaseMod.addAudio("Thirsty","audio/Thirsty.mp3");
        BaseMod.addAudio("Cry","audio/Cry.mp3");
        BaseMod.addAudio("Sleepy","audio/Sleep,sleep.mp3");
        BaseMod.addAudio("SleepyHamood","audio/SleepyHamood.mp3");
        BaseMod.addAudio("YouWillBeFine","audio/YouWillBeFine.mp3");
        BaseMod.addAudio("KillAll","audio/KillAll.mp3");
        BaseMod.addAudio("NoNoNo","audio/NoNoNo.mp3");
        BaseMod.addAudio("ManyuDian","audio/ManyuDian.mp3");
        BaseMod.addAudio("JustMortal","audio/JustMortal.mp3");
        BaseMod.addAudio("3Duuz","audio/3Duuz.mp3");
        BaseMod.addAudio("machine","audio/machine.mp3");
        BaseMod.addAudio("Salt","audio/Salt.mp3");
        BaseMod.addAudio("XiangPiaoPiao","audio/XiangPiaoPiao.mp3");
        BaseMod.addAudio("Pain","audio/Pain.mp3");
        BaseMod.addAudio("Painful","audio/Painful.mp3");
        BaseMod.addAudio("Pick","audio/pick.mp3");
        BaseMod.addAudio("ShakeShakeHands","audio/ShakeShakeHand.mp3");
        BaseMod.addAudio("LotusBox","audio/LotusBox.mp3");
        BaseMod.addAudio("ShadeCrossSlash","audio/ShadeCrossSlash.mp3");
        BaseMod.addAudio("OverBurningBlade", "audio/OverBurningBlade.mp3");
        BaseMod.addAudio("PlasmaHand","audio/PlasmaHand.mp3");
        BaseMod.addAudio("YiCut","audio/YiCut.mp3");
        BaseMod.addAudio("DragonPunch","audio/DragonPunch.mp3");
        BaseMod.addAudio("SharpenToKill","audio/SharpenToKill.mp3");
        BaseMod.addAudio("NamiYoyo","audio/NamiYoyo.mp3");
        BaseMod.addAudio("FrzMudSwallow","audio/FrzMudSwallow.mp3");
        BaseMod.addAudio("SandDefendHand","audio/SandDefendHand.mp3");
        BaseMod.addAudio("DragonSmog","audio/DragonSmog.mp3");
        BaseMod.addAudio("StoneStrong","audio/StoneStrong.mp3");
        BaseMod.addAudio("DarknessCrawl","audio/DarknessCrawl.mp3");
        BaseMod.addAudio("OiShoryuKen","audio/OiShoryuKen.mp3");
        BaseMod.addAudio("ManTooWeak","audio/ManTooWeak.mp3");
        BaseMod.addAudio("GetPeopleTax","audio/GetPeopleTax.mp3");
        BaseMod.addAudio("GodAndBuddha","audio/GodAndBuddha.mp3");
        BaseMod.addAudio("FlameThrower","audio/FlameThrower.mp3");
        BaseMod.addAudio("MambaMissile","audio/MambaMissile.mp3");
        BaseMod.addAudio("YeSuanMilk","audio/YeSuanMilk.mp3");
        BaseMod.addAudio("LeLiSu","audio/LeLiSu.mp3");
        BaseMod.addAudio("PenglaiCan","audio/PenglaiCan.mp3");
        BaseMod.addAudio("MengguBanana","audio/MengguBanana.mp3");
        BaseMod.addAudio("ColdCopper","audio/ColdCopper.mp3");
        BaseMod.addAudio("ICantImagine","audio/ICantImagine.mp3");
        BaseMod.addAudio("DarknessSnakeHand","audio/DarknessSnakeHand.mp3");
        BaseMod.addAudio("GoodNight","audio/GoodNight.mp3");
        BaseMod.addAudio("WaterSandStorm","audio/WaterSandStorm.mp3");
        BaseMod.addAudio("GonnaEatShit","audio/GonnaEatShit.mp3");
        BaseMod.addAudio("MonkHand","audio/MonkHand.mp3");
        BaseMod.addAudio("LeechFriend","audio/LeechFriend.mp3");
        BaseMod.addAudio("NuclearDragon","audio/NuclearDragon.mp3");
        BaseMod.addAudio("MaiNaMai","audio/MaiNaMai.mp3");
        BaseMod.addAudio("AnnaiMaster","audio/AnnaiMaster.mp3");
        BaseMod.addAudio("GetAllHands","audio/GetAllHands.mp3");
        BaseMod.addAudio("FourNightsLightning","audio/FourNightsLightning.mp3");
        BaseMod.addAudio("PowerJesus","audio/PowerJesus.mp3");
        BaseMod.addAudio("MummyMummy","audio/MummyMummy.mp3");
        BaseMod.addAudio("DisappointedAfterBocchi","audio/DisappointedAfterBocchi.mp3");
        BaseMod.addAudio("SariraRevive","audio/SariraRevive.mp3");
        BaseMod.addAudio("NoHand","audio/NoHand.mp3");
        BaseMod.addAudio("BeastShout","audio/BeastShout.mp3");
        BaseMod.addAudio("SnakeSwitch","audio/SnakeSwitch.mp3");
        BaseMod.addAudio("WePeace","audio/WePeace.mp3");
        BaseMod.addAudio("OhFuckFlash","audio/OhFuckFlash.mp3");
        BaseMod.addAudio("DeadBurningBladeSmog","audio/DeadBurningBladeSmog.mp3");
        BaseMod.addAudio("IgnisHealing","audio/IgnisHealing.mp3");
        BaseMod.addAudio("NamiLightning","audio/NamiLightning.mp3");
        BaseMod.addAudio("DarknessShoryuKen","audio/DarknessShoryuKen.mp3");
        BaseMod.addAudio("DeathGodBlade","audio/DeathGodBlade.mp3");
        BaseMod.addAudio("HuashanSmog","audio/HuashanSmog.mp3");
        BaseMod.addAudio("LanBladeCutting","audio/LanBladeCutting.mp3");
        BaseMod.addAudio("LanBlade","audio/LanBlade.mp3");
        BaseMod.addAudio("HeavenCross","audio/HeavenCross.mp3");
        BaseMod.addAudio("BuddhaHand","audio/BuddhaHand.mp3");
        BaseMod.addAudio("HolyLittleStorm","audio/HolyLittleStorm.mp3");
        BaseMod.addAudio("Man!","audio/Man!.mp3");
        BaseMod.addAudio("MambaOut","audio/MambaOut.mp3");
        BaseMod.addAudio("RadiationAnnihilation","audio/RadiationAnnihilation.mp3");
        BaseMod.addAudio("BlackZiCannon","audio/BlackZiCannon.mp3");
        BaseMod.addAudio("FrogFrogGo","audio/FrogFrogGo.mp3");
        BaseMod.addAudio("RacingAgainstMessi","audio/RacingAgainstMessi.mp3");
        BaseMod.addAudio("LearnNamiFlying","audio/LearnNamiFlying.mp3");
        BaseMod.addAudio("DarkSoulCut","audio/DarkSoulCut.mp3");
        BaseMod.addAudio("BuildSandWall","audio/BuildSandWall.mp3");
        BaseMod.addAudio("EclipseMistBlade","audio/EclipseMistBlade.mp3");
        BaseMod.addAudio("CometCorruptedStar","audio/CometCorruptedStar.mp3");
        BaseMod.addAudio("PastHasGoneHand","audio/PastHasGoneHand.mp3");
        BaseMod.addAudio("ShenWei","audio/ShenWei.mp3");
        BaseMod.addAudio("BackFourthHand","audio/BackFourthHand.mp3");
        BaseMod.addAudio("DimDeadTree","audio/DimDeadTree.mp3");
        BaseMod.addAudio("BlackDragonHand","audio/BlackDragonHand.mp3");
        BaseMod.addAudio("GoBackHands","audio/GoBackHands.mp3");
        BaseMod.addAudio("SpyPeaShooter","audio/SpyPeaShooter.mp3");
        BaseMod.addAudio("SheBuJin","audio/SheBuJin.mp3");
        BaseMod.addAudio("ShootOnThis","audio/ShootOnThis.mp3");
        BaseMod.addAudio("YEEART","audio/YEEART.mp3");
        BaseMod.addAudio("UBW","audio/UBW.mp3");
        BaseMod.addAudio("TakeYourHeart","audio/TakeYourHeart.mp3");
        BaseMod.addAudio("Turbine","audio/Turbine.mp3");
        BaseMod.addAudio("IRepresentShinobi","audio/IRepresentShinobi.mp3");
        BaseMod.addAudio("PlayHearthStone","audio/PlayHearthStone.mp3");
        BaseMod.addAudio("Hia","audio/Hia.mp3");
        BaseMod.addAudio("nandesu","audio/nandesu.mp3");
        BaseMod.addAudio("LexKelaPotion","audio/LexKelaPotion.mp3");
        BaseMod.addAudio("Happy~","audio/Happy~.mp3");
        BaseMod.addAudio("DarkSoulCut_Backup","audio/DarkSoulCut_Backup.mp3");
        BaseMod.addAudio("DarkSoulCut_2","audio/DarkSoulCut_2.mp3");
        BaseMod.addAudio("Die!Worm","audio/Die!Worm.ogg");
        BaseMod.addAudio("Running","audio/Running.mp3");
        BaseMod.addAudio("Crawl","audio/Crawl.mp3");
        BaseMod.addAudio("JRMummy","audio/JRMummy.mp3");
        BaseMod.addAudio("BeastVoice","audio/BeastVoice.mp3");
        BaseMod.addAudio("SandWall","audio/SandWall.mp3");
        BaseMod.addAudio("BigSandWall","audio/BigSandWall.mp3");
        BaseMod.addAudio("DeathHand","audio/DeathHand.mp3");
        BaseMod.addAudio("BladeDefence","audio/BladeDefence.mp3");
        BaseMod.addAudio("FlashSlash","audio/FlashSlash.wav");
        BaseMod.addAudio("SouthCrossSeal","audio/SouthCrossSeal.wav");
        BaseMod.addAudio("ShadowBlade","audio/ShadowBlade.mp3");
        BaseMod.addAudio("OhZombie","audio/OhZombie.mp3");
        BaseMod.addAudio("SpicyChicken","audio/SpicyChicken.mp3");
        BaseMod.addAudio("KFC","audio/KFC.mp3");
        BaseMod.addAudio("Lizhi","audio/Lizhi.mp3");
        BaseMod.addAudio("SaltyFish","audio/SaltyFish.mp3");
        BaseMod.addAudio("Water","audio/Water.mp3");
        BaseMod.addAudio("Flash","audio/Flash.mp3");
        BaseMod.addAudio("SoCool","audio/SoCool.mp3");
        BaseMod.addAudio("HeBullyMe","audio/HeBullyMe.mp3");
        BaseMod.addAudio("OldStep","audio/OldStep.mp3");
        BaseMod.addAudio("Mosquito","audio/Mosquito.mp3");
        BaseMod.addAudio("MosquitoHand","audio/MosquitoHand.mp3");
        BaseMod.addAudio("ToiletWater","audio/ToiletWater.mp3");
        BaseMod.addAudio("AmoXilin","audio/AmoXilin.mp3");
        BaseMod.addAudio("NH42SO4","audio/NH42SO4.mp3");
        BaseMod.addAudio("OrcsInvation","audio/OrcsInvation.mp3");
        BaseMod.addAudio("MyMoney","audio/MyMoney.mp3");
        BaseMod.addAudio("KillYourGrandpa","audio/KillYourGrandpa.mp3");
        BaseMod.addAudio("Hamood","audio/Hamood.mp3");
        BaseMod.addAudio("IRegret","audio/IRegret.mp3");
        BaseMod.addAudio("HamoodKick","audio/HamoodKick.mp3");
        BaseMod.addAudio("KillHamood","audio/KillHamood.mp3");
        BaseMod.addAudio("Kill!@#A%ll.mp3","audio/Kill!@#A%ll.mp3");
        BaseMod.addAudio("HengheShui","audio/HengheShui.mp3");
        BaseMod.addAudio("ASan","audio/ASan.mp3");
        BaseMod.addAudio("NanoExplosion","audio/NanoExplosion.mp3");
    }

    private void loadCardsToAdd() {
        //将自定义的卡牌添加到这里
        this.cardsToAdd.clear();
        this.cardsToAdd.add(new HengheShui());
        this.cardsToAdd.add(new KillAll());
        this.cardsToAdd.add(new MosquitoHand());
        this.cardsToAdd.add(new SaltyFish());
        this.cardsToAdd.add(new Lizhi());
        this.cardsToAdd.add(new SpicyChicken());
        this.cardsToAdd.add(new OhZombie());
        this.cardsToAdd.add(new ShadowBlade());
        this.cardsToAdd.add(new SouthCrossSeal());
        this.cardsToAdd.add(new FlashSlash());
        this.cardsToAdd.add(new BladeDefence());
        this.cardsToAdd.add(new DeathHand());
        this.cardsToAdd.add(new PlayHearthStone());
        this.cardsToAdd.add(new Turbine());
        this.cardsToAdd.add(new TakeYourHeart());
        this.cardsToAdd.add(new UBW());
        this.cardsToAdd.add(new SheBuJin());
        this.cardsToAdd.add(new IRepresentShinobi());
        this.cardsToAdd.add(new GoBackHands());
        this.cardsToAdd.add(new BlackDragonHand());
        this.cardsToAdd.add(new BackFourthHand());
        this.cardsToAdd.add(new ShenWei());
        this.cardsToAdd.add(new ScareForm());
        this.cardsToAdd.add(new PastHasGoneHand());
        this.cardsToAdd.add(new CometCorruptedStar());
        this.cardsToAdd.add(new EclipseMistBlade());
        this.cardsToAdd.add(new BuildSandWall());
        this.cardsToAdd.add(new DarkSoulCut());
        this.cardsToAdd.add(new LearnNamiFlying());
        this.cardsToAdd.add(new RacingAgainstMessi());
        this.cardsToAdd.add(new FrogFrogGo());
        this.cardsToAdd.add(new BlackZiCannon());
        this.cardsToAdd.add(new RadiationAnnihilation());
        this.cardsToAdd.add(new HolyLittleStorm());
        this.cardsToAdd.add(new BuddhaHand());
        this.cardsToAdd.add(new Strike_Ninja());
        this.cardsToAdd.add(new Defend_Ninja());
        this.cardsToAdd.add(new ShadeCrossSlash());
        this.cardsToAdd.add(new NamiYoyo());
        this.cardsToAdd.add(new SandDefendHand());
        this.cardsToAdd.add(new ShakeShakeHands());
        this.cardsToAdd.add(new GodAndBuddha());
        this.cardsToAdd.add(new YiCut());
        this.cardsToAdd.add(new OverBurningBlade());
        this.cardsToAdd.add(new PlasmaHand());
        this.cardsToAdd.add(new DragonPunch());
        this.cardsToAdd.add(new SharpenToKill());
        this.cardsToAdd.add(new FrzMudSwallow());
        this.cardsToAdd.add(new DragonSmog());
        this.cardsToAdd.add(new StoneStrong());
        this.cardsToAdd.add(new OiShoryuKen());
        this.cardsToAdd.add(new DarknessCrawl());
        this.cardsToAdd.add(new ManTooWeak());
        this.cardsToAdd.add(new GetPeopleTax());
        this.cardsToAdd.add(new FlameThrower());
        this.cardsToAdd.add(new MambaMissile());
        this.cardsToAdd.add(new YeSuanMilk());
        this.cardsToAdd.add(new LeLiSu());
        this.cardsToAdd.add(new PenglaiCan());
        this.cardsToAdd.add(new MengguBanana());
        this.cardsToAdd.add(new ColdCopper());
        this.cardsToAdd.add(new DarknessSnakeHand());
        this.cardsToAdd.add(new ICantImagine());
        this.cardsToAdd.add(new GoodNight());
        this.cardsToAdd.add(new WaterSandStorm());
        this.cardsToAdd.add(new GonnaEatShit());
        this.cardsToAdd.add(new MonkHand());
        this.cardsToAdd.add(new LeechFriend());
        this.cardsToAdd.add(new NuclearDragon());
        this.cardsToAdd.add(new AnnaiMaster());
        this.cardsToAdd.add(new GetAllHands());
        this.cardsToAdd.add(new FourNightsLightning());
        this.cardsToAdd.add(new NamiLightning());
        this.cardsToAdd.add(new PowerJesus());
        this.cardsToAdd.add(new MummyMummy());
        this.cardsToAdd.add(new DisappointedAfterBocchi());
        this.cardsToAdd.add(new SariraRevive());
        this.cardsToAdd.add(new NoHand());
        this.cardsToAdd.add(new BeastShout());
        this.cardsToAdd.add(new SnakeSwitch());
        this.cardsToAdd.add(new WePeace());
        this.cardsToAdd.add(new IgnisHealing());
        this.cardsToAdd.add(new OhFuckFlash());
        this.cardsToAdd.add(new DarknessShoryuKen());
        this.cardsToAdd.add(new DeathGodBlade());
        this.cardsToAdd.add(new LanBlade());
        this.cardsToAdd.add(new LanBladeCutting());
        this.cardsToAdd.add(new HeavenCross());
        this.cardsToAdd.add(new AngrySoAngry());
    }

    public void receiveEditKeywords() {
        BaseMod.addKeyword("ninjamod", "蕾克拉", new String[] { "蕾克拉" }, "释放 #y忍术 所必需的特殊的能量 ， 上限999层");
        BaseMod.addKeyword("ninjamod", "忍术X", new String[] { "忍术" }, "消耗X点 #y蕾克拉 发动额外效果 ， 默认为1点");
        BaseMod.addKeyword("ninjamod","手系忍术",new String[]{"手系忍术"},"手系忍术的流派");
        BaseMod.addKeyword("ninjamod","刀系忍术",new String[]{"刀系忍术"},"刀系忍术的流派（包括小刀）");
        BaseMod.addKeyword("ninjamod","科学忍具",new String[]{"科学忍具"}," #y蕾克拉 会减少 [E] 消耗，打出时减少1点 #y蕾克拉 ，会覆盖其他临时费用修改效果");
        BaseMod.addKeyword("ninjamod","砂壁",new String[]{"砂壁"},"回合开始时获得等于层数的格挡并减少一半的层数");
        BaseMod.addKeyword("ninjamod","豌豆射手",new String[]{"豌豆射手"},"你的回合结束时，造成2点伤害，次数等同于层数");
        BaseMod.addKeyword("ninjamod","僵尸",new String[]{"僵尸"},"常态没有效果，触发时，获得等同于数量三倍的格挡");
        BaseMod.addKeyword("ninjamod","死神火焰",new String[]{"死神火焰"},"你每打出一张牌，使其损失对应层数的生命值，消除 #y死亡律动 ");

        BaseMod.addKeyword("ninjamod", "Lexkra", new String[] { "lexkra" }, "Special energy for casting Ninjutsu");
        BaseMod.addKeyword("ninjamod", "NinjutsuX", new String[] { "ninjutsu" }, "Cost X Lexkra to give card extra effect, basically cost 1");
        BaseMod.addKeyword("ninjamod","Touchjutsu",new String[]{"touchjutsu"},"Style for Touchjutsu");
        BaseMod.addKeyword("ninjamod","Bladejutsu",new String[]{"bladejutsu"},"Style for Bladejutsu");
        BaseMod.addKeyword("ninjamod","KagakuNingu",new String[]{"kagaku_ningu"},"Amount of Lexkra will decrease [E] cost, cost 1 Lexkra when used. It will cover other decreasing effects on [E]");
        BaseMod.addKeyword("ninjamod","SandWall",new String[]{"sand_wall"},"Gain block equal to the amount of this power at start of the turn and decrease half of Sandwall");
        BaseMod.addKeyword("ninjamod","PeaShooter",new String[]{"peashooter"},"deal 2 damage at end of turn, deal times equal to this amount");
        BaseMod.addKeyword("ninjamod","HadesFlame",new String[]{"hades_flame"},"Whenever you use a card, deal hp loss equals to");
    }

    @Override
    public void receivePotionGet(AbstractPotion abstractPotion) {
        BaseMod.addPotion(LexKelaPotion.class , Color.BLACK, Color.GREEN,Color.CLEAR,"LexKelaPotion",ThmodClassEnum.Ninja_CLASS);
        BaseMod.addPotion(AmoXilin.class,Color.GREEN,Color.YELLOW,Color.CLEAR,"AmoXilin",ThmodClassEnum.Ninja_CLASS);
        BaseMod.addPotion(NH42SO4.class,Color.WHITE, Color.WHITE,Color.WHITE,"NH42SO4",ThmodClassEnum.Ninja_CLASS);
    }



}