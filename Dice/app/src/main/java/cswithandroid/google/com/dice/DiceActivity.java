package cswithandroid.google.com.dice;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class DiceActivity extends AppCompatActivity {

    private int userTurnScore;
    private int ComputerOverallScore;
    private int ComputerTurnScore;
    private int userOverallScore;
    private Button roll,reset,hold;
    private  ImageView dice;
    private TextView compOverallScore;

    private TextView compTScore;
    void init(){
        userOverallScore = 0;
        userTurnScore = 0;
        ComputerOverallScore = 0;
        ComputerTurnScore = 0;

    }


    void compTurn() throws InterruptedException {

        roll.setEnabled(false);hold.setEnabled(false);

        ComputerTurnScore = 0;
        boolean flag = true;
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int maxLimit = 10;

                    Random rand = new Random();
                    int faceValue = 1 + rand.nextInt(5);

                    switch (faceValue) {
                        case 1:
                            dice.setImageDrawable(getResources().getDrawable(R.drawable.dice1));
                            ComputerTurnScore = 0;
                            roll.setEnabled(true);
                            hold.setEnabled(true);
                            compTScore.setText("CompScore: " + ComputerTurnScore);
                            handler.removeCallbacks(this);
                            return;
                        case 2:
                            dice.setImageDrawable(getResources().getDrawable(R.drawable.dice2));
                            ComputerTurnScore += 2;
                            break;
                        case 3:
                            dice.setImageDrawable(getResources().getDrawable(R.drawable.dice3));
                            ComputerTurnScore += 3;
                            break;
                        case 4:
                            dice.setImageDrawable(getResources().getDrawable(R.drawable.dice4));
                            ComputerTurnScore += 4;
                            break;
                        case 5:
                            dice.setImageDrawable(getResources().getDrawable(R.drawable.dice5));
                            ComputerTurnScore += 5;
                            break;
                        case 6:
                            ComputerTurnScore += 6;
                            break;
                    }
                    compTScore.setText("CompScore: " + ComputerTurnScore);

                if(ComputerTurnScore>=maxLimit){
                    roll.setEnabled(true);hold.setEnabled(true);
                    ComputerOverallScore += ComputerTurnScore;
                    ComputerTurnScore = 0;
                    compOverallScore.setText("CompOverallScore: "+ComputerOverallScore);
                    compTScore.setText("CompScore: "+0);
                    handler.removeCallbacks(this);
                    return;
                }
                handler.postDelayed(this,1000);
            }
        };
            handler.postDelayed(runnable,1000);



        }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);
        init();
        final TextView userTScore = (TextView) findViewById(R.id.turnscore_user);
        compTScore = (TextView) findViewById(R.id.computer_score_turn);
        compOverallScore = (TextView) findViewById(R.id.computer_score);
        final TextView userOverallTScore;
        userOverallTScore = (TextView) findViewById(R.id.your_score);

       dice = (ImageView) findViewById(R.id.dice_image);


         roll = (Button) findViewById(R.id.roll_button);
         hold = (Button) findViewById(R.id.hold_button);
         reset = (Button) findViewById(R.id.reset_button);

        roll.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Random rand = new Random();
                int faceValue = 1 + rand.nextInt(5);

                switch(faceValue){
                    case 1:
                        dice.setImageDrawable(getResources().getDrawable(R.drawable.dice1));
                        userTurnScore = 0;
                        //call computer method;
                        try {
                            compTurn();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        break;
                    case 2:

                        dice.setImageDrawable(getResources().getDrawable(R.drawable.dice2));
                        userTurnScore +=2;
                        break;
                    case 3:
                        dice.setImageDrawable(getResources().getDrawable(R.drawable.dice3));
                        userTurnScore +=3;
                        break;
                    case 4:
                        dice.setImageDrawable(getResources().getDrawable(R.drawable.dice4));
                        userTurnScore +=4;
                        break;
                    case 5:
                        dice.setImageDrawable(getResources().getDrawable(R.drawable.dice5));
                        userTurnScore +=5;
                        break;
                    case 6:
                        dice.setImageDrawable(getResources().getDrawable(R.drawable.dice6));
                        userTurnScore +=6;
                        break;
                }
                userTScore.setText("UserScore: "+userTurnScore);
                //userTurnScore = 0;
            }
        });

        hold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userOverallScore += userTurnScore;
                userOverallTScore.setText("UserAll: "+userOverallScore);
                userTScore.setText("UserScore: "+0);
                userTurnScore = 0;
                //call computer method
               try {
                    compTurn();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            init();
               userTScore.setText("UserScore: " + 0);
                userOverallTScore.setText("UserAll: "+0);
                compTScore.setText("CompScore: "+0);
                compOverallScore.setText("CompAll: "+0);
            }
        });
    }
}
