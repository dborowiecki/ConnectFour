package Mongo;

import java.rmi.UnknownHostException;

import org.jongo.Jongo;
import org.jongo.MongoCollection;

import com.mongodb.DB;
import com.mongodb.MongoClient;




public class GameCollection {

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

    public PlayerMongo findByName(String name){
        return players.findOne("{_id: #", name).as(PlayerMongo.class);
    }

    public PlayerMongo findByColor(String color){
        return players.findOne("{color: #", color).as(PlayerMongo.class);
    }


    public BoardMongo getBoard(){
        return board.findOne().as(BoardMongo.class);
    }

    public void savePlayer(PlayerMongo p){
        players.save(p);
    }


    public void saveMove(MoveMongo move){
        moves.save(move);
    }

    public void saveBoard(BoardMongo b){
        board.save(b);
    }
}
