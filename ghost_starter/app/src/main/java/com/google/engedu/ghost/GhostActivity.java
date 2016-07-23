package com.google.engedu.ghost;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;


public class GhostActivity extends ActionBarActivity {
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    private GhostDictionary dictionary;
    private boolean userTurn = false;
    public static boolean bluff = false;
    private Random random = new Random();
   // private SimpleDictionary simpleDictionary;
    private FastDictionary fastDictionary;
    private TextView ghostTextView,gameStatusTextView,UserScoreTextView,ComputerScoreTextView;
    private Button challengeButton,resetButton;
    private StringBuffer ghostString;
    private int UserScore,ComputerScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);
        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("words.txt");
            //simpleDictionary = new SimpleDictionary(inputStream);
            fastDictionary = new FastDictionary(inputStream);
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }

        ghostTextView = (TextView) findViewById(R.id.ghostText);
        gameStatusTextView = (TextView) findViewById(R.id.gameStatus);
        UserScoreTextView = (TextView) findViewById(R.id.textView_user_score);
        ComputerScoreTextView = (TextView) findViewById(R.id.textView_computer_score);
        challengeButton = (Button) findViewById(R.id.button_challenge);
        resetButton = (Button) findViewById(R.id.button_restart);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ghostString = new StringBuffer("");
                UserScore = 0;
                ComputerScore = 0;
                ghostTextView.setText("");
                UserScoreTextView.setText("0");
                ComputerScoreTextView.setText("0");
                //onStart(null);
            }
        });
        ghostString = new StringBuffer("");
        UserScore = 0;
        ComputerScore = 0;

        challengeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempcheck = ghostTextView.getText().toString();

                if(tempcheck.length()>=2){
                    boolean checkflag = fastDictionary.isWord(tempcheck);
                   // boolean checkflag = simpleDictionary.isWord(tempcheck);
                    if(checkflag){
                        Log.d("exits: ",ghostString.toString());
                        ghostString = new StringBuffer("");
                        UserScore++;
                        UserScoreTextView.setText(UserScore+"");
                        ghostTextView.setText("");
                        Toast.makeText(GhostActivity.this,"Word found! You won!",Toast.LENGTH_LONG).show();
                    }else {
                        //This is bluff;
                        //String longerWord = simpleDictionary.getAnyWordStartingWith(tempcheck);
                        String longerWord = fastDictionary.getGoodWordStartingWith(tempcheck);
                        if(longerWord==null){

                            ghostString = new StringBuffer("");
                            UserScore++;
                            UserScoreTextView.setText(UserScore+"");
                            ghostTextView.setText("");
                            Toast.makeText(GhostActivity.this,"Prefix doesnt exist! You won!",Toast.LENGTH_LONG).show();
                        }
                        else{
                            ComputerScore++;
                            ComputerScoreTextView.setText(ComputerScore+"");
                            ghostTextView.setText("");
                            ghostString = new StringBuffer("");
                            Toast.makeText(GhostActivity.this,longerWord+" Computer won!",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else{

                  /*  String longerWord = simpleDictionary.getAnyWordStartingWith(checkString);
                    String nextStringWord = longerWord.substring(checkString.length());
                    char appendch = nextStringWord.charAt(0);
                    ghostString.append(appendch);
                    ghostTextView.setText(ghostString);
                    */
                   // Toast.makeText(GhostActivity.this,"Cannot Check now",Toast.LENGTH_SHORT).show();

                }
            }
        });
        onStart(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putInt("userscore",UserScore);
        outState.putInt("computerscore",ComputerScore);
        outState.putString("buffer",ghostString.toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        UserScore = savedInstanceState.getInt("userscore");
        ComputerScore = savedInstanceState.getInt("computerscore");
        ghostString = new StringBuffer(savedInstanceState.getString("buffer"));
        ghostTextView.setText(ghostString);
        UserScoreTextView.setText(UserScore);
        ComputerScoreTextView.setText(ComputerScore);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void computerTurn() {
        TextView label = (TextView) findViewById(R.id.gameStatus);
        // Do computer turn stuff then make it the user's turn again
        bluff = random.nextBoolean();


        String checkString = ghostTextView.getText().toString();
        if(checkString.length()>=4){
            boolean checkflag = fastDictionary.isWord(checkString);
            //boolean checkflag = simpleDictionary.isWord(checkString);
            if(checkflag){
                //word found!
                Log.d("exits: ",ghostString.toString());
                ghostString = new StringBuffer("");
                ComputerScore++;
                ComputerScoreTextView.setText(ComputerScore+"");
                ghostTextView.setText("");
                Toast.makeText(GhostActivity.this,"Word found! Computer won!",Toast.LENGTH_LONG).show();

            }else {
                //This is bluff;
                String longerWord = fastDictionary.getAnyWordStartingWith(checkString);
                Log.d("checkString:",checkString);
               // String longerWord = simpleDictionary.getAnyWordStartingWith(checkString);
                if(longerWord==null){
                    //bluff caught;
                    ghostString = new StringBuffer("");
                    ComputerScore++;
                    ComputerScoreTextView.setText(ComputerScore+"");
                    ghostTextView.setText("");
                    Toast.makeText(GhostActivity.this,"No such prefix exist! Computer won!",Toast.LENGTH_LONG).show();
                }
                else{
                    String nextStringWord = longerWord.substring(checkString.length());
                    char appendch = nextStringWord.charAt(0);
                    Log.d("appending:",appendch+"");
                    ghostString.append(appendch);
                    ghostTextView.setText(ghostString);
                    /*if(simpleDictionary.isWord(ghostString.toString())){
                        UserScore++;
                        UserScoreTextView.setText(UserScore+"");
                        ghostString = new StringBuffer("");
                        ghostTextView.setText("");
                    }*/
                }
            }
        }
        else{

            String longerWord = fastDictionary.getAnyWordStartingWith(checkString);
            Log.d("checkString:",checkString);
           // String longerWord = simpleDictionary.getAnyWordStartingWith(checkString);
            if(longerWord==null){
                ghostString = new StringBuffer("");
                ComputerScore++;
                ComputerScoreTextView.setText(ComputerScore+"");
                ghostTextView.setText("");
                Toast.makeText(GhostActivity.this,"No such prefix exist! Computer won!",Toast.LENGTH_LONG).show();

            }
            else {
                String nextStringWord = longerWord.substring(checkString.length());
                char appendch = nextStringWord.charAt(0);
                Log.d("appending:",appendch+"");
                ghostString.append(appendch);
                ghostTextView.setText(ghostString);
            }
        }
        userTurn = true;
        label.setText(USER_TURN);
    }

    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     * @param view
     * @return true
     */
    public boolean onStart(View view) {
        userTurn = random.nextBoolean();
        TextView text = (TextView) findViewById(R.id.ghostText);
        text.setText("");
        TextView label = (TextView) findViewById(R.id.gameStatus);
        if (userTurn) {
            label.setText(USER_TURN);
        } else {
            label.setText(COMPUTER_TURN);
            computerTurn();
        }
        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        int keyunicode = event.getUnicodeChar(event.getMetaState());
        char character = (char) keyunicode;

        //Log.d("key:pressed: ",character+"");

        if(character>='a'&&character<='z'){
            ghostString = ghostString.append(character);
            ghostTextView.setText(ghostString);
            computerTurn();
            return true;
        }
        else if( character>='A'&&character<='Z'){
            character = (char)((int)character + 32);
            ghostString = ghostString.append(character);
            ghostTextView.setText(ghostString);
            computerTurn();

            return true;
        }

        return super.onKeyUp(keyCode, event);


    }
}
