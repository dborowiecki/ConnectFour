package Mongo;

import java.rmi.UnknownHostException;

import org.jongo.Jongo;
import org.jongo.MongoCollection;

import com.mongodb.DB;
import com.mongodb.MongoClient;



interface GameCollectionI{
    PlayerMongoI findByName(String name);
    PlayerMongoI findByColor(String color);
    void savePlayer(PlayerMongoI p);
    void saveMove(MoveMongoI m);
    void saveBoard(BoardMongoI b);
    void deleteMove(MoveMongoI m);
}
public class GameCollection implements GameCollectionI{

    private MongoCollection players;
    private MongoCollection moves;
    private MongoCollection board;


    public GameCollection() throws UnknownHostException {
        @SuppressWarnings({ "deprecation", "resource" })
        DB db = new MongoClient().getDB("game");
        players = new Jongo(db).getCollection("players");
        moves   = new Jongo(db).getCollection("moves");
        board   = new Jongo(db).getCollection("board");
    }

    public void flushGame(){
        moves.drop();
        players.drop();
        board.drop();
    }

    public PlayerMongoI findByName(String name){
        return players.findOne("{name: #", name).as(PlayerMongo.class);
    }

    public PlayerMongoI findByColor(String color){
        return players.findOne("{color: #", color).as(PlayerMongo.class);
    }


    public BoardMongo getBoard(){
        return board.findOne().as(BoardMongo.class);
    }

    public void savePlayer(PlayerMongoI p){
        players.save(p);
    }


    public void saveMove(MoveMongoI move){
        moves.save(move);
    }

    public void deleteMove(MoveMongoI move){
        moves.remove("{order: #",move.getOrder());
    }
    public void saveBoard(BoardMongoI b){
        board.save(b);
    }
}
