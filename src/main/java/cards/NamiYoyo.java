package cards;

import actions.NinjutsuAction;
import actions.ScienceAction;
import basemod.abstracts.CustomCard;
import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.CorpseExplosionPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import patches.AbstractCardEnum;
import patches.CardTagsEnum;
import powers.NanoExplosion;

public class NamiYoyo extends CustomCard{
    //从.json文件中提取键名为NamiYoyo的信息
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("NamiYoyo");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Ninja/NamiYoyo.png";
    private static final int COST = 2;
    private static final int ATTACK_DMG = 11;
    private static final int UPGRADE_PLUS_DMG = 3;
    public static final String ID = "NamiYoyo";
    private static final int NINJUTSU = 3;
    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public NamiYoyo() {
        super(ID, NAME, IMG_PATH, 4, DESCRIPTION, CardType.SKILL, AbstractCardEnum.Ninja_COLOR, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage = ATTACK_DMG;
        this.baseMagicNumber = 8;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(CardTagsEnum.SCIENCE);
    }

    @Override
    public void upgrade(){
        if(!this.upgraded){
            this.upgradeName();
            this.upgradeMagicNumber(3);
        }
    }

    @Override
    public void use(AbstractPlayer p,AbstractMonster m){
        int count =0;
        CardCrawlGame.sound.play("NamiYoyo");

        this.addToBot(new ApplyPowerAction(m, p, new PoisonPower(m, p, this.magicNumber), this.magicNumber, AbstractGameAction.AttackEffect.POISON));
        this.addToBot(new ApplyPowerAction(m, p, new NanoExplosion(m), 1, AbstractGameAction.AttackEffect.POISON));

        this.addToBot(new ScienceAction(p));
    }

    public AbstractCard makeCopy() {
        return new NamiYoyo();
    }
}