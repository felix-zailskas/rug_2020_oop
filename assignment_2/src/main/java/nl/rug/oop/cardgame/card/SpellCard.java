package nl.rug.oop.cardgame.card;

import lombok.Data;

import java.awt.*;

/**
 * Spell card
 */
@Data
public class SpellCard extends Card{

    /**
     * Creates a new spell card
     * @param name Name
     * @param cost Cost
     * @param image Image
     */
    public SpellCard(String name, int cost, Image image) {
        super(name, cost, image);
    }

    /**
     * Play method
     */
    @Override
    public void play() {
        super.play();
    }
}
