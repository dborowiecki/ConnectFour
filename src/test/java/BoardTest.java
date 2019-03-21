
import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsMapContaining;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Every.everyItem;



@RunWith(JUnitParamsRunner.class)
public class BoardTest {

    Board b;
    static final String EMPTY_BOARD =
            " ◸   ◹  ◸   ◹  ◸   ◹  ◸   ◹  ◸   ◹  ◸   ◹  ◸   ◹ \n"+
            "|  \u001B[0m   ||  \u001B[0m   ||  \u001B[0m   ||  \u001B[0m   ||  \u001B[0m   ||  \u001B[0m   ||  \u001B[0m   |\n"+
            " ◺   ◿  ◺   ◿  ◺   ◿  ◺   ◿  ◺   ◿  ◺   ◿  ◺   ◿ \n"+
            " ◸   ◹  ◸   ◹  ◸   ◹  ◸   ◹  ◸   ◹  ◸   ◹  ◸   ◹ \n"+
                    "|  \u001B[0m   ||  \u001B[0m   ||  \u001B[0m   ||  \u001B[0m   ||  \u001B[0m   ||  \u001B[0m   ||  \u001B[0m   |\n"+
            " ◺   ◿  ◺   ◿  ◺   ◿  ◺   ◿  ◺   ◿  ◺   ◿  ◺   ◿ \n"+
            " ◸   ◹  ◸   ◹  ◸   ◹  ◸   ◹  ◸   ◹  ◸   ◹  ◸   ◹ \n"+
                    "|  \u001B[0m   ||  \u001B[0m   ||  \u001B[0m   ||  \u001B[0m   ||  \u001B[0m   ||  \u001B[0m   ||  \u001B[0m   |\n"+
            " ◺   ◿  ◺   ◿  ◺   ◿  ◺   ◿  ◺   ◿  ◺   ◿  ◺   ◿ \n"+
            " ◸   ◹  ◸   ◹  ◸   ◹  ◸   ◹  ◸   ◹  ◸   ◹  ◸   ◹ \n"+
                    "|  \u001B[0m   ||  \u001B[0m   ||  \u001B[0m   ||  \u001B[0m   ||  \u001B[0m   ||  \u001B[0m   ||  \u001B[0m   |\n"+
            " ◺   ◿  ◺   ◿  ◺   ◿  ◺   ◿  ◺   ◿  ◺   ◿  ◺   ◿ \n"+
            " ◸   ◹  ◸   ◹  ◸   ◹  ◸   ◹  ◸   ◹  ◸   ◹  ◸   ◹ \n"+
                    "|  \u001B[0m   ||  \u001B[0m   ||  \u001B[0m   ||  \u001B[0m   ||  \u001B[0m   ||  \u001B[0m   ||  \u001B[0m   |\n"+
            " ◺   ◿  ◺   ◿  ◺   ◿  ◺   ◿  ◺   ◿  ◺   ◿  ◺   ◿ \n"+
            " ◸   ◹  ◸   ◹  ◸   ◹  ◸   ◹  ◸   ◹  ◸   ◹  ◸   ◹ \n"+
                    "|  \u001B[0m   ||  \u001B[0m   ||  \u001B[0m   ||  \u001B[0m   ||  \u001B[0m   ||  \u001B[0m   ||  \u001B[0m   |\n"+
            " ◺   ◿  ◺   ◿  ◺   ◿  ◺   ◿  ◺   ◿  ◺   ◿  ◺   ◿ \n";
    @Before
    public void setup() {
         b = new Board();
    }

    public static String readStringFromFile(String fileName)
    {
        try {
            return new String(Files.readAllBytes(Paths.get(fileName)));
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    //@Ignore
    @Test
    public void assertGenerateEmptyBoard(){
        String expectedEmptyBoard = EMPTY_BOARD;
        Board board = new Board();

        assertThat(board.getBoard(), is(expectedEmptyBoard));
    }

    @Test
    public void assertAddPlayerTokenPositive(){
        String playerName = "Player1";
        boolean result = b.addToken(playerName, 1);
        BoardField expectedBoardField = b.getBoardFields()[b.getNumberOfRows()-1][1];

        assertThat(true, is(result));
        assertThat(b.getPlayerFields(), hasEntry(expectedBoardField, playerName));
    }

    @Test
    public void assertAddPlayerTokenPositive2Rows(){
        String playerName = "Player1";
        b.addToken(playerName, 1);
        b.addToken(playerName, 1);
        List<BoardField> expected = new LinkedList<>();
        expected.add(b.getBoardFields()[b.getNumberOfRows()-1][1]);
        expected.add(b.getBoardFields()[b.getNumberOfRows()-2][1]);

        assertThat(b.getPlayerFields().keySet(), containsInAnyOrder(expected.get(0),expected.get(1)) );
    }

    @Test
    public void assertAddPlayerTokenOutOfBound(){
        boolean result = b.addToken("Player1", -1);
        assertThat(result, equalTo(false));
    }
    @Test
    public void assertAddPlayerTokenOutOfBound2(){
        boolean result = b.addToken("Player1", b.getNumberOfColumns());
        assertThat(result, equalTo(false));
    }

    private void fillColumn(Board b, int column){
        for(int i=0;i<b.getNumberOfRows();i++){
            b.addToken("Player1", column);
        }
    }
    @Test
    public void assertAddPlayerTokenColumnOverload(){
        fillColumn(b, 1);
        boolean result = b.addToken("Player1", 1);
        assertThat(result, equalTo(false));
    }
}
