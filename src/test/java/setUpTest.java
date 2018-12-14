import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class setUpTest {

    @Test
    public void testHelloWorldIsReturned() {
        SetUp setUp = new SetUp();

        assertEquals("Hello World", setUp.helloWorld());
    }
}
