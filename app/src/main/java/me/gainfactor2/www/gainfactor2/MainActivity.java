package me.gainfactor2.www.gainfactor2;

import java.io.File;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

// Logging
import android.util.Log;


public class MainActivity extends ActionBarActivity {
    // Member variables
   private static final String[] GAIN_QUOTES = {"Do you even lift, Bruh?", "Reps for Jesus", "Where's the beach?",
            "Oh, I didn't see you there.", "Beefcake!!!!", "Nobody makes me bleed my own blood!",
            "Get to the choppa!!", "Gainz brah!", "Bi's for the guys.", "Curls for the girls.",
            "Dost thou even hoist?", "Don't skip leg day. Don't skip any day.", "Where have you been all my life?",
            "BRO, LOOK HOW FKIN SWOOL YOUR NECK IS", "Treat yourself to a selfie.", "Come at me, bro.", "Get oiled!",
            "Live swole or die mirin'.", "Traps for the chaps.", "Be the swole you want to see in the world.",
            "That's one small rep for man, and one giant gain for man's thighs.", "Let he who is without swole do the first set.",
            "A rep for a rep leaves the whole world swole.", "I lift, therefore I am - Rene Desquats.", "No. Try not. Lift... or don't lift. There is no try.",
            "I find your lack of swole disturbing.", "Nothing tastes as good as deadlifts feel.", "I came, I squatted, I deadlifted.", "4 sets, and 20 curls ago...",
            "Et tu, glute?", "Come with me if you want to lift.", "You drop 100% of the weights you don't lift.", "Skinny people rarely make history.", "If those peaks were any bigger," +
            "you'd have snow on them.","No mice on those shoulders - too many traps.", "If the bar ain't bending, you're just pretending.",
            "Touch my pecs and call me Wendy.", "LIGHT WEIGHT BABY!!", "Monster gainz!!",
            "Ain't nothin' but a peanut.", "YAAAARRRGGHHHH", "Cardio? Is that Spanish?", "No weight means no date.", "Skinny people rarely make history.", "Skinny people rarely make history."};

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
        setContentView(R.layout.activity_main);

            // Initialize placeholder text for quotes
            drawPhrase();
            // Quote timer
        new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                drawPhrase();
                this.start();
            }
        }.start();
    }


    // Create timer with callback function that animates text

    // NEW ACTIVITY METHODS
    // Gain calculator
    public void spawnGainCalcActivity(View view){
        // This function will open the activity to calculate gains. It doesn't need to send data.
        Intent intent = new Intent(this, GainCalcActivity.class); // GainCalcActivity is the activity we want to initiate
        startActivity(intent);
    }



    // Favourites
    public void spawnFavouritesActivity(View view){
        // This function will open the activity to check the database.
        Intent intent = new Intent(this, DatabaseActivity.class); // GainCalcActivity is the activity we want to initiate
        startActivity(intent);
    }


    // Tutorial
    public void spawnTutorialActivity(View view) {
        Intent intent = new Intent(this, WizardActivity.class); // GainCalcActivity is the activity we want to initiate
        startActivity(intent);
    }


    public void drawPhrase() {
        int i = GAIN_QUOTES.length - 1;

        Random rand = new Random();
        int randomNum = rand.nextInt(i);

        TextView textView = (TextView) findViewById(R.id.textViewQuote); // Get the handle to the text placeholder
        textView.setText(GAIN_QUOTES[randomNum], TextView.BufferType.NORMAL); // Set the phrase text
        textView.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.abc_fade_in));
        //textView.setTextAppearance(getApplicationContext(), R.style.); // http://stackoverflow.com/questions/4630440/how-to-change-a-textviews-style-at-runtime
    }
}
