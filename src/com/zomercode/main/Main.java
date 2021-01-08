package com.zomercode.main;

public class Main
{
    public static void main(String[] args)
    {
        GameLogic.clearConsole();
        GameLogic.printSeperator(40);
        System.out.println("Do you want to enter the game?");
        GameLogic.printSeperator(40);
        System.out.println("(1) Yes! \n(2) No, please exit the program...");

        while (true)
        {
            int input = GameLogic.readInt("-> ", 2);

            if (input == 1) {

                GameLogic.startGame();
                break;
            }
            else {

                System.exit(0);
            }
        }

    } //Main method

} //Class
