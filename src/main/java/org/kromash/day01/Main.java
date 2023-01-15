package org.kromash.day01;

import org.kromash.common.Solution;

import java.util.Collections;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.stream.Stream;

public class Main extends Solution {
    public Main() {
        super(1);
    }

    public static void main(String[] args) {
        solve(Main.class);
    }

    public String partOne() {

        int maxSum = 0;
        int sum = 0;

        for (var line : readInputLines()) {
            if (!line.equals("")) {
                sum += Integer.parseInt(line);
            } else {
                if (sum > maxSum) {
                    maxSum = sum;
                }
                sum = 0;
            }

        }
        if (sum > maxSum) {
            maxSum = sum;
        }
        return String.valueOf(maxSum);
    }

    public String partTwo() {

        PriorityQueue<Integer> sums = new PriorityQueue<>(Collections.reverseOrder());
        int sum = 0;

        for (var line : readInputLines()) {
            if (!line.equals("")) {
                sum += Integer.parseInt(line);
            } else {
                sums.add(sum);

                sum = 0;
            }

        }
        sums.add(sum);

        return Stream.generate(sums::poll)
                .limit(3)
                .filter(Objects::nonNull)
                .reduce(Integer::sum)
                .orElse(0)
                .toString();
    }


}
