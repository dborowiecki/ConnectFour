package Mongo;
import Game.TerminalColrs;


import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class PlayerMongo {
 //   private long _id;
    String name;
    String color;
    List<Integer> moves;
    Integer score;

    public PlayerMongo(String name, String color, Integer score){
        this.setName(name);
        this.setColor(color);
        this.score = score;
    }


    public void setName(String name){
        if(name==null||name.length()==0)
            throw new IllegalArgumentException("Name cant be empty");

        this.name = name;
    }

    public void setColor(String color){
        this.color = TerminalColrs.translateColor(color);
    }

    public String getColor(){
        return color;
    }

    public String getName(){
        return name;
    }

    public Integer getScore(){
        return score;
    }

    public void addScore(Integer score){
        Integer[] scores = {-1,0,1};
        if (Arrays.asList(scores).contains(score))
            throw new IllegalArgumentException("Score have to be -1, 0 or 1");
        else
            this.score+=score;
    }
}
