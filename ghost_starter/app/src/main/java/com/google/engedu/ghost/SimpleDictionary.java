package com.google.engedu.ghost;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class SimpleDictionary implements GhostDictionary {
    private ArrayList<String> words;

    public SimpleDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        words = new ArrayList<>();
        String line = null;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH)
              words.add(line.trim());
        }
    }

    @Override
    public boolean isWord(String word) {
        return words.contains(word);
    }

    @Override
    public String getAnyWordStartingWith(String prefix) {

        if(prefix.equals("")||prefix.equals(" ")||prefix.isEmpty()){
            Random random = new Random();
            int sizer = random.nextInt(words.size());
            Log.d("word:",words.get(sizer));
            return words.get(sizer);
        }
        else{

            int index = myBinarySearch(prefix);
            if(index>=0){
                Log.d("words:",words.get(index));
                return words.get(index);
            }
            else{
                return null;
            }
        }


    }

    int myBinarySearch(String prefix){


        int low = 0;
        int high = words.size()-1;
        int mid = (low + high)/2;

        while(low<=high){
            if(words.get(mid).startsWith(prefix)){
                return mid;
            }
            else{
                int val = words.get(mid).compareTo(prefix);
                if(val<0) {
                    low = mid + 1;
                }
                else{
                    high = mid - 1;
                }
            }

            mid = (low+high)/2;
        }




        return -1;
    }

    @Override
    public String getGoodWordStartingWith(String prefix) {
        return null;
    }
}
