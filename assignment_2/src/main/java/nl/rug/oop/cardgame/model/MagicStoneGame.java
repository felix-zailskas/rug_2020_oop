package nl.rug.oop.cardgame.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nl.rug.oop.cardgame.controller.button.EndTurnButton;
import nl.rug.oop.cardgame.model.hero.Hero;
import nl.rug.oop.cardgame.view.MagicStoneFrame;

import java.util.Observable;

@EqualsAndHashCode(callSuper = true)
@Data
public class MagicStoneGame extends Observable {

    private Battlefield battlefield;
    private MagicStoneFrame frame;

    public MagicStoneGame() {
        this.battlefield = new Battlefield();
    }

    /**
     * Starts the actual turn based game
     *
     * @param battlefield Playing board
     */
    public void startGame(Battlefield battlefield, MagicStoneFrame frame) {
        turnRotation(battlefield, frame);
    }

    /**
     * Rotates the turns
     *
     * @param battlefield Playing board
     */
    public void turnRotation(Battlefield battlefield, MagicStoneFrame frame) {
        Hero player = battlefield.getPlayer();
        Hero ai = battlefield.getAi();
        boolean start = true;
        for (int i = 1; start; i++) {
            notifyUpdate();
//            frame.update(frame.getGraphics());
            System.out.println();
            System.out.println("It's turn number " + ((i + (i % 2)) / 2));
            if (i % 2 == 1) {
//                battlefield.setPlayerTurn(true);
                resetUsedCreatures(player);
                notifyUpdate();
//                frame.update(frame.getGraphics());
                battlefield.incMana(player);
                player.setMana(player.getMaxMana());
                notifyUpdate();
//                frame.update(frame.getGraphics());
                player.takeTurn(battlefield, frame, frame.getPanel(), this);
//                frame.update(frame.getGraphics());
                notifyUpdate();
            } else {
                resetUsedCreatures(ai);
                battlefield.incMana(ai);
                ai.setMana(ai.getMaxMana());
                ai.takeTurn(battlefield, frame, frame.getPanel(), this);
                battlefield.setPlayerTurn(true);
            }
            endGameCheck(battlefield);
        }
    }

    private void notifyUpdate() {
        setChanged();
        notifyObservers();
    }

    public void endPlayerTurn() {
       this.battlefield.setPlayerTurn(false);
    }

    /**
     * Checks whether either hero has died if so ends the game
     */
    public void endGameCheck(Battlefield battlefield) {
        if (battlefield.getPlayer().getHealth() <= 0) frame.gameOver(false);
        else if (battlefield.getAi().getHealth() <= 0) frame.gameOver(true);
    }

    /**
     * Resets the param used for each played creature
     *
     * @param hero Hero
     */
    public void resetUsedCreatures(Hero hero) {
        if (hero.getPlayedCreatures().size() == 0) return;
        for (int i = hero.getPlayedCreatures().size() - 1; i >= 0; i--) {
            if (hero.getPlayedCreatures().get(i) != null) hero.getPlayedCreatures().get(i).setUsed(false);
        }
    }

}
