package org.kromash.day25;

import org.kromash.common.Solution;

import java.util.Collections;

public class Main extends Solution {
    public Main() {
        super(25);
    }


    public static void main(String[] args) {
        solve(Main.class);
    }


    private int convertSNAFUNumber(char c) {
        return switch (c) {
            case '2' -> 2;
            case '1' -> 1;
            case '0' -> 0;
            case '-' -> -1;
            case '=' -> -2;
            default -> throw new IllegalStateException("Unexpected value: " + c);
        };
    }

    private char convertSNAFUNumberRev(char c) {
        return switch (c) {
            case '2' -> '=';
            case '1' -> '-';
            case '0' -> '0';
            case '-' -> '1';
            case '=' -> '2';
            default -> throw new IllegalStateException("Unexpected value: " + c);
        };
    }

    private long convertFromSNAFU(String snafu) {
        long value = 0;
        long power = 1;
        for (int i = 0; i < snafu.length(); ++i) {
            char c = snafu.charAt(snafu.length() - i - 1);
            value += power * convertSNAFUNumber(c);
            power *= 5;
        }
        return value;
    }

    private String convertToSNAFU(long value) {
        StringBuilder sb = new StringBuilder();

        long currMax = 2;
        long power = 1;
        int c = 1;

        while (currMax < value) {
            power *= 5;
            c += 1;
            currMax += 2 * power;
        }

        long ceil = 0;

        if (value > currMax - power) {
            ceil = 2;
        } else if (value > currMax - 2 * power) {
            ceil = 1;
        }
        sb.append(ceil);
        sb.append(String.join("", Collections.nCopies(c - 1, "0")));
        long left = (value - ceil * power);
        String leftSNAFU = "";
        if (left != 0) {
            leftSNAFU = convertToSNAFU(Math.abs(left));
            if (left < 0) {
                leftSNAFU = reverseSNAFU(leftSNAFU);
            }
        }

        for (int i = 0; i < leftSNAFU.length(); i++) {
            sb.setCharAt(sb.length() - leftSNAFU.length() + i, leftSNAFU.charAt(i));
        }


        return sb.toString();
    }

    String reverseSNAFU(String snafu) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < snafu.length(); i++) {
            char c = snafu.charAt(i);
            sb.append(convertSNAFUNumberRev(c));

        }
        return sb.toString();

    }

    public String partOne() {
        long sum = 0;

        for (var line : readInputLines()) {
            sum += convertFromSNAFU(line);

        }
        return convertToSNAFU(sum);
    }

    public String partTwo() {
        return "Free";
    }


}
