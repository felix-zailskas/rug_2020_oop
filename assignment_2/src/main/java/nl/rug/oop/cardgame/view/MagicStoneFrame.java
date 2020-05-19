package nl.rug.oop.cardgame.view;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nl.rug.oop.cardgame.controller.actions.CardClicker;
import nl.rug.oop.cardgame.controller.button.AttackPhaseButton;
import nl.rug.oop.cardgame.controller.button.EndTurnButton;
import nl.rug.oop.cardgame.model.MagicStoneGame;
import nl.rug.oop.cardgame.model.card.Card;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.concurrent.TimeUnit;

@EqualsAndHashCode(callSuper = true)
@Data
public class MagicStoneFrame extends JFrame {

    private MagicStonePanel panel;
    private CardClicker clicker;
    private JLabel gifLabel;


    public MagicStoneFrame(MagicStoneGame magicStoneGame) {
        super("Magic Stone");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new MagicStonePanel(magicStoneGame);
        panel.setLayout(null);
        EndTurnButton endTurnButton = new EndTurnButton(magicStoneGame);
        endTurnButton.setBounds(590, 510, 100, 30);
        panel.add(endTurnButton);
        add(panel);
        clicker = new CardClicker(magicStoneGame, panel, this);
        AttackPhaseButton attackPhaseButton = new AttackPhaseButton(magicStoneGame, this, panel, clicker);
        attackPhaseButton.setBounds(590, 108, 100, 30);
        panel.add(attackPhaseButton);
        setPreferredSize(new Dimension(1280, 720));
        gifLabel = new JLabel();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    public void playGif(String gifName, int[] coords) {
        ImageIcon gif = new ImageIcon(this.getClass().getResource(File.separator + "textures"
                + File.separator + gifName + ".gif"));
        gif.setImage(gif.getImage().getScaledInstance(coords[2],coords[2], Image.SCALE_DEFAULT));
        gifLabel.setIcon(gif);
        gifLabel.setBounds(coords[0], coords[1], coords[2], coords[2]);
        panel.add(gifLabel);
        pack();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gifLabel.setIcon(null);
        return;
    }

    public void gameOver(boolean win) {
        remove(panel);
        GameOverPanel overPanel = new GameOverPanel(win);
        add(overPanel);
        setPreferredSize(new Dimension(300, 200));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }
}
