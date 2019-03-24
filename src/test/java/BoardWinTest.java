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
        boolean result = b.checkFour(b.getBoardFields()[5][3]);

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
        boolean result = b.checkFour(b.getBoardFields()[4][3]);
        boolean result1 = b.checkFour(b.getBoardFields()[3][6]);

        System.out.println(b.getBoard());
        Boolean[] actual = {result, result1};
        Assertions.assertThat(actual)
                .contains(true, true)
                .doesNotContain(false);// contains(false, false, false));
    }

    @Test
    @FileParameters(value = "src/test/resources/verticalNegative.csv", mapper = TokenMapper.class)
    public void checkFourVerticalNegative(List<Object[]> tokens){
        b.addPlayer("p1", TerminalColrs.ANSI_RED);
        b.addPlayer("p2", TerminalColrs.ANSI_BLUE);
        for(Object[] token: tokens){
            b.addToken((String)token[0],(Integer)token[1]);
        }
        boolean result = b.checkFour(b.getBoardFields()[5][0]);
        boolean result1 = b.checkFour(b.getBoardFields()[5][1]);
        boolean result2 = b.checkFour(b.getBoardFields()[3][2]);
        boolean result3 = b.checkFour(b.getBoardFields()[3][1]);


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
        boolean result = b.checkFour(b.getBoardFields()[5][3]);
        boolean result1 = b.checkFour(b.getBoardFields()[4][1]);
        boolean result2 = b.checkFour(b.getBoardFields()[2][0]);

        System.out.println(b.getBoard());
        Boolean[] actual = {result, result1, result2};
        Assertions.assertThat(actual)
                .contains(true, true, true)
                .doesNotContain(false);// contains(false, false, false));
    }

    @Test
    public void  checkFourDiagonalNegative(){

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
