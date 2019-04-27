package Mongo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class GamMongoCustomMockTest {

    BoardMongoI board;
    PlayerMongoI player;
    MoveMongoI move;
    GameCollectionI gameCollection;
    GameMongo game;

    @BeforeEach
    public void setup(){
        board = new BoardMock();
        player = new PlayerMock("John Doe");
        move = new MoveMock();
        gameCollection = new GameCollectionMock();
        game = new GameMongo(gameCollection);
        game.setBoard(board);
    }

    @AfterEach
    public void teardown(){
        board = null;
        player = null;
        move = null;
        game = null;
    }

    @Test
    public void mocksTest(){
        gameCollection.savePlayer(player);

        org.junit.jupiter.api.Assertions.assertAll(
                () -> Assertions.assertThat(board.getColumns()).isEqualTo(0),
                () -> Assertions.assertThat(player.getName()).isEqualToIgnoringCase("john doe"),
                () -> Assertions.assertThat(gameCollection.findByName("John Doe")).isInstanceOf(player.getClass()),
                () -> Assertions.assertThat(move).isInstanceOf(MoveMongoI.class)
        );
    }

    @Test
    public void endGameWinTest(){
        PlayerMock secondPlayer = new PlayerMock("John");
        game.addPlayer(player);
        game.addPlayer(secondPlayer);

        game.endGame("John");
        org.junit.jupiter.api.Assertions.assertAll(
                () -> Assertions.assertThat(player.getLose()).isEqualTo(10),
                () -> Assertions.assertThat(secondPlayer.getWins()).isEqualTo(10)
        );
    }

    @Test
    public void endGameInvalidUserTest(){
        PlayerMock secondPlayer = new PlayerMock("Im not in game");
        game.addPlayer(player);
        game.addPlayer(secondPlayer);


        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> game.endGame("John"))
                .withMessageContaining("Player was not in game")
        .withNoCause();
    }

    @Test
    public void endGameDrawTest(){
        game.addPlayer(player);

        game.endGameDraw();
        org.junit.jupiter.api.Assertions.assertAll(
                () -> Assertions.assertThat(player.getDraw()).isEqualTo(10),
                () -> Assertions.assertThat(player.getWins()).isEqualTo(0),
                () -> Assertions.assertThat(player.getLose()).isEqualTo(0)
        );
    }

    @Test
    public void getPlayerScoreTest(){

        game.addPlayer(player);
        game.endGameDraw();
        game.endGameDraw();
        game.endGame("John Doe");
        game.endGame("John Doe");
        game.endGame("John Doe");
        HashMap<String, Integer> result = game.getPlayerScore("John Doe");

        Assertions.assertThat(result).containsEntry("Win",30)
        .containsEntry("Lose", 0)
        .containsEntry("Draw",20);
    }

    @Test
    public void getScoreIllegalPlayerTest(){
        game.addPlayer(player);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> game.getPlayerScore("John"))
                .withMessageContaining("Player not found")
                .withNoCause();
    }

    @Test
    public void getMovesTest(){
        game.makeMove("John Doe", 0);
        List<MoveMongoI> moves = game.getMoves();
        Assertions.assertThat(moves).hasSize(1).hasOnlyElementsOfType(MoveMongo.class);
    }
}

class BoardMock implements BoardMongoI{

    @Override
    public int getRows() {return 0;}

    @Override
    public int getColumns() { return 0; }

    @Override
    public void incNumberOfMoves() { }

    @Override
    public void decNumberOfMoves() { }

    @Override
    public int getNumberOfMoves() { return 0; }

    @Override
    public void setRows(int rows) { }

    @Override
    public void setColumns(int columns) { }
}

class PlayerMock implements PlayerMongoI{
    String id;
    int win,draw,lose;

    public PlayerMock(String id){
        this.id=id;
    }
    @Override
    public String getName() { return id; }

    @Override
    public String getColor() { return "RED"; }

    @Override
    public Integer getWins() { return win; }

    @Override
    public Integer getLose() { return lose; }

    @Override
    public Integer getDraw() { return draw; }

    @Override
    public void setColor(String color) { }

    @Override
    public void setName(String name) { }

    @Override
    public void addWin() { win+=10;}

    @Override
    public void addLose() { lose+=10;}

    @Override
    public void addDraw() { draw+=10;}
}

class GameCollectionMock implements GameCollectionI{
    List<PlayerMongoI> p;
    List<MoveMongoI> moves;
    List<BoardMongoI> board;
    public GameCollectionMock(){
        moves = new LinkedList<>();
        board = new LinkedList<>();
        p = new LinkedList<PlayerMongoI>();
    }

    @Override
    public PlayerMongoI findByName(String name) {
        for(PlayerMongoI player: p)
            if(player.getName()==name)
                return player;
            return null;
    }

    @Override
    public PlayerMongoI findByColor(String color) {
        for(PlayerMongoI player: p)
            if(player.getColor()==color)
                return player;

            return null;
    }

    @Override
    public void savePlayer(PlayerMongoI player) {p.add(player); }

    @Override
    public void saveMove(MoveMongoI m) {moves.add(m); }

    @Override
    public void saveBoard(BoardMongoI b) { board.add(b);}

    @Override
    public void deleteMove(MoveMongoI m) { moves.remove(m);}
}

class MoveMock implements MoveMongoI{
    private static int instances=0;

    public MoveMock(){
        instances++;
    }

    @Override
    protected void finalize(){ try{super.finalize();instances--; }catch (Throwable  e){}}

    @Override
    public PlayerMongoI getPlayer() { return new PlayerMock("movemocked"); }

    @Override
    public Integer getColumn() { return 1; }

    @Override
    public Integer getOrder() { return  instances; }

    @Override
    public void setPlayer(PlayerMongoI p) { }

    @Override
    public void setColumn(Integer column) { }

    @Override
    public void setOrder(Integer order) { }
}