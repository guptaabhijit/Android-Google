package com.google.engedu.puzzle8;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;

public class PuzzleBoard  {

    private static final int NUM_TILES = 3;
    private static final int UPPER_LIMIT = 8;
    private int steps;
    private PuzzleBoard previousBoard;
    private static final int[][] NEIGHBOUR_COORDS = {
            {-1, 0},
            {1, 0},
            {0, -1},
            {0, 1}
    };
    private ArrayList<PuzzleTile> tiles;

    PuzzleBoard(Bitmap bitmap, int parentWidth) {
        int count = 0;
        tiles = new ArrayList();
        steps = 0;
        previousBoard = null;

        tiles.ensureCapacity(NUM_TILES * NUM_TILES);
        bitmap = Bitmap.createScaledBitmap(bitmap, parentWidth, parentWidth, true);
        for (int i = 0; i < NUM_TILES; i++) {
            for (int j = 0; j < NUM_TILES; j++) {
                if (i == NUM_TILES - 1 && j == NUM_TILES - 1) {
                    tiles.add(null);
                    continue;
                }

                Bitmap bitm = Bitmap.createBitmap(bitmap, j * (parentWidth / NUM_TILES), i * (parentWidth / NUM_TILES), (parentWidth / NUM_TILES), (parentWidth / NUM_TILES));
                tiles.add(new PuzzleTile(bitm, count));
                count++;

            }
        }
        Log.d("puzzleBoard:", "here");

    }

    PuzzleBoard(PuzzleBoard otherBoard) {
        tiles = (ArrayList<PuzzleTile>) otherBoard.tiles.clone();
        this.steps = otherBoard.steps + 1;
        previousBoard = otherBoard;

    }

    public void reset() {
        // Nothing for now but you may have things to reset once you implement the solver.
        steps = 0;
        previousBoard = null;

    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        return tiles.equals(((PuzzleBoard) o).tiles);
    }

    public void draw(Canvas canvas) {
        if (tiles == null) {
            return;
        }
        for (int i = 0; i < NUM_TILES * NUM_TILES; i++) {
            PuzzleTile tile = tiles.get(i);
            if (tile != null) {
                tile.draw(canvas, i % NUM_TILES, i / NUM_TILES);
            }
        }
    }

    public boolean click(float x, float y) {
        for (int i = 0; i < NUM_TILES * NUM_TILES; i++) {
            PuzzleTile tile = tiles.get(i);
            if (tile != null) {
                if (tile.isClicked(x, y, i % NUM_TILES, i / NUM_TILES)) {
                    return tryMoving(i % NUM_TILES, i / NUM_TILES);
                }
            }
        }
        return false;
    }

    public boolean resolved() {
        for (int i = 0; i < NUM_TILES * NUM_TILES - 1; i++) {
            PuzzleTile tile = tiles.get(i);
            if (tile == null || tile.getNumber() != i)
                return false;
        }
        return true;
    }

    private int XYtoIndex(int x, int y) {
        return x + y * NUM_TILES;
    }

    protected void swapTiles(int i, int j) {
        PuzzleTile temp = tiles.get(i);
        tiles.set(i, tiles.get(j));
        tiles.set(j, temp);
    }

    private boolean tryMoving(int tileX, int tileY) {
        for (int[] delta : NEIGHBOUR_COORDS) {
            int nullX = tileX + delta[0];
            int nullY = tileY + delta[1];
            if (nullX >= 0 && nullX < NUM_TILES && nullY >= 0 && nullY < NUM_TILES &&
                    tiles.get(XYtoIndex(nullX, nullY)) == null) {
                swapTiles(XYtoIndex(nullX, nullY), XYtoIndex(tileX, tileY));
                return true;
            }

        }
        return false;
    }

    public int manhattan(){

        int dist = 0;
        int count = 0;
        Iterator<PuzzleTile> it = tiles.iterator();

        while(it.hasNext()){
            PuzzleTile pt = it.next();
            if(pt!=null) {
                int ox, oy, nx, ny;
                ox = pt.getNumber() % NUM_TILES;
                oy = pt.getNumber() / NUM_TILES;
                nx = count % NUM_TILES;
                ny = count / NUM_TILES;
                dist+= Math.abs(ox-nx) + Math.abs(oy-ny);
            }
            count++;
        }
        return dist;

    }
    public PuzzleBoard getPreviousBoard(){
        return previousBoard;
    }

    public ArrayList<PuzzleBoard> neighbours() {


        int indexOfNull = tiles.indexOf(null);
       // Log.d("indexofNull", indexOfNull + "");

        ArrayList<PuzzleBoard> returnList = new ArrayList<>();
        int left, right, up, down;

        up = indexOfNull + NEIGHBOUR_COORDS[0][0];
        down = indexOfNull + NEIGHBOUR_COORDS[3][1];
        left = indexOfNull + NEIGHBOUR_COORDS[2][1] * NUM_TILES;
        right = indexOfNull + NEIGHBOUR_COORDS[1][0] * NUM_TILES;
        PuzzleBoard copy;


        if (up >= 0) {
            copy = new PuzzleBoard(this);
            copy.swapTiles(up, indexOfNull);
            returnList.add(copy);
        }
        if (down <= UPPER_LIMIT) {
            copy = new PuzzleBoard(this);
            copy.swapTiles(down, indexOfNull);
            returnList.add(copy);
        }
        if (left >= 0) {
            copy = new PuzzleBoard(this);
            copy.swapTiles(left, indexOfNull);
            returnList.add(copy);
        }
        if (right <= UPPER_LIMIT) {
            copy = new PuzzleBoard(this);
            copy.swapTiles(right, indexOfNull);
            returnList.add(copy);
        }



        return returnList;
    }


    public int priority() {
        return steps + manhattan();
    }

}
