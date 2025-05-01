package patches;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

@SpirePatch(clz = AbstractCard.class, method = "applyPowers")
public class ScienceCardCostPatch {
    @SpirePostfixPatch
    public static void applyCostReduction(AbstractCard __instance) {
        AbstractPlayer player = AbstractDungeon.player;
        if (player == null) return;
        // 检查是否带有SCIENCE标签
        if (__instance.hasTag(CardTagsEnum.SCIENCE)) {
            // 获取specialEnergy能力层数
            AbstractPower power = player.getPower("LexKela");
            if (power != null && power.amount >= 0 ) {
                // 动态修改费用（显示用）
                __instance.costForTurn = Math.max(0, __instance.cost - power.amount);
                __instance.isCostModifiedForTurn = true;
            }
            else {
                __instance.costForTurn = __instance.cost;
                __instance.isCostModifiedForTurn = true;
                }


            }
        }
    }
