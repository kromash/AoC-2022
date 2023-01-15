package org.kromash.day11;

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
            Monkey 0:
              Starting items: 79, 98
              Operation: new = old * 19
              Test: divisible by 23
                If true: throw to monkey 2
                If false: throw to monkey 3
                        
            Monkey 1:
              Starting items: 54, 65, 75, 74
              Operation: new = old + 6
              Test: divisible by 19
                If true: throw to monkey 2
                If false: throw to monkey 0
                        
            Monkey 2:
              Starting items: 79, 60, 97
              Operation: new = old * old
              Test: divisible by 13
                If true: throw to monkey 1
                If false: throw to monkey 3
                        
            Monkey 3:
              Starting items: 74
              Operation: new = old + 3
              Test: divisible by 17
                If true: throw to monkey 0
                If false: throw to monkey 1
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
        assertEquals("10605", main.partOne());
    }

    @Test
    void partTwo() {
        assertEquals("2713310158", main.partTwo());
    }
}