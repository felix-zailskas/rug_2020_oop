package nl.rug.oop.rpg.interfaces;

import nl.rug.oop.rpg.game.Player;

/**
 * Interface to make object interactable
 */
public interface Interactable {

    /**
     * Triggers an interaction between the interactable and the player objects
     * @param player
     */
    void interact(Player player);

}
