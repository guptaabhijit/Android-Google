package edu.noted.check;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {




    private Button b;
    private TextView tv;


    private ObjectAnimator anim,anim1,anim2,anim3,anim4;
    AnimatorSet anim12,anim34;
    Button b1, b2, b3;

    private static int counter = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        // tv = (TextView) findViewById(R.id.tvH);
        // b = (Button) findViewById(R.id.button);
       /* Log.d("pivotx",""+tv.getLeft());
        Log.d("pivoty",""+tv.getRight());
        Log.d("pivottop",""+tv.getTop());
        Log.d("pivotbottm",""+tv.getBottom());


        Log.d("pivotx",""+b.getLeft());
        Log.d("pivoty",""+b.getRight());
        Log.d("pivottop",""+b.getTop());
        Log.d("pivotbottm",""+b.getBottom());
*/


        b1=(Button)findViewById(R.id.alice);
        b2 = (Button) findViewById(R.id.eve1);
        b3 = (Button) findViewById(R.id.eve2);

        coordinates();

        anim12=new AnimatorSet();
        anim34=new AnimatorSet();
        anim1 = ObjectAnimator.ofFloat(b2, "translationX", -200.0f, 790.0f);
        anim2 = ObjectAnimator.ofFloat(b2, "translationY", -45.0f, -45.0f);
        anim1.setRepeatCount(-1);
        anim1.setRepeatMode(2);
        anim2.setRepeatCount(-1);
        anim2.setRepeatMode(2);
        anim12.playTogether(anim1,anim2);
        anim12.setDuration(4500);
        anim12.setTarget(b2);
        //anim.setInterpolator(new LinearInterpolator());
        anim12.start();

        anim3 = ObjectAnimator.ofFloat(b3, "translationX", 200.0f, -750.0f);
        anim4 = ObjectAnimator.ofFloat(b3, "translationY", 20.0f, 20.0f);
        anim3.setRepeatCount(-1);
        anim3.setRepeatMode(2);
        anim4.setRepeatCount(-1);
        anim4.setRepeatMode(2);
        anim34.playTogether(anim3,anim4);
        anim34.setDuration(4500);
        anim34.setTarget(b3);
        //anim.setInterpolator(new LinearInterpolator());
        anim34.start();



    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

      /*  Log.d("pivoty",""+tv.getRight());
        Log.d("pivottop",""+tv.getTop());
        Log.d("pivotbottm",""+tv.getBottom());
        Log.d("pivotx",""+tv.getLeft());


        Log.d("pivotx",""+b.getLeft());
        Log.d("pivoty",""+b.getRight());
        Log.d("pivottop",""+b.getTop());
        Log.d("pivotbottm", "" + b.getBottom());
   */ }


    public void buttonClicked(View thumbnailView) {
        Button b1 = (Button) findViewById(R.id.alice);

        anim = ObjectAnimator.ofFloat(b1, "translationY", 0.0f, -1550.0f);
        anim.setDuration(5000);
        anim.setTarget(b1);
        //anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(5);
        anim.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void coordinates()
    {
        new CountDownTimer(2500,100)
        {

            @Override
            public void onTick(long millisUntilFinished) {

                //b1 = alice,b2=eve1,b3=eve2
                //anim.pause();
                float xa=b1.getX();
                float ya=b1.getY();
                float atop,abottom,aleft,aright;
                atop = b1.getTop();
                abottom = b1.getBottom();
                aleft = b1.getLeft();
                aright = b1.getRight();


                Log.d("Xalice",""+xa);
                Log.d("Yalice", "" + ya);
                float xe1=b2.getX();
                float ye1=b2.getY();

                float xe2=b3.getX();
                float ye2=b3.getY();




                if(xa==xe1&&ya==ye1 || xa==xe2&&ya==ye2){
                    anim.pause();
                    anim34.pause();
                    anim12.pause();

                }
                if(counter%100==0){


                    Log.d("L",""+aleft);
                    Log.d("R", "" + aright);
                    Log.d("B",""+abottom);
                    Log.d("Top:", "" + atop);

                }

               /* Log.d("Xeve1",""+xe1);
                Log.d("Yeve1", "" + ye1);

                Log.d("Xeve2",""+xe2);
                Log.d("Yeve2", "" + ye2);
*/
                Log.d("cout:",counter+"");
                counter++;

            }
            @Override
            public void onFinish() {

            }
        }.start();
    }


}
