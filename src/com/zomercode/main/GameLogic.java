package com.zomercode.main;

import java.util.Scanner;

public class GameLogic
{
    static Scanner scanner = new Scanner(System.in);

    static Player player;

    public static boolean isRunning;

    //random encounters
    public static String[] encounters = {"Battle", "Battle", "Battle", "Rest", "Rest"};

    //Enemy names
    public static String[] enemies = {"Ogre", "Ogre", "Goblin", "Goblin", "Stone Elemental"};

    //story elements
    public static int place = 0, act = 1;
    public static String[] places = {"Everlasting Mountains", "Haunted Landlines", "Castle of the Evil Emperor", "Throne Room"};

    //method to get user input from user
    public static int readInt(String prompt, int userChoices)
    {
        int input;

        do
        {
            System.out.print(prompt);
            try {

                input = Integer.parseInt(scanner.next());

                if (input > userChoices || input < 1) {

                    System.out.println("Please enter a valid input!");
                }
            } catch (Exception e) {

                input = -1;
                System.out.println("Please enter an integer!");
            }

        } while (input < 1 || input > userChoices);

        return input;
    }

    public static void clearConsole()
    {
        for (int i = 0; i < 100; i++)
        {
            System.out.println();
        }
    }

    public static void printSeperator(int n)
    {
        for (int i = 0; i < n; i++)
        {
            System.out.print("-");
        }

        System.out.println();
    }

    public static void printHeading(String title)
    {
        printSeperator(30);
        System.out.println(title);
        printSeperator(30);
    }

    public static void anythingToContinue()
    {
        System.out.println("\nEnter anything to continue...");
        scanner.next();
    }

    public static void startGame()
    {
        boolean nameSet = false;
        String name;

        clearConsole();
        printSeperator(40);
        printSeperator(30);
        System.out.println("AGE OF THE EVIL EMPEROR");
        System.out.println("TEXT-BASED GAME BY HTTPS://ZOMERGREGORIO.ML");
        printSeperator(30);
        printSeperator(40);
        anythingToContinue();

        do
        {
            clearConsole();
            printHeading("Enter you in-game name:");
            name = scanner.next();

            //asking the player if he wants to change his in-game name
            clearConsole();
            printHeading("Your in-game name is " + name + ".\nIs that correct?");
            System.out.println("(1) - Yes!");
            System.out.println("(2) - No, I want to change my in-game name");
            int input = readInt("-> ", 2);

            if (input == 1) {

                nameSet = true;
            }
        } while (!nameSet);

        //print Intro
        Story.printIntro();

        player = new Player(name);

        //print First Act Intro
        Story.printFirstActIntro();

        isRunning = true;

        gameLoop();
    }

    //change the game's values based on player xp
    public static void checkAct()
    {
        //change acts based on xp
        if (player.xp >= 10 && act == 1) {

            //increment act and place
            act = 2;
            place = 1;

            //story
            Story.printFirstActOutro();

            //let the player "level up"
            player.chooseTrait();

            //story
            Story.printSecondActIntro();

            //assign new values to enemies
            enemies[0] = "Evil Mercenary";
            enemies[1] = "Goblin";
            enemies[2] = "Wolve Pack";
            enemies[3] = "Henchman of the Evil Emperor";
            enemies[4] = "Scary Stranger";

            //assign new values to encounters
            encounters[0] = "Battle";
            encounters[1] = "Battle";
            encounters[2] = "Battle";
            encounters[3] = "Rest";
            encounters[4] = "Shop";
        }
        else if (player.xp >= 50 && act == 2) {

            //increment act and place
            act = 3;
            place = 2;

            //story
            Story.printSecondActOutro();

            //level up
            player.chooseTrait();

            //story
            Story.printThirdActIntro();

            //assign new values to enemies
            enemies[0] = "Evil Mercenary";
            enemies[1] = "Evil Mercenary";
            enemies[2] = "Henchman of the Evil Emperor";
            enemies[3] = "Henchman of the Evil Emperor";
            enemies[4] = "Henchman of the Evil Emperor";

            //assign new values to encounters
            encounters[0] = "Battle";
            encounters[1] = "Battle";
            encounters[2] = "Battle";
            encounters[3] = "Battle";
            encounters[4] = "Shop";

            //fully heal the player
            player.hp = player.maxHp;
        }

        else if (player.xp >= 100 && act == 3) {

            //incremet act and place
            act = 4;
            place = 3;

            //story
            Story.printThirdActOutro();

            //level up
            player.chooseTrait();

            //story
            Story.printFourthActIntro();

            //fully heal the player
            player.hp = player.maxHp;

            finalBattle();
        }
    }

