package nl.rug.oop.rpg;

import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Game {

    private Player player;
    private ArrayList<Room> totalRooms;
    private ArrayList<Door> totalDoors;
    private ArrayList<String> possibleMoves;
    private ArrayList<String> fightMoves;
    private int iceMagic;
    private int fireMagic;


    public Game(String name) {
        this.totalRooms = new ArrayList<Room>();
        this.totalDoors = new ArrayList<Door>();
        this.possibleMoves = new ArrayList<String>();
        this.fightMoves = new ArrayList<String>();
        try {
            JsonReader.parseRoomJSON(totalRooms);
            JsonReader.parseDoorJSON(totalRooms, totalDoors);
            JsonReader.parseConnectionJSON(totalRooms, totalDoors);
            JsonReader.parseNPCJSON(totalRooms, "enemies.json");
            JsonReader.parseNPCJSON(totalRooms, "healers.json");
            JsonReader.parseNPCJSON(totalRooms, "traders.json");
            JsonReader.parseItemJSON(totalRooms);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.player = new Player(name, this.totalRooms.get(0), DefaultStats.PLAYER_HIT_POINTS, DefaultStats.PLAYER_ATTACK_POINTS, DefaultStats.PLAYER_HIT_POINTS);

        MagicOrb orb = new MagicOrb( totalRooms.get(5));
        Priest priest = new Priest("Priest");
        HighPriest highPriest = new HighPriest("High");
        Knight knight1 = new Knight("Knight1");
        Knight knight2 = new Knight("§§");
        Knight knight3 = new Knight("RRR");
        Rat rat = new Rat("Rat");
        Spider spider = new Spider("spider");
        Snake snake = new Snake("Snake");
        Orc orc = new Orc("orc");
        Dragon dragon = new Dragon("JAJA");
        WeaponSmith weaponSmith = new WeaponSmith("Weapons");
        ArmorSmith armorSmith = new ArmorSmith("Armor");
        Gambler gambler = new Gambler("gambler");

        this.totalRooms.get(0).addNPC(gambler);
        this.totalRooms.get(0).addNPC(priest);
        this.totalRooms.get(0).addNPC(highPriest);
        this.totalRooms.get(0).addNPC(knight1);
        this.totalRooms.get(0).addNPC(knight2);
        this.totalRooms.get(0).addNPC(knight3);
        this.totalRooms.get(0).addNPC(rat);
        this.totalRooms.get(0).addNPC(spider);
        this.totalRooms.get(0).addNPC(orc);
        this.totalRooms.get(0).addNPC(dragon);
        this.totalRooms.get(0).addNPC(snake);
        this.totalRooms.get(0).addNPC(weaponSmith);
        this.totalRooms.get(0).addNPC(armorSmith);
        this.totalRooms.get(0).addNPC(new BlueWizard("Jim"));
        this.totalRooms.get(0).addNPC(new RedWizard("Jim"));

        this.possibleMoves.add("Look around");
        this.possibleMoves.add("Look for a way out");
        this.possibleMoves.add("Look for company");
        this.possibleMoves.add("Look for items");
        this.possibleMoves.add("Look at your stats");
        this.possibleMoves.add("Look in your inventory");

        this.fightMoves.add("Run");
        this.fightMoves.add("Attack");


        this.player.addCollectable(new HealingPotion());
        this.player.addCollectable(new GoldNugget());
//        GoldNugget goldNugget = new GoldNugget();
//        totalRooms.get(0).addItem(goldNugget);
    }

    public boolean notOver() {
        return !player.isDead();
    }

    public void printOptions() {
        System.out.println("What do you want to do?");
        for (int i = 0; i < this.possibleMoves.size(); i++) {
            System.out.println("\t(" + i + ") " + this.possibleMoves.get(i));
        }
    }

    public void useInventory() {
        Scanner scanner = new Scanner(System.in);
        int move;
        if (player.getInventory().size() == 0) {
            System.out.println("You currently have no items.");
            return;
        }
        for (int i = 0; i < player.getInventory().size(); i++) {
            System.out.println("\t(" + i + ") " + this.player.getInventory().get(i).toString());
        }
        System.out.println("What item will you use (-1 to not use an item)");
        try {
            move = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("That is not a valid input!");
            return;
        }
        if (move == -1) return;
        if (move < player.getInventory().size() && move > -1) {
            player.getInventory().get(move).use(player);
        } else System.out.println("That is not a valid item!");
    }

    public void displayStats() {
        System.out.println("Your Character: " + player.getName());
        System.out.println(TextColor.ANSI_GREEN + "\tHealth: " + TextColor.ANSI_RESET + player.getHitPoints() + "/" + player.getMaxHitPoints());
        System.out.println(TextColor.ANSI_RED + "\tAttack: " + TextColor.ANSI_RESET + player.getAttackPoints());
        System.out.println(TextColor.ANSI_YELLOW + "\tGold: " + TextColor.ANSI_RESET + player.getGold());
    }

    public void inspectRoom() {
        System.out.print("You see: ");
        this.player.getCurrentRoom().inspect();
    }

    public void inspectDoors() {
        System.out.println("You look for doors.");
        System.out.println("You see:");
        ArrayList<Door> doors = this.player.getCurrentRoom().getDoors();
        for (int i = 0; i < doors.size(); i++) {
            System.out.print("\t(" + i + ") ");
            doors.get(i).inspect();
        }
        System.out.println("Which door will you take? (-1 to stay)");
    }

    public void inspectItems() {
        System.out.println("You look for items.");
        System.out.println("You see:");
        ArrayList<Collectable> items = this.player.getCurrentRoom().getItems();
        for (int i = 0; i < items.size(); i++) {
            System.out.print("\t(" + i + ") ");
            System.out.println(items.get(i).toString());
        }
        System.out.println("Which item will you take? (-1 to not collect any items)");
    }

    public void interactItem(int currentMove) {
        ArrayList<Collectable> items = this.player.getCurrentRoom().getItems();
        if (currentMove < items.size() && currentMove > -2) {
            if (currentMove == -1) {
                System.out.println("You did not collect any items.");
                return;
            }
            items.get(currentMove).collect(player);
            player.getCurrentRoom().removeItem();
            System.out.println("You collected " + items.get(currentMove).toString());
        }
    }

    public void interactDoor(int currentMove) {
        ArrayList<Door> doors = this.player.getCurrentRoom().getDoors();
        if (currentMove < doors.size() && currentMove > -2) {
            if (currentMove == -1) {
                System.out.println("You stayed in the same room.");
                return;
            }
            doors.get(currentMove).interact(player);
            player.getCurrentRoom().inspect();
        }
    }

    public void inspectNPCs() {
        String color = TextColor.ANSI_RESET;
        System.out.println("You look if there's somebody.");
        System.out.println("You see:");
        ArrayList<DungeonNpc> npcs = this.player.getCurrentRoom().getNPCs();
        for (int i = 0; i < npcs.size(); i++) {
            if(npcs.get(i) instanceof Enemy) color = TextColor.ANSI_RED;
            if(npcs.get(i) instanceof Healer) color = TextColor.ANSI_GREEN;
            if(npcs.get(i) instanceof Trader) color = TextColor.ANSI_BLUE;
            System.out.print("\t(" + i + ") "+ "[" + color + npcs.get(i).getType() + TextColor.ANSI_RESET + "]" + "(" + npcs.get(i).getSpecies() + ") " + npcs.get(i).toString() + ": ");
            npcs.get(i).inspect();
        }
        System.out.println("Who will you approach? (-1 to stay by yourself)");
    }

    public void interactNPC(int currentMove) {
        ArrayList<DungeonNpc> npcs = this.player.getCurrentRoom().getNPCs();
        if (currentMove < npcs.size() && currentMove > -2) {
            if (currentMove == -1) {
                System.out.println("You decided to leave them alone.");
                return;
            }
            DungeonNpc currentNPC= npcs.get(currentMove);
            if (currentNPC instanceof Enemy) {
                engageFight(player, (Enemy) currentNPC);
            } else if (currentNPC instanceof Healer) {
                healPlayer(player, (Healer) currentNPC);
            } else if (currentNPC instanceof Trader) {
                tradeWith(player, (Trader) currentNPC);
            }

        }
    }

    private void winGame() {
        System.out.println(TextColor.ANSI_BLACK + "Congratulations you have won the game! You are a real dungeon master!" + TextColor.ANSI_RESET);
        System.exit(0);
    }

    private void gameOver() {
        System.out.println(TextColor.ANSI_BLACK + "GAME OVER!" + TextColor.ANSI_RESET);
        System.exit(0);
    }

    private void tradeWith(Player player, Trader trader) {
        Scanner scanner = new Scanner(System.in);
        int move;
        System.out.println(trader.getName() + ": " + TextColor.ANSI_PURPLE + trader.tradeDialog() + "\n Are you interested?" + TextColor.ANSI_RESET);
        System.out.println("\t(0) I think that is too expensive!\n\t(1) Let's trade!");
        try {
            move = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("That is not a valid input!");
            return;
        }
        if (move == 0) return;
        else if (move == 1){
            if (player.getGold() < trader.getPrice()) {
                System.out.println("You do not have enough gold!");
                return;
            }
            trader.interact(player);
            System.out.println("You traded with " + trader.getName() + ". You have " + player.getGold() + " gold.");
            player.getCurrentRoom().removeDeadNPC();
        } else System.out.println("That is not a valid input!");
    }

    private void healPlayer(Player player, Healer healer) {
        Scanner scanner = new Scanner(System.in);
        int move;
        System.out.println(healer.getName() + ":" + TextColor.ANSI_PURPLE + "I can only heal you once, and then I will leave!\n Are you sure you want me to heal you?" + TextColor.ANSI_RESET);
        System.out.println("\t(0) No better do it later!\n\t(1) Heal me!");
        try {
            move = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("That is not a valid input!");
            return;
        }
        if (move == 0) return;
        else if (move == 1){
            healer.interact(player);
            System.out.println("You are at " + player.getHitPoints() + " health.");
            player.getCurrentRoom().removeDeadNPC();
        } else System.out.println("That is not a valid input!");
    }

    private void engageFight(Player player, Enemy enemy) {
        Random r = new Random();
        int chance;
        int move;
        int damageToEnemy = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.println("You engaged a fight with " + enemy.getName());
        while (enemy.getHitPoints() > 0 && player.getHitPoints() > 0) {
            System.out.println("What will you do?");
            for(int i = 0; i < this.fightMoves.size(); i++) {
                System.out.println("\t (" + i + ") " + this.fightMoves.get(i));
            }
            try {
                move = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("That is not a valid input!");
                continue;
            }
            if (move > fightMoves.size()) {
                System.out.println("That is not a valid input!");
                continue;
            }
            if (move == 0){
                if (enemy instanceof MiniBoss || enemy instanceof Boss) {
                    System.out.println("You cannot run from a boss fight!");
                    continue;
                }
                System.out.println(TextColor.ANSI_YELLOW + "You ran from the fight. " + TextColor.ANSI_RED + enemy.getName() + TextColor.ANSI_YELLOW + " recovered to full health!" + TextColor.ANSI_RESET);
                enemy.increaseHitPoints(damageToEnemy);
                return;
            }
            if (player.isFrozen()) {
                chance = r.nextInt(101);
                if (chance > 60) {
                    System.out.println(TextColor.ANSI_RED + "You are frozen solid." + TextColor.ANSI_RESET);
                    enemy.interact(player);
                    if (player.isDead()) {
                        System.out.println(TextColor.ANSI_RED + "You have been slain by " + enemy.getName() + "!" + TextColor.ANSI_RESET);
                        gameOver();
                    }
                    continue;
                } else {
                    System.out.println(TextColor.ANSI_BLUE + "You are no longer frozen!" + TextColor.ANSI_RESET);
                    player.removeFreeze();
                }
            }
            if (player.isBurned()) {
                chance = r.nextInt(101);
                if (chance > 55) {
                    System.out.println(TextColor.ANSI_RED + "You are burned and take " + DefaultStats.BURN_DAMAGE  + " damage." +TextColor.ANSI_RESET);
                    player.reduceHitPoints(DefaultStats.BURN_DAMAGE);
                    if (player.isDead()) {
                        System.out.println(TextColor.ANSI_RED + "You have burned to death!" + TextColor.ANSI_RESET);
                        gameOver();
                    }
                } else {
                    System.out.println(TextColor.ANSI_BLUE + "You do no longer burn!" + TextColor.ANSI_RESET);
                    player.removeBurn();
                }
            }
            if (move == 1){
                System.out.println(TextColor.ANSI_YELLOW + "You attack " + enemy.getName() + TextColor.ANSI_RESET);
                System.out.println(TextColor.ANSI_YELLOW + enemy.getName() + " takes " + player.getAttackPoints() + " damge!" + TextColor.ANSI_RESET);
                player.attack(enemy);
                damageToEnemy += this.player.getAttackPoints();

            } else if (move == fireMagic) {
                System.out.println(TextColor.ANSI_YELLOW + "You have burned " + enemy.getName() + "!" + TextColor.ANSI_RESET);
                enemy.burn();
            } else if (move == iceMagic) {
                System.out.println(TextColor.ANSI_YELLOW + "You have frozen " + enemy.getName() + "!" + TextColor.ANSI_RESET);
                enemy.freeze();
            }
            if (enemy.isBurned()) {
                chance = r.nextInt(101);
                if (chance > 45) {
                    System.out.println(TextColor.ANSI_YELLOW + enemy.getName() + " is burned and takes " + DefaultStats.BURN_DAMAGE  + " damage." +TextColor.ANSI_RESET);
                    enemy.reduceHitPoints(DefaultStats.BURN_DAMAGE);
                } else {
                    System.out.println(TextColor.ANSI_YELLOW + enemy.getName() + " does no longer burn!" + TextColor.ANSI_RESET);
                    enemy.removeBurn();
                }
            }
            if (enemy.isDead()) {
                System.out.println(TextColor.ANSI_YELLOW + "You have slain " + enemy.getName() + "!\nYou earned " + enemy.getGoldValue() + " gold." + TextColor.ANSI_RESET);
                if (enemy instanceof BlueWizard) {
                    System.out.println(TextColor.ANSI_YELLOW + "You have defeated a blue wizard! You gained ice magic and can now freeze enemies in combat!" + TextColor.ANSI_RESET);
                    iceMagic = fightMoves.size();
                    fightMoves.add("Ice Magic");
                }
                if (enemy instanceof RedWizard) {
                    System.out.println(TextColor.ANSI_YELLOW + "You have defeated a red wizard! You gained fire magic and can now burn enemies in combat!" + TextColor.ANSI_RESET);
                    fireMagic = fightMoves.size();
                    fightMoves.add("Fire Magic");
                }
                player.getCurrentRoom().removeDeadNPC();
                player.increaseGold(enemy.getGoldValue());
                player.increaseHitPoints(enemy.getAttackPoints());
                return;
            }
            if (enemy.isFrozen()) {
                chance = r.nextInt(101);
                if (chance > 50) {
                    System.out.println(TextColor.ANSI_BLUE + enemy.getName() + " is frozen solid." + TextColor.ANSI_RESET);
                    continue;
                } else {
                    System.out.println(TextColor.ANSI_BLUE + enemy.getName() + " is no longer frozen!" + TextColor.ANSI_RESET);
                    enemy.removeFreeze();
                }
            }
            enemy.interact(player);
            if (player.isDead()) {
                System.out.println(TextColor.ANSI_RED + "You have been slain by " + enemy.getName() + "!" + TextColor.ANSI_RESET);
                gameOver();
            }

        }
    }
}