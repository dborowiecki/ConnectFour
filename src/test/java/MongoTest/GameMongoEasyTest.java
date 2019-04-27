package MongoTest;
import Mongo.*;
import org.assertj.core.api.Assertions;
import org.easymock.*;
import org.easymock.internal.MocksControl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.assertj.core.api.Assertions.assertThat;
import static org.easymock.EasyMock.*;
import static org.easymock.MockType.NICE;


import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class GameMongoEasyTest {

    @Mock
    BoardMongo boardMongo;
    @Mock
    PlayerMongo playerMongo;
    @Mock
    MoveMongo moveMongo;
    @Mock
    GameCollection gameCollection;

    @TestSubject
    GameMongo game;

    @BeforeEach
    public void setup(){
        gameCollection = mock(NICE, GameCollection.class);
        boardMongo = mock(NICE, BoardMongo.class);
        playerMongo= mock(NICE, PlayerMongo.class);
        moveMongo  = mock(NICE, MoveMongo.class);

        game = new GameMongo(gameCollection);
    }

    @AfterEach
    public void teardown(){
        gameCollection = null;
        boardMongo     = null;
        playerMongo    = null;
        moveMongo      = null;
        game           = null;
    }

    @Test
    public void mockingWorksAsExpectedTest(){
        expect(gameCollection.findByColor("RED")).andReturn(playerMongo);
        replay(gameCollection);
        assertThat(gameCollection.findByColor("RED")).isEqualTo(playerMongo);
    }


    @Test
    public void getPlayerMovesEmptyPlayerTest(){
        expect(gameCollection.findByName("name")).andReturn(null);
        replay(gameCollection);
        assertThat(game.getPlayerMoves("name")).isNullOrEmpty();
    }

    @Test
    public void getPlayerMovesTest(){
        List<MoveMongo> allMoves = new LinkedList<>();
        MoveMongo move1 = new MoveMongo(playerMongo, 0);
        MoveMongo move2 = new MoveMongo(playerMongo, 1);
        allMoves.add(move1);
        allMoves.add(move2);
        allMoves.add(new MoveMongo(mock(PlayerMongo.class), 1));
        game.setMoves(allMoves);


        expect(gameCollection.findByName("player")).andReturn(playerMongo);
        replay(gameCollection);
        List<MoveMongo> moves = game.getPlayerMoves("player");

        assertThat(moves).isInstanceOf(List.class).containsExactly(move1, move2);
    }

    @Test
    public void makeMoveTest() {
        List<MoveMongo> moves = new LinkedList<>();

        game.setBoard(boardMongo);

        expect(gameCollection.findByName("name")).andReturn(playerMongo);
        expect(boardMongo.getColumns()).andReturn(10);
        expect(boardMongo.getNumberOfMoves()).andReturn(1);

        gameCollection.saveMove((MoveMongo) anyObject());
        expectLastCall().andAnswer(
                new IAnswer() {
                    public Object answer() {
                        moves.add(moveMongo);
                        return null;
                    }
                });

        replay(gameCollection, boardMongo);

        game.makeMove("name", 0);

        Assertions.assertThat(moves).containsExactly(moveMongo);
    }

    @Test
    public void makeMoveNegativeColumnsTest(){
        game.setBoard(boardMongo);

        expect(gameCollection.findByName("name")).andReturn(playerMongo);
        expect(boardMongo.getColumns()).andReturn(10);
        replay(gameCollection, boardMongo);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> game.makeMove("name",-1))
                .withNoCause();
    }

    @Test
    public void makeMoveOverSizeColumnsTest(){
        BoardMongo b = new BoardMongo(10,10);
        game.setBoard(b);
        expect(gameCollection.findByName("name")).andReturn(playerMongo);
        replay(gameCollection);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> game.makeMove("name",20))
                .withMessageContaining("Column have to be between 0 and 10")
                .withNoCause();
    }

    @Test
    public void reverseLastMoveNoMovesExceptionTest(){
        expect(gameCollection.findByName("player")).andReturn(playerMongo);
        replay(gameCollection);
        game.setMoves(new LinkedList<>());
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> game.reverseLastMove("name"))
                .withMessage("There vere no moves")
                .withNoCause();
    }
}

