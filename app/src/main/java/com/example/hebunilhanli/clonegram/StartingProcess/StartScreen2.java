package com.example.hebunilhanli.clonegram.StartingProcess;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.hebunilhanli.clonegram.R;

public class StartScreen2 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen2);
        goStartScreen();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(),LoginScreen.class);
                startActivity(intent);
            }
        },5000);
    }

    private void goStartScreen(){

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.animations);
        ImageView girislogo = findViewById(R.id.imageView7);
        ImageView girislogo2 = findViewById(R.id.imageView5);
        animation.reset();
        girislogo.clearAnimation();
        girislogo2.clearAnimation();
        girislogo.startAnimation(animation);
        girislogo2.startAnimation(animation);





    }
}
