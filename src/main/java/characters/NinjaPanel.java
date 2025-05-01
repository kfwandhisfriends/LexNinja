package characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;

public class NinjaPanel extends CutscenePanel {
    private String sfx;

    public NinjaPanel(String imgUrl, String sfx) {
        super(imgUrl);
        this.sfx = sfx;
    }

    public NinjaPanel(String imgUrl) {
        super(imgUrl);
    }

    public void activate() {
        if (this.sfx != null)
            CardCrawlGame.sound.play(this.sfx);
        this.activated = true;
    }
}