    //calculate a random encounter
    public static void randomEncounter()
    {
        int encounter = (int) (Math.random()* encounters.length);

        //calling the respective methods
        if (encounters[encounter].equals("Battle")) {

            randomBattle();
        }
        else if (encounters[encounter].equals("Rest")) {

            takeRest();
        }
        else {

            shop();
        }
    }

    public static void continueJourney()
    {
        //check if act must be increased
        checkAct();

        //check if game isn't in last act
        if (act != 4) {

            randomEncounter();
        }
    }

    public static void characterInfo()
    {
        clearConsole();
        printHeading("CHARACTER INFO");
        System.out.println(player.name + "\tHP: " + player.hp + "/" + player.maxHp);
        printSeperator(20);

        //player xp and gold
        System.out.println("XP: " + player.xp + "\tGold: " + player.gold);
        printSeperator(20);

        //# of pots
        System.out.println("No. of pots: " + player.pots);
        printSeperator(20);

        //print the chosen trait
        if (player.numAttackUpgrades > 0) {

            System.out.println("Offensive trait: " + player.attackUpgrades[player.numAttackUpgrades - 1]);
            printSeperator(20);
        }
        if (player.numDefenseUpgrades > 0) {

            System.out.println("Defensive trait: " + player.defenseUpgrades[player.numDefenseUpgrades - 1]);
        }

        anythingToContinue();
    }

    //shopping or encountering a travelling trader
    public static void shop()
    {
        clearConsole();
        printHeading("You meet a mysterious stranger.\nHe offers you something:");
        int price = (int) (Math.random()* (10 + player.pots*3) + 10 + player.pots);
        System.out.println("- Magic Potion: " + price + " gold.");
        printSeperator(20);

        //ask the player to buy one
        System.out.println("Do you want to buy one? \n(1) Yes! \n(2) No thanks.");
        int input = readInt("-> ", 2);

        if (input == 1) {

            clearConsole();

            //check if player has enough gold
            if (player.gold >= price) {

                printHeading("You bought a magical potion for " + price + " gold.");
                player.pots++;
                player.gold -= price;
            }
            else {

                printHeading("You don't have enough gold to buy this...");
            }

            anythingToContinue();
        }
    }

    public static void takeRest()
    {
        clearConsole();

        if (player.restsLeft >= 1) {

            printHeading("Do you want to take a rest? (" + player.restsLeft + " rest(s) left).");
            System.out.println("(1) Yes\n(2) No, not now.");
            int input = readInt("-> ", 2);

            if (input == 1) {

                clearConsole();

                if (player.hp < player.maxHp) {

                    int hpRestored = (int) (Math.random() * (player.xp/4 + 1) + 10);
                    player.hp += hpRestored;

                    if (player.hp > player.maxHp) {

                        player.hp = player.maxHp;
                    }

                    System.out.println("You took a rest and restore up to " + hpRestored + " health.");
                    System.out.println("You are now at " + player.hp + "/" + player.maxHp + " health.");
                    player.restsLeft--;
                }
                else {

                    System.out.println("You are at full health. You don't need to rest now.");
                }
            }
            /**
            else {

                System.out.println("You are at full health. You don't need to rest now.");
            }
            **/

            else {

                anythingToContinue();
            }
        }
    }

    //create random battle
    public static void randomBattle()
    {
        clearConsole();
        printHeading("You encountered an evil minded creature. You'll have to fight it!");
        anythingToContinue();

        //create new enemies with random names
        battle(new Enemy(enemies[(int) (Math.random() * enemies.length)], player.xp));
    }

