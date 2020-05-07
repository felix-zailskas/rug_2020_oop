package nl.rug.oop.rpg.npcs.trader;

import nl.rug.oop.rpg.extra.DefaultStats;
import nl.rug.oop.rpg.game.Player;

import java.io.Serializable;

/**
 * StrongWeaponSmith extends abstract class Trader increases players attack for gold
 */
public class StrongWeaponSmith extends Trader implements Serializable {

    private static final long serialVersionUID = 32L;

    /**
     * Constructor for a strong weapon smith
     * Power and price are set to default values
     * @param name
     */
    public StrongWeaponSmith(String name) {
        super("Attack is the only defense!", name, DefaultStats.STRONG_WEAPONSMITH_POWER,
                DefaultStats.STRONG_WEAPONSMITH_PRICE);
    }

    /**
     * Trading results in increase of attack of the player
     * @param player
     */
    @Override
    public void trade(Player player) {
        player.increaseAttackPoints(this.getPower());
        super.trade(player);
    }

    /**
     * Return the species of this trader
     * @return "Excellent Weaponsmith"
     */
    @Override
    public String getSpecies() {
        return "Excellent Weaponsmith";
    }

    /**
     * Return the trade dialog for this trader
     * @return String
     */
    @Override
    public String tradeDialog() {
        String toReturn = "I will increase your attack power by " + this.getPower() + " for "
                + this.getPrice() + " gold!";
        return toReturn;
    }
}