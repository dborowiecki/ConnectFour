package Mongo;

import org.assertj.core.api.Assertions;
import org.jongo.FindOne;
import org.jongo.MongoCollection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.doReturn;


@ExtendWith(MockitoExtension.class)
public class GameCollectionTest {

    @Mock(name = "player")
    MongoCollection mongoCollectionMock;
    @Mock
    FindOne queryMock;

    @InjectMocks
    GameCollection gameCollection;


    @Test
    public void findByNameTest(){
        PlayerMongoI p = new PlayerMongo("John","red");
        doReturn(queryMock).when(mongoCollectionMock).findOne("{_id: 'John'}");
        doReturn(p).when(queryMock).as(PlayerMongo.class);
        Assertions.assertThat(gameCollection.findByName("John")).isEqualTo(p);
    }

    @Test
    public void findByColorTest(){
        PlayerMongoI p = new PlayerMongo("John","red");
        doReturn(queryMock).when(mongoCollectionMock).findOne("{color:'red'}");
        doReturn(p).when(queryMock).as(PlayerMongo.class);
        Assertions.assertThat(gameCollection.findByColor("red")).isEqualTo(p);
    }

    @Test
    public void getBoardTest(){
        BoardMongoI b = new BoardMongo(5,5);
        doReturn(queryMock).when(mongoCollectionMock).findOne();
        doReturn(b).when(queryMock).as(BoardMongo.class);

        Assertions.assertThat(gameCollection.getBoard())
                .isEqualToComparingFieldByFieldRecursively(b);
    }

}
