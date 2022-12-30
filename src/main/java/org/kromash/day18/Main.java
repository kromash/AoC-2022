package org.kromash.day18;

import org.javatuples.Triplet;
import org.kromash.common.Solution;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Main extends Solution {

    public Main() {
        super(18);
    }

    public static void main(String[] args) {
        new Main().solve();
    }

    public String partOne() {
        Grid grid = new Grid(readInputLines());

        return String.valueOf(grid.countSides());
    }

    public String partTwo() {
        Grid grid = new Grid(readInputLines());

        return String.valueOf(grid.countOutsideSides());
    }

}
