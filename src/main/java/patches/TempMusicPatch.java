package patches;

import com.badlogic.gdx.audio.Music;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.audio.MainMusic;
import com.megacrit.cardcrawl.audio.TempMusic;

@SpirePatch (
        clz = TempMusic.class, method = "getSong"
)
public class TempMusicPatch {
    @SpirePrefixPatch
    public static SpireReturn<Music> Prefix(TempMusic __instance, String key) {
        switch (key) {
            case "Sleep":
                return SpireReturn.Return(MainMusic.newMusic("audio/Sleep.ogg"));
            case "NinjaMod:Hamood":
                return SpireReturn.Return(MainMusic.newMusic("audio/Hamood.mp3"));
        }
        return SpireReturn.Continue();
    }
}