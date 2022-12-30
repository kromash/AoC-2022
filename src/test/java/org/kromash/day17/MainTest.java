package org.kromash.day17;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class MainTest {
    static String TEST_DATA = """
            >>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>
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
        assertEquals("3068", main.partOne());
    }

    @Test
    void partTwo() {
        assertEquals("1514285714288", main.partTwo());
    }
}