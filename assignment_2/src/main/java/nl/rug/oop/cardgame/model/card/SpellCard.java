package nl.rug.oop.cardgame.model.card;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nl.rug.oop.cardgame.model.Battlefield;

import java.awt.*;

/**
 * Spell card
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SpellCard extends Card {

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
     * @return
     */
    @Override
    public boolean play(Battlefield battlefield, int hero) {
        System.out.println("Played a spell you draw 2 cards");
        if (hero == 0) {
            for (int i = 0; i < 2; i++) {
                Card card = battlefield.getPlayer().getDeck().drawCard();
                if(card != null) {
                    System.out.println("Drawing card: " + card.getName() + " : Mana Cost -> " + card.getCost());
                    battlefield.getPlayer().getDeckHand().addCard(card);
                }
            }
            return false;
        }
        for (int i = 0; i < 2; i++) {
            Card card = battlefield.getAi().getDeck().drawCard();
            if(card != null) {
                System.out.println("Drawing card: " + card.getName() + " : Mana Cost -> " + card.getCost());
                battlefield.getAi().getDeckHand().addCard(card);
            }
        }
        return false;
    }
}
