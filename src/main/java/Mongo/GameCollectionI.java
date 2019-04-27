package Mongo;

public interface GameCollectionI{
    PlayerMongoI findByName(String name);
    PlayerMongoI findByColor(String color);
    void savePlayer(PlayerMongoI p);
    void saveMove(MoveMongoI m);
    void saveBoard(BoardMongoI b);
    void deleteMove(MoveMongoI m);
    void removeTemporaryCollections();
}
