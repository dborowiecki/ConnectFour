package Mongo;

import java.rmi.UnknownHostException;

import org.jongo.Jongo;
import org.jongo.MongoCollection;


import com.mongodb.DB;
import com.mongodb.MongoClient;


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

    public GameCollection(MongoCollection players,MongoCollection moves,MongoCollection board){
        this.players=players;
        this.moves=moves;
        this.board=board;
    }

    public PlayerMongoI findByName(String name){
        return players.findOne("{_id: '"+name+"'}").as(PlayerMongo.class);
    }

    public PlayerMongoI findByColor(String color){
        return players.findOne("{color:'"+color+"'}").as(PlayerMongo.class);
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

    public void deleteMove(MoveMongoI move){moves.remove("{order: '"+move.getOrder()+"'}");
    }
    public void saveBoard(BoardMongoI b){
        board.save(b);
    }

    public void removeTemporaryCollections(){
        moves.drop();
        board.drop();
    }
}
