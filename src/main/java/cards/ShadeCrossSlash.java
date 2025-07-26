package cards;

import actions.NinjutsuAction;
import actions.NinjutsuActionBot;
import actions.ShadeCrossSlashAction;
import basemod.abstracts.CustomCard;
import basemod.helpers.BaseModCardTags;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.ShakeScreenAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.DieDieDieEffect;
import com.megacrit.cardcrawl.vfx.combat.GrandFinalEffect;
import patches.AbstractCardEnum;
import demoMod.ninjaMod;
import patches.CardTagsEnum;
import powers.AmoXilin;
import powers.LexKela;
import powers.ScarePower;

public class ShadeCrossSlash extends CustomCard {
    //从.json文件中提取键名为ShadeCrossSlash的信息
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("ShadeCrossSlash");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Ninja/ShadeCrossSlash.png";
    private static final int COST = 1;
    private static final int ATTACK_DMG = 6;
    private static final int UPGRADE_PLUS_DMG = 2;
    public static final String ID = "ShadeCrossSlash";

    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public ShadeCrossSlash() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, AbstractCardEnum.Ninja_COLOR, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        this.baseDamage = ATTACK_DMG;
        this.isMultiDamage = true;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(CardTagsEnum.BLADE);
        this.tags.add(CardTagsEnum.NINJUTSU);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {


        AbstractPower lexPower = p.getPower("LexKela");
        AbstractPower dimdeadtree = p.getPower("DimDeadTreePower");
        AbstractPower amoxilin = p.getPower(AmoXilin.POWER_ID);
        int ninjutsuKela = 2;

        AbstractPower scare = p.getPower("ScarePower");
        if (scare != null && scare.amount > 0) {
            //受惊形态不消耗蕾克拉初尝试
            CardCrawlGame.sound.play("ShadeCrossSlash");

            this.addToBot(new VFXAction(new BorderLongFlashEffect(Color.LIGHT_GRAY)));
            if (Settings.FAST_MODE) {
                this.addToBot(new VFXAction(new GrandFinalEffect(), 0.7F));
            } else {
                this.addToBot(new VFXAction(new GrandFinalEffect(), 1.0F));
            }
            this.addToBot(new VFXAction(p, new CleaveEffect(), 0.1F));
            this.addToBot(new ShakeScreenAction(0.1F, ScreenShake.ShakeDur.MED, ScreenShake.ShakeIntensity.HIGH));
            this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HEAVY));
            this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            this.addToTop(new DrawCardAction(this.magicNumber));

            this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ScarePower(p, 0, -1), -1));
        } else {
            if (dimdeadtree != null) {


                CardCrawlGame.sound.play("ShadeCrossSlash");
                this.addToBot(new VFXAction(new BorderLongFlashEffect(Color.LIGHT_GRAY)));
                if (Settings.FAST_MODE) {
                    this.addToBot(new VFXAction(new GrandFinalEffect(), 0.7F));
                } else {
                    this.addToBot(new VFXAction(new GrandFinalEffect(), 1.0F));
                }
                this.addToBot(new VFXAction(p, new CleaveEffect(), 0.1F));
                this.addToBot(new ShakeScreenAction(0.1F, ScreenShake.ShakeDur.MED, ScreenShake.ShakeIntensity.HIGH));
                this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HEAVY));
                this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                this.addToTop(new DrawCardAction(this.magicNumber));


            } else {
                if (amoxilin!=null) {
                    ninjutsuKela-=amoxilin.amount;
                }
                if (ninjutsuKela<=0){
                    ninjutsuKela = 0;
                }
                if (lexPower != null && lexPower.amount >= ninjutsuKela || ninjutsuKela == 0) {
                    CardCrawlGame.sound.play("ShadeCrossSlash");
                    this.addToBot(new VFXAction(new BorderLongFlashEffect(Color.LIGHT_GRAY)));
                    if (Settings.FAST_MODE) {
                        this.addToBot(new VFXAction(new GrandFinalEffect(), 0.7F));
                    } else {
                        this.addToBot(new VFXAction(new GrandFinalEffect(), 1.0F));
                    }
                    this.addToBot(new VFXAction(p, new CleaveEffect(), 0.1F));
                    this.addToBot(new ShakeScreenAction(0.1F, ScreenShake.ShakeDur.MED, ScreenShake.ShakeIntensity.HIGH));
                    this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HEAVY));
                    this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                    this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                    this.addToTop(new DrawCardAction(this.magicNumber));
                    this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LexKela(AbstractDungeon.player, -ninjutsuKela)));
                } else {
                    this.addToBot(new VFXAction(p, new CleaveEffect(), 0.1F));
                    this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HEAVY));

                }

            }

        }
        }

        public AbstractCard makeCopy () {
            return new ShadeCrossSlash();
        }
    }
