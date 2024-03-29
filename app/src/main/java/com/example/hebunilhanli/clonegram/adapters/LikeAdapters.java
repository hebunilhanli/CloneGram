package com.example.hebunilhanli.clonegram.adapters;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.view.View;
import android.view.ViewGroup;

public class LikeAdapters {
    private static final String TAG = "Heart";
    private static final DecelerateInterpolator DECELERATE_INTERPOLATOR = new DecelerateInterpolator();
    private static final AccelerateInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();

    public ImageView heartWhite, heartRed;

    public LikeAdapters (ImageView heartWhite, ImageView heartRed){
        this.heartWhite = heartWhite;
        this.heartRed = heartRed;
    }

    public void toggleLike(){
        Log.d(TAG, "toggleLike: toggling heart.");

        AnimatorSet animatorSet = new AnimatorSet();

        if (heartRed.getVisibility() == View.VISIBLE){
            Log.d(TAG,"toggleLike: toggling red heart off.");
            heartRed.setScaleX(0.1f);
            heartRed.setScaleY(0.1f);

            ObjectAnimator scaleDownY= ObjectAnimator.ofFloat(heartRed,"scaleY",1f,0f);
            scaleDownY.setDuration(300);
            scaleDownY.setInterpolator(ACCELERATE_INTERPOLATOR);
            ObjectAnimator scaleDownX= ObjectAnimator.ofFloat(heartRed,"scaleX",1f,0f);
            scaleDownX.setDuration(300);
            scaleDownX.setInterpolator(ACCELERATE_INTERPOLATOR);

            heartRed.setVisibility(View.GONE);
            heartWhite.setVisibility(View.VISIBLE);

            //animatorSet.playTogether(scaleDownY,scaleDownX);
        }else if (heartRed.getVisibility() == View.GONE){
           Log.d(TAG,"toggleLike: toggling red heart on.");
            heartRed.setScaleX(0.1f);
            heartRed.setScaleY(0.1f);

            ObjectAnimator scaleDownY= ObjectAnimator.ofFloat(heartRed,"scaleY",0.1f,1f);
            scaleDownY.setDuration(300);
            scaleDownY.setInterpolator(DECELERATE_INTERPOLATOR);
            ObjectAnimator scaleDownX= ObjectAnimator.ofFloat(heartRed,"scaleX",0.1f,1f);
            scaleDownX.setDuration(300);
            scaleDownX.setInterpolator(DECELERATE_INTERPOLATOR);

            heartRed.setVisibility(View.VISIBLE);
            heartWhite.setVisibility(View.GONE);


            animatorSet.playTogether(scaleDownY,scaleDownX);
        }
        animatorSet.start();
    }

}
