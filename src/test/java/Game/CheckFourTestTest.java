package Game;

import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;
import junitparams.mappers.IdentityMapper;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.Reader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(JUnitParamsRunner.class)
public class CheckFourTestTest{

    HashMap<String, Integer> playerMoves;
    public final static int NUMBER_OF_REQUIRED_CONNECTIONS = 4;
    Board b;
    ConnectionsChecker c;

    @Before
    public void setUp(){
        playerMoves = new HashMap<>();
        b = new Board();
        c = new ConnectionsChecker(b);
    }

    @Test
    @FileParameters(value = "src/test/resources/horizontalNegative.csv", mapper = TokenMapper.class)
    public void checkForHorizontalNegative(List<Object[]> tokens) {
        b.addPlayer("p1", TerminalColrs.ANSI_RED);
        for(Object[] token: tokens){
            b.addToken((String)token[0],(Integer)token[1]);
        }
        boolean result = c.checkForHorizontal(5,3, NUMBER_OF_REQUIRED_CONNECTIONS);

        assertThat(result, is(false));
    }

    @Test
    @FileParameters(value = "src/test/resources/horizontalPositive.csv", mapper = TokenMapper.class)
    public void checkForHorizontalPositive(List<Object[]> tokens){
        b.addPlayer("p1", TerminalColrs.ANSI_RED);
        b.addPlayer("p2", TerminalColrs.ANSI_BLUE);
        for(Object[] token: tokens){
            b.addToken((String)token[0],(Integer)token[1]);
        }
        boolean result =  c.checkForHorizontal(4,3, NUMBER_OF_REQUIRED_CONNECTIONS);
        boolean result1 = c.checkForHorizontal(4,0, NUMBER_OF_REQUIRED_CONNECTIONS);
        boolean result2 = c.checkForHorizontal(4,1, NUMBER_OF_REQUIRED_CONNECTIONS);
        boolean result3 = c.checkForHorizontal(3,4, NUMBER_OF_REQUIRED_CONNECTIONS);
        boolean result4 = c.checkForHorizontal(3,6, NUMBER_OF_REQUIRED_CONNECTIONS);


        Boolean[] actual = {result, result1, result2, result3, result4};
        Assertions.assertThat(actual)
                .contains(true, true, true, true, true)
                .doesNotContain(false);
    }

    @Test
    @FileParameters(value = "src/test/resources/verticalNegative.csv", mapper = TokenMapper.class)
    public void checkForVerticalNegative(List<Object[]> tokens){
        b.addPlayer("p1", TerminalColrs.ANSI_RED);
        b.addPlayer("p2", TerminalColrs.ANSI_BLUE);
        for(Object[] token: tokens){
            b.addToken((String)token[0],(Integer)token[1]);
        }
        boolean result =  c.checkForVertical(5,0,NUMBER_OF_REQUIRED_CONNECTIONS);
        boolean result1 = c.checkForVertical(5,1,NUMBER_OF_REQUIRED_CONNECTIONS);
        boolean result2 = c.checkForVertical(3,2,NUMBER_OF_REQUIRED_CONNECTIONS);
        boolean result3 = c.checkForVertical(3,1,NUMBER_OF_REQUIRED_CONNECTIONS);


        Boolean[] actual = {result, result1,result2, result3};
        Assertions.assertThat(actual)
                .contains(false, false,false,false)
                .doesNotContain(true);
    }


    @Test
    @FileParameters(value = "src/test/resources/verticalPositive.csv", mapper = TokenMapper.class)
    public void checkForVerticalPositive(List<Object[]> tokens){
        b.addPlayer("p1", TerminalColrs.ANSI_RED);
        b.addPlayer("p2", TerminalColrs.ANSI_BLUE);
        for(Object[] token: tokens){
            b.addToken((String)token[0],(Integer)token[1]);
        }

        boolean result1 =  c.checkForVertical(5,3,NUMBER_OF_REQUIRED_CONNECTIONS);
        boolean result2 =  c.checkForVertical(5,3,NUMBER_OF_REQUIRED_CONNECTIONS);
        boolean result3 = c.checkForVertical(4,1,NUMBER_OF_REQUIRED_CONNECTIONS);
        boolean result4 = c.checkForVertical(2,0,NUMBER_OF_REQUIRED_CONNECTIONS);
        boolean result5 = c.checkForVertical(3,0,NUMBER_OF_REQUIRED_CONNECTIONS);


        Boolean[] actual = {result1, result2, result3, result4, result5};
        Assertions.assertThat(actual)
                .contains(true, true, true, true,true)
                .doesNotContain(false);
    }

    @Test
    @FileParameters(value = "src/test/resources/diagonalNegative.csv", mapper = TokenMapper.class)
    public void  checkForDiagonalNegative(List<Object[]> tokens){
        b.addPlayer("p1", TerminalColrs.ANSI_BLUE);
        b.addPlayer("p2", TerminalColrs.ANSI_RED);

        for (Object[] token: tokens){
            b.addToken((String)token[0], (Integer)token[1]);
        }
        boolean result1 = c.checkForDiagonal(5,0, NUMBER_OF_REQUIRED_CONNECTIONS);
        boolean result2 = c.checkForDiagonal(4,1, NUMBER_OF_REQUIRED_CONNECTIONS);
        boolean result3 = c.checkForDiagonal(3,2, NUMBER_OF_REQUIRED_CONNECTIONS);
        boolean result4 = c.checkForDiagonal(2,3, NUMBER_OF_REQUIRED_CONNECTIONS);

        Boolean[] actual = {result1, result2, result3, result4};
        
        Assertions.assertThat(actual).containsExactly(false, false, false, false)
                .doesNotContain(true);
    }

    @Test
    @FileParameters(value = "src/test/resources/diagonalPositive.csv", mapper = TokenMapper.class)
    public void  checkForDiagonalPositive(List<Object[]> tokens){
        b.addPlayer("p1", TerminalColrs.ANSI_BLUE);
        b.addPlayer("p2", TerminalColrs.ANSI_RED);

        for (Object[] token: tokens){
            b.addToken((String)token[0], (Integer)token[1]);
        }
        boolean result1 = c.checkForDiagonal(5,0, NUMBER_OF_REQUIRED_CONNECTIONS);
        boolean result2 = c.checkForDiagonal(4,1, NUMBER_OF_REQUIRED_CONNECTIONS);
        boolean result3 = c.checkForDiagonal(0,0, NUMBER_OF_REQUIRED_CONNECTIONS);
        boolean result4 = c.checkForDiagonal(0,3, NUMBER_OF_REQUIRED_CONNECTIONS);
        boolean result5 = c.checkForDiagonal(1,1, NUMBER_OF_REQUIRED_CONNECTIONS);
        Boolean[] actual = {result1, result2, result3, result4, result5};
        
        Assertions.assertThat(actual).containsExactly(true, true, true, true, true)
                .doesNotContain(false);

    }

    @Test
    @FileParameters(value = "src/test/resources/verticalNegative.csv", mapper = TokenMapper.class)
    public void  checkFourTestNegative(List<Object[]> tokens){
        b.addPlayer("p1", TerminalColrs.ANSI_BLUE);
        b.addPlayer("p2", TerminalColrs.ANSI_RED);

        for (Object[] token: tokens){
            b.addToken((String)token[0], (Integer)token[1]);
        }
        boolean result1 = c.checkForConnectedTokens(5,0, NUMBER_OF_REQUIRED_CONNECTIONS);
        boolean result2 = c.checkForConnectedTokens(4,1, NUMBER_OF_REQUIRED_CONNECTIONS);
        boolean result3 = c.checkForConnectedTokens(3,2, NUMBER_OF_REQUIRED_CONNECTIONS);
        boolean result4 = c.checkForConnectedTokens(2,3, NUMBER_OF_REQUIRED_CONNECTIONS);

        Boolean[] actual = {result1, result2, result3, result4};

        Assertions.assertThat(actual).containsExactly(false, false, false, false)
                .doesNotContain(true);
    }

    public static class TokenMapper extends IdentityMapper {
        @Override
        public Object[] map(Reader reader) {
            Object[] map = super.map(reader);
            List<Object> result = new LinkedList<Object>();
            for (Object lineObj : map) {
                String line = lineObj.toString();
                int index = line.indexOf(",");
                String player = line.substring(0, index);
                String columnStr = line.substring(index + 1, line.length());
                Integer column = Integer.parseInt(columnStr);
                result.add(new Object[] {player, column});
            }
            Object[] out = {result};
            return out;
        }
    }
}
