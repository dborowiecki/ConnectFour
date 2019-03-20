
import org.hamcrest.collection.IsMapContaining;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Every.everyItem;

public class BoardTest {

    Board b;

    @Before
    public void setup() {
         b = new Board();
    }

    @Ignore
    @Test
    public void assertGenerateEmptyBoard(){
        Board board = new Board();

        assertThat("", is(board.getBoard()));
    }

    @Test
    public void assertAddPlayerToken(){
        String playerName = "Player1";
        boolean result = b.addToken(playerName, 1);
        BoardField expectedBoardField = b.getBoardFields()[b.getNumberOfRows()-1][1];

        assertThat(true, is(result));
        assertThat(b.getPlayerFields(), hasEntry(expectedBoardField, playerName));
    }
}
