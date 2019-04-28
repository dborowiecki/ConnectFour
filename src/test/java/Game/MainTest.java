package Game;

import Main.Main;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class MainTest {
    private ByteArrayOutputStream myOut;
    private ByteArrayInputStream myIn;

    @Before
    public void setup(){
        myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));
    }

    @Test
    public void mainMethodTestWrongInput(){
        //Generate input
        String filling = createInputStream(new String[]{"wrong", "4"});
        myIn = new ByteArrayInputStream(filling.getBytes());
        System.setIn(myIn);

        Main.main(new String[]{});


        //Check if this calls both methods from start
        Assertions.assertThat(myOut.toString()).contains("This is not correct input");
    }

    @Test
    public void mainMethodCorrectInput(){
        //Generate input
        String filling = createInputStream(new String[]{"3","4"});
        myIn = new ByteArrayInputStream(filling.getBytes());
        System.setIn(myIn);

        Main.main(new String[]{});

        //Check output from possible player inputs
        Assertions.assertThat(myOut.toString()).contains("Thank you for playing").contains("PLAYER-WIN-DRAW-LOST");
    }
    private String createInputStream(String[] values){
        StringBuilder build = new StringBuilder();
        for(String value: values){
            build.append(value);
            build.append("\n");
        }
        return build.toString();
    }
}
