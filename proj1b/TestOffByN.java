import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
    static OffByN offBy5 = new OffByN(5);

    @Test
    public void TestOffByOne() {
        assertTrue(offBy5.equalChars('a', 'f'));
        assertTrue(offBy5.equalChars('f', 'a'));
    }
}
