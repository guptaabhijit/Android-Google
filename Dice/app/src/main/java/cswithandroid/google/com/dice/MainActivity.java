package cswithandroid.google.com.dice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView your = (TextView) findViewById(R.id.your_score);
        TextView comp = (TextView) findViewById(R.id.computer_score);

        ImageView dice = (ImageView) findViewById(R.id.dice_image);

        Button roll = (Button) findViewById(R.id.roll_button);
        Button hold = (Button) findViewById(R.id.hold_button);
        Button reset = (Button) findViewById(R.id.reset_button);

        roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        hold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
