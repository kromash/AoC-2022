package org.kromash.day06;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MainTest {
    String TEST_DATA = "mjqjpqmgbljsphdztnvjfqwrcgsmlb";

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
        assertEquals("7", main.partOne());
    }

    @Test
    void partTwo() {
        assertEquals("19", main.partTwo());
    }
}