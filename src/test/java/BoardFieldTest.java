import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Every.everyItem;

public class BoardFieldTest {

    static final String UPPERLINE =  " ◸   ◹";
    static final String MIDDLE_LINE = "|";
    static final String LOWERLINE =  " ◺   ◿";
    static final String USER_CHAR = "●";
    BoardField b = new BoardField();

    @Test
    public void assertBoardUpperLine(){
        assertThat(BoardField.UPPERLINE, equals(UPPERLINE));
    }

    @Test
    public void assertBoardLowerLine(){
        assertThat(BoardField.UPPERLINE, equals(UPPERLINE));
    }

    @Test
    public void assertUserChar(){
        assertThat(BoardField.PLAYER_CHAR, equals(USER_CHAR));
    }

    @Test
    public void assertUserChangeLook(){

    }


}