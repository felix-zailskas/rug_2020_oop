package nl.rug.oop.cardgame.model.card;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nl.rug.oop.cardgame.model.Battlefield;
import nl.rug.oop.cardgame.model.hero.Hero;
import nl.rug.oop.cardgame.view.frame.MagicStoneFrame;

@EqualsAndHashCode(callSuper = true)
@Data
public class HealthSpell extends SpellCard {

    public HealthSpell(EnumCard enumCard) {
        super(enumCard);
    }

    @Override
    public boolean play(Battlefield battlefield, int hero, int pos, MagicStoneFrame frame) {
        Hero player = battlefield.getPlayer();
        Hero ai = battlefield.getAi();
        Hero target;
        boolean heal = this.type.equals("INSTANTHEALTH");
        int dealValue = this.getEnumCard().getValue();
        if (!heal) dealValue *= -1;
        if (hero == 0) {
            if (heal) target = player;
            else target = ai;
        } else {
            if (heal) target = ai;
            else target = player;
        }
        target.setHeroHealth(target.getHealth() + dealValue);
        return super.play(battlefield, hero, pos, frame);
    }

}
