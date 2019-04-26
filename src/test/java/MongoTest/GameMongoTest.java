package MongoTest;
import Game.TerminalColrs;
import Mongo.BoardMongo;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameMongoTest {

    //Co zastepujemy
    @Mock
    GameCollection gameCollection;
    @Mock
    PlayerMongo player;
    @Mock
    BoardMongo board;

    //Nasza atrapa
    @InjectMocks
    GameMongo game;


    @Test
    public void mockingTest(){
        PlayerMongo joe = new PlayerMongo("Walter White", "red", 0);
        doReturn(joe).when(gameCollection).findByName("Walter White");
        Assertions.assertThat(gameCollection.findByName("Walter White")).isEqualTo(joe);
    }


    @Test
    public void emptyMovesAtStart(){
        Assertions.assertThat(game.getPlayerMoves("Player")).isEmpty();
    }


    @Test
    public void noPlayersAtStart(){
        Assertions.assertThat(game.getPlayers())
                .isEmpty();
    }


    @Test
    public void createBoardTest(){
        //Arrange
        BoardMongo exepcted = new BoardMongo(10,10);
        doReturn(exepcted).when(gameCollection).getBoard();
        //doReturn(true).when(gameCollection).saveBoard(game.getBoard());
        //Act
        game.addBoard(exepcted);
        BoardMongo actual = gameCollection.getBoard();
        //Assert
        Assertions.assertThat(actual).usingRecursiveComparison().isEqualTo(actual);

    }

    @Test
    public void createBoardSizeExceptionColumns(){
        //Arrange
        when(board.getColumns()).thenReturn(-1);
        when(board.getRows()).thenReturn(10);
        //doReturn(true).when(gameCollection).saveBoard(game.getBoard());
        //Act
        //Assert
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> game.addBoard(board))
                .withMessageContaining("Columns: -1");
    }

    @Test
    public void createBoardSizeExceptionRows(){
        //Arrange
        when(board.getColumns()).thenReturn(10);
        when(board.getRows()).thenReturn(-10);
        //doReturn(true).when(gameCollection).saveBoard(game.getBoard());
        //Act
        //Assert
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> game.addBoard(board))
                .withMessageContaining("Rows: -10")
                .withNoCause();
    }

    @Test
    public void addPlayerTest(){
        
    }


}