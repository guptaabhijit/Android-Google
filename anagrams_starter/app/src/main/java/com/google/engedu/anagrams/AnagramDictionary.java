package com.google.engedu.anagrams;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();

    private static int wordLength = DEFAULT_WORD_LENGTH;

    private HashSet<String> wordSet = new HashSet();
    private HashMap<String,ArrayList> lettersToWord = new HashMap();
    private HashMap<Integer,ArrayList> sizeToWords = new HashMap();
    private ArrayList<String> wordList = new ArrayList();


    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;

        while((line = in.readLine()) != null) {
            String word = line.trim();

            wordSet.add(word);
            wordList.add(word);

            int sizeOftheWord = word.length();
            ArrayList<String> tempwordSizeList;
            if(sizeToWords.containsKey(sizeOftheWord)){
                tempwordSizeList = sizeToWords.get(sizeOftheWord);
                tempwordSizeList.add(word);
            }
            else{
                tempwordSizeList = new ArrayList<>();
                tempwordSizeList.add(word);
                sizeToWords.put(sizeOftheWord,tempwordSizeList);
            }


            char arr[] = word.toCharArray();
            Arrays.sort(arr);
            String sortedword =  new String(arr);
            //Log.d("WORD",sortedword);
            ArrayList<String> temp;
            if(lettersToWord.containsKey(sortedword)){
                temp = lettersToWord.get(sortedword);
                temp.add(word);
            }
            else{
                temp = new ArrayList<>();
                temp.add(word);
                lettersToWord.put(sortedword,temp);
        }



        }

    }

    public boolean isGoodWord(String word, String base) {


        if(word.contains(base)|| !wordSet.contains(word)){
            return false;
        }

        return true;
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();


        for(int i=0;i<26;i++) {
           String wordtemp = word + (char) ('a' + i);
            char arr[] = wordtemp.toCharArray();
            Arrays.sort(arr);
            String sortedword = new String(arr);

            if(lettersToWord.containsKey(sortedword)){
                ArrayList<String> temp = lettersToWord.get(sortedword);
                for(int j=0;j<temp.size();j++)
                {
                    String tempword = temp.get(j);
                    if(!tempword.contains(word))
                            result.add(tempword);
                         // Log.d("Res",result.toString());
                }
            }

        }


        return result;
    }

    public String pickGoodStarterWord() {

       Random rand = new Random();
        ArrayList tempList = sizeToWords.get(wordLength-1);
        //int x = rand.nextInt(wordList.size());
        int x = rand.nextInt(tempList.size());
       // int x = tempList.size();
        //int finalpos = wordList.size();
        int finalpos = tempList.size();
        for(int i = x;i<finalpos;i++)
        {

            //String check = wordList.get(i);
            String check = (String) tempList.get(i);

            ArrayList checklist =  getAnagramsWithOneMoreLetter(check);
                if(checklist.size()>=MIN_NUM_ANAGRAMS){
                    {
                        wordLength = wordLength + 1;
                        if(wordLength==MAX_WORD_LENGTH){
                            wordLength = 3;
                        }
                        return check;
                    }
            }
            if(i == finalpos-1){
                i=0;
                finalpos = x;
            }


        }
        wordLength = wordLength + 1;
        if(wordLength==MAX_WORD_LENGTH){
            wordLength = 3;
        }

        return null;

        //return "ost";
    }
}
