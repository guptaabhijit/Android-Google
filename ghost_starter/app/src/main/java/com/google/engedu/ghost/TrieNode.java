package com.google.engedu.ghost;

import java.util.HashMap;
import java.util.Random;
import java.util.Set;


public class TrieNode {
    private HashMap<String, TrieNode> children;
    private boolean isWord;

    public TrieNode() {
        children = new HashMap<>();
        isWord = false;
    }
    public void add(String s) {

        TrieNode node = this;
        for(int i=0;i<s.length();i++)
        {
            char a = s.charAt(i);
            String key = a+"";
            TrieNode tempnode;
            if(node.children.containsKey(key)){
                 tempnode = node.children.get(key);
            }
            else{

                 tempnode = new TrieNode();
                if(i==s.length()-1){
                    tempnode.isWord = true;
                }
                node.children.put(key,tempnode);
            }
            node = tempnode;

        }


    }

    public boolean isWord(String s) {

        TrieNode node = this;
      for(int i=0;i<s.length();i++)
      {
          char a = s.charAt(i);
          String key = a+"";
          if(node.children.containsKey(key)){

              if(i==s.length()-1){
                  if(node.children.get(key).isWord)
                  {
                      return true;
                  }
                  else{
                      return false;
                  }
              }
          }
          else{
              return false;
          }
          node = node.children.get(key);
      }
      return false;
    }

    //bluffing also;
    public String getAnyWordStartingWith(String s) {

        TrieNode node = this;
        for(int i=0;i<s.length();i++)
        {
            char a = s.charAt(i);
            String key = a+"";
            if(!node.children.containsKey(key)){
                return null;
            }
            node = node.children.get(key);
            if(i==s.length()-1)
            {
                if(GhostActivity.bluff)
                {//bluffing;
                 int random = new Random().nextInt(25);
                  return s + new String((char)(random + 'a') + "");
                }else {
                    Set<String> setnode = node.children.keySet();
                    String arr[] = new String[setnode.size()];
                    setnode.toArray(arr);
                    int random = new Random().nextInt(arr.length);
                    return s + arr[random];
                }
            }
        }


        return null;
    }


    public String getGoodWordStartingWith(String s) {

        TrieNode node = this;
        for(int i=0;i<s.length();i++)
        {
            char a = s.charAt(i);
            String key = a+"";
            if(!node.children.containsKey(key)){
                return null;
            }
            node = node.children.get(key);
            if(i==s.length()-1)
            {
                while(!node.isWord){
                    Set<String> setnode = node.children.keySet();
                    String arr[] = new String[setnode.size()];
                    setnode.toArray(arr);
                    int random = new Random().nextInt(arr.length);
                    s = s + arr[random];
                    node = node.children.get(arr[random]);
                }
                return s;
            }
        }

        return null;

    }
}
