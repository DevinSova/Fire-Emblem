/**
 * Main Methods to use throughout the game.
 *
 * Devin Sova
 * May 4th 2016
 */
import java.io.File;
import java.lang.*;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;;

public class MainMethods
{
    public static void checkCombat(Character a, Character b)
    {
        Weapon aWep = a.currentWep();
        Weapon bWep = b.currentWep();
        int aAccuracy = 0;
        int bAccuracy = 0;
        int aDamage = 0;
        int bDamage = 0;
        int aCrit = 0;
        int bCrit = 0;
        boolean aTwice = false;
        boolean bTwice = false;
        String specialEffect = "";

        aAccuracy = (a.Skill() + aWep.Hit() + (15 * aWep.compareTriangle(bWep)) - b.Evade());
        if(aAccuracy > 100)
            aAccuracy = 100;
        if(aAccuracy < 0)
            aAccuracy = 0;
        bAccuracy = (b.Skill() + bWep.Hit() + (15 * bWep.compareTriangle(aWep)) - a.Evade());
        if(bAccuracy > 100)
            bAccuracy = 100;
        if(bAccuracy < 0)
            bAccuracy = 0;

        if(aWep.Type() == "Sword" || aWep.Type() == "Axe" || aWep.Type() == "Lance" || aWep.Type() == "Bow")
            aDamage = (a.StrengthMagic() + aWep.Might() + aWep.compareTriangle(bWep) - b.Defense());
        else
            aDamage = (a.StrengthMagic() + aWep.Might() + aWep.compareTriangle(bWep) - b.Resistance());


        if(bWep.Type() == "Sword" || bWep.Type() == "Axe" || bWep.Type() == "Lance" || aWep.Type() == "Bow")
            bDamage = (b.StrengthMagic() + bWep.Might() + bWep.compareTriangle(aWep) - a.Defense());
        else
            bDamage = (b.StrengthMagic() + bWep.Might() + bWep.compareTriangle(aWep) - a.Resistance());

        if(aDamage < 0)
            aDamage = 0;

        if(bDamage < 0)
            bDamage = 0;

        aCrit = (a.Skill()/2 + aWep.Crit() - b.Luck());

        if(aCrit > 100)
            aCrit = 0;

        if(aCrit < 0)
            aCrit = 0;

        bCrit = (b.Skill()/2 + bWep.Crit() - a.Luck());

        if(bCrit > 100)
            bCrit = 100;

        if(bCrit < 0)
            bCrit = 0;

        if(a.AttackSpeed() - b.AttackSpeed() > 4)
            aTwice = true;
        if(b.AttackSpeed() - a.AttackSpeed() > 4)
            bTwice = true;

        if(aWep.Name().contains("Brave"))
        {
            aTwice = true;
            specialEffect = "Special Effectiveness!";
        }
        if(aWep.Name().equals("Light Brand") || aWep.Name().equals("Wind Sword"))
        {
            aDamage = aDamage + b.Defense() - b.Resistance();
            specialEffect = "Special Effectiveness!";
        }
        if((aWep.Name().equals("Armorslayer") || aWep.Name().equals("Heavy Spear") || aWep.Name().equals("Hammer")) && (b.Type().equals("General") || b.Type().equals("Great Lord")))
        {
            aDamage += 10;
            aAccuracy += 5;
            specialEffect = "Special Effectiveness!";
        }

        if((aWep.Name().equals("Longsword") || aWep.Name().equals("Horseslayer") || aWep.Name().equals("Halberd")) && (b.Type().equals("Paladin") || b.Type().contains("Nomad")))
        {
            aDamage += 10;
            aAccuracy += 5;
            specialEffect = "Special Effectiveness!";
        }
        if((aWep.Name().equals("Wind Sword") || aWep.Name().equals("Wyrmslayer") || aWep.Type().equals("Bow")) && (b.Type().contains("Falcon") || b.Type().contains("Wyvern")))
        {
            aDamage += 10;
            aAccuracy += 5;
            specialEffect = "Special Effectiveness!";
        }

        if(bWep.Name().contains("Brave"))
        {
            bTwice = true;
            specialEffect = "Special Effectiveness!";
        }
        if(bWep.Name().equals("Light Brand") || bWep.Name().equals("Wind Sword"))
        {
            bDamage = bDamage + a.Defense() - a.Resistance();
            specialEffect = "Special Effectiveness!";
        }
        if((bWep.Name().equals("Armorslayer") || bWep.Name().equals("Heavy Spear") || bWep.Name().equals("Hammer")) && (a.Type().equals("General") || a.Type().equals("Great Lord")))
        {
            bDamage += 10;
            bAccuracy += 5;
            specialEffect = "Special Effectiveness!";
        }

        if((bWep.Name().equals("Longsword") || bWep.Name().equals("Horseslayer") || bWep.Name().equals("Halberd")) && (a.Type().equals("Paladin") || a.Type().contains("Nomad")))
        {
            aDamage += 10;
            aAccuracy += 5;
            specialEffect = "Special Effectiveness!";
        }
        if((bWep.Name().equals("Wind Sword") || bWep.Name().equals("Wyrmslayer") || bWep.Type().equals("Bow")) && (a.Type().contains("Falcon") || a.Type().contains("Wyvern")))
        {
            aDamage += 10;
            aAccuracy += 5;
            specialEffect = "Special Effectiveness!";
        }

        System.out.println("\n\t" + a.Name()       + " vs. " + b.Name());
        System.out.println("HP: (" + a.currentHP() + "/" + a.maxHP() + ")\t\tHP: (" + b.currentHP() + "/" + b.maxHP() + ")");
        System.out.println("Damage: " + aDamage   + "\t\tDamage: " + bDamage);
        System.out.println("Hit: " + aAccuracy + "%   \t\tHit: " + bAccuracy + "%");
        System.out.println("Crit: " + aCrit     + "%  \t\tCrit: " + bCrit + "%");
        if(aWep.compareTriangle(bWep) == 1)
            System.out.println("Wep: " + aWep.Name() + "\u21E7\tWep:" + bWep.Name() + "\u21E9\t");
        else if(bWep.compareTriangle(aWep) == 1)
            System.out.println("Wep: " + aWep.Name() + "\u21E9\tWep:" + bWep.Name() + "\u21E7\t");
        else
            System.out.println("Wep: " + aWep.Name() + "\t\tWep: " + bWep.Name() + "\t");
        if(aTwice)
            System.out.println(a.Name() + " will strike twice!");
        if(bTwice)
            System.out.println(b.Name() + " will strike twice!");
        System.out.println(specialEffect);
    }
    public static Object[] Combat(Character a, Character b) throws Exception
    {
        Random gen = new Random();
        //(Character A, Character B, True(Because attack happened!)
        Object[] array = {null, null, true};
        Weapon aWep = a.currentWep();
        Weapon bWep = b.currentWep();
        int aAccuracy = 0;
        int bAccuracy = 0;
        int aDamage = 0;
        int bDamage = 0;
        int aCrit = 0;
        int bCrit = 0;
        boolean aTwice = false;
        boolean bTwice = false;

        aAccuracy = (a.Skill() + aWep.Hit() + (15 * aWep.compareTriangle(bWep)) - b.Evade());
        if(aAccuracy > 100)
            aAccuracy = 100;
        if(aAccuracy < 0)
            aAccuracy = 0;
        bAccuracy = (b.Skill() + bWep.Hit() + (15 * bWep.compareTriangle(aWep)) - a.Evade());
        if(bAccuracy > 100)
            bAccuracy = 100;
        if(bAccuracy < 0)
            bAccuracy = 0;

        if(aWep.Type() == "Sword" || aWep.Type() == "Axe" || aWep.Type() == "Lance" || aWep.Type() == "Bow")
            aDamage = (a.StrengthMagic() + aWep.Might() + aWep.compareTriangle(bWep) - b.Defense());
        else
            aDamage = (a.StrengthMagic() + aWep.Might() + aWep.compareTriangle(bWep) - b.Resistance());


        if(bWep.Type() == "Sword" || bWep.Type() == "Axe" || bWep.Type() == "Lance" || aWep.Type() == "Bow")
            bDamage = (b.StrengthMagic() + bWep.Might() + bWep.compareTriangle(aWep) - a.Defense());
        else
            bDamage = (b.StrengthMagic() + bWep.Might() + bWep.compareTriangle(aWep) - a.Resistance());

        if(aDamage < 0)
            aDamage = 0;

        if(bDamage < 0)
            bDamage = 0;

        aCrit = (a.Skill()/2 + aWep.Crit() - b.Luck());

        if(aCrit > 100)
            aCrit = 0;

        if(aCrit < 0)
            aCrit = 0;

        bCrit = (b.Skill()/2 + bWep.Crit() - a.Luck());

        if(bCrit > 100)
            bCrit = 100;

        if(bCrit < 0)
            bCrit = 0;

        if(a.AttackSpeed() - b.AttackSpeed() > 4)
            aTwice = true;
        if(b.AttackSpeed() - a.AttackSpeed() > 4)
            bTwice = true;

        if(aWep.Name().contains("Brave"))
        {
            aTwice = true;
        }
        if(aWep.Name().equals("Light Brand") || aWep.Name().equals("Wind Sword"))
        {
            aDamage = aDamage + b.Defense() - b.Resistance();
        }
        if((aWep.Name().equals("Armorslayer") || aWep.Name().equals("Heavy Spear") || aWep.Name().equals("Hammer")) && (b.Type().equals("General") || b.Type().equals("Great Lord")))
        {
            aDamage += 10;
            aAccuracy += 5;
        }

        if((aWep.Name().equals("Longsword") || aWep.Name().equals("Horseslayer") || aWep.Name().equals("Halberd")) && (b.Type().equals("Paladin") || b.Type().contains("Nomad")))
        {
            aDamage += 10;
            aAccuracy += 5;
        }
        if((aWep.Name().equals("Wind Sword") || aWep.Name().equals("Wyrmslayer") || aWep.Type().equals("Bow")) && (b.Type().contains("Falcon") || b.Type().contains("Wyvern")))
        {
            aDamage += 10;
            aAccuracy += 5;
        }

        if(bWep.Name().contains("Brave"))
        {
            bTwice = true;
        }
        if(bWep.Name().equals("Light Brand") || bWep.Name().equals("Wind Sword"))
        {
            bDamage = bDamage + a.Defense() - a.Resistance();
        }
        if((bWep.Name().equals("Armorslayer") || bWep.Name().equals("Heavy Spear") || bWep.Name().equals("Hammer")) && (a.Type().equals("General") || a.Type().equals("Great Lord")))
        {
            bDamage += 10;
            bAccuracy += 5;
        }

        if((bWep.Name().equals("Longsword") || bWep.Name().equals("Horseslayer") || bWep.Name().equals("Halberd")) && (a.Type().equals("Paladin") || a.Type().contains("Nomad")))
        {
            aDamage += 10;
            aAccuracy += 5;
        }
        if((bWep.Name().equals("Wind Sword") || bWep.Name().equals("Wyrmslayer") || bWep.Type().equals("Bow")) && (a.Type().contains("Falcon") || a.Type().contains("Wyvern")))
        {
            aDamage += 10;
            aAccuracy += 5;
        }

        //Character A attacks first
        System.out.println(a.Name() + " attacks first!");
        Thread.sleep(2000);
        if(gen.nextInt(100) <= aAccuracy)
        {
            if(gen.nextInt(100) <= aCrit)
            {
                b.TakeDamage(aDamage * 3);
                System.out.println(a.Name() + " crits " + b.Name() + " for " + (aDamage * 3));
                Thread.sleep(2000);

                if(b.CheckHP())
                {
                    System.out.println(b.Name() + " has fallen.");
                    Thread.sleep(2000);
                    array[0] = a;
                    return array;
                }
                else
                {
                    System.out.println(b.Name() + " has been hit hard but is still standing!");
                    Thread.sleep(2000);
                }
            }
            else
            {
                b.TakeDamage(aDamage);
                System.out.println(a.Name() + " hits " + b.Name() + " for " + (aDamage));
                Thread.sleep(2000);

                if(b.CheckHP())
                {
                    System.out.println(b.Name() + " has fallen.");
                    Thread.sleep(2000);
                    array[0] = a;
                    return array;
                }
                else
                {
                    System.out.println(b.Name() + " has been hit but is still standing!");
                    Thread.sleep(2000);
                }
            }
        }
        else
            System.out.println(a.Name() + " missed!");
        Thread.sleep(2000);

        //Character B attacks second
        System.out.println(b.Name() + " attacks!");
        Thread.sleep(2000);
        if(gen.nextInt(100) <= bAccuracy)
        {
            if(gen.nextInt(100) <= bCrit)
            {
                a.TakeDamage(bDamage * 3);
                System.out.println(b.Name() + " crits " + a.Name() + " for " + (bDamage * 3));
                Thread.sleep(2000);

                if(a.CheckHP())
                {
                    System.out.println(a.Name() + " has fallen.");
                    Thread.sleep(2000);
                    array[1] = b;
                    return array;
                }
                else
                {
                    System.out.println(a.Name() + " has been hit hard but is still standing!");
                    Thread.sleep(2000);
                }
            }
            else
            {
                a.TakeDamage(bDamage);
                System.out.println(b.Name() + " hits " + a.Name() + " for " + (bDamage));
                Thread.sleep(2000);
                if(a.CheckHP())
                {
                    System.out.println(a.Name() + " has fallen.");
                    Thread.sleep(2000);
                    array[1] = b;
                    return array;
                }
                else
                {
                    System.out.println(a.Name() + " has been hit but is still standing!");
                    Thread.sleep(2000);
                }
            }
        }
        else
        {
            System.out.println(b.Name() + " missed!");
            Thread.sleep(2000);
        }


        if(aTwice)
        {
            System.out.println(a.Name() + " attacks again!");
            Thread.sleep(2000);
            if(gen.nextInt(100) <= aAccuracy)
            {
                if(gen.nextInt(100) <= aCrit)
                {
                    b.TakeDamage(aDamage * 3);
                    System.out.println(a.Name() + " crits " + b.Name() + " for " + (aDamage * 3));
                    Thread.sleep(2000);

                    if(b.CheckHP())
                    {
                        System.out.println(b.Name() + " has fallen.");
                        Thread.sleep(2000);
                        array[0] = a;
                        return array;
                    }
                    else
                    {
                        System.out.println(b.Name() + " has been hit hard but is still standing!");
                        Thread.sleep(2000);
                    }
                }
                else
                {
                    b.TakeDamage(aDamage);
                    System.out.println(a.Name() + " hits " + b.Name() + " for " + (aDamage));
                    Thread.sleep(2000);

                    if(b.CheckHP())
                    {
                        System.out.println(b.Name() + " has fallen.");
                        Thread.sleep(2000);
                        array[0] = a;
                        return array;
                    }
                    else
                    {
                        System.out.println(b.Name() + " has been hit but is still standing!");
                        Thread.sleep(2000);
                    }
                }
            }
            else
                System.out.println(a.Name() + " missed!");
            Thread.sleep(2000);
        }

        if(bTwice)
        {
            System.out.println(b.Name() + " attacks again!");
            Thread.sleep(2000);
            if(gen.nextInt(100) <= bAccuracy)
            {
                if(gen.nextInt(100) <= bCrit)
                {
                    a.TakeDamage(bDamage * 3);
                    System.out.println(b.Name() + " crits " + a.Name() + " for " + (bDamage * 3));
                    Thread.sleep(2000);

                    if(a.CheckHP())
                    {
                        System.out.println(a.Name() + " has fallen.");
                        Thread.sleep(2000);
                        array[1] = b;
                        return array;
                    }
                    else
                    {
                        System.out.println(a.Name() + " has been hit hard but is still standing!");
                        Thread.sleep(2000);
                    }
                }
                else
                {
                    a.TakeDamage(bDamage);
                    System.out.println(b.Name() + " hits " + a.Name() + " for " + (bDamage));
                    Thread.sleep(2000);
                    if(a.CheckHP())
                    {
                        System.out.println(a.Name() + " has fallen.");
                        Thread.sleep(2000);
                        array[1] = b;
                        return array;
                    }
                    else
                    {
                        System.out.println(a.Name() + " has been hit but is still standing!");
                        Thread.sleep(2000);
                    }
                }
            }
            else
            {
                System.out.println(b.Name() + " missed!");
                Thread.sleep(2000);
            }
        }
        array[0] = a;
        array[1] = b;
        return array;
    }
    public static ArrayList<Character> constructTeam() //Randomly picks 5 people
    {
        ArrayList<Character> teams = new ArrayList<Character>();
        ArrayList<Character> characters = constructCharacters();
        for(int i = 0; i < 5; i++)
        {
            teams.add(characters.remove(((int)(Math.random() * characters.size()))));
        }
        return teams;
    }

