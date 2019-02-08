package com.example.enzo.a1vs100;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.enzo.a1vs100.QuestionsActivity.MY_PREFS_NAME;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView youWinTV = findViewById(R.id.youWin);
        TextView numberOfWinsTV = findViewById(R.id.numberOfWinsTV);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> onBackPressed());

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String winOrLose = prefs.getString("winorlose", "lose");
        int numberOfwins = prefs.getInt("numberOfGames", 1999);
        int remaining = prefs.getInt("remaining", 4999);
        if (winOrLose.equals("win")){
            youWinTV.setText("You won!");
            if (numberOfwins == 1) {
                numberOfWinsTV.setText("You now have " + numberOfwins + " win");
            } else if (numberOfwins <10){
                numberOfWinsTV.setText("You now have " + numberOfwins + " wins");
            } else {
                numberOfWinsTV.setText("You now have an amazing " + numberOfwins + " wins");
            }
        } else {
            youWinTV.setText("You lost... \n That was incorrect");
            numberOfWinsTV.setText("You finished #" + remaining);
        }




    }


}
