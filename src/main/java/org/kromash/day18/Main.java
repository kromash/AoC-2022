package org.kromash.day18;

import org.kromash.common.Solution;

public class Main extends Solution {

    public Main() {
        super(18);
    }


    public static void main(String[] args) {
        solve(Main.class);
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
