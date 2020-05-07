package nl.rug.oop.rpg.npcs.trader;

import nl.rug.oop.rpg.extra.DefaultStats;
import nl.rug.oop.rpg.game.Player;

import java.io.Serializable;

/**
 * ArmorSmith extends abstract class Trader increases maximum health of the player for gold
 */
public class ArmorSmith extends Trader implements Serializable {

    private static final long serialVersionUID = 14L;

    /**
     * Constructor for an armor smith
     * Power and price are set to default values
     * @param name
     */
    public ArmorSmith(String name) {
        super("Shields and chestplates have saved my life!", name, DefaultStats.ARMORSMITH_POWER,
                DefaultStats.ARMORSMITH_PRICE);
    }

    /**
     * Trading results in increase of health of the player
     * @param player
     */
    @Override
    public void trade(Player player) {
        player.increaseMaxHitPoints(this.getPower());
        super.trade(player);
    }

    /**
     * Return the species of this trader
     * @return "Armorsmith"
     */
    @Override
    public String getSpecies() {
        return "Armorsmith";
    }

    /**
     * Return the trade dialog for an armor smith
     * @return String
     */
    @Override
    public String tradeDialog() {
        String toReturn = "I will increase your maximum health by " + this.getPower() + " for "
                + this.getPrice() + " gold!";
        return toReturn;
    }
}
