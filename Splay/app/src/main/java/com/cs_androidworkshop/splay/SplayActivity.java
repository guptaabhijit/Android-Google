package com.cs_androidworkshop.splay;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

public class SplayActivity extends AppCompatActivity {

    private ObjectAnimator anim,anim1,anim2,anim3,anim4;
    AnimatorSet anim12,anim34;
    Button b1,b2,b3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_splay);

        b1=(Button)findViewById(R.id.alice);
        b2 = (Button) findViewById(R.id.eve1);
        b3 = (Button) findViewById(R.id.eve2);

        anim12=new AnimatorSet();
        anim34=new AnimatorSet();
        anim1 = ObjectAnimator.ofFloat(b1, "translationX", -200.0f, 790.0f);
        anim2 = ObjectAnimator.ofFloat(b1, "translationY", -45.0f, -45.0f);
        anim1.setRepeatCount(-1);
        anim1.setRepeatMode(2);
        anim2.setRepeatCount(-1);
        anim2.setRepeatMode(2);
        anim12.playTogether(anim1,anim2);
        anim12.setDuration(4500);
        anim12.setTarget(b2);
        //anim.setInterpolator(new LinearInterpolator());
        anim12.start();

        anim3 = ObjectAnimator.ofFloat(b1, "translationX", 200.0f, -750.0f);
        anim4 = ObjectAnimator.ofFloat(b1, "translationY", 20.0f, 20.0f);
        anim3.setRepeatCount(-1);
        anim3.setRepeatMode(2);
        anim4.setRepeatCount(-1);
        anim4.setRepeatMode(2);
        anim34.playTogether(anim1,anim2);
        anim34.setDuration(4500);
        anim34.setTarget(b2);
        //anim.setInterpolator(new LinearInterpolator());
        anim34.start();
/*
        Animation animation1 = new TranslateAnimation(-200,790,-45,-45);
        animation1.setRepeatCount(-1);
        animation1.setDuration(4000);
        animation1.setRepeatMode(2);
        animation1.setFillAfter(true);*/
/*

        Animation animation2 = new TranslateAnimation(200,-750,20,20);
        animation2.setRepeatCount(-1);
        animation2.setDuration(4000);
        animation2.setRepeatMode(2);
        animation2.setFillAfter(true);
*/
/*

        b2.setAnimation(animation1);

        b3.setAnimation(animation2);
*/
    }




    public void coordinates()
    {
        new CountDownTimer(2500,100)
        {

            @Override
            public void onTick(long millisUntilFinished) {

                //anim.pause();
                float xa=b1.getX();
                float ya=b1.getY();

                Log.d("Xalice",""+xa);
                Log.d("Yalice", "" + ya);

                float xe1=b2.getX();
                float ye1=b2.getY();

                float xe2=b3.getX();
                float ye2=b3.getY();

                Log.d("Xeve1",""+xe1);
                Log.d("Yeve1", "" + ye1);

                Log.d("Xeve2",""+xe2);
                Log.d("Yeve2", "" + ye2);
            }
            @Override
            public void onFinish() {

            }
        }.start();
    }

}



