import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.assertj.core.api.Assertions;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import static org.hamcrest.core.Every.everyItem;



@RunWith(JUnitParamsRunner.class)
public class GameManagerTest {

    GameManager gm;

    @Before
    public void setUp(){
        gm = new GameManager();
    }
    @After
    public void teardown(){
        System.setIn(System.in);
        System.setOut(System.out);
        gm = null;

    }

    @Test
    public void setUpGameTestDefaultBoardSize(){
        ByteArrayInputStream in = new ByteArrayInputStream("Y\n".getBytes());
        System.setIn(in);
        gm.setUpGame();
        System.setIn(System.in);
        int boardSize = getBoardSize(gm.getBoard());
        assertThat(boardSize, equalTo(42));
    }

    @Test
    public void setUpGameTestDCustomBoardSize(){
        ByteArrayInputStream in = new ByteArrayInputStream("n\n12\n8".getBytes());
        System.setIn(in);
        gm.setUpGame();

        int boardSize = getBoardSize(gm.getBoard());
        System.setIn(System.in);

        assertThat(boardSize, equalTo(12*8));
    }

    @Test
    @Parameters({"janusz,red,karol,blue",
            "player1,purple,player2,yellow",
            "ja,blue,ty,red"})
    public void addPlayersTestPositive(String p1, String p1Color,String p2,String p2Color){
        createSetUp();
        //Catch output
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));
        //Generate input
        String inputStream = createInputStream(new String[]{p1, p1Color, p2, p2Color});
        ByteArrayInputStream in = new ByteArrayInputStream(inputStream.getBytes());
        System.setIn(in);
        //Create board to compare
        Board b = new Board();
        b.addPlayer(p1, TerminalColrs.translateColor(p1Color));
        b.addPlayer(p2, TerminalColrs.translateColor(p2Color));
        //Create board
        gm.addPlayers();

        Assertions.assertThat(gm).extracting(GameManager::getBoard).isEqualToComparingFieldByFieldRecursively(b);

    }

    @Test
    @Parameters({"janusz,kolor\nn,karol,blue, Color KOLOR not handled",
            "p1,blue,p1,red\nn, Player with this name alredy exists"})
    public void addPlayersTestNegative(String p1, String p1Color,String p2,String p2Color, String errMsg){

        createSetUp();
        //Catch output
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));
        //Generate input
        String inputStream = createInputStream(new String[]{p1, p1Color, p2, p2Color});
        ByteArrayInputStream in = new ByteArrayInputStream(inputStream.getBytes());
        System.setIn(in);

        //Create board
        gm.addPlayers();
        String out = myOut.toString();
        Assertions.assertThat(out).contains(errMsg);

    }

    private int getBoardSize(Board b){
       return  b.getBoardFields().length*b.getBoardFields()[0].length;
    }

    private void createSetUp(){
        ByteArrayInputStream in = new ByteArrayInputStream("Y\n".getBytes());
        System.setIn(in);
        gm.setUpGame();
        System.setIn(System.in);
    }

    private String createInputStream(String[] values){
        StringBuilder build = new StringBuilder();
        for(String value: values){
            build.append(value);
            build.append("\n");
        }
        return build.toString();
    }

}
