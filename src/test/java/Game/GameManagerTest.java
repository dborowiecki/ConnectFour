package Game;

import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import junitparams.mappers.IdentityMapper;
import org.assertj.core.api.Assertions;
import org.junit.*;
import org.junit.runner.RunWith;

import java.io.*;
import java.util.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;




@RunWith(JUnitParamsRunner.class)
public class GameManagerTest {

    GameManager gm;
    public static final String TEST_LEADBOARD_PATH = "src/test/leadboard.csv";
    public static final String SAVES_FILE_NAME = "src/test/save1.test";
    @Before
    public void setUp(){
        gm = new GameManager();
        gm.setLeadboard(TEST_LEADBOARD_PATH);
    }
    @After
    public void teardown(){
        System.setIn(System.in);
        System.setOut(System.out);
        gm = null;
        cleanFiles();
    }

    private void cleanFiles() {
        try {
            new FileWriter(TEST_LEADBOARD_PATH);
            new FileWriter(SAVES_FILE_NAME);
        } catch (Exception e){
            System.out.println("Error with leadboard test file");
        }
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
    public void setUpGameTestInvalidBoardSize(){
        ByteArrayInputStream in = new ByteArrayInputStream("n\n-5\n8".getBytes());
        System.setIn(in);
        gm.setUpGame();

        int boardSize = getBoardSize(gm.getBoard());
        System.setIn(System.in);

        assertThat(boardSize, equalTo(42));
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
    @Parameters({"janusz,kolor\nred,karol,blue, Color is invalid",
            "p1,blue,p1,red\nred, This player is alredy in game"})
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

    @Test
    @Parameters(method = "MovesParams")
    public void makePlayerMoveToRightAndLeftTest(String[] moves){
        //Catch output
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));

        //Create board
        createSetUp();
        addPlayers();

        //Generate input
        String inputStream = createInputStream(moves);
        ByteArrayInputStream in = new ByteArrayInputStream(inputStream.getBytes());
        System.setIn(in);

        gm.makeMove("p1", new Scanner(in));

        int expectedColumn = Integer.parseInt(moves[moves.length-1]);
        BoardField expectedField = gm.getBoard().getField(5,expectedColumn);
        String actualFieldOwner  = gm.getBoard().getPlayerFields().get(expectedField);

        Assertions.assertThat(actualFieldOwner).isEqualTo("p1");
    }
    private Object[] MovesParams() {
        Object[] l = new Object[3];
        l[0]=(new String[]{"d", "d","d","d", "d", "d", "d", "d", "s", "6"});
        l[1]=(new String[]{"d","d","d","d","d","a","a","a","a","a","a","s", "0"});
        l[2]=(new String[]{"d","d", "d", "a", "d","d","d","a","s", "4"});
        return l;
    }

