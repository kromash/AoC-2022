package org.kromash.day21;

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
            root: pppw + sjmn
            dbpl: 5
            cczh: sllz + lgvd
            zczc: 2
            ptdq: humn - dvpt
            dvpt: 3
            lfqf: 4
            humn: 5
            ljgn: 2
            sjmn: drzm * dbpl
            sllz: 4
            pppw: cczh / lfqf
            lgvd: ljgn * ptdq
            drzm: hmdt - zczc
            hmdt: 32
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
        assertEquals("152", main.partOne());
    }

    @Test
    void partTwo() {
        assertEquals("301", main.partTwo());
    }
}