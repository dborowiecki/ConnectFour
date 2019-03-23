import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;
import junitparams.mappers.IdentityMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.Reader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

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
    public void testMethod(List<Object[]> tokens) {
        b.addPlayer("p1", TerminalColrs.ANSI_RED);
        for(Object[] token: tokens){
            b.addToken((String)token[0],(Integer)token[1]);
        }
        boolean result = b.checkFour(b.getBoardFields()[5][3]);

        assertThat(result, is(true));

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
    @Test
    @FileParameters("src/test/resources/horizontalNegative.csv")
    public void checkFourHorizontalNegative(String player, int column){

    }

    @Test
    public void checkFourVerticalNegative(){

    }

    @Test
    public void checkFourHorizontalPositive(){

    }

    @Test
    public void checkFourVerticalPositive(){

    }

    @Test
    public void  checkFourDiagonalNegative(){

    }
    @Test
    public void checkFourDiagonalPositive(){

    }
}
