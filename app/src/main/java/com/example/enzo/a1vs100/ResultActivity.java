package com.example.enzo.a1vs100;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import static com.example.enzo.a1vs100.QuestionsActivity.MY_PREFS_NAME;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String winOrLose = prefs.getString("winorlose", "lose");
        if (winOrLose.equals("win")){
            Toast.makeText(this, "You win", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "You LOOOOSE", Toast.LENGTH_SHORT).show();
        }

    }


}