    @Test
    public void turnBackMoveTest(){
        //Catch output
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));

        //Create board
        createSetUp();
        addPlayers();

        //Generate input
        String beforeTurnBack = createInputStream(new String[]{"d", "d","d","s"});
        String afterTurnBack = createInputStream(new String[]{"z","d","s","s"});
        ByteArrayInputStream in;

        in = new ByteArrayInputStream(beforeTurnBack.getBytes());
        System.setIn(in);
        gm.makeMove("p1", new Scanner(in));
        in = new ByteArrayInputStream(afterTurnBack.getBytes());
        System.setIn(in);
        gm.makeMove("p2", new Scanner(in));

        int expectedNotPresentColumn =2;
        int expectedColumn = 1;
        HashMap<BoardField, String > boardFields = gm.getBoard().getPlayerFields();
        BoardField expectedField = gm.getBoard().getField(5,expectedColumn);
        BoardField shouldBeEmpty = gm.getBoard().getField(5, expectedNotPresentColumn);

        Assertions.assertThat(boardFields).containsKeys(expectedField).doesNotContainKeys(shouldBeEmpty);
    }

    @Test
    public void putTokenColumnOverload(){
        //Catch output
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));

        //Create board
        createSetUp();
        addPlayers();

        //Generate input
        fillFirstColumn();
        String overloading = createInputStream(new String[]{"s", "d", "s"});
        ByteArrayInputStream in;
        in = new ByteArrayInputStream(overloading.getBytes());
        gm.makeMove("p1", new Scanner(in));

        Assertions.assertThat(myOut.toString()).contains("You can't put token in this column");
    }

    @Test
    public void getWinnerTest(){
        //Catch output
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));

        //Create board
        createSetUp();
        addPlayers();

        //Generate input
        String overloading = createInputStream(new String[]{"s"});
        ByteArrayInputStream in;

        for(int i=0;i<gm.CONNECTED_FOR_WIN;i++) {
            in = new ByteArrayInputStream(overloading.getBytes());
            gm.makeMove("p1", new Scanner(in));
        }
        in = new ByteArrayInputStream(overloading.getBytes());
        boolean foundWinner = gm.makeMove("p1", new Scanner(in));

        assertThat(foundWinner, equalTo(true));
    }

    @Test
    @FileParameters(value = "src/test/resources/fillDrawBoard.csv", mapper = GameManagerTest.TokenMapper.class)
    public void makeDrawTest(Object[] token){
        //Catch output
       final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
       System.setOut(new PrintStream(myOut));

        //Create board
        createSetUp();
        addPlayers();

        //Generate input
        String filling = createInputStream((String[])token);
        ByteArrayInputStream in;
        in = new ByteArrayInputStream(filling.getBytes());
        System.setIn(in);
        gm.runGame();
        Assertions.assertThat(myOut.toString()).contains("IT'S A DRAW, THANKS FOR PLAYING");
    }

    @Test
    @FileParameters(value = "src/test/resources/fillWinBoard.csv", mapper = GameManagerTest.TokenMapper.class)
    public void saveWinnerLoserToLeadBoardTest(Object[] token){
        //Catch output
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));

        //Create board
        createSetUp();
        addPlayers();

        //Generate input
        String filling = createInputStream((String[])token);
        ByteArrayInputStream in;
        in = new ByteArrayInputStream(filling.getBytes());
        System.setIn(in);
        gm.runGame();

        String fileContent = readFromLeadboard();
        Assertions.assertThat(fileContent).contains("p1,1,0,0").contains("p2,0,0,1");
    }

    @Test
    @FileParameters(value = "src/test/resources/savingGameTest.txt", mapper = GameManagerTest.TokenMapper.class)
    public void savingGameTest(Object[] token){
        //Catch output
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));

        //Create board
        createSetUp();
        addPlayers();

        //Generate input
        String filling = createInputStream((String[])token);
        ByteArrayInputStream in;
        in = new ByteArrayInputStream(filling.getBytes());
        System.setIn(in);

        try {
            gm.runGame();
        } catch (Exception e){
            //catching end of user input error
        }

        String savePath = SAVES_FILE_NAME;
        in = new ByteArrayInputStream(savePath.getBytes());
        System.setIn(in);
        GameManager savedGame = GameManager.loadGame();
        Assertions.assertThat(savedGame)
                .usingRecursiveComparison()
                .ignoringFields("sc")
                .isEqualTo(gm);
    }

    @Test
    public void loadFileCatchException(){
        //Catch output
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));

        String filling = createInputStream(new String[]{"notFile","y", "notFile2", "n"});
        ByteArrayInputStream in = new ByteArrayInputStream(filling.getBytes());
        System.setIn(in);
        GameManager gm = GameManager.loadGame();
        Assertions.assertThat(gm).isNull();
    }
    @Test
    public void startGameTest(){
        //Catch output
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));

        //Create board
        createSetUp();
        addPlayers();

        //Generate input
        String filling = createInputStream(new String[]{"1"});
        ByteArrayInputStream in;
        in = new ByteArrayInputStream(filling.getBytes());
        System.setIn(in);
        try{gm.startGame();} catch (NoSuchElementException ignored){}

        //Check if this calls both methods from start
        Assertions.assertThat(myOut.toString()).contains("Default board size? (Y/n)");
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
    private void addPlayers(){
        ByteArrayInputStream in = new ByteArrayInputStream("p1\nred\np2\nblue\n".getBytes());
        System.setIn(in);
        gm.addPlayers();
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

    private void fillFirstColumn(){
        String beforeTurnBack = createInputStream(new String[]{"s"});
        ByteArrayInputStream in;
        for(int i=0; i<gm.getBoard().getNumberOfRows();i++) {
            in = new ByteArrayInputStream(beforeTurnBack.getBytes());
            System.setIn(in);
            gm.makeMove("p1", new Scanner(in));
        }
    }

    private String readFromLeadboard() {
        String output = "";
        String nextLine;
        try {
            FileReader fileReader = new FileReader(TEST_LEADBOARD_PATH);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((nextLine = bufferedReader.readLine()) != null) {
                output+=nextLine;
            }
        } catch (Exception e){
            System.out.println("Error while reading leadboard");
        }
        return output;
    }


    public static class TokenMapper extends IdentityMapper {
        @Override
        public Object[] map(Reader reader) {
            Object[] map = super.map(reader);
            List<String> movesSum = new LinkedList<>();
            for (Object lineObj : map) {
                String line = lineObj.toString();
                int index = line.indexOf(",");
                String columnStr = line.substring(index + 1);
                String[] moves = columnStr.split(";");

                movesSum.addAll(Arrays.asList(moves));
            }
            Object[] out = {movesSum.toArray()};
            return out;
        }
    }

}
