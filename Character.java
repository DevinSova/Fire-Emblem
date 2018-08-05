import java.util.Scanner;
public class Character
{
    private Scanner input;
    private String Name;
    private String Type;
    private Item[] Inventory;
    private final int MaxHP;     //Max HP
    private int currentHP;       //Current HP
    private int StrengthMagic;   //Strength and Magic
    private int Skill;           //Hit Chance
    private int Speed;           //Attack # of times
    private int Defense;         //Physical Defense
    private int Resistance;      //Magic Defense
    private int Luck;            //Effects Multiple Things
    private int Constitution;    //Size of Unit

    public Character(String name, String type, int Maxhp, int StrMgc, int Skl, int Spd, int Def, int Res, int Luc, int Con, Item[] inven)
    {
        input = new Scanner(System.in);
        Name = name;
        Type = type;
        Inventory = inven;
        MaxHP = Maxhp;
        currentHP = Maxhp;
        StrengthMagic = StrMgc;
        Skill = Skl;
        Speed = Spd;
        Defense = Def;
        Resistance = Res;
        Luck = Luc;
        Constitution = Con;
    }

    public Object[] Attack(Character a, boolean b) throws Exception
    {
        int num = (int)Math.random() * Inventory.length + 1;
        if(b)
        {
            printInventory();
            num = -1;
            while(num > Inventory.length || num < 1)
            {
                System.out.print("Input index of weapon: ");
                num = input.nextInt();
                MainMethods.PlaySelectionSound();
            }
        }
        switchItems(num - 1);
        if(b)
        {
            MainMethods.checkCombat(this, a);
            input.nextLine();
            System.out.print("Confirm Attack (Y/N): ");
            String answer = input.nextLine();
            MainMethods.PlaySelectionSound();
            if(answer.equalsIgnoreCase("y"))
                return MainMethods.Combat(this, a);
            else
                return new Object[] {this, a, false};
        }
        System.out.println(this.Name() + " is targeting " + a.Name() + " with their " + this.currentWep().Name());
        Thread.sleep(2000);
        MainMethods.checkCombat(this, a);
        Thread.sleep(6000);
        return MainMethods.Combat(this, a);
    }

    public void switchItems(int i)
    {
        Item temp = Inventory[i];
        Inventory[i] = Inventory[0];
        Inventory[0] = temp;
    }

    public String Name()
    {
        return Name;
    }

    public int StrengthMagic()
    {
        return StrengthMagic;
    }

    public int Skill()
    {
        return Skill;
    }

    public int Speed()
    {
        return Speed;
    }

    public int Defense()
    {
        return Defense;
    }

    public int Resistance()
    {
        return Resistance;
    }

    public boolean CheckHP() //Returns true if dead.
    {
        if (currentHP > 0)
            return false;
        return true;
    }

    public int maxHP()
    {
        return MaxHP;
    }

    public int currentHP()
    {
        return currentHP;
    }

    public void TakeDamage(int d)
    {
        currentHP = currentHP - d;
    }

    public int Constitution()
    {
        return Constitution;
    }

    public int Luck()
    {
        return Luck;
    }

    public Item[] Inventory()
    {
        return Inventory;
    }

    public int AttackSpeed()
    {
        Weapon temp = currentWep();
        if(temp.Weight() < Constitution)
            return Speed;
        return Speed - (temp.Weight() - Constitution);
    }

    public int Evade()
    {
        return (AttackSpeed() * 2) + Luck;
    }

    public Weapon currentWep()
    {
        return (Weapon)Inventory[0];
    }

    public String Type()
    {
        return Type;
    }

    public void printInventory()
    {
        System.out.print("\n" + Name + "'s Inventory:\n");
        for(int i = 1; i < Inventory.length + 1; i++)
        {
            System.out.print(i  + ": " + Inventory[i - 1] + "\n");
        }
        System.out.println("\n");
    }

    public void printStats()
    {
        if(Type.equals("Sage") || Type.equals("Bishop") || Type.equals("Druid"))
            System.out.print("Stats: Magic: " + StrengthMagic + "\tSkill: " + Skill + "\tSpeed: " + Speed + "\tDefense: " + Defense + "\tResistance: " + Resistance + "\tLuck: " + Luck);
        else
            System.out.print("Stats: \tStrength: " + StrengthMagic + "\tSkill: " + Skill + "\tSpeed: " + Speed + "\tDefense: " + Defense + "\tResistance: " + Resistance + "\tLuck: " + Luck);
    }

    public void printCharacter(boolean a)
    {
        if(Name.length() <= 5 && Type.length() <= 6)
            System.out.println(Name + "\t\t" + Type + ",\t\tHP: (" + currentHP + "/" + MaxHP + ") Current Weapon: " + this.currentWep().Name);
        else if(Name.length() < 5 && Type.length() > 6)
            System.out.println(Name + "\t\t" + Type + ",\tHP: (" + currentHP + "/" + MaxHP + ") Current Weapon: " + this.currentWep().Name);
        else if(Name.length() > 5 && Type.length() < 6)
            System.out.println(Name + "\t" + Type + ",\tHP: (" + currentHP + "/" + MaxHP + ") Current Weapon: " + this.currentWep().Name);
        else
            System.out.println(Name + "\t" + Type + ",\tHP: (" + currentHP + "/" + MaxHP + ") Current Weapon: " + this.currentWep().Name);
        if(a)
        {
            printStats();
            printInventory();
        }
    }
}
