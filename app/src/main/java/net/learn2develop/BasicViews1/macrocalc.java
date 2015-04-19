package net.learn2develop.BasicViews1;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import net.learn2develop.BasicViews1.piefragment.piechartinterface;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;
import java.io.File;
import android.widget.GridLayout;
import android.widget.TextView;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;


/**
 * Created by joshua on 2015-03-04.
 */
public class macrocalc extends Activity implements piechartinterface {
    int rc = 100,protein,carbs,fats,doneLoading =1;
    piefragment frag = null;
    public String PREFS_NAME =  Environment.getExternalStorageDirectory() + "/meals.txt";
    public File file = new File(PREFS_NAME);
    ArrayList<mealEntry> mealsArray = new ArrayList<mealEntry>();

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator);
       frag = null;
        try{
            FileInputStream fin = openFileInput("test.txt");
            int c;
            String temp="";
            while( (c = fin.read()) != -1){
                temp = temp + Character.toString((char)c);
            }
            String[] list = temp.split("\\|");
            for(String t : list)
            {
               doneLoading =0;
                String[] tempList =  t.split("\\ ");
                mealEntry tempMeal = new mealEntry(Integer.parseInt(tempList[0]),Integer.parseInt(tempList[1])
                ,Integer.parseInt(tempList[2]),Integer.parseInt(tempList[3]));
                updateMealView(tempMeal);
                updateTotals(tempMeal);
                mealsArray.add(tempMeal);
            }

            setFrag();
        }catch(Exception e){

        }
        doneLoading =1;


    }


    public void newMeal(View view) {
        startActivityForResult(new Intent(this, addmealactivity.class), rc);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == rc) {
            if (resultCode == RESULT_OK) {
                int protein, carbs, fats, calories;
                protein = data.getIntExtra("Protein", 0);
                carbs = data.getIntExtra("Carbs", 0);
                fats = data.getIntExtra("Fats", 0);
                calories = data.getIntExtra("Calories", 0);
                mealEntry entry = new mealEntry(protein,carbs,fats,calories);
                mealsArray.add(entry);
                updateMealView(entry);
                updateTotals(entry);


            }

        }
    }
     public void updateMealView(mealEntry entry) {
        GridLayout meals = (GridLayout) findViewById(R.id.meals);
        TextView protext = new TextView(this);
        protext.setText("" + entry.carbs);
        protext.setTextColor(Color.BLUE);
        protext.setTextSize(22);
        meals.addView(protext);

        protext = new TextView(this);
        protext.setText("" + entry.protein);
        protext.setTextColor(Color.GREEN);
        protext.setTextSize(22);
        meals.addView(protext);



        protext = new TextView(this);
        protext.setText("" + entry.fats);
        protext.setGravity(Gravity.TOP);
        protext.setTextColor(Color.MAGENTA);
        protext.setTextSize(22);
        meals.addView(protext);

        protext = new TextView(this);
        protext.setText("" + entry.calories);
        protext.setTextColor(Color.GRAY);
        protext.setTextSize(22);
        meals.addView(protext);
        meals.invalidate();
    }
    public void analyze(View view)
    {

        Intent load = new Intent(this,analysis.class);
        TextView ptot = (TextView) findViewById(R.id.ptot);
        String[] temp = ptot.getText().toString().split(" ");
        load.putExtra("Protein",Integer.parseInt(temp[1]));
        ptot = (TextView) findViewById(R.id.ctot);
        temp = ptot.getText().toString().split(" ");
        load.putExtra("Carbs",Integer.parseInt(temp[1]));
        ptot = (TextView) findViewById(R.id.ftot);
        temp = ptot.getText().toString().split(" ");
        load.putExtra("Fats",Integer.parseInt(temp[1]));
        ptot = (TextView) findViewById(R.id.caltot);
        temp = ptot.getText().toString().split(" ");
        load.putExtra("Calories",Integer.parseInt(temp[1]));


        if(Integer.parseInt(temp[1])> 1000) {
            startActivity(load);
            try {
                mealsArray = new ArrayList<mealEntry>();
                FileOutputStream fos = openFileOutput("test.txt", Context.MODE_PRIVATE);
                fos.close();

                this.onCreate(null);
            }
            catch(Exception e){}
      }
        else
        {
           Toast.makeText(getBaseContext(),"Need More Data",Toast.LENGTH_LONG).show();
        }
    }
    public void updateTotals(mealEntry entry)
    {

        TextView ptot = (TextView) findViewById(R.id.ptot);
        String[] temp = ptot.getText().toString().split(" ");
        ptot.setTextColor(Color.BLUE);
        protein = (Integer.parseInt(temp[1]) +entry.protein);
        ptot.setText("P " + protein );

        ptot = (TextView) findViewById(R.id.ctot);
        temp = ptot.getText().toString().split(" ");
        ptot.setTextColor(Color.GREEN);
        carbs = (Integer.parseInt(temp[1]) + entry.carbs);
        ptot.setText("C " +carbs );


        ptot = (TextView) findViewById(R.id.ftot);
        temp = ptot.getText().toString().split(" ");
        ptot.setTextColor(Color.MAGENTA);
        fats = (Integer.parseInt(temp[1]) + entry.fats);
        ptot.setText("F " + fats);


        ptot = (TextView) findViewById(R.id.caltot);
        temp = ptot.getText().toString().split(" ");
        ptot.setTextColor(Color.GRAY);
        ptot.setText("Cal " + (Integer.parseInt(temp[1]) + entry.calories));

      if(doneLoading ==1 ) {setFrag();}
    }
    public void setFrag()
    {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if(frag == null)
            {
                frag = new piefragment();
                fragmentTransaction.add(R.id.piehere, frag);
            }
            else
            {

                frag.refresh();
            }
            fragmentTransaction.commit();
    }
    public int[] getNumbers()
    {
        int[] num = {(protein *4),(carbs *4),(fats *9)};
        return num;
    }

    @Override
    protected void onDestroy() {


        try {
         FileOutputStream fos = openFileOutput("test.txt", Context.MODE_PRIVATE);;
           for(mealEntry meal : mealsArray)
            {

                fos.write(meal.toString().getBytes());
            }
            fos.close();
        }
        catch(Exception a)
        {
        }
        super.onDestroy();

    }
}