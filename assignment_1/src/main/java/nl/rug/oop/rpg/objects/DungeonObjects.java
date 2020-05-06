package nl.rug.oop.rpg.objects;

import nl.rug.oop.rpg.interfaces.Inspectable;

import java.io.Serializable;

/**
 * Abstract class DungeonObjects which will be used for non npc objects in the rpg
 */
public abstract class DungeonObjects implements Inspectable, Serializable {

    private static final long serialVersionUID = 4L;

    private String description;

    /**
     * Creats a dungeon object and sets the description
     * @param description
     */
    public DungeonObjects(String description) {
        this.description = description;
    }

    /**
     * Returns the description of a dungeon object
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of a dungeon object
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the description of a dungeon object when the player inspects it
     */
    @Override
    public void inspect() {
        System.out.println(description);
    }
}
