package org.kromash.day10;

import org.kromash.common.Solution;

import java.util.List;

public class Main extends Solution {
    List<Integer> cycles;
    int r = 1;
    int cycle = 0;
    int result = 0;
    StringBuilder sb;
    StringBuilder resultSB;

    public Main() {
        super(10);
        cycles = List.of(20, 60, 100, 140, 180, 220);
        sb = new StringBuilder();
    }


    public static void main(String[] args) {
        solve(Main.class);
    }

    public String partOne() {

        r = 1;
        cycle = 0;
        result = 0;
        cycles = List.of(20, 60, 100, 140, 180, 220);


        for (var line : readInputLines()) {
            String[] operation = line.split(" ");


            if (operation[0].equals("noop")) {
                addCycle();
            } else {
                addCycle();
                addCycle();
                r += Integer.parseInt(operation[1]);
            }
        }

        return String.valueOf(result);
    }

    private void addCycle() {
        cycle += 1;
        if (cycles.contains(cycle)) {
            result += cycle * r;
        }
    }

    private void addCycleCRT() {
        cycle += 1;
        int pixelPos = (cycle - 1) % 40;

        if (pixelPos >= r - 1 && pixelPos <= r + 1) {
            sb.append('#');
        } else {
            sb.append('.');
        }
        if (cycle % 40 == 0) {
            System.out.println(sb);
            resultSB.append(sb);
            resultSB.append('\n');
            sb = new StringBuilder();
        }
    }


    public String partTwo() {

        r = 1;
        cycle = 0;
        result = 0;
        sb = new StringBuilder();
        resultSB = new StringBuilder();


        for (var line : readInputLines()) {
            String[] operation = line.split(" ");


            if (operation[0].equals("noop")) {
                addCycleCRT();
            } else {
                addCycleCRT();
                addCycleCRT();
                r += Integer.parseInt(operation[1]);
            }
        }

        return resultSB.toString();
    }


}
