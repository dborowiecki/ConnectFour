package Mongo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

public class GamMongoCustomMockTest {

    BoardMongoI board;
    PlayerMongoI player;
    MoveMongoI move;
    GameCollectionI game;

    @BeforeEach
    public void setup(){
        board = new BoardMock();
        player = new PlayerMock();
        move = new MoveMock();
        game = new GameCollectionMock();
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
        Assertions.assertAll(

        );
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

    @Override
    public String getName() { return "John Doe"; }

    @Override
    public String getColor() { return "RED"; }

    @Override
    public Integer getWins() { return 0; }

    @Override
    public Integer getLose() { return 0; }

    @Override
    public Integer getDraw() { return 0; }

    @Override
    public void setColor(String color) { }

    @Override
    public void setName(String name) { }
}

class GameCollectionMock implements GameCollectionI{
    PlayerMongoI p;
    List<MoveMongoI> moves;
    List<BoardMongoI> board;

    public GameCollectionMock(){
        moves = new LinkedList<>();
        board = new LinkedList<>();
    }

    @Override
    public PlayerMongoI findByName(String name) {
        return p;
    }

    @Override
    public PlayerMongoI findByColor(String color) {
        return p;
    }

    @Override
    public void savePlayer(PlayerMongoI p) { }

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
    protected void finalize(){ instances--; }

    @Override
    public PlayerMongoI getPlayer() { return new PlayerMock(); }

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