/**
 * Write a description of class Weapon here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Item
{
    public String Name;
    public int Uses;

    public Item(String n, int u)
    {
        Name = n;
        Uses = u;
    }

    public String toString()
    {
        return (Name);
    }

    public void decreaseUse()
    {
        Uses -= 1;
    }
}
