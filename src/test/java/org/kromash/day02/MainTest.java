package org.kromash.day02;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class MainTest {
    static String TEST_DATA = """
            A Y
            B X
            C Z
            """;

    Main main;

    @BeforeEach
    void setUp() {
        main = spy(Main.class);
        when(main.readInputLines()).thenReturn(List.of(TEST_DATA.split("\n")));
    }

    @Test
    void partOne() {
        assertEquals("15", main.partOne());
    }

    @Test
    void partTwo() {
        assertEquals("12", main.partTwo());
    }
}