package nl.rug.oop.rpg.io;
import nl.rug.oop.rpg.Game;

import java.io.*;

public class Serializer {

    public static void saveGame(Game game, String fileName) {
        File saveDirectory = new File("saves");
        saveDirectory.mkdir();

        try(FileOutputStream fileOutputStream = new FileOutputStream(saveDirectory + File.separator + fileName + ".ser");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(game);
            System.out.println("Save successful!");
        } catch (FileNotFoundException e) {
            System.out.println("File could not be found!");
        } catch (IOException e) {
            System.out.println("Could not write to file!");
        }
    }

    public static Game loadGame(String fileName) throws IOException, ClassNotFoundException {
        File saveDirectory = new File("saves");

        try(FileInputStream fileInputStream = new FileInputStream(saveDirectory + File.separator + fileName+ ".ser");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

            Game game = (Game) objectInputStream.readObject();
            return game;
        }
    }
}
