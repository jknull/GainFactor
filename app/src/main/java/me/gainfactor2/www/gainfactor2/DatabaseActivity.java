package me.gainfactor2.www.gainfactor2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class DatabaseActivity extends ActionBarActivity {

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
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        final ListView listView = (ListView)findViewById(R.id.listView);
        String[] values = getData();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!Globals.l.table.isEmpty()) {
                    int itemPosition = position;
                    String itemValue = (String) listView.getItemAtPosition(position);
                    Globals.name = itemValue;
                    Intent intent = new Intent(view.getContext(), ItemActivity.class); // GainCalcActivity is the activity we want to initiate
                    startActivity(intent);
                }

            }
        });

    }


    public String[] getData(){

           String[] s ;
            if(Globals.l.table.keySet().isEmpty()){
               return new String[]{"No Items Stored"};
            }
             s = Globals.l.table.keySet().toArray(new String[0]);

            return s;


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_database, menu);
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
}
