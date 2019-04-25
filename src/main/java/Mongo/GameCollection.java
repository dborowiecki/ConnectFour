package Mongo;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import java.net.UnknownHostException;

public class GameCollection {

    private MongoCollection players;

    public GameCollection() throws UnknownHostException {
        @SuppressWarnings({ "deprecation", "resource" })
        DB db = new MongoClient().getDB("Game");
        players = new Jongo(db).getCollection("players");
    }

    public PlayerMongo findByName(String name){
        return players.findOne("{_id: #", name).as(PlayerMongo.class);
    }

    public void save(PlayerMongo p){
        players.save(p);
    }
}
