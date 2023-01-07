package org.kromash.day12;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class MainTest {
    static String TEST_DATA = """
            Sabqponm
            abcryxxl
            accszExk
            acctuvwj
            abdefghi
            """;

    Main main;

    @BeforeEach
    void setUp() {
        main = spy(Main.class);
        when(main.readInputLines()).thenReturn(List.of(TEST_DATA.split("\n")));
    }

    @Test
    void partOne() {
        assertEquals("31", main.partOne());
    }

    @Test
    void partTwo() {
        assertEquals("29", main.partTwo());
    }
}