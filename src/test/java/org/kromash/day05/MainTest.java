package org.kromash.day05;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MainTest {
    String TEST_DATA = """
                [D]   \s
            [N] [C]   \s
            [Z] [M] [P]
             1   2   3\s
                        
            move 1 from 2 to 1
            move 3 from 1 to 3
            move 2 from 2 to 1
            move 1 from 1 to 2
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
        assertEquals("CMZ", main.partOne());
    }

    @Test
    void partTwo() {
        assertEquals("MCD", main.partTwo());
    }
}