/**
 * Write a description of class Weapon here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Weapon extends Item
{
    private char Rank;      //Rank Character
    private String Type;    //Sword, Lance, Axe, Anima, Light, Dark
    private int Hit;        //Hit Chance
    private int Crit;       //Critical chance
    private int Weight;     //Subtracts Speed from Character
    private int Might;      //Damage
    private int Range;      //Range Reg Weapons = 1, Two squares = 2
    private String Description;
    /**
     * Ranks: D, C, B, A, S, Prf(P)
     * Item Types: Sword, Lance, Axe.
     * Magic Types: Light, Dark, Anima.
     */
    Weapon(String n, String t, char r, int u, int w, int m, int h, int c, int a)
    {
        super(n, u);
        Rank = r;
        Type = t;
        Hit = h;
        Crit = c;
        Weight = w;
        Might = m;
        Range = a;
        Description = "-";
    }
    Weapon(String n, String t, char r, int u, int w, int m, int h, int c, int a, String d)
    {
        super(n, u);
        Rank = r;
        Type = t;
        Hit = h;
        Crit = c;
        Weight = w;
        Might = m;
        Range = a;
        Description = d;
    }

    public String Name()
    {
        return Name;
    }

    public char Rank()
    {
        return Rank;
    }

    public String Type()
    {
        return Type;
    }

    public int Hit()
    {
        return Hit;
    }

    public int Crit()
    {
        return Crit;
    }

    public int Weight()
    {
        return Weight;
    }

    public int Might()
    {
        return Might;
    }

    public int Range()
    {
        return Range;
    }

    public String toString()
    {
        if(Name.length() < 5)
            return (Name + "\t\tRank: " + Rank + "\t\tType: " + Type + "\tHit: " + Hit + "%\tCrit: " + Crit + "%\tWeight: " + Weight + "\tMight: " + Might + "\t" + Description);
        else
            return (Name + "\tRank: " + Rank + "\t\tType: " + Type + "\tHit: " + Hit + "%\tCrit: " + Crit + "%\tWeight: " + Weight + "\tMight: " + Might + "\t" + Description);
    }

    public int compareTriangle(Weapon other)
    {
        int num = 1;
        if(Name.contains("reaver"))
            num *= -1;

        if(other.Name().contains("reaver"))
            num *= -1;

        if(Type == "Sword" && other.Type() == "Axe")
        {
            return num;
        }
        else if(Type == "Axe" && other.Type() == "Sword")
        {
            return -num;
        }
        else if(Type == "Lance" && other.Type() == "Sword")
        {
            return num;
        }
        else if(Type == "Sword" && other.Type() == "Lance")
        {
            return -num;
        }
        else if(Type == "Axe" && other.Type() == "Lance")
        {
            return num;
        }
        else if(Type == "Lance" && other.Type() == "Axe")
        {
            return -num;
        }
        else if(Type == "Light" && other.Type() == "Dark")
        {
            return 1;
        }
        else if(Type == "Dark" && other.Type() == "Light")
        {
            return -1;
        }
        else if(Type == "Anima" && other.Type() == "Light")
        {
            return 1;
        }
        else if(Type == "Light" && other.Type() == "Anima")
        {
            return -1;
        }
        else if(Type == "Dark" && other.Type() == "Anima")
        {
            return 1;
        }
        else if(Type == "Anima" && other.Type() == "Dark")
        {
            return -1;
        }
        else
            return 0;
    }
}

