package org.kromash.day13;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kromash.common.SolutionTestBase;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class MainTest extends SolutionTestBase {
    static String TEST_DATA = """
            [1,1,3,1,1]
            [1,1,5,1,1]
                        
            [[1],[2,3,4]]
            [[1],4]
                        
            [9]
            [[8,7,6]]
                        
            [[4,4],4,4]
            [[4,4],4,4,4]
                        
            [7,7,7,7]
            [7,7,7]
                        
            []
            [3]
                        
            [[[]]]
            [[]]
                        
            [1,[2,[3,[4,[5,6,7]]]],8,9]
            [1,[2,[3,[4,[5,6,0]]]],8,9]
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
        assertEquals("13", main.partOne());
    }

    @Test
    void partTwo() {
        assertEquals("140", main.partTwo());
    }
}