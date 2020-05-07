package nl.rug.oop.rpg.game;

import nl.rug.oop.rpg.StartGame;
import nl.rug.oop.rpg.io.FileHandler;
import nl.rug.oop.rpg.io.Serializer;
import nl.rug.oop.rpg.npcs.enemies.BlueWizard;
import nl.rug.oop.rpg.npcs.enemies.MiniBoss;
import nl.rug.oop.rpg.npcs.enemies.RedWizard;
import nl.rug.oop.rpg.objects.Room;
import nl.rug.oop.rpg.objects.doors.Door;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import static nl.rug.oop.rpg.io.JsonReader.*;

public class GameOptions {

    /**
     * Starts the game and presents the user with their possible move options to navigate the rpg
     * @param game
     * @param fileName
     */
    public static void gameStart(Game game, String fileName) {
        Scanner scanner = new Scanner(System.in);
        int currentMove;

        while (true) {
            System.out.println("What do you want to do?");
            StartGame.printOptions(game.getPossibleMoves());
            try {
                currentMove = scanner.nextInt();
            } catch (InputMismatchException e) {
                GUI.invalidInputMessage();
                scanner.nextLine();
                continue;
            }
            switch (currentMove) {
                // inspect room
                case 0:
                    GUInspect.inspectRoom(game.getPlayer());
                    break;
                // inspect doors
                case 1:
                    GUInspect.inspectDoors(game.getPlayer());
                    try {
                        currentMove = scanner.nextInt();
                        GameInteract.interactDoor(currentMove, game);
                    } catch (InputMismatchException e) {
                        GUI.invalidInputMessage();
                        scanner.nextLine();
                    }
                    break;
                // inspect NPCs
                case 2:
                    if(GUInspect.inspectNPCs(game.getPlayer())) {
                        try {
                            currentMove = scanner.nextInt();
                            GameInteract.interactNPC(currentMove, game);
                        } catch (InputMismatchException e) {
                            GUI.invalidInputMessage();
                            scanner.nextLine();
                        }
                    }
                    break;
                // show items in the room
                case 3:
                    if(GUInspect.inspectItems(game.getPlayer())) {
                        try {
                            currentMove = scanner.nextInt();
                            GameInteract.interactItem(currentMove, game);
                        } catch (InputMismatchException e) {
                            GUI.invalidInputMessage();
                            scanner.nextLine();
                        }
                    }
                    break;
                // show inventory
                case 4:
                    game.useInventory();
                    break;
                // show Stats
                case 5:
                    GUI.displayStats(game.getPlayer());
                    break;
                case 6:
                    Serializer.saveGame(game, "quicksave");
                    break;
                case 7:
                    StartGame.initOldGame("quicksave", "quickload");
                    break;
                case 8:
                    StartGame.gameSave(game, fileName);
                    break;
                case 9:
                    if(FileHandler.fileLoader("saves", null)) break;
            }
        }
    }

    public static void createGame(ArrayList<Room> totalRooms, ArrayList<Door> totalDoors,
                                  ArrayList<String> possibleMoves, ArrayList<String> fightMoves,
                                  ArrayList<MiniBoss> miniBosses) {

        BlueWizard blueWizard = new BlueWizard("Ice Poseidon");
        miniBosses.add(blueWizard);

        RedWizard redWizard = new RedWizard("Hades");
        miniBosses.add(redWizard);

        try {
            parseRoomJSON(totalRooms);
            parseDoorJSON(totalRooms, totalDoors);
            parseConnectionJSON(totalRooms, totalDoors);
            parseNPCJSON(totalRooms, "enemies.json");
            parseNPCJSON(totalRooms, "healers.json");
            parseNPCJSON(totalRooms, "traders.json");
            parseItemJSON(totalRooms);
        } catch (FileNotFoundException e) {
            System.out.println("The default map files could not be found!");
        } catch (ParseException e) {
            System.out.println("The default map files could not be parsed correctly!");

        } catch (IOException e) {
            System.out.println("The default map files are not formatted correctly!");
        }

        possibleMoves.add("Look around");
        possibleMoves.add("Look for a way out");
        possibleMoves.add("Look for company");
        possibleMoves.add("Look for items");
        possibleMoves.add("Look in your inventory");
        possibleMoves.add("Look at your stats");
        possibleMoves.add("QuickSave");
        possibleMoves.add("QuickLoad");
        possibleMoves.add("Save");
        possibleMoves.add("Load");

        fightMoves.add("Run");
        fightMoves.add("Attack");
        fightMoves.add("Items");
    }

}