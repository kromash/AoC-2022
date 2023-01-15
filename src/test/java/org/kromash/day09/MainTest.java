package org.kromash.day09;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kromash.common.SolutionTestBase;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class MainTest extends SolutionTestBase {

    String TEST_DATA = """
            R 4
            U 4
            L 3
            D 1
            R 4
            D 1
            L 5
            R 2
            """;
    String TEST_DATA_2 = """
            R 5
            U 8
            L 8
            D 3
            R 17
            D 10
            L 25
            U 20
            """;
    @InjectMocks
    Main main;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void partOne() {
        when(main.readInputLines()).thenReturn(List.of(TEST_DATA.split("\n")));
        assertEquals("13", main.partOne());
    }

    @Test
    void partTwo() {

        when(main.readInputLines()).thenReturn(List.of(TEST_DATA_2.split("\n")));
        assertEquals("36", main.partTwo());
    }
}