package nl.rug.oop.rpg;

public abstract class Trader extends DungeonNpc{

    private String name;
    private int power;
    private int price;
    private boolean hasTraded;

    public Trader(String description, String name, int power, int price) {
        super(description);
        this.name = name;
        this.power = power;
        this.price = price;
        this.hasTraded = false;
    }

    public Trader(String description) {
        this(description, "a Trader", 1, 1);
    }

    public int getPower() {
        return this.power;
    }

    public String getName() {
        return this.name;
    }

    public boolean getTradeStatus() {
        return this.hasTraded;
    }

    public void trade(Player player) {
        player.decreaseGold(this.price);
        return;
    }

    @Override
    public void interact(Player player) {
        trade(player);
        this.hasTraded = true;
    }

    @Override
    public String getType() {
        return "Trader";
    }

    @Override
    public String toString() {
        return this.name;
    }
}
