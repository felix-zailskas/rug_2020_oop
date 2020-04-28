package nl.rug.oop.rpg;

public class ArmorSmith extends Trader{

    public ArmorSmith(String name) {
        super("Shields and chestplates have saved my life!", name, DefaultStats.ARMORSMITH_POWER, DefaultStats.ARMORSMITH_PRICE);
    }

    @Override
    public void trade(Player player) {
        player.increaseMaxHitPoints(this.getPower());
        System.out.println("Your health increased by " + this.getPower() + ".");
        super.trade(player);
    }

    @Override
    public String getSpecies() {
        return "Armorsmith";
    }

    @Override
    public String tradeDialog() {
        String toReturn = "I will increase your maximum health by " + this.getPower() + " for " + this.getPrice() + " gold!";
        return toReturn;
    }
}
