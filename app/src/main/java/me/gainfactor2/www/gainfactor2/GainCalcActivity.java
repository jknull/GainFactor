package me.gainfactor2.www.gainfactor2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class GainCalcActivity extends ActionBarActivity {


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

    @Override // R: This is all mandatory
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gain_calc);

    }


    // R: New stuff
    public void calculateGains(View view) {

        // We need to get the input from the fields
        EditText editTextCals = (EditText) findViewById(R.id.editTextCalories);
        EditText editTextServ = (EditText) findViewById(R.id.editTextServing);
        EditText editTextPrice = (EditText) findViewById(R.id.editTextPrice);
        EditText editTextQuantity = (EditText) findViewById(R.id.editTextQuantity);
        EditText editTextProtein = (EditText) findViewById(R.id.editTextProtein);

        // First we need to make sure that the two required fields have entries and are valid
        if (editTextCals.length() == 0 || editTextServ.length() == 0 || editTextProtein.length() == 0) {
            Context context = getApplicationContext();
            CharSequence text = "Missing Values.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            return;
        } //
        else if (Integer.parseInt(editTextServ.getText().toString()) <  Integer.parseInt(editTextProtein.getText().toString()))
        {
            Context context = getApplicationContext();
            CharSequence text = "How is there more protein than total mass?";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            return;
        }

        Intent intent = new Intent(this, GainFactorDisplay.class); // Display Gains displays the results
        String cals = editTextCals.getText().toString();
        String serv = editTextServ.getText().toString();
        double dprice;
        double dquantity;
        if(editTextPrice.length()!=0){
            String price = editTextPrice.getText().toString();
            dprice=Double.parseDouble(price);
        }else{
            dprice=Double.NaN;
        }
        if(editTextQuantity.length()!=0){
            String quantity = editTextQuantity.getText().toString();
            dquantity = Double.parseDouble(quantity);
        }else{
            dquantity=Double.NaN;
        }

        String protein = editTextProtein.getText().toString();

        Globals.f = new Food(dprice,Double.parseDouble(cals),Double.parseDouble(serv),dquantity,Double.parseDouble(protein),false);

        startActivity(intent);
    }
}
