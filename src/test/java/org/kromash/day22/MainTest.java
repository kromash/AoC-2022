package org.kromash.day22;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class MainTest {
    static String TEST_DATA = """
                    ...#
                    .#..
                    #...
                    ....
            ...#.......#
            ........#...
            ..#....#....
            ..........#.
                    ...#....
                    .....#..
                    .#......
                    ......#.
                        
            10R5L5R10L4R5L5
            """;

    Main main;

    @BeforeEach
    void setUp() {
        main = spy(Main.class);

        when(main.readInputLines()).thenReturn(List.of(TEST_DATA.split("\n")));
    }

    @Test
    void partOne() {
        assertEquals("6032", main.partOne());
    }

    @Test
    void partTwo() {
        assertEquals("5031", main.partTwo());
    }
}