package com.zomercode.main;

public class Player extends Character
{
    public int numAttackUpgrades, numDefenseUpgrades;

    //additional player stats
    int gold, restsLeft, pots;

    //array to store skills names
    public String[] attackUpgrades = {"Strength", "Power", "Might", "Godlike Strength"};
    public String[] defenseUpgrades = {"Heavy Bones", "Stoneskin", "Scale Armor", "Holy Aura"};

    public Player(String name)
    {
        super(name, 100, 0);

        this.numAttackUpgrades = 0;
        this.numDefenseUpgrades = 0;

        this.gold = 5;
        this.restsLeft = 1;
        this.pots = 0;

        chooseTrait();
    }

    @Override
    public int attack()
    {
        return (int) (Math.random() * (xp/4 + numAttackUpgrades * 3 + 3) + xp/10 + numAttackUpgrades * 2 + numDefenseUpgrades + 1);
    }

    @Override
    public int defend()
    {
        return (int) (Math.random() * (xp/4 + numDefenseUpgrades * 3 + 3) + xp/10 + numDefenseUpgrades* 2 + numAttackUpgrades + 1);
    }

    public void chooseTrait()
    {
        GameLogic.clearConsole();
        System.out.println("Choose a trait: ");
        System.out.println("(1) " + attackUpgrades[numAttackUpgrades]);
        System.out.println("(2) " + defenseUpgrades[numDefenseUpgrades]);

        int input = GameLogic.readInt("-> ", 2);
        GameLogic.clearConsole();

        if (input == 1) {

            GameLogic.printHeading("You chose " + attackUpgrades[numAttackUpgrades] + "!");
            numAttackUpgrades++;
        }
        else {

            GameLogic.printHeading("You chose " + defenseUpgrades[numDefenseUpgrades]);
            numDefenseUpgrades++;
        }

        GameLogic.anythingToContinue();

    }
}
