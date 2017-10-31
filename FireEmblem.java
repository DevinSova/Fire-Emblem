/**
 * Fire Emblem Version 2.0
 *
 * Devin Sova
 * May 21st, 2016
 */

import java.lang.*;
import java.util.Scanner;
import java.util.ArrayList;

public class FireEmblem
{
    public static void main(String[] args) throws Exception
    {

        //TODO: Change Mainmethods to Model, to elemenate the stupid passing of characters and such.
        //TODO: this class will be controller and view

        MainMethods.PlayMusic();
        System.out.println(" ______ __  _ ____  ______   ______ _ ___ ____  ______  __    _____ __ __ ____    ");
        System.out.println("/_  __//_ |/_' _  |/_  __/  /_  __//_' _ ' _  \\/_  _  \\/_ |  |  __//_ '_ ' _  \\");
        System.out.println(" | |___ | | | |_| | | |___   | |___ | | | | | | | |_| | | |  | |___ | | | | | |   ");
        System.out.println(" |  __/ | | |    _| |  __/   |  __/ | | | | | | |  _ <  | |  |  __/ | | | | | |   ");
        System.out.println(" | |    | | | |\\ \\  | |___   | |___ | | | | | | | |_| | | |__| |___ | | | | | | ");
        System.out.println(" |_|    |_| |_| \\_\\ |____/   |____/ |_| |_| |_| |_____/ |___/|____/ |_| |_| |_| ");
        Scanner in = new Scanner(System.in);
        int count = 1;
        int selectionA = -1;
        int selectionB = -1;
        boolean a = false;
        boolean b = false;
        ArrayList<Character> Allies = MainMethods.constructTeam();
        ArrayList<Character> Enemies = MainMethods.constructTeam();
        System.out.print("\nWhat is your name, Tactician?: ");
        String name = in.nextLine();
        MainMethods.PlaySelectionSound();
        System.out.println("Hello " + name + "!\nWelcome to Fire Emblem!\nThis game is a turn by turn based tatical role playing game where you command a team of special individuals to defeat the enemy.");
        System.out.println("Remember, Axes best Lances, Swords best Axes, and Lances best Swords.");
        System.out.println("Anima bests Light, Dark bests Anima, and Light bests Dark.");
        System.out.println("Characters have a couple of stats. Strength/Magic, higher number, higher damage. Skill, higher number, higher hit rate."
                + "\n\tLuck affects many things. Defense reduces damage from weapons. Resistance reduces damage from magic.");
        System.out.print("\nPress Enter when you are done reading.");
        in.nextLine();
        MainMethods.PlaySelectionSound();
        System.out.println("Here is your team, " + name);
        try {
            Thread.sleep(2000);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        for(Character temp: Allies)
        {
            if(count == 10)
                System.out.print(count + ":");
            else
                System.out.print(count + ": ");
            temp.printCharacter(true);
            try {
                Thread.sleep(3000);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            count++;
        }
        count = 1;

        if(Allies.size() != 0)
        {
            while(Allies.size() > 0 && Enemies.size() > 0)
            {
                if(b)
                {
                    System.out.println("Enemy Phase");
                    try {
                        Thread.sleep(3000);
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    Object[] temp = Enemies.remove((int)(Math.random() * Enemies.size())).Attack(Allies.remove((int)Math.random() * Allies.size()), false);
                    if(temp[0] != null)
                    {
                        Allies.add(0, (Character)temp[0]);
                        Allies.get(0).printCharacter(false);
                    }

                    if(temp[1] != null)
                    {
                        Enemies.add(0, (Character)temp[1]);
                        Enemies.get(0).printCharacter(false);
                    }
                    System.out.println("\nPress Enter to continue");
                    in.nextLine();
                }
                b = true;
                if(a)
                {
                    System.out.println("\n"+ name + "'s team:");
                    for(Character temp: Allies)
                    {
                        System.out.print(count + ": ");
                        temp.printCharacter(true);
                        count++;
                    }
                    count = 1;
                }
                a = true;
                System.out.println("Player Phase");
                try {
                    Thread.sleep(500);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                while(selectionA < 0 || selectionA > Allies.size() - 1)
                {
                    System.out.print("Select an Ally number you wish to use: ");
                    selectionA = in.nextInt() - 1;
                    MainMethods.PlaySelectionSound();
                }


                System.out.println(Allies.get(selectionA).Name() + " Selected\n\n");

                for(Character temp: Enemies)
                {
                    System.out.print(count + ": ");
                    temp.printCharacter(false);
                    count++;
                }
                count = 1;

                while(selectionB < 0 || selectionB > Enemies.size() - 1)
                {
                    System.out.print("\nSelect an Enemy number you wish to attack: ");
                    selectionB = in.nextInt() - 1;
                    MainMethods.PlaySelectionSound();
                }

                Object[] temp = Allies.remove(selectionA).Attack(Enemies.remove(selectionB), true);

                if(temp[0] != null)
                {
                    Allies.add(0, (Character)temp[0]);
                    Allies.get(0).printCharacter(false);
                }

                if(temp[1] != null)
                {
                    Enemies.add(0, (Character)temp[1]);
                    Enemies.get(0).printCharacter(false);
                }

                selectionA = -1;
                selectionB = -1;

                if((Boolean)temp[2] == false)
                {
                    System.out.println("\nAttack cancelled\n");
                    b = false;
                    continue;
                }
                else
                    b = true;

                System.out.println("\nPress Enter to continue");
                in.nextLine();
                in.nextLine();
            }
        }
        if(Enemies.size() == 0)
        {
            System.out.println(name + ", you won!");
            MainMethods.PlayVictory();
            try {
                Thread.sleep(5500);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            System.out.println(" ______ __  _ ____  ______   ______ _ ___ ____  ______  __    _____ __ __ ____    ");
            System.out.println("/_  __//_ |/_' _  |/_  __/  /_  __//_' _ ' _  \\/_  _  \\/_ |  |  __//_ '_ ' _  \\");
            System.out.println(" | |___ | | | |_| | | |___   | |___ | | | | | | | |_| | | |  | |___ | | | | | |   ");
            System.out.println(" |  __/ | | |    _| |  __/   |  __/ | | | | | | |  _ <  | |  |  __/ | | | | | |   ");
            System.out.println(" | |    | | | |\\ \\  | |___   | |___ | | | | | | | |_| | | |__| |___ | | | | | | ");
            System.out.println(" |_|    |_| |_| \\_\\ |____/   |____/ |_| |_| |_| |_____/ |___/|____/ |_| |_| |_| ");
            try {
                Thread.sleep(3600);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            System.out.println("Thank you for playing! \n"
                    + "I've worked very hard on this project!\n"
                    + "Based on the real game \"Fire Emblem\" that Nintendo owns all the rights too. ");

        }
        else
            System.out.println(name + ", you lost..");
        in.close();

    }
}