package cards;

import actions.HolyLittleStormAction;
import actions.NinjutsuAction;
import actions.PlaySoundAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import patches.AbstractCardEnum;
import patches.CardTagsEnum;
import powers.LexKela;

public class HolyLittleStorm extends CustomCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("HolyLittleStorm");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Ninja/HolyLittleStorm.png";
    public static final String ID = "HolyLittleStorm";

    public HolyLittleStorm(){
        super (ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.ATTACK, AbstractCardEnum.Ninja_COLOR, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        this.tags.add(CardTagsEnum.NINJUTSU);
        this.baseDamage = 8;
        this.tags.add(CardTagsEnum.BLADE);
        this.tags.add(CardTagsEnum.HAND);
    }

    public void use(AbstractPlayer p, AbstractMonster m ){
        AbstractPower lexkela = p.getPower("LexKela");
        int amount = lexkela.amount;
        if(p.hasRelic("MachineNinja")){
            if(!p.hasPower("DimDeadTreePower")) {
                amount--;
            }
        }
        this.addToBot(new NinjutsuAction(p,new HolyLittleStormAction(p,this.damage,amount),amount,"HolyLittleStorm" ));
    }

    public void upgrade(){
        if(!this.upgraded){
            this.upgradeName();
            this.upgradeBaseCost(0);
        }
    }

    public AbstractCard makeCopy(){
        return new HolyLittleStorm();
    }
}
