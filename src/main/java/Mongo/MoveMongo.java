package Mongo;


interface MoveMongoI{
    public PlayerMongo getPlayer();
    public Integer getColumn();
    public Integer getOrder();
}
public class MoveMongo implements MoveMongoI{
    private PlayerMongo player;
    private Integer order;
    private Integer column;

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


    public PlayerMongo getPlayer(){ return player;}
    public Integer getColumn() {return column;}
    public Integer getOrder() {return order;}
}
