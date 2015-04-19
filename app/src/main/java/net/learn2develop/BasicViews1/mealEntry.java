package net.learn2develop.BasicViews1;

/**
 * Created by joshua on 2015-03-05.
 */
public class mealEntry {
    int protein;
    int carbs;
    int fats;
    int calories;
    public mealEntry(int p ,int c,int f,int cal)
    {
        protein = p;
        carbs =c;
        fats =f;
        calories = cal;
    }
    public String toString()
    {
        String str = "" + protein + " " + carbs + " " + fats + " " + calories + "|";
        return str;
    }
}
