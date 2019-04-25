package MongoTest;
import Mongo.GameCollection;
import Mongo.GameMongo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.*;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class GameMongoTest {

    //Co zastepujemy
    @Mock
    GameCollection friends;

    //Nasza atrapa
    @InjectMocks
    GameMongo friendships;



}