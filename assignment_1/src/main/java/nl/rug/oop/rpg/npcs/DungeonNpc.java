package nl.rug.oop.rpg.npcs;

import nl.rug.oop.rpg.Game;
import nl.rug.oop.rpg.Player;
import nl.rug.oop.rpg.interfaces.Inspectable;
import nl.rug.oop.rpg.interfaces.Interactable;
import nl.rug.oop.rpg.TextColor;

import java.io.Serializable;

public abstract class DungeonNpc implements Inspectable, Interactable, Serializable {

    private static final long serialVersionUID = 18L;

    private String description;
    private String name;
    protected boolean engaged;

    public DungeonNpc(String description, String name) {
        this.name = name;
        this.description = description;
    }

    public boolean hasBeenEngaged() { return this.engaged; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType(){
        return "DungeonNpc";
    }

    public String getSpecies(){
        return "";
    }

    public void engage(Player player, Game game){}

    public String getName() { return this.name;}

    @Override
    public void inspect() {
        System.out.println(TextColor.ANSI_PURPLE + description + TextColor.ANSI_RESET);
    }
}
