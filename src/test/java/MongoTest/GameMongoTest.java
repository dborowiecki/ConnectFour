package MongoTest;
import Game.TerminalColrs;
import Mongo.GameCollection;
import Mongo.GameMongo;
import Mongo.PlayerMongo;
import com.mongodb.MongoClient;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class GameMongoTest {

    //Co zastepujemy
    @Mock
    GameCollection gameCollection;

    //Nasza atrapa
    @InjectMocks
    GameMongo game;


    @Test
    public void mockingWorskAsExpected(){
        PlayerMongo joe = new PlayerMongo("Walter White", "red", 0);
        doReturn(joe).when(gameCollection).findByName("Walter White");
        Assertions.assertThat(gameCollection.findByName("Walter White")).isEqualTo(joe);
    }


}