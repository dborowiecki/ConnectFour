package MongoTest;
import Mongo.*;
import org.assertj.core.api.Assertions;
import org.easymock.*;
import org.easymock.internal.MocksControl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.assertj.core.api.Assertions.assertThat;
import static org.easymock.EasyMock.*;
import static org.easymock.MockType.NICE;


import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class GameMongoEasyTest {

    @Mock
    BoardMongo boardMongo;
    @Mock
    PlayerMongo playerMongo;
    @Mock
    MoveMongo moveMongo;
    @Mock
    GameCollection gameCollection;

    @TestSubject
    GameMongo game;

    @BeforeEach
    public void setup(){
        gameCollection = mock(NICE, GameCollection.class);
        boardMongo = mock(NICE, BoardMongo.class);
        playerMongo= mock(NICE, PlayerMongo.class);
        moveMongo  = mock(NICE, MoveMongo.class);

        game = new GameMongo();
    }

    @AfterEach
    public void teardown(){
        gameCollection = null;
        boardMongo     = null;
        playerMongo    = null;
        moveMongo      = null;
        game           = null;
    }


    @Test
    public void mockingWorksAsExpected(){
        //Zapisanie zachowania - co sie ma stac
        expect(gameCollection.findByColor("RED")).andReturn(playerMongo);
        //Odpalenie obiektu do sprawdzenia zachowania
        replay(gameCollection);
        assertThat(gameCollection.findByColor("RED")).isEqualTo(playerMongo);
    }



}

