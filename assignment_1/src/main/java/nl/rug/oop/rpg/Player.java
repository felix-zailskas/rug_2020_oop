package nl.rug.oop.rpg;

public class Player implements Attackable{

    private String name;
    private Room currentRoom;
    private int hitPoints;
    private int attackPoints;

    public Player(String name, Room currentRoom, int hitPoints, int attackPoints) {
        this.name = name;
        this.currentRoom = currentRoom;
        this.hitPoints = hitPoints;
        this.attackPoints = attackPoints;
    }

    public Player(String name, Room currentRoom) {
        this(name, currentRoom, 10, 0);
    }

    public Player(String name) {
        this(name, null,10, 0);
    }

    public String[] getPossibleMoves(){
        String[] options = new String[3];
        options[0] = "Look around";
        options[1] = "Look for a way out";
        options[2] = "Look for company";
        return options;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void attack(Attackable attacked) {
        attacked.reduceHitPoints(this.attackPoints);
    }

    @Override
    public void reduceHitPoints(int value) {
        this.hitPoints -= value;
    }

    @Override
    public boolean isDead() {
        return this.hitPoints <= 0;
    }
}
