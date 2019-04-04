import org.assertj.core.api.Assertions;
import org.junit.Test;

public class TerminalColorTest {

    @Test
    public void properTestCalls(){
        String[] expected = {
                             TerminalColrs.ANSI_RED,
                             TerminalColrs.ANSI_GREEN,
                             TerminalColrs.ANSI_BLUE,
                             TerminalColrs.ANSI_YELLOW,
                             TerminalColrs.ANSI_PURPLE};
        String[] actual = {
                TerminalColrs.translateColor("RED     "),
                TerminalColrs.translateColor("GREEN   "),
                TerminalColrs.translateColor("BLUE    "),
                TerminalColrs.translateColor("YELLOW  "),
                TerminalColrs.translateColor("PURPLE  ")};

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void wrongColorErrorTest(){
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> TerminalColrs.translateColor("róż"));
    }
}
