package com.google.engedu.puzzle8;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Random;

public class PuzzleBoardView extends View {
    public static final int NUM_SHUFFLE_STEPS = 40;
    private Activity activity;
    private PuzzleBoard puzzleBoard;
    private ArrayList<PuzzleBoard> animation;
    private Random random = new Random();

    public PuzzleBoardView(Context context) {
        super(context);
        activity = (Activity) context;
        animation = null;
    }

    public void initialize(Bitmap imageBitmap, View parent) {
        int width = parent.getWidth();
        puzzleBoard = new PuzzleBoard(imageBitmap, width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (puzzleBoard != null) {
            if (animation != null && animation.size() > 0) {
                puzzleBoard = animation.remove(0);
                puzzleBoard.draw(canvas);
                if (animation.size() == 0) {
                    animation = null;
                    puzzleBoard.reset();
                    Toast toast = Toast.makeText(activity, "Solved! ", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    this.postInvalidateDelayed(500);
                }
            } else {
                puzzleBoard.draw(canvas);
            }
        }
    }

    public void shuffle() {
        if (animation == null && puzzleBoard != null) {
            // Do something.

            int random = 30 + new Random().nextInt(5);
            ArrayList<PuzzleBoard> arrayListBoards;
            for(int i=0;i<random;i++)
            {
                arrayListBoards = puzzleBoard.neighbours();
                puzzleBoard = arrayListBoards.get(new Random().nextInt(arrayListBoards.size()));
            }
            invalidate();

            puzzleBoard.reset();

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (animation == null && puzzleBoard != null) {
            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (puzzleBoard.click(event.getX(), event.getY())) {
                        invalidate();
                        if (puzzleBoard.resolved()) {
                            Toast toast = Toast.makeText(activity, "Congratulations!", Toast.LENGTH_LONG);
                            toast.show();
                        }
                        return true;
                    }
            }
        }
        return super.onTouchEvent(event);
    }

    public void solve() {

        if(puzzleBoard==null) return;
      /*  PriorityQueue<PuzzleBoard> pq = new PriorityQueue<>(11, new Comparator<PuzzleBoard>() {
            @Override
            public int compare(PuzzleBoard lhs, PuzzleBoard rhs) {
                return lhs.priority() - rhs.priority();
            }
        });
    */
        myPriorityQueue pq = new myPriorityQueue(new Comparator<PuzzleBoard>() {
            @Override
            public int compare(PuzzleBoard lhs, PuzzleBoard rhs) {
                return lhs.priority() - rhs.priority();
            }
        });
        pq.add(puzzleBoard);
        while (!pq.isEmpty()){
            PuzzleBoard temp = (PuzzleBoard)pq.peek();
            pq.remove();
            if(!temp.resolved()){

                //No solution then add all the neighbours of this state of the board;
                ArrayList<PuzzleBoard> listtemp = temp.neighbours();
                Iterator<PuzzleBoard> it = listtemp.iterator();
                while(it.hasNext()){
                    PuzzleBoard tp = it.next();
                    //check for the previous and current state to avoid the unnecessary adding
                    // in the priority queue;
                    if(!tp.equals(tp.getPreviousBoard())){
                        pq.add(tp);
                    }
                }
            }
            else{

                //you got the solution and then add the previous boards to the resultlist;
                ArrayList<PuzzleBoard> resultList = new ArrayList<>();
                while(temp!=null){
                    resultList.add(temp);
                    temp = temp.getPreviousBoard();
                }
                Collections.reverse(resultList);
                animation = resultList;
                pq.clear();
                invalidate();
                break;
            }

        }


    }
}
