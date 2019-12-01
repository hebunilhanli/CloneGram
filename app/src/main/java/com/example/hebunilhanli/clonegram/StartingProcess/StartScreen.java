package com.example.hebunilhanli.clonegram.StartingProcess;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hebunilhanli.clonegram.R;

public class StartScreen extends AppCompatActivity {

    Handler handler;
    Runnable runnable;
    int time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);



    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(getApplicationContext(),StartScreen2.class);
            startActivity(intent);
        }
    },2000);


    }
}