    public static void battle(Enemy enemy)
    {
        while (true)
        {
            clearConsole();
            printHeading(enemy.name + "\nHP: " + enemy.hp + "/" + enemy.maxHp);
            System.out.println("Choose an action: ");
            printSeperator(20);
            System.out.println("(1) Fight\n(2) Use Potion\n(3) Run Away");
            int input = readInt("->", 3);

            //react accordingly to player input
            if (input == 1) {

                //FIGHT
                //calculate damage and damageTook (damage enemy deals to player)
                int dmg = player.attack() - enemy.defend();
                int dmgTook = enemy.attack() - player.defend();

                if (dmgTook < 0) {

                    //add some damage if player defends very well
                    dmg -= dmgTook/2;
                    dmgTook = 0;
                }
                if (dmg < 0) {

                    dmg = 0;
                }

                //deal damage to both parties
                player.hp -= dmgTook;
                enemy.hp -= dmg;

                //print info of this battle round
                clearConsole();
                printHeading("BATTLE");
                System.out.println("You dealt " + dmg + " damage to the " + enemy.name + ".");
                printSeperator(15);
                System.out.println("The " + enemy.name + " dealt " + dmgTook + " damage to you.");
                anythingToContinue();

                //check if player is still alive or dead
                if (player.hp <= 0) {

                    playerDied(); //method to end the game
                    break;
                }
                else if (enemy.hp <= 0) {

                    //tell the player he won
                    clearConsole();
                    printHeading("You defeated the " + enemy.name + "!");

                    //increase player xp
                    player.xp += enemy.xp;
                    System.out.println("You earned " + enemy.xp + " XP!");

                    //random drops
                    boolean addRest = (Math.random()*5 + 1 <= 2.25);
                    int goldEarned = (int) (Math.random()*enemy.xp);

                    if (addRest) {

                        player.restsLeft++;
                        System.out.println("You earned the chance to get an additional rest!");
                    }
                    if (goldEarned > 0) {

                        player.gold += goldEarned;
                        System.out.println("You collect " + goldEarned + " gold from the " + enemy.name + "'s corpse!");
                    }

                    anythingToContinue();
                    break;
                }
            }
            else if (input == 2) {

                //USE POTION
                clearConsole();
                if (player.pots > 0 && player.hp < player.maxHp) {

                    //player CAN take a potion
                    //make sure player wants to drink the potion
                    printHeading("Do you want to drink a potion? (" + player.pots + " left).");
                    System.out.println("(1) Yes\n(2) No, maybe later");
                    input = readInt("-> ", 2);

                    if (input == 1) {

                        player.hp = player.maxHp;
                        clearConsole();
                        printHeading("You drank a magic potion. It restored your health back to " + player.maxHp);
                        anythingToContinue();
                    }
                }
                else {

                    //player CANNOT take a potion
                    printHeading("You don't have any potion or you have full health.");
                    anythingToContinue();
                }
            }
            else {

                //RUN AWAY
                clearConsole();

                //check if player isn't in the last act
                if (act != 4) {

                    //chance of 35% to escape
                    if (Math.random()*10 + 1 <= 3.5) {

                        printHeading("You ran away from the " + enemy.name + "!");
                        anythingToContinue();
                        break;
                    }
                    else {

                        printHeading("You didn't manage to escape.");

                        //calculate the damage player takes
                        int dmgTook = enemy.attack();
                        System.out.println("In you hurry you took 0 " + dmgTook + " damage!");
                        anythingToContinue();

                        //check if player's still alive
                        if (player.hp <= 0) {

                            playerDied();
                        }
                    }
                }
                else {

                    printHeading("YOU CANNOT ESCAPE THE EVIL EMPEROR!!!");
                    anythingToContinue();
                }
            }
        }
    }

    public static void printMenu()
    {
        clearConsole();
        printHeading(places[place]);
        System.out.println("Choose an action: ");
        printSeperator(20);
        System.out.println("(1) Continue on your journey");
        System.out.println("(2) Character Info");
        System.out.println("(3) Exit Game");
    }

    public static void finalBattle()
    {
        //creating the Evil Emperor and let the player fights against him
        battle(new Enemy("THE EVIL EMPEROR", 300));

        //printing the proper ending
        Story.printEnd(player);
        isRunning = false;

    }

    //method that gets called when player died
    public static void playerDied()
    {
        clearConsole();
        printHeading("You died...");
        printHeading("You earned " + player.xp + " XP on your journey. Try to earn more next time!");
        System.out.println("Thank you for playing the game. I hope you enjoyed it.");
        isRunning = false;
    }

    public static void gameLoop()
    {
        while (isRunning)
        {
            printMenu();
            int input = readInt("-> ", 3);

            if (input == 1) {

                continueJourney();
            }
            else if (input == 2) {

                characterInfo();
            }
            else {

                isRunning = false;
            }
        }
    }

} //Class
