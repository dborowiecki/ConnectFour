import junitparams.JUnitParamsRunner;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import java.io.ByteArrayInputStream;
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

    private int getBoardSize(Board b){
       return  b.getBoardFields().length*b.getBoardFields()[0].length;
    }
}
