package com.github.codeidoscope;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class setUpTest {

    @Test
    public void testHelloWorldIsReturned() {
        SetUp setUp = new SetUp();

        assertEquals("Hello World", setUp.helloWorld());
    }
}
