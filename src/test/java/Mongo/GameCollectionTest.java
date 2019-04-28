package Mongo;

import org.assertj.core.api.Assertions;
import org.jongo.FindOne;
import org.jongo.MongoCollection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;


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

        verify(mongoCollectionMock, times(1)).findOne("{_id: 'John'}");
    }

    @Test
    public void findByColorTest(){
        PlayerMongoI p = new PlayerMongo("John","red");
        doReturn(queryMock).when(mongoCollectionMock).findOne("{color:'red'}");
        doReturn(p).when(queryMock).as(PlayerMongo.class);
        Assertions.assertThat(gameCollection.findByColor("red")).isEqualTo(p);

        verify(mongoCollectionMock,times(1)).findOne(anyString());
        verify(queryMock,times(1)).as(anyObject());
    }

    @Test
    public void getBoardTest(){
        BoardMongoI b = new BoardMongo(5,5);
        doReturn(queryMock).when(mongoCollectionMock).findOne();
        doReturn(b).when(queryMock).as(BoardMongo.class);

        Assertions.assertThat(gameCollection.getBoard())
                .isEqualToComparingFieldByFieldRecursively(b);

        verify(mongoCollectionMock,times(1)).findOne();
        verify(queryMock,times(1)).as(anyObject());
    }

    @Test
    public void savePlayerTest(){
        List<PlayerMongoI> players = new LinkedList<>();
        PlayerMongoI player = new PlayerMongo("John","red");

        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        players.add(player);
                        return null;
                    }
                }).when(mongoCollectionMock).save(player);

        gameCollection.savePlayer(player);
        Assertions.assertThat(players).containsExactly(player);
        verify(mongoCollectionMock,times(1)).save(anyObject());
    }

    @Test
    public void removeTempDataTest(){
        List<BoardMongoI> boards = new LinkedList<BoardMongoI>();
        boards.add(new BoardMock());
        List<MoveMongoI> moves = new LinkedList<MoveMongoI>();
        moves.add(new MoveMock());
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        boards.clear();
                        moves.clear();
                        return null;
                    }
                }).when(mongoCollectionMock).drop();

        gameCollection.removeTemporaryCollections();

        org.junit.jupiter.api.Assertions.assertAll(
                () -> Assertions.assertThat(boards.isEmpty()).isTrue(),
                () -> Assertions.assertThat(moves).isEmpty()
        );

        verify(mongoCollectionMock, times(2)).drop();
    }

}
