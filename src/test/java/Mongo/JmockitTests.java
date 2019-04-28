//package Mongo;
//
//import mockit.Expectations;
//import mockit.Mocked;
//import mockit.Tested;
//import mockit.*;
//import mockit.Verifications;
//import org.assertj.core.api.Assertions;
//import org.jongo.FindOne;
//import org.jongo.MongoCollection;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
//
//
//
//public class JmockitTests {
//
//    @Tested(fullyInitialized = true)
//    GameCollection gameCollection;
//    @Mocked
//    MongoCollection mongoCollectionMock;
//    @Mocked
//    FindOne queryMock;
//
//    @BeforeEach
//    public void setup(){
//        gameCollection = new GameCollection(mongoCollectionMock,mongoCollectionMock,mongoCollectionMock);
//    }
//    @AfterEach
//    public void teardown(){
//        gameCollection=null;
//    }
//
//    @Test
//    public void findByNameTest(){
//        PlayerMongoI p = new PlayerMongo("John","red");
//
//        new Expectations() {{ mongoCollectionMock.findOne("{_id: 'John'}"); result = queryMock; }};
//        new Expectations() {{ queryMock.as(PlayerMongo.class);result=p;}};
//        Assertions.assertThat(gameCollection.findByName("rom")).isEqualTo(p);
//
//
//        new Verifications() {{ mongoCollectionMock.findOne("{_id: 'John'}"); times = 1; }};
//        new Verifications() {{ queryMock.as(PlayerMongo.class); times = 1; }};
//
//    }
//}
