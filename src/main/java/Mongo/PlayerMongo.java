package Mongo;

import java.util.Arrays;
import java.util.List;

interface PlayerMongoI{
    String getName();
    String getColor();
    Integer getWins();
    Integer getLose();
    Integer getDraw();
}

public class PlayerMongo implements PlayerMongoI{
 //   private long _id;
    String name;
    String color;
    List<Integer> moves;
    Integer win;
    Integer draw;
    Integer lose;

    public PlayerMongo(String name, String color){
        this.setName(name);
        this.setColor(color);
    }


    public void setName(String name){

        this.name = name;
    }

    public void setColor(String color){
        this.color = color;
    }

    public String getColor(){
        return color;
    }

    public String getName(){
        return name;
    }

    public Integer getWins(){
        return win;
    }
    public Integer getLose(){ return lose;}
    public Integer getDraw(){ return draw; }

    public void addWin(){
       this.win=this.win+1;
    }
    public void addWLose(){
        this.lose = this.lose+1;
    }
    public void addDraw(){
        this.draw = this.draw +1;
    }
}
