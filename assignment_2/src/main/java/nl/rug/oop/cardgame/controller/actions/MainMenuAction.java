package nl.rug.oop.cardgame.controller.actions;

import nl.rug.oop.cardgame.model.MagicStoneGame;
import nl.rug.oop.cardgame.model.menu.MainMenu;
import nl.rug.oop.cardgame.view.frame.MagicStoneFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MainMenuAction extends AbstractAction {

    MainMenu mainMenu;

    public MainMenuAction(MainMenu mainMenu) {
        super("Back to the Main Menu");
        this.mainMenu = mainMenu;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mainMenu.goBackToMenu();
    }
}
