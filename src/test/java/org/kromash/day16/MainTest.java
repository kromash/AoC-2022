package org.kromash.day16;

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
            Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
            Valve BB has flow rate=13; tunnels lead to valves CC, AA
            Valve CC has flow rate=2; tunnels lead to valves DD, BB
            Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE
            Valve EE has flow rate=3; tunnels lead to valves FF, DD
            Valve FF has flow rate=0; tunnels lead to valves EE, GG
            Valve GG has flow rate=0; tunnels lead to valves FF, HH
            Valve HH has flow rate=22; tunnel leads to valve GG
            Valve II has flow rate=0; tunnels lead to valves AA, JJ
            Valve JJ has flow rate=21; tunnel leads to valve II
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
        assertEquals("1651", main.partOne());
    }

    @Test
    void partTwo() {
        assertEquals("1707", main.partTwo());
    }
}