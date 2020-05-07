package nl.rug.oop.rpg.npcs.enemies;

import nl.rug.oop.rpg.game.Game;
import nl.rug.oop.rpg.interfaces.Attackable;
import nl.rug.oop.rpg.extra.DefaultStats;
import nl.rug.oop.rpg.objects.doors.MiniBossDoor;
import nl.rug.oop.rpg.game.Player;

import java.io.Serializable;
import java.util.Random;

/**
 * RedWizard extends abstract class MiniBoss
 */
public class RedWizard extends MiniBoss implements Serializable {

    private static final long serialVersionUID = 28L;

    /**
     * Constructor for a red wizard
     * Sets the door associated with this mini boss to door
     * Attack points, hit points and gold value are all set to default values
     * @param description
     * @param name
     * @param door
     */
    public RedWizard(String description, String name, MiniBossDoor door) {
        super(description, name, DefaultStats.WIZARD_HIT_POINTS , DefaultStats.WIZARD_ATTACK_POINTS,
                DefaultStats.WIZARD_GOLD_VALUE, door);
    }

    /**
     * Constructor for a red wizard using only a name
     * Sets the door associated with this mini boss to null
     * @param name
     */
    public RedWizard(String name) {
        this("You will burn to Death!!", name, null);
    }

    /**
     * Defeating a red wizard adds fire magic to the combat options of the game
     * @param game
     */
    @Override
    public void die(Game game) {
        game.addFireMagic();
        super.die(game);
    }

    /**
     * When a red wizard attacks there is a chance to burn the attacked
     * If the attacked does not get burnt then the red wizard deals its attack power as damage
     * @param attacked
     */
    @Override
    public void attack(Attackable attacked) {
        Random r = new Random();
        int chance = r.nextInt(101);
        if (chance < 21) {
            ((Player)attacked).setBurned(true);
        }
        super.attack(attacked);
    }

    /**
     * Returns the species of this enemy
     * @return "Red wizard"
     */
    @Override
    public String getSpecies() {
        return "Red Wizard";
    }

}
