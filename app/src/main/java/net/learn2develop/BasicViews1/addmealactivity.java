/* Source: Wei-Ming Lee's website
 * http://www.wrox.com/WileyCDA/WroxTitle/Beginning-Android-4-Application-Development.productCd-1118199545.html
 * Modified and Commented by Peter Liu (9/14/2013) 
 */
package net.learn2develop.BasicViews1;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import net.learn2develop.BasicViews1.piefragment.piechartinterface;


import android.widget.EditText;
import android.widget.Toast;

public class addmealactivity extends Activity implements piechartinterface {
    TextWatcher tw = null;
    piefragment frag = null;
    EditText protein,carbs,fat;
    public int[] getNumbers()
    {
        int ip=0,ic=0,ifat=0;
        if (protein.getText().length() > 0){ip = Integer.parseInt(protein.getText().toString()) *4;}
        if (carbs.getText().length() > 0){ic = Integer.parseInt(carbs.getText().toString()) *4;}
        if (fat.getText().length() > 0){ifat = Integer.parseInt(fat.getText().toString()) *9;}
        int[] num = {ip,ic,ifat};
        return num;
    }


    public void updatefrag()
    {

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(frag == null  )
        {
            frag = new piefragment();
            fragmentTransaction.add(R.id.pieadd, frag);
        }
        else
        {
            frag.refresh();
        }
        fragmentTransaction.commit();
    }

    public void setup()
    {

          tw = new TextWatcher() {

                public void afterTextChanged(Editable s) {

                     updatefrag();
                }
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {                }
                public void onTextChanged(CharSequence s, int start, int before, int count) {                }
            };
            protein = (EditText) findViewById(R.id.proteinedit);
            carbs = (EditText) findViewById(R.id.carbsedit);
            fat = (EditText) findViewById(R.id.fatedit);
            protein.addTextChangedListener(tw);
            carbs.addTextChangedListener(tw);
            fat.addTextChangedListener(tw);


    }
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addentry);
        
        setup();
    }

    @Override
    protected void onPause() {

           super.onPause();

    }

    @Override
    protected void onResume() {
           super.onResume();
     }

    public void newMealEntry(View view) {

    int calories = 0, error = 0;
    if (protein.getText().length() > 0) {
        calories = Integer.parseInt(protein.getText().toString()) * 4;
    } else {
        displayToast("You did not enter a number for protein");
        error++;
    }
    if (carbs.getText().length() > 0) {
        calories += Integer.parseInt(carbs.getText().toString()) * 4;
    } else if (error == 0) {
        displayToast("You did not enter a number for carbs");
        error++;
    }
    if (fat.getText().length() > 0) {
        calories += Integer.parseInt(fat.getText().toString()) * 9;
    } else if (error == 0) {
        displayToast("You did not enter a number for fat");
        error++;
    }
    if (error == 0) {
        if (calories > 2000) {
            displayToast("Eating this much in one sitting is impossible, recalculate your meals");
        } else if (calories > 1500) {
            displayToast("Make sure to eat less then 1500 calories in a sitting in the future");
            sendEntry(protein, carbs, fat, calories);
        } else if (calories < 100) {
            displayToast("Meals must have at least 100 calories to be added to the calculator");
        } else if (calories < 500) {
            displayToast("That is not much of a meal...");
            sendEntry(protein, carbs, fat, calories);
        } else {
            sendEntry(protein, carbs, fat, calories);
        }


    }
}
    public void sendEntry(EditText protein,EditText carbs,EditText fat,int calories)
    {
        int iprotein = Integer.parseInt(protein.getText().toString());
        int icarbs = Integer.parseInt(carbs.getText().toString());
        int ifat = Integer.parseInt(fat.getText().toString());
        entryCommentary(iprotein,icarbs,ifat,calories);
        Intent rc = new Intent();
        rc.putExtra("Protein", iprotein );
        rc.putExtra("Carbs", icarbs );
        rc.putExtra("Fats", ifat );
        rc.putExtra("Calories", calories);
        this.setResult(RESULT_OK, rc);
        finish();

    }
    public void entryCommentary(int protein,int carbs, int fat,int calories)
    {
        fat *= 9;
        protein *= 4;
        carbs *=4;
        if(fat > (calories * .75 )){displayToast("This meal contained a great deal of fat.");}
        if(fat < (calories * .05 )){displayToast("This meal contained very little or no fat");}
        if(protein > (calories * .75 )){displayToast("This meal contained a great deal of protein.");}
        if(protein < (calories * .05 )){displayToast("This meal contained very little or no protein");}
        if(carbs > (calories * .75 )){displayToast("This meal contained a great deal of carbs.");}
        if(carbs < (calories * .05 )){displayToast("This meal contained very little or no carbs");}
    }

    private void displayToast(String msg) {
        Toast.makeText(getBaseContext(), msg,  Toast.LENGTH_SHORT).show();

     /*   try {
            Thread.sleep(Toast.LENGTH_SHORT, 0);
        }catch(Exception c ){}*/
    }

}