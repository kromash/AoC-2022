package org.kromash.day08;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MainTest {

    String TEST_DATA = """
            30373
            25512
            65332
            33549
            35390
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
        assertEquals("21", main.partOne());
    }

    @Test
    void partTwo() {
        assertEquals("8", main.partTwo());
    }
}