package Mongo;

import org.assertj.core.api.Assertions;
import org.easymock.IAnswer;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.jongo.MongoCollection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.easymock.EasyMock.replay;
import static org.easymock.MockType.NICE;
import static org.easymock.MockType.STRICT;

public class GameColectionEasyTest {
    @Mock
    MongoCollection mongoCollectionMock;

    @TestSubject
    GameCollection gameCollection;

    @BeforeEach
    public void setup(){
        mongoCollectionMock = mock(NICE, MongoCollection.class);

        gameCollection = new GameCollection(
                mongoCollectionMock,mongoCollectionMock,mongoCollectionMock
        );
    }

    @AfterEach
    public void teardown(){
        gameCollection = null;
      mongoCollectionMock=null;
    }

    @Test
    public void saveMoveTest(){
        List<MoveMongoI> moves=new LinkedList<>();
        MoveMongoI m = mock(STRICT,MoveMongo.class);

        mongoCollectionMock.save((MoveMongo) anyObject());
        expectLastCall().andAnswer(
                new IAnswer() {
                    public Object answer() {
                        moves.add(m);
                        return null;
                    }
                });

        replay(mongoCollectionMock);

        gameCollection.saveMove(m);

        Assertions.assertThat(moves).containsExactly(m);
        verify(mongoCollectionMock);
    }

    @Test
    public void deleteMoveTest(){
        List<MoveMongoI> moves=new LinkedList<>();
        MoveMongoI removed = new MoveMock();
        moves.add(removed);
        moves.add( new MoveMock());
        moves.add( new MoveMock());

        mongoCollectionMock.remove("{order: '0'}");
        expectLastCall().andAnswer(
                new IAnswer() {
                    public Object answer() {
                        moves.remove(removed);
                        return null;
                    }
                });

        replay(mongoCollectionMock);

        gameCollection.deleteMove(removed);

        Assertions.assertThat(moves).hasSize(2);
        verify(mongoCollectionMock);
    }

    @Test
    public void saveBoardTest(){
        List<BoardMongoI> boards=new LinkedList<>();
        BoardMongoI board = mock(STRICT,BoardMongo.class);

        mongoCollectionMock.save((MoveMongo) anyObject());
        expectLastCall().andAnswer(
                new IAnswer() {
                    public Object answer() {
                        boards.add(board);
                        return null;
                    }
                });

        replay(mongoCollectionMock);

        gameCollection.saveBoard(board);

        Assertions.assertThat(boards).containsExactly(board);
        verify(mongoCollectionMock);
    }

}
