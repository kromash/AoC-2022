package org.kromash.day20;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class MainTest {
    static String TEST_DATA = """
        1
        2
        -3
        3
        -2
        0
        4
        """;

    Main main;

    @BeforeEach
    void setUp() {
        main = mock(Main.class);
        when(main.readInputLines()).thenReturn(List.of(TEST_DATA.split("\n")));
        when(main.partOne()).thenCallRealMethod();
        when(main.partTwo()).thenCallRealMethod();
    }

    @Test
    void partOne() {
        assertEquals("3", main.partOne());
    }


    @Test
    void partTwo() {
        assertEquals(String.valueOf(1623178306L), main.partTwo());
    }
}