    public static ArrayList<Character> constructCharacters()
    {
        ArrayList<Character> characters = new ArrayList<Character>();
        Item[] items = constructItems();

        Item[] eliwoodInv = {items[8], items[12], items[7]};
        Character Eliwood = new Character("Eliwood", "Lord Knight", 52, 24, 24, 24, 18, 16, 24, 9, eliwoodInv);
        characters.add(Eliwood);

        Item[] hectorInv = {items[25], items[22], items[2]};
        Character Hector = new Character("Hector", "Great Lord", 57, 30, 23, 20, 28, 13, 15, 15, hectorInv);
        characters.add(Hector);

        Item[] lynInv = {items[6], items[27], items[50]};
        Character Lyn = new Character("Lyn", "Blade Lord", 46, 21, 29, 30, 15, 11, 22, 6, lynInv);
        characters.add(Lyn);

        Item[] sainInv = {items[16], items[14], items[4]};
        Character Sain = new Character("Sain", "Paladin", 53, 25, 17, 23, 16, 10, 18, 11, sainInv);
        characters.add(Sain);

        Item[] kentInv = {items[4], items[20], items[13]};
        Character Kent = new Character("Kent", "Paladin", 54, 21, 26, 23, 17, 11, 9, 11, kentInv);
        characters.add(Kent);

        Item[] florinaInv = {items[10], items[13], items[1]};
        Character Florina = new Character("Florina", "Falcon Knight", 47, 22, 25, 28, 10, 20, 25, 5, florinaInv);
        characters.add(Florina);

        Item[] wilInv = {items[27], items[28], items[29]};
        Character Wil = new Character("Wil", "Sniper", 51, 25, 25, 22, 14, 10, 21, 7, wilInv);
        characters.add(Wil);

        Item[] dorcasInv = {items[23], items[58], items[29], items[60]};
        Character Dorcas = new Character("Dorcas", "Warrior ", 60, 29, 24, 11, 18, 8, 19, 16, dorcasInv);
        characters.add(Dorcas);

        Item[] serraInv = {items[40], items[38], items[37]};
        Character Serra = new Character("Serra", "Bishop", 37, 23, 20, 25, 9, 28, 30, 5, serraInv);
        characters.add(Serra);

        Item[] erkInv = {items[36], items[32], items[35]};
        Character Erk = new Character("Erk", "Sage", 46, 20, 24, 26, 11, 21, 13, 6, erkInv);
        characters.add(Erk);

        Item[] rathInv = {items[30], items[3], items[27]};
        Character Rath = new Character("Rath ", "Nomadic Trooper", 54, 22, 22, 25, 13, 11, 13, 8, rathInv);
        characters.add(Rath);

        Item[] matthewInv = {items[3], items[1], items[0]};
        Character Matthew = new Character("Matthew", "Assassin", 51, 19, 23, 30, 14, 9, 21, 7, matthewInv);
        characters.add(Matthew);

        Item[] luciusInv = {items[39], items[38], items[37]};
        Character Lucius = new Character("Lucius", "Bishop", 40, 25, 25, 24, 4, 30, 9, 7, luciusInv);
        characters.add(Lucius);

        Item[] wallaceInv = {items[16], items[15], items[20]};
        Character Wallace = new Character("Wallace", "General", 53, 26, 19, 15, 26, 16, 17, 15, wallaceInv);
        characters.add(Wallace);

        Item[] marcusInv = {items[9], items[13], items[4], items[19]};
        Character Marcus = new Character("Marcus", "Paladin", 41, 19, 23, 15, 11, 13, 12, 11, marcusInv);
        characters.add(Marcus);

        Item[] lowenInv = {items[56], items[18], items[45]};
        Character Lowen = new Character("Lowen", "Paladin", 60, 18, 18, 18, 24, 13, 23, 12, lowenInv);
        characters.add(Lowen);

        Item[] rebeccaInv = {items[31], items[61], items[21]};
        Character Rebecca = new Character("Rebecca", "Sniper", 44, 24, 27, 29, 10, 15, 24, 6, rebeccaInv);
        characters.add(Rebecca);

        Item[] bartreInv = {items[59], items[23], items[22]};
        Character Bartre = new Character("Bartre", "Warrior", 60, 28, 20, 20, 18, 12, 16, 15, bartreInv);
        characters.add(Bartre);

        Item[] oswinInv = {items[17], items[20], items[14]};
        Character Oswin = new Character("Oswin", "General", 59, 27, 21, 18, 29, 14, 12, 16, oswinInv);
        characters.add(Oswin);

        Item[] guyInv = {items[3], items[0], items[46]};
        Character Guy = new Character("Guy", "Swordmaster", 53, 17, 29, 30, 11, 9, 20, 6, guyInv);
        characters.add(Guy);

        Item[] ravenInv = {items[5], items[49], items[3]};
        Character Raven = new Character("Raven", "Hero", 58, 25, 27, 26, 16, 7, 14, 9, ravenInv);
        characters.add(Raven);

        Item[] canasInv = {items[44], items[43], items[42]};
        Character Canas = new Character("Canas", "Druid", 47, 25, 23, 20, 14, 24, 14, 8, canasInv);
        characters.add(Canas);

        Item[] dartInv = {items[24], items[57], items[21]};
        Character Dart = new Character("Dart", "Berserker", 60, 30, 18, 28, 15, 9, 14, 13, dartInv);
        characters.add(Dart);

        Item[] fioraInv = {items[55], items[11], items[12]};
        Character Fiora = new Character("Fiora", "Falcon Knight", 48, 21, 25, 28, 15, 25, 15, 6, fioraInv);
        characters.add(Fiora);

        Item[] legaultInv = {items[2], items[3], items[1]};
        Character Legault = new Character("Legault", "Assassin", 42, 15, 20, 30, 18, 11, 26, 9, legaultInv);
        characters.add(Legault);

        Item[] isadoraInv = {items[11], items[19], items[4]};
        Character Isadora = new Character("Isadora", "Paladin", 43, 19, 18, 25, 11, 11, 18, 6, isadoraInv);
        characters.add(Isadora);

        Item[] heathInv = {items[14], items[56], items[55]};
        Character Heath = new Character("Heath", "Wyvern Lord", 57, 27, 25, 23, 20, 8, 13, 10, heathInv);
        characters.add(Heath);

        Item[] hawkeyeInv = {items[21], items[59], items[22]};
        Character Hawkeye = new Character("Hawkeye", "Berserker", 56, 25, 18, 15, 16, 16, 18, 16, hawkeyeInv);
        characters.add(Hawkeye);

        Item[] geitzInv = {items[21], items[61], items[20]};
        Character Geitz = new Character("Geitz", "Warrior", 55, 25, 17, 19, 13, 7, 18, 13, geitzInv);
        characters.add(Geitz);

        Item[] pentInv = {items[35], items[34], items[33]};
        Character Pent = new Character("Pent", "Sage", 40, 21, 22, 23, 14, 20, 20, 8, pentInv);
        characters.add(Pent);

        Item[] louiseInv = {items[29], items[30], items[27]};
        Character Louise = new Character("Louise", "Sniper", 40, 19, 22, 24, 11, 18, 22, 6, louiseInv);
        characters.add(Louise);

        Item[] karelInv = {items[47], items[48], items[4]};
        Character Karel = new Character("Karel", "Swordmaster", 39, 20, 29, 24, 13, 12, 18, 9, karelInv);
        characters.add(Karel);

        Item[] harkenInv = {items[51], items[50], items[7]};
        Character Harken = new Character("Harken", "Hero", 46, 25, 23, 21, 19, 13, 13, 11, harkenInv);
        characters.add(Harken);

        Item[] ninoInv = {items[36], items[34], items[35]};
        Character Nino = new Character("Nino", "Sage", 39, 25, 27, 26, 10, 25, 26, 4, ninoInv);
        characters.add(Nino);

        Item[] jaffarInv = {items[3], items[1], items[45]};
        Character Jaffar = new Character("Jaffar", "Assassin", 38, 20, 26, 26, 17, 13, 11, 8, jaffarInv);
        characters.add(Jaffar);

        Item[] vaidaInv = {items[15], items[13], items[54]};
        Character Vaida = new Character("Vadia", "Wyvern Lord", 50, 24, 21, 16, 24, 8, 13, 12, vaidaInv);
        characters.add(Vaida);

        Item[] renaultInv = {items[40], items[39], items[38]};
        Character Renault = new Character("Renault", "Bishop", 44, 13, 23, 21, 16, 19, 10, 9, renaultInv);
        characters.add(Renault);

        Item[] farinaInv = {items[13], items[11], items[12]};
        Character Farina = new Character("Farina", "Falcon Knight", 49, 23, 24, 26, 19, 23, 23, 6, farinaInv);
        characters.add(Farina);

        Item[] karlaInv = {items[47], items[50], items[2]};
        Character Karla = new Character("Karla", "Swordmaster", 38, 17, 27, 27, 12, 15, 22, 7, karlaInv);
        characters.add(Karla);

        return characters;
    }

