package org.kromash.day01;

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
            1000
            2000
            3000
                        
            4000
                        
            5000
            6000
                        
            7000
            8000
            9000
                        
            10000
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
        assertEquals("24000", main.partOne());
    }

    @Test
    void partTwo() {
        assertEquals("45000", main.partTwo());
    }
}