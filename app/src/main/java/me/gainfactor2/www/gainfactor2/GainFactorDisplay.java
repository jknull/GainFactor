package me.gainfactor2.www.gainfactor2;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
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


public class GainFactorDisplay extends ActionBarActivity {


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
        setContentView(R.layout.activity_gain_factor_display);

        setGainResults();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gain_factor_display, menu);
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

    // This MUST be implemented to display results. Remember that we don't necessarily require a price
    public void setGainResults(){
        TextView  editTextGainFactor = (TextView ) findViewById(R.id.EditTextGainFactor);
        TextView  editTextGainValue = (TextView ) findViewById(R.id.EditTextGainValue);

        // Tedius but robust rounding steps
        double tempGF = Globals.f.getGF() * 1000.0;
        tempGF = Math.round(tempGF);
        tempGF = tempGF / 1000.0;
        double tempPP = Globals.f.getPP() * 1000.0;
        tempPP = Math.round(tempPP);
        tempPP = tempPP / 1000.0;


        //gFactorString.format("%.2f", Globals.f.getGF());

        // The Gain Value is only displayed if it was calculated
        editTextGainFactor.setText(Double.toString(tempGF), TextView.BufferType.NORMAL);
        if (!Double.isNaN(Globals.f.getPP())) {
            editTextGainValue.setText(Double.toString(tempPP), TextView.BufferType.NORMAL);
        }
        else
            editTextGainValue.setText("-", TextView.BufferType.NORMAL);

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

    }

    // The text for the item name is from editTextItemName
    public void addFavourite(View view){
        // We need to get the input from the fields
        EditText editTextName = (EditText) findViewById(R.id.editTextItemName);

        // First we need to make sure that the two required fields have entries and are valid
        if (editTextName.length() == 0) {
            Context context = getApplicationContext();
            CharSequence text = "Missing Name.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            return;
        }

        Context context = getApplicationContext();
        CharSequence text = "Entry added as favourite";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        view.setClickable(false); // Turns button off //
        view.setVisibility(View.INVISIBLE);

        // Also make text entry invisible
        editTextName.setVisibility(editTextName.INVISIBLE);

        Globals.l.add(editTextName.getText().toString(),Globals.f);


    }
}
