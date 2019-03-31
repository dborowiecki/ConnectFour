import org.assertj.core.api.Assertions;
import org.junit.*;

import org.junit.Rule;
import org.junit.rules.ExternalResource;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.PrintWriter;

public class ScoreCsvTest {
    ScoreCsv sc;
    public static final String CSV_READ_TEST_FILE  = "src/test/resources/CsvExample.csv";
    public static final String CSV_WRITE_TEST_FILE = "src/test/resources/CsvWriteExample.csv";
    public static final String CSV_NOT_EXISTING  = "src/test/resources/ThisFileDontExixt.java";

    @After
    public void teardown(){
        cleanFile(CSV_WRITE_TEST_FILE);
    }
    @Test
    public void readFromFileTest(){
        String expected = "x";
        sc = new ScoreCsv(CSV_READ_TEST_FILE);
        String actualOut = sc.readLeaderBoard();
        Assertions.assertThat(actualOut).
                contains("PLAYER-WIN-DRAW-LOST")
                .contains("p1 5 0 0")
                .contains("p2 0 0 5")
                .contains("p3 0 5 0");
    }

    @Test
    public void saveScoreToFileTest(){
        sc = new ScoreCsv(CSV_WRITE_TEST_FILE);
        sc.addScore("p1", -1);
        sc.addScore("p1", 1);
        sc.addScore("p1", 0);
        sc.addScore("p1", 1);
        Assertions.assertThat(sc.readLeaderBoard())
                .contains("PLAYER-WIN-DRAW-LOST")
                .contains("p1 2 1 1");
    }

    @Test
    public void writeToFileExceptionCatchTest(){
        sc = new ScoreCsv(CSV_WRITE_TEST_FILE);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> sc.addScore("p1", -42));
    }

    private void cleanFile(String file){
        try {
            FileWriter fileWriter = new FileWriter(file);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.write("");
            printWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
