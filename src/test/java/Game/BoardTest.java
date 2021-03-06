package Game;

import junitparams.JUnitParamsRunner;
import org.assertj.core.api.Assertions;
import junitparams.Parameters;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
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

    @After
    public void tearUP(){ b = null;}
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
    public void assertCustomBoardSizeConstructor(){
        int columns = 10;
        int rows = 10;
        Board board = new Board(columns, rows);
        Integer[] actual = {board.getNumberOfColumns(), board.getNumberOfRows()};
        assertThat(actual, is(arrayContaining(columns, rows)));
    }


    @Rule
    public ExpectedException exceptionGrabber = ExpectedException.none();

    @Test
    public void assertIllegalBoardSize(){
        exceptionGrabber.expect(IllegalArgumentException.class);
        new Board(-1, -1);
    }

    @Test
    public void assertAddPlayerTokenPositive(){
        String playerName = "Player1";
        b.addPlayer(playerName, TerminalColrs.ANSI_CYAN);
        boolean result = b.addToken(playerName, 1);
        BoardField expectedBoardField = b.getBoardFields()[b.getNumberOfRows()-1][1];

        assertThat(true, is(result));
        assertThat(b.getPlayerFields(), hasEntry(expectedBoardField, playerName));
    }

    @Test
    public void assertAddPlayerTokenPositive2Rows(){
        String playerName = "Player1";
        b.addPlayer(playerName, TerminalColrs.ANSI_CYAN);
        b.addToken(playerName, 1);
        b.addToken(playerName, 1);
        List<BoardField> expected = new LinkedList<>();
        expected.add(b.getBoardFields()[b.getNumberOfRows()-1][1]);
        expected.add(b.getBoardFields()[b.getNumberOfRows()-2][1]);


        assertThat(b.getPlayerFields().keySet(), containsInAnyOrder(expected.toArray()));
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
    @Test(expected = IllegalArgumentException.class)
    public void addTokenNoExistingPlayer(){
        b.addToken("NotInGameMan", 1);

    }

    private void fillColumn(Board b, int column){
        for(int i=0;i<b.getNumberOfRows();i++){
            b.addToken("Player1", column);
        }
    }
    @Test
    public void assertAddPlayerTokenColumnOverload(){
        b.addPlayer("Player1",TerminalColrs.ANSI_CYAN);
        fillColumn(b, 1);
        boolean result = b.addToken("Player1", 1);
        assertThat(result, equalTo(false));
    }

    @Test
    public void assertAddPlayerPositive(){
        String playerName1 = "Player1";
        String playerName2 = "Player2";
        String playerColor = TerminalColrs.ANSI_BLUE;
        String player2Color = TerminalColrs.ANSI_GREEN;
        HashMap<String, String> actual = new HashMap<>();
        actual.put(playerName1, playerColor);
        actual.put(playerName2, player2Color);

        b.addPlayer(playerName1, playerColor);
        b.addPlayer(playerName2, player2Color);
        assertThat(actual.entrySet(), everyItem(isIn(b.getPlayerColors().entrySet())));
        assertThat(actual.values(), is(containsInAnyOrder(b.getPlayerColors().values().toArray())));
    }

    @Test(expected=IllegalArgumentException.class)
    public void assertAddDuplicatePlayer(){
        b.addPlayer("Duplicate", TerminalColrs.ANSI_BLUE);
        b.addPlayer("Duplicate", TerminalColrs.ANSI_BLUE);
        assertThat(true, equalTo(true));
    }

    @Test(expected=IllegalArgumentException.class)
    public void assertAddPlayerNoExistingColor(){
        b.addPlayer("Player", "orange");
    }

    @Test
    public void assertCheckPlayerInGamePositive(){
        String playerName = "Player";
        b.addPlayer(playerName, TerminalColrs.ANSI_CYAN);

        assertThat(b.hasPlayer(playerName), is(true));
    }

    @Test
    public void assertCheckPlayerInGameNegative(){
        String playerName = "Player";

        assertThat(b.hasPlayer(playerName), is(false));
    }


    private Object[] IntParams() {
        Object[] l = new Object[3];
        l[0]=(new Integer[]{1,2,3,4,5});
        l[1]=(new Integer[]{1,1,1,1,1});
        l[2]=(new Integer[]{1,2,2,1,2});
        return l;
    }

    @Test
    @Parameters(method = "IntParams")
    public void removeTokenPositive(Integer[] tokens){
        String playerName = "p1";
        b.addPlayer(playerName, TerminalColrs.ANSI_RED);
        for(Integer i: tokens)
            b.addToken("p1", i);

        for(Integer i: tokens)
            b.removeTokenFromColumn(i);

        assertThat(b.getPlayerFields().keySet().isEmpty(), equalTo(true));
    }

    @Test
    @Parameters(method = "IntParams")
    public void removeTokenNegative(Integer[] tokens){
        String playerName = "p1";
        b.addPlayer(playerName, TerminalColrs.ANSI_RED);
        List<Boolean> results = new LinkedList<>();
        for(Integer i: tokens)
            results.add(b.removeTokenFromColumn(i));

       Assertions.assertThat(results).doesNotContain(true).containsOnly(false);
    }

}

