package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.DaggerSprayEffect;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import patches.CardTagsEnum;

public class UBWAction extends AbstractGameAction {
    private int damage;

    public UBWAction( int damage) {
        this.damage = damage;
        this.actionType = ActionType.DAMAGE;
    }

    public void update(){
        this.isDone=true;

            int count = 0;

            for(AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
                if (c.hasTag(CardTagsEnum.BLADE) || c.cardID == "YiCut" || c.cardID == "Shiv") {
                    ++count;
                }
            }

            --count;
        for(int i = 0; i < count; ++i) {
            this.addToTop(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, this.damage, DamageInfo.DamageType.NORMAL),AttackEffect.SLASH_HEAVY));
        }

        this.addToTop(new PlaySoundAction("UBW"));
        for(int j=0;j<3;++j){
                this.addToTop(new VFXAction(new DaggerSprayEffect(AbstractDungeon.getMonsters().shouldFlipVfx()), 0.0F));
            }



    }

}
