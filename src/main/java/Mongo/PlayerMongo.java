package Mongo;

import org.jongo.marshall.jackson.oid.MongoId;

import java.util.Arrays;
import java.util.List;

public class PlayerMongo implements PlayerMongoI{

    @MongoId
    String name;
    String color;
    Integer win;
    Integer draw;
    Integer lose;

    public PlayerMongo(){}
    public PlayerMongo(String name, String color){
        this.setName(name);
        this.setColor(color);
    }


    public void setName(String name){
        win=0;
        draw=0;
        lose=0;
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

    public void addWin(){ this.win=this.win+1; }
    public void addLose(){
        this.lose = this.lose+1;
    }
    public void addDraw(){
        this.draw = this.draw +1;
    }
}
