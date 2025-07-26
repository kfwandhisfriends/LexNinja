package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MonkHandAction extends AbstractGameAction {

    private AbstractMonster m;
    private AbstractPlayer p;
    private DamageInfo info;
    public MonkHandAction (AbstractPlayer p,AbstractMonster m, DamageInfo info){
        this.p = p;
        this.m = m;
        this.info = info;
    }

    public void update(){
        this.addToTop(new DamageAction(m, this.info, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        this.isDone=true;
    }

}