    public static Item[] constructItems()
    {
        Item[] items = new Item[62];
        Weapon IronSword = new Weapon("Iron Sword", "Sword", 'E', 46, 5, 5, 90, 0, 1);
        items[0] = IronSword;
        Weapon SlimSword = new Weapon("Slim Sword", "Sword", 'E', 30, 2, 3, 100, 5, 1);
        items[1] = SlimSword;
        Weapon SteelSword = new Weapon("Steel Sword", "Sword", 'D', 30, 10, 8, 75, 0, 1);
        items[2] = SteelSword;
        Weapon KillingEdge = new Weapon("Killing Edge", "Sword", 'C', 20, 7, 9, 75, 30, 1);
        items[3] = KillingEdge;
        Weapon SilverSword = new Weapon("Silver Sword", "Sword", 'A', 20, 8, 13, 80, 0, 1);
        items[4] = SilverSword;
        Weapon RegalBlade = new Weapon("Regal Blade", "Sword", 'S', 25, 9, 20, 85, 0, 1);
        items[5] = RegalBlade;
        Weapon ManiKatti = new Weapon("Mani Katti", "Sword", 'S', 45, 3, 8, 80, 20, 1);
        items[6] = ManiKatti;
        Weapon Rapier = new Weapon("Rapier", "Sword", 'S', 40, 5, 7, 95, 10, 1);
        items[7] = Rapier;
        Weapon Durandal = new Weapon("Durandal", "Sword", 'S', 20, 16, 17, 90, 0, 1);
        items[8] = Durandal;
        Weapon IronLance = new Weapon("Iron Lance", "Lance", 'E', 45, 8, 7, 80, 0, 1);
        items[9] = IronLance;
        Weapon SlimLance = new Weapon("Slim Lance", "Lance", 'E', 30, 4, 4, 85, 5, 1);
        items[10] = SlimLance;
        Weapon Javelin = new Weapon("Javelin", "Lance", 'E', 20, 11, 6, 65, 0, 2);
        items[11] = Javelin;
        Weapon SteelLance = new Weapon ("Steel Lance", "Lance", 'D', 30, 13, 10, 70, 0, 1);
        items[12] = SteelLance;
        Weapon KillerLance = new Weapon("Killer Lance", "Lance", 'C', 20, 9, 10, 70, 30, 1);
        items[13] = KillerLance;
        Weapon ShortSpear = new Weapon("Short Spear", "Lance", 'C', 18, 12, 9, 60, 0, 2);
        items[14] = ShortSpear;
        Weapon Spear = new Weapon("Spear", "Lance", 'B', 15, 10, 12, 70, 5, 2);
        items[15] = Spear;
        Weapon SilverLance = new Weapon("Silver Lance", "Lance", 'A', 20, 10, 14, 75, 0, 1);
        items[16] = SilverLance;
        Weapon RexHasta = new Weapon("Rex Hasta", "Lance", 'S', 25, 11, 21, 80, 0, 1);
        items[17] = RexHasta;
        Weapon HandAxe = new Weapon("Hand Axe", "Axe", 'E', 20, 12, 7, 60, 0, 2);
        items[18] = HandAxe;
        Weapon IronAxe = new Weapon("Iron Axe", "Axe", 'E', 45, 10, 8, 75, 0, 1);
        items[19] = IronAxe;
        Weapon SteelAxe = new Weapon("Steel Axe", "Axe", 'E', 30, 15, 11, 65, 0, 1);
        items[20] = SteelAxe;
        Weapon KillerAxe = new Weapon("Killer Axe", "Axe", 'C', 20, 11, 11, 65, 30, 1);
        items[21] = KillerAxe;
        Weapon Tomahawk = new Weapon ("Tomahawk", "Axe", 'A', 15, 14, 13, 65, 0, 2);
        items[22] = Tomahawk;
        Weapon SilverAxe = new Weapon("Silver Axe", "Axe", 'A', 20, 12, 15, 70, 0, 1);
        items[23] = SilverAxe;
        Weapon Basilikos = new Weapon("Basilikos", "Axe", 'S', 25, 13, 22, 75, 0, 1);
        items[24] = Basilikos;
        Weapon Armads = new Weapon("Armads", "Axe", 'S', 25, 18, 18, 85, 0, 1);
        items[25] = Armads;
        Weapon IronBow = new Weapon("Iron Bow", "Bow", 'E', 45, 5, 6, 85, 0, 2);
        items[26] = IronBow;
        Weapon ShortBow = new Weapon("Short Bow", "Bow", 'D', 22, 3, 5, 85, 10, 2);
        items[27] = ShortBow;
        Weapon SteelBow = new Weapon("Steel Bow", "Bow", 'D', 30, 9, 9, 70, 0, 2);
        items[28] = SteelBow;
        Weapon KillerBow = new Weapon("Killer Bow", "Bow", 'C', 20, 7, 9, 75, 30, 2);
        items[29] = KillerBow;
        Weapon SilverBow = new Weapon("Silver Bow", "Bow", 'A', 20, 6, 13, 75, 0, 2);
        items[30] = SilverBow;
        Weapon Rienfleche = new Weapon("Reinfleche", "Bow", 'S', 25, 7, 20, 80, 0, 2);
        items[31] = Rienfleche;
        Weapon Fire = new Weapon("Fire", "Anima", 'E', 40, 4, 5, 90, 0, 2);
        items[32] = Fire;
        Weapon Elfire = new Weapon("Elfire", "Anima", 'C', 30, 10, 10, 85, 0, 2);
        items[33] = Elfire;
        Weapon Fimbulvetr = new Weapon("Fimbulvetr", "Anima", 'A', 20, 12, 13, 80, 0, 2);
        items[34] = Fimbulvetr;
        Weapon Excalibur = new Weapon("Excalibur", "Anima", 'S', 25, 13, 18, 90, 10, 2);
        items[35] = Excalibur;
        Weapon Forblaze = new Weapon("Forblaze", "Anima", 'S', 20, 11, 14, 85, 5, 2);
        items[36] = Forblaze;
        Weapon Shine = new Weapon("Shine", "Light", 'D', 30, 8, 6, 90, 8, 2);
        items[37] = Shine;
        Weapon Divine = new Weapon("Divine", "Light", 'C', 25, 12, 8, 85, 10, 2);
        items[38] = Divine;
        Weapon Aura = new Weapon("Aura", "Light", 'A', 20, 15, 12, 85, 15, 2);
        items[39] = Aura;
        Weapon Luce = new Weapon("Luce", "Light", 'S', 25, 16, 16, 95, 25, 2);
        items[40] = Luce;
        Weapon Flux = new Weapon("Flux", "Dark", 'D', 45, 8, 7, 80, 0, 2);
        items[41] = Flux;
        Weapon Nosferatu = new Weapon("Nosferatu", "Dark", 'C', 20, 14, 10, 70, 0, 2);
        items[42] = Nosferatu;
        Weapon Fenrir = new Weapon("Fenrir", "Dark", 'A', 20, 18, 15, 70, 0, 2);
        items[43] = Fenrir;
        Weapon Gerspenst = new Weapon("Gerspenst", "Dark", 'S', 25, 20, 23, 80, 0, 2);
        items[44] = Gerspenst;
        Weapon Armorslayer = new Weapon("Armorslayer", "Sword", 'D', 18, 11, 8, 80, 0, 1, "Effective vs. Armored Units (Generals, & Great Lords)");
        items[45] = Armorslayer;
        Weapon Longsword = new Weapon("Longsword", "Sword", 'D', 18, 11, 6, 85, 0, 1, "Effective vs. Horseback Units (Nomads & Paladins)");
        items[46] = Longsword;
        Weapon WoDao = new Weapon("Wo Dao", "Sword", 'D', 20, 5, 8, 75, 35, 1);
        items[47] = WoDao;
        Weapon Wyrmslayer = new Weapon("Wyrmslayer", "Sword", 'C', 20, 5, 7, 75, 0, 1, "Effective vs Wyrm riding Units (Wyrm Riders, Wyrm Lords");
        items[48] = Wyrmslayer;
        Weapon Lightbrand = new Weapon("Light Brand", "Sword", 'C', 25, 9, 9, 70, 0, 2, "Targets Resistance, Inflicts light magic damage");
        items[49] = Lightbrand;
        Weapon Lancereaver = new Weapon("Lancereaver", "Sword", 'C', 15, 9, 9, 75, 5, 1, "Strong vs. Lances. Weak vs. Axes.");
        items[50] = Lancereaver;
        Weapon BraveSword = new Weapon("Brave Sword", "Sword", 'B', 30, 12, 9, 75, 0, 1, "User will always strike twice.");
        items[51] = BraveSword;
        Weapon WindSword = new Weapon("Wind Sword", "Sword", 'B', 40, 9, 9, 70, 0, 2, "Targets Resistance. Inflicts Wind damage. Effective vs. flying units (Wyrm and Pegasus Riders)");
        items[52] = WindSword;
        Weapon Heavyspear = new Weapon("Heavy Spear", "Lance", 'D', 16, 14, 9, 70, 0, 1, "Effective vs. Armored Units (Generals, & Great Lords)");
        items[53] = Heavyspear;
        Weapon Horseslayer = new Weapon("Horseslayer", "Lance", 'D', 16, 13, 7, 70, 0, 1, "Effective vs. Horseback Units (Nomads & Paladins)");
        items[54] = Horseslayer;
        Weapon Axereaver = new Weapon("Axereaver", "Lance", 'C', 15, 11, 10, 70, 5, 1, "Strong vs. Axes. Weak vs Swords.");
        items[55] = Axereaver;
        Weapon BraveLance = new Weapon("Brave Lance", "Lance", 'B', 30, 14, 10, 70, 0, 1, "User will always strike twice.");
        items[56] = BraveLance;
        Weapon Halberd = new Weapon("Halberd", "Axe", 'D', 18, 15, 10, 60, 0, 1, "Effective vs. Horseback Units (Nomads, Paladins)");
        items[57] = Halberd;
        Weapon Hammer = new Weapon("Hammer", "Axe", 'D', 20, 15, 10, 55, 0, 1, "Effective vs. Armored Units (Generals, & Great Lords)");
        items[58] = Hammer;
        Weapon Swordreaver = new Weapon("Swordreaver", "Axe", 'C', 15, 13, 11, 65, 5, 1, "Strong vs. Swords. Weak vs. Lances.");
        items[59] = Swordreaver;
        Weapon Braveaxe = new Weapon("Brave Axe", "Axe", 'B', 30, 16, 10, 65, 0, 1, "User will always strike twice.");
        items[60] = Braveaxe;
        Weapon Bravebow = new Weapon("Bravebow", "Bow", 'B', 30, 12, 10, 70, 0, 2, "User will always strike twice.");
        items[61] = Bravebow;
        return items;
    }


    public static void PlayMusic()
    {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("music.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-20.0f); // Reduce volume by 10 decibels.
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    public static void PlaySelectionSound()
    {
        try{
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("selection.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10.0f); // Reduce volume by 10 decibels.
            clip.start();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    public static void PlayVictory()
    {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("victory.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
