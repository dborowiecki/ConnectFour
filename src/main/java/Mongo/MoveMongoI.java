package Mongo;

public interface MoveMongoI{
    public PlayerMongoI getPlayer();
    public Integer getColumn();
    public Integer getOrder();
    void setPlayer(PlayerMongoI p);
    void setColumn(Integer column);
    void setOrder(Integer order);
}
