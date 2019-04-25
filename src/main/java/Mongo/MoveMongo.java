package Mongo;

public class MoveMongo {
    PlayerMongo player;
    Integer order;
    Integer column;

    public MoveMongo(PlayerMongo player, int column){
        setPlayer(player);
        setColumn(column);

    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public void setPlayer(PlayerMongo player){
        this.player = player;
    }

    public void setOrder(Integer order){
        this.order = order;
    }

    public MoveMongo getMove(){
        return this;
    }
}
