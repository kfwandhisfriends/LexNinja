package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class ManTooWeakAction extends AbstractGameAction {

    private AbstractPlayer p;
    private int magicNumber;

    public ManTooWeakAction(int magicNumber){
        this.p = AbstractDungeon.player;
        this.magicNumber = magicNumber;
    }

    public void update(){
        this.addToTop(new ApplyPowerAction(p,p,new StrengthPower(p,this.magicNumber),this.magicNumber));
        this.addToTop(new ApplyPowerAction(p,p,new DexterityPower(p,this.magicNumber),this.magicNumber));
        this.isDone=true;
    }
}
