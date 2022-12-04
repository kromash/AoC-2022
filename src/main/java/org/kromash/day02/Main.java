package org.kromash.day02;

import org.kromash.common.Solution;

public class Main extends Solution {

    public Main() {
        super(2);
    }

    public static void main(String[] args) {
        new Main().solve();
    }

    public String partOne() {
        int score = 0;

        for (var line : readInputLines()) {
            int opponent = line.charAt(0) - 'A';
            int me = line.charAt(2) - 'X';
            int outcome = 0;

            if ((opponent + 1) % 3 == me) {
                outcome = 6;
            } else if (opponent == me) {
                outcome = 3;
            } else {
                outcome = 0;
            }
            score += outcome + me + 1;


        }

        return String.valueOf(score);
    }

    public String partTwo() {
        int score = 0;

        for (var line : readInputLines()) {
            int opponent = line.charAt(0) - 'A';
            int outcome = line.charAt(2) - 'X';

            int me = 0;

            if (outcome == 0) {
                me = (opponent + 2) % 3;
            } else if (outcome == 1) {
                me = opponent;
            } else if (outcome == 2) {
                me = (opponent + 1) % 3;
            }

            score += outcome * 3 + me + 1;


        }

        return String.valueOf(score);
    }

}
