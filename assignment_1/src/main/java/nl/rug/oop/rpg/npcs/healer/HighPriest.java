package nl.rug.oop.rpg.npcs.healer;

import nl.rug.oop.rpg.extra.DefaultStats;
import nl.rug.oop.rpg.game.Player;

import java.io.Serializable;

/**
 * HighPriest extends abstract class Healer heals the player fully
 */
public class HighPriest extends Healer implements Serializable {

    private static final long serialVersionUID = 22L;

    /**
     * Constructor for a HighPriest
     * Heal power is set to a default value
     * @param name
     */
    public HighPriest(String name) {
        super("God Shall bless you!", name, DefaultStats.PRIEST_HEAL_POWER);
    }

    /**
     * A high priest heals a player fully
     * @param player
     */
    @Override
    public void heal(Player player) {
        player.increaseHitPoints(player.getMaxHitPoints());
        this.setHealStatus(true);
    }

    /**
     * Return the species of this healer
     * @return "High Priest"
     */
    @Override
    public String getSpecies() {
        return "High Priest";
    }
}