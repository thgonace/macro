package net.learn2develop.BasicViews1;

import android.app.Activity;
import android.content.Intent;
import net.learn2develop.BasicViews1.piefragment.piechartinterface;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;



/**
 * Created by joshua on 2015-03-06.
 */
public class analysis extends Activity implements piechartinterface {
   /* private static int[] COLORS = new int[] { Color.GREEN, Color.BLUE,Color.MAGENTA};

    private static double[] VALUES = new double[] { 10, 11, 12};

    private static String[] NAME_LIST = new String[] { "Protein", "Carbs", "Fat"};

    private CategorySeries mSeries = new CategorySeries("");

    private DefaultRenderer mRenderer = new DefaultRenderer();

    private GraphicalView mChartView;*/

    LinearLayout scv;
    LinearLayout rcv;
    int protein,carbs,fats,calories;
    public int[] getNumbers()
    {
        int[] num = {(protein *4),(carbs *4),(fats *9)};
        return num;
    }
    public void extract()
    {
        Intent load = getIntent();
        protein = load.getIntExtra("Protein",0);
        carbs = load.getIntExtra("Carbs",0);
        fats = load.getIntExtra("Fats",0);
         calories = load.getIntExtra("Calories",0);
    }
    public void prepAnalysis()
    {
        scv = (LinearLayout)findViewById(R.id.analysis);
        rcv = (LinearLayout) findViewById(R.id.recommendations);
        analyizeProtein();
        analyizeCarbs();
        analyizeFats();
        analyizeCalories();
        rcv.invalidate(); scv.invalidate();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        extract();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.analysis);
        prepAnalysis();
      }


    public void writeAnalysis(String analysis, String recommendation)
    {
        TextView tv = new TextView(this); TextView av = new TextView(this);
        tv.setTextSize(23);  av.setTextSize(23);
        tv.setText(analysis); av.setText(recommendation);
        scv.addView(tv); rcv.addView(av);
    }
    public void analyizeProtein()
    {
        if((protein * 4) >= (calories/3)){
            writeAnalysis("You are eating to much Protein","Eat less meat");
        }
        else if((protein * 4) >= (calories/5)){
            writeAnalysis("You are eating adequate Protein","Continue eating plenty of meat");  }
        else { writeAnalysis("You are not eating enough Protein","Eat more meat"); }
    }
    public void analyizeCarbs()
    {
        if((carbs * 4) >= (calories/2)){
            writeAnalysis("You are eating to many Carbs","Eat less grains");}
        else if((carbs * 4) >= (calories/3)){
            writeAnalysis("You are eating adequate Carbs ","Continue eating adequate amounts of grains");  }
        else{ writeAnalysis("You are not eating enough Carbs","Eat more food containing whole grains");  }

    }
    public void analyizeFats()
    {
        if((fats * 9) >= (calories/2)){
            writeAnalysis("You are eating to much Fat","Eat less fatty foods like cheese or nuts");  }
        else if((carbs * 4) >= (calories/4)){
            writeAnalysis("You are eating adequate Fats ","Continue eating fatty foods like cheese or nuts"); }
        else{
            writeAnalysis("You are not eating enough Fats","You are eating to few fatty foods like cheese or nuts");}

    }
    public void analyizeCalories()
    {
        if(calories > 3000){
            writeAnalysis("You are eating to much ","Eat less food");    }
        else if(calories > 2500){
            writeAnalysis("You are eating adequately ","Continue eating roughly the same amount of food");
        }
        else{
            writeAnalysis("You are not eating enough","You need to start eating more food");
         }

    }


}
