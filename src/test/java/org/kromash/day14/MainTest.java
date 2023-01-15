package org.kromash.day14;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kromash.common.SolutionTestBase;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class MainTest extends SolutionTestBase {
    static String TEST_DATA = """
            498,4 -> 498,6 -> 496,6
            503,4 -> 502,4 -> 502,9 -> 494,9
            """;

    @InjectMocks
    Main main;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(main.readInputLines()).thenReturn(List.of(TEST_DATA.split("\n")));

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