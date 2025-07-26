package actions;

import cards.OhFuckFlash;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.actions.watcher.SkipEnemiesTurnAction;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;

public class OhFuckFlashAction extends AbstractGameAction {

    public OhFuckFlashAction(){

    }

    public void update(){
        this.addToTop(new SkipEnemiesTurnAction());
        this.addToTop(new PressEndTurnButtonAction());
        this.addToTop(new VFXAction(new WhirlwindEffect(), 0.0F));
        this.isDone=true;
    }

}
