package com.example.enzo.a1vs100;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.enzo.a1vs100.QuestionsActivity.MY_PREFS_NAME;

public class ResultActivity extends AppCompatActivity {

    String shareSub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView youWinTV = findViewById(R.id.youWin);
        TextView numberOfWinsTV = findViewById(R.id.numberOfWinsTV);
        ImageView share = findViewById(R.id.share);
        ImageView tryAgain = findViewById(R.id.tryAgain);


        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> onBackPressed());

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String winOrLose = prefs.getString("winorlose", "lose");
        int numberOfwins = prefs.getInt("numberOfGames", 1999);
        int remaining = prefs.getInt("remaining", 4999);
        if (winOrLose.equals("win")) {
            youWinTV.setText("You won!");
            if (numberOfwins == 1) {
                numberOfWinsTV.setText("You now have " + numberOfwins + " win");
            } else if (numberOfwins < 10) {
                numberOfWinsTV.setText("You now have " + numberOfwins + " wins");
            } else {
                numberOfWinsTV.setText("You now have an amazing " + numberOfwins + " wins");
            }
        } else {
            youWinTV.setText("You lost...\nThat was incorrect");
            numberOfWinsTV.setText("You finished #" + remaining);
        }


        share.setOnClickListener(v -> {
            Intent myIntent = new Intent(Intent.ACTION_SEND);
            myIntent.setType("text/plain");
            String shareBody = "1 vs 100";

            if (remaining > 0) {
                shareSub = "I just finished #" + remaining + " on 1 vs 100 you should try it's so fun!";
            } else {
                shareSub = "I just beat 100 people on 1 vs 100! I bet you can't win";
            }
            myIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody);
            myIntent.putExtra(Intent.EXTRA_TEXT, shareSub);
            startActivity(Intent.createChooser(myIntent, "Share via"));
        });

        tryAgain.setOnClickListener(v -> {
            Intent myIntent = new Intent(ResultActivity.this, QuestionsActivity.class);
            startActivity(myIntent);
            finish();

        });
    }
}
