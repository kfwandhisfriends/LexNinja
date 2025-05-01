package patches;

import basemod.BaseMod;
import com.evacipated.cardcrawl.mod.stslib.relics.OnPlayerDeathRelic;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import relics.Sarira;

@SpirePatch(clz = AbstractPlayer.class, method = "damage")
public class SariraRevivePatch  {
    @SpirePrefixPatch
    public static void beforeDamage(AbstractPlayer __instance) {
        // 检测玩家是否持有遗物且处于死亡边缘
        if (__instance.currentHealth <= 0 && __instance.hasRelic("Sarira")) {
            __instance.currentHealth = 1;
            AbstractDungeon.player.isDying = false;
            AbstractDungeon.player.isDead = false;
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMBAT;
            __instance.currentHealth = 1; // 临时阻止死亡
        }
    }



    @SpirePostfixPatch
    public static void afterDamage(AbstractPlayer __instance) {

        if ( __instance.currentHealth <= 0&& __instance.hasRelic("Sarira")) {
            // 触发复活逻辑
            __instance.currentHealth = 1;
            AbstractDungeon.player.isDying = false;
            AbstractDungeon.player.isDead = false;
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMBAT;
            __instance.currentHealth = 1; // 临时阻止死亡
            System.out.println("舍利子回魂！");
            __instance.currentHealth = __instance.maxHealth; // 强制满血
        }
    }

}
