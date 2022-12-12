package org.kromash.day09;

import org.javatuples.Pair;
import org.kromash.common.Solution;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main extends Solution {
    public Main() {
        super(9);
    }

    public static void main(String[] args) {
        new Main().solve();
    }

    public String partOne() {
        Set<Pair<Integer, Integer>> visited = new HashSet<>();
        visited.add(new Pair<>(0, 0));
        Pair<Integer, Integer> head = new Pair<>(0, 0);
        Pair<Integer, Integer> tail = new Pair<>(0, 0);
        for (var line : readInputLines()) {
            String[] move = line.split(" ");

            String dir = move[0];
            int c = Integer.parseInt(move[1]);

            for (int i = 0; i < c; ++i) {

                switch (dir) {
                    case "R":
                        head = new Pair<>(head.getValue0(), head.getValue1() + 1);
                        break;
                    case "L":
                        head = new Pair<>(head.getValue0(), head.getValue1() - 1);
                        break;
                    case "U":
                        head = new Pair<>(head.getValue0() + 1, head.getValue1());
                        break;
                    case "D":
                        head = new Pair<>(head.getValue0() - 1, head.getValue1());
                        break;
                }


                int pos0 = tail.getValue0();
                int pos1 = tail.getValue1();
                if ((Math.abs(tail.getValue0() - head.getValue0()) > 1 && Math.abs(
                        tail.getValue1() - head.getValue1()) > 0)) {
                    pos0 += (head.getValue0() - tail.getValue0()) - Math.signum(head.getValue0() - tail.getValue0());
                    pos1 += (head.getValue1() - tail.getValue1());
                } else if (Math.abs(tail.getValue0() - head.getValue0()) > 0 && Math.abs(
                        tail.getValue1() - head.getValue1()) > 1) {
                    pos0 += (head.getValue0() - tail.getValue0());
                    pos1 += (head.getValue1() - tail.getValue1()) - Math.signum(head.getValue1() - tail.getValue1());
                } else if (Math.abs(tail.getValue0() - head.getValue0()) > 1) {
                    pos0 += (head.getValue0() - tail.getValue0()) - Math.signum(head.getValue0() - tail.getValue0());
                } else if (Math.abs(tail.getValue1() - head.getValue1()) > 1) {
                    pos1 += (head.getValue1() - tail.getValue1()) - Math.signum(head.getValue1() - tail.getValue1());
                }

                tail = new Pair<>(pos0, pos1);
                visited.add(tail);
            }

        }
        return String.valueOf(visited.size());
    }

    public String partTwo() {

        Set<Pair<Integer, Integer>> visited = new HashSet<>();
        visited.add(new Pair<>(0, 0));
        Pair<Integer, Integer> headPos = new Pair<>(0, 0);

        List<Pair<Integer, Integer>> tailList = new ArrayList<>();
        for (int i = 0; i < 9; ++i) {
            tailList.add(new Pair<>(0, 0));
        }

        for (var line : readInputLines()) {
            String[] move = line.split(" ");

            String dir = move[0];
            int c = Integer.parseInt(move[1]);

            for (int i = 0; i < c; ++i) {

                switch (dir) {
                    case "R":
                        headPos = new Pair<>(headPos.getValue0(), headPos.getValue1() + 1);
                        break;
                    case "L":
                        headPos = new Pair<>(headPos.getValue0(), headPos.getValue1() - 1);
                        break;
                    case "U":
                        headPos = new Pair<>(headPos.getValue0() + 1, headPos.getValue1());
                        break;
                    case "D":
                        headPos = new Pair<>(headPos.getValue0() - 1, headPos.getValue1());
                        break;
                }


                Pair<Integer, Integer> head = headPos;
                for (int j = 0; j < 9; ++j) {
                    Pair<Integer, Integer> tail = tailList.get(j);

                    int pos0 = tail.getValue0();
                    int pos1 = tail.getValue1();
                    if ((Math.abs(tail.getValue0() - head.getValue0()) > 1 && Math.abs(
                            tail.getValue1() - head.getValue1()) > 1)) {
                        pos0 += (head.getValue0() - tail.getValue0()) - Math.signum(
                                head.getValue0() - tail.getValue0());
                        pos1 += (head.getValue1() - tail.getValue1()) - Math.signum(
                                head.getValue1() - tail.getValue1());
                    } else if ((Math.abs(tail.getValue0() - head.getValue0()) > 1 && Math.abs(
                            tail.getValue1() - head.getValue1()) > 0)) {
                        pos0 += (head.getValue0() - tail.getValue0()) - Math.signum(
                                head.getValue0() - tail.getValue0());
                        pos1 += (head.getValue1() - tail.getValue1());
                    } else if (Math.abs(tail.getValue0() - head.getValue0()) > 0 && Math.abs(
                            tail.getValue1() - head.getValue1()) > 1) {
                        pos0 += (head.getValue0() - tail.getValue0());
                        pos1 += (head.getValue1() - tail.getValue1()) - Math.signum(
                                head.getValue1() - tail.getValue1());
                    } else if (Math.abs(tail.getValue0() - head.getValue0()) > 1) {
                        pos0 += (head.getValue0() - tail.getValue0()) - Math.signum(
                                head.getValue0() - tail.getValue0());
                    } else if (Math.abs(tail.getValue1() - head.getValue1()) > 1) {
                        pos1 += (head.getValue1() - tail.getValue1()) - Math.signum(
                                head.getValue1() - tail.getValue1());
                    }

                    tail = new Pair<>(pos0, pos1);

                    tailList.set(j, tail);
                    head = new Pair<>(pos0, pos1);
                }
                visited.add(head);
            }

        }
        return String.valueOf(visited.size());
    }


}
