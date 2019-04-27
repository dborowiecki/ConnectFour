package Mongo;


interface MoveMongoI{
    public PlayerMongoI getPlayer();
    public Integer getColumn();
    public Integer getOrder();
    void setPlayer(PlayerMongoI p);
    void setColumn(Integer column);
    void setOrder(Integer order);
}

public class MoveMongo implements MoveMongoI{
    private PlayerMongoI player;
    private Integer order;
    private Integer column;

    public MoveMongo(PlayerMongoI player, int column){
        setPlayer(player);
        setColumn(column);

    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public void setPlayer(PlayerMongoI player){
        this.player = player;
    }

    public void setOrder(Integer order){
        this.order = order;
    }


    public PlayerMongoI getPlayer(){ return player;}
    public Integer getColumn() {return column;}
    public Integer getOrder() {return order;}
}
