package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

public class KillAllAction extends AbstractGameAction {

    public void update(){
        CardCrawlGame.sound.play("Kill!@#A%ll.mp3");
        CardCrawlGame.sound.play("KillAll");
        for(int i = 0; i < AbstractDungeon.getCurrRoom().monsters.monsters.size(); ++i) {
            AbstractMonster target = (AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i);
            if (!target.isDying && target.currentHealth > 0 && !target.isEscaping && !target.isDead) {
                target.damage(new DamageInfo(target,999, DamageInfo.DamageType.NORMAL));
                AbstractDungeon.effectsQueue.add(new LightningEffect(target.hb.cX, target.hb.cY));
                CardCrawlGame.sound.playA("ORB_LIGHTNING_EVOKE", 0.9F);
                CardCrawlGame.sound.playA("ORB_LIGHTNING_PASSIVE", -0.3F);
            }
        }
        AbstractDungeon.effectsQueue.add(new LightningEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY));
        CardCrawlGame.sound.playA("ORB_LIGHTNING_EVOKE", 0.9F);
        CardCrawlGame.sound.playA("ORB_LIGHTNING_PASSIVE", -0.3F);
        AbstractDungeon.player.damage(new DamageInfo(AbstractDungeon.player,999, DamageInfo.DamageType.NORMAL));

        this.isDone = true;
    }
}
