package org.kromash.day14;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MainTest {
    static String TEST_DATA = """
            498,4 -> 498,6 -> 496,6
            503,4 -> 502,4 -> 502,9 -> 494,9
            """;

    Main main;

    @BeforeEach
    void setUp() {
        main = mock(Main.class);
        when(main.readInputLines()).thenReturn(List.of(TEST_DATA.split("\n")));
        when(main.loadRocks()).thenCallRealMethod();
        when(main.loadCave()).thenCallRealMethod();
        when(main.loadInfinityCave()).thenCallRealMethod();
        when(main.partOne()).thenCallRealMethod();
        when(main.partTwo()).thenCallRealMethod();
    }

    @Test
    void partOne() {
        assertEquals("24", main.partOne());
    }

    @Test
    void partTwo() {
        assertEquals("93", main.partTwo());
    }
}