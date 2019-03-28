import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;
import junitparams.mappers.IdentityMapper;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.Reader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.*;

@RunWith(JUnitParamsRunner.class)
public class BoardWinTest{

    HashMap<String, Integer> playerMoves;
    Board b;

    @Before
    public void setUp(){
        playerMoves = new HashMap<>();
        b = new Board();
    }

    @Test
    @FileParameters(value = "src/test/resources/horizontalNegative.csv", mapper = TokenMapper.class)
    public void checkFourHorizontalNegative(List<Object[]> tokens) {
        b.addPlayer("p1", TerminalColrs.ANSI_RED);
        for(Object[] token: tokens){
            b.addToken((String)token[0],(Integer)token[1]);
        }
        boolean result = b.checkFourHorizontal(5,3);

        assertThat(result, is(false));
    }

    @Test
    @FileParameters(value = "src/test/resources/horizontalPositive.csv", mapper = TokenMapper.class)
    public void checkFourHorizontalPositive(List<Object[]> tokens){
        b.addPlayer("p1", TerminalColrs.ANSI_RED);
        b.addPlayer("p2", TerminalColrs.ANSI_BLUE);
        for(Object[] token: tokens){
            b.addToken((String)token[0],(Integer)token[1]);
        }
        boolean result = b.checkFourHorizontal(4,3);
        boolean result1 = b.checkFourHorizontal(3,6);


        Boolean[] actual = {result, result1};
        Assertions.assertThat(actual)
                .contains(true, true)
                .doesNotContain(false);
    }

    @Test
    @FileParameters(value = "src/test/resources/verticalNegative.csv", mapper = TokenMapper.class)
    public void checkFourVerticalNegative(List<Object[]> tokens){
        b.addPlayer("p1", TerminalColrs.ANSI_RED);
        b.addPlayer("p2", TerminalColrs.ANSI_BLUE);
        for(Object[] token: tokens){
            b.addToken((String)token[0],(Integer)token[1]);
        }
        boolean result = b.checkFourVertical(5,0);
        boolean result1 = b.checkFourVertical(5,1);
        boolean result2 = b.checkFourVertical(3,2);
        boolean result3 = b.checkFourVertical(3,1);


        Boolean[] actual = {result, result1,result2, result3};
        Assertions.assertThat(actual)
                .contains(false, false,false,false)
                .doesNotContain(true);
    }


    @Test
    @FileParameters(value = "src/test/resources/verticalPositive.csv", mapper = TokenMapper.class)
    public void checkFourVerticalPositive(List<Object[]> tokens){
        b.addPlayer("p1", TerminalColrs.ANSI_RED);
        b.addPlayer("p2", TerminalColrs.ANSI_BLUE);
        for(Object[] token: tokens){
            b.addToken((String)token[0],(Integer)token[1]);
        }
        boolean result = b.checkFourVertical(5,3);
        boolean result1 = b.checkFourVertical(4,1);
        boolean result2 = b.checkFourVertical(2,0);


        Boolean[] actual = {result, result1, result2};
        Assertions.assertThat(actual)
                .contains(true, true, true)
                .doesNotContain(false);
    }

    @Test
    @FileParameters(value = "src/test/resources/diagonalNegative.csv", mapper = TokenMapper.class)
    public void  checkFourDiagonalNegative(List<Object[]> tokens){
        b.addPlayer("p1", TerminalColrs.ANSI_BLUE);
        b.addPlayer("p2", TerminalColrs.ANSI_RED);

        for (Object[] token: tokens){
            b.addToken((String)token[0], (Integer)token[1]);
        }
        boolean result1 = b.checkFourDiagonal(5,0);
        boolean result2 = b.checkFourDiagonal(4,1);
        boolean result3 = b.checkFourDiagonal(3,2);
        boolean result4 = b.checkFourDiagonal(2,3);

        Boolean[] actual = {result1, result2, result3, result4};
        System.out.print(b.getBoard());
        Assertions.assertThat(actual).containsExactly(false, false, false, false)
                .doesNotContain(true);
    }

    @Test
    @FileParameters(value = "src/test/resources/diagonalPositive.csv", mapper = TokenMapper.class)
    public void  checkFourDiagonalPositive(List<Object[]> tokens){
        b.addPlayer("p1", TerminalColrs.ANSI_BLUE);
        b.addPlayer("p2", TerminalColrs.ANSI_RED);

        for (Object[] token: tokens){
            b.addToken((String)token[0], (Integer)token[1]);
        }
        boolean result1 = b.checkFourDiagonal(5,0);
        boolean result2 = b.checkFourDiagonal(4,1);
        boolean result3 = b.checkFourDiagonal(0,0);
        boolean result4 = b.checkFourDiagonal(0,3);
        boolean result5 = b.checkFourDiagonal(1,1);
        Boolean[] actual = {result1, result2, result3, result4, result5};
        System.out.print(b.getBoard());
        Assertions.assertThat(actual).containsExactly(true, true, true, true, true)
                .doesNotContain(false);

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
