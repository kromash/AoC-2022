package org.kromash.day22;

import org.kromash.common.Solution;

public class Main extends Solution {
    public Main() {
        super(22);
    }


    public static void main(String[] args) {
        solve(Main.class);
    }

    public String partOne() {
        Board board = new Board(getInputStream());
        board.goThroughPath();
        return String.valueOf(board.getPositionValue());
    }

    public String partTwo() {

        BoardCube boardCube = new BoardCube(getInputStream());

        boardCube.goThroughPath();
        return String.valueOf(boardCube.getPositionValue());
    }


}
