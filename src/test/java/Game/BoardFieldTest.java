package Game;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

public class BoardFieldTest {

    static final String UPPERLINE =  " ◸   ◹ ";
    static final String MIDDLE_LINE = "|";
    static final String LOWERLINE =  " ◺   ◿ ";
    static final String USER_CHAR = "●";

    BoardField b = new BoardField();


    @Test
    public void assertBoardUpperLine(){
        assertThat(BoardField.UPPERLINE, is(UPPERLINE));
    }

    @Test
    public void assertBoardLowerLine(){
        assertThat(BoardField.UPPERLINE, is(UPPERLINE));
    }

    @Test
    public void assertUserChar(){
        assertThat(BoardField.PLAYER_CHAR, is(USER_CHAR));
    }

    @Test
    public void assertSettingBackgroundColorPositive(){
        b.setBackgroundColor(TerminalColrs.ANSI_BLUE_BACKGROUND);
        assertThat(b.getBackgroundColor(), is(TerminalColrs.ANSI_BLUE_BACKGROUND));
    }

    @Test
    public void assertConstructorColor(){
        BoardField board = new BoardField();
        board.setBackgroundColor(TerminalColrs.ANSI_BLUE_BACKGROUND);
        assertThat(board.getBackgroundColor(), is(TerminalColrs.ANSI_BLUE_BACKGROUND));
    }


    @Test
    public void assertSetPlayerColorPositive(){
        b.setPlayerColor(TerminalColrs.ANSI_BLUE);
        assertThat(b.getPlayerColor(), is(TerminalColrs.ANSI_BLUE));
    }

    @Rule
    public ExpectedException exceptionGrabber = ExpectedException.none();
    @Test
    public void assertColorChangeWrongFormatException() throws IllegalArgumentException{
        exceptionGrabber.expect(IllegalArgumentException.class);
        b.setBackgroundColor("wrong format");
    }

    @Test
    public void assertColorChangeWrongFormatException_2() throws IllegalArgumentException{
        exceptionGrabber.expect(IllegalArgumentException.class);
        b.setPlayerColor("wrong format");
    }

}







