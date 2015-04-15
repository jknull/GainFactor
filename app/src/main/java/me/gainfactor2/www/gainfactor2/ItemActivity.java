package me.gainfactor2.www.gainfactor2;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class ItemActivity extends ActionBarActivity {
    //TODO add to every activity
    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        File file = new File("LUT.ser");
        if(!file.exists()) {
            try {
                FileOutputStream fileOut = openFileOutput("LUT.ser", Context.MODE_PRIVATE);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                LUT lut = new LUT();
                out.writeObject(lut);
                out.close();
                fileOut.close();
            }catch (Exception e){}
        }else{
            try{
                FileInputStream fileIn = openFileInput("LUT.ser");
                ObjectInputStream in = new ObjectInputStream((fileIn));
                Globals.l = (LUT)in.readObject();
                fileIn.close();
                in.close();
            }catch(Exception e){}


        }
    }
    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first
        try {
            FileOutputStream fileOut = openFileOutput("LUT.ser", Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(Globals.l);
            out.close();
            fileOut.close();
        }catch(Exception e){}


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        Food curFood = Globals.l.get(Globals.name);
        String GF = Double.toString(curFood.getGF());
        String PP = Double.toString(curFood.getPP());
        String name = Globals.name;

        // Get handles to all of th elabels we wish to change
        TextView foodName = (TextView) findViewById(R.id.TextViewFoodName);
        TextView gainFactor = (TextView) findViewById(R.id.TextViewGainFactor);
        TextView gainValue = (TextView) findViewById(R.id.TextViewGainValue);





        // Setting the name
        foodName.setText(name, TextView.BufferType.NORMAL);



        // Rounding
        // Tedius but robust rounding steps
        double tempGF = Globals.f.getGF() * 1000.0;
        tempGF = Math.round(tempGF);
        tempGF = tempGF / 1000.0;
        double tempPP = Globals.f.getPP() * 1000.0;
        tempPP = Math.round(tempPP);
        tempPP = tempPP / 1000.0;



        // The Gain Value is only displayed if it was calculated
        gainFactor.setText(Double.toString(tempGF), TextView.BufferType.NORMAL);
        if (!Double.isNaN(Globals.f.getPP())) {
            gainValue.setText(Double.toString(tempPP), TextView.BufferType.NORMAL);
        }
        else
            gainValue.setText("-", TextView.BufferType.NORMAL);



        //Get handles to the text boxes
        TextView  gainDescriptor = (TextView ) findViewById(R.id.EditTextDesrip1);
        TextView  valueDescriptor = (TextView ) findViewById(R.id.EditTextDesrip2);

        // Set the descriptors based on research
        if (tempGF < 0.5)
            gainDescriptor.setText("(Low Gains)", TextView.BufferType.NORMAL);
        else if (tempGF < 1.5)
            gainDescriptor.setText("(Moderate Gains)", TextView.BufferType.NORMAL);
        else if (tempGF < 2.0)
            gainDescriptor.setText("(High Gains)", TextView.BufferType.NORMAL);
        else if (tempGF >= 2.0)
            gainDescriptor.setText("(Extreme Gains)", TextView.BufferType.NORMAL);


        if (tempPP < 50)
            valueDescriptor.setText("(Poor Value)", TextView.BufferType.NORMAL);
        else if (tempPP < 200)
            valueDescriptor.setText("(Moderate Value)", TextView.BufferType.NORMAL);
        else if (tempPP >= 200)
            valueDescriptor.setText("(Great Value)", TextView.BufferType.NORMAL);
        else
            valueDescriptor.setText("(-)", TextView.BufferType.NORMAL);


        // Then I will apply descriptors
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void removeItem(View view) {

        Context context = getApplicationContext();
        CharSequence text = "Item deleted from favourites.";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        view.setClickable(false); // Turns button off //
        view.setVisibility(View.INVISIBLE);

        TextView foodName = (TextView) findViewById(R.id.TextViewFoodName);
        foodName.setText("DELETED", TextView.BufferType.NORMAL);

        Globals.l.remove(Globals.name);
    }
}
