package org.kromash.day17;

import com.google.common.base.Joiner;
import org.javatuples.Pair;
import org.kromash.common.Solution;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Main extends Solution {
    public Main() {
        super(17);
    }

    public static void main(String[] args) {
        solve(Main.class);
    }

    public String partOne() {
        String line = readInputLines().iterator().next();
        Chamber chamber = new Chamber(line);

        return String.valueOf(chamber.simulate(2022));
    }

    public String partTwo() {
        String line = readInputLines().iterator().next();
        Chamber chamber = new Chamber(line);

        return String.valueOf(chamber.simulate(1000000000000L));
    }

    class Chamber {
        String[][] SHAPES;
        String moves;
        int currentStep;
        ArrayList<Character[]> chamber;
        int LINE_WIDTH = 7;
        int maxHeight;

        Chamber(String moves) {
            SHAPES = new String[][]{new String[]{"####"}, new String[]{".#.", "###", ".#."}, new String[]{"..#", "." +
                    ".#", "###"}, new String[]{"#", "#", "#", "#"}, new String[]{"##", "##"}};
            this.moves = moves;
            chamber = new ArrayList<>();
            maxHeight = 0;
        }

        public long simulate(long blocks) {

            currentStep = 0;

            int initialBlocks = moves.length() * SHAPES.length * 2;
            int blocksOffset = moves.length() * SHAPES.length;
            ArrayList<Pair<Integer, Integer>> memo = new ArrayList<>();
            ArrayList<Integer> heights = new ArrayList<>();
            for (int i = 0; i < initialBlocks; ++i) {
                int currentMove = currentStep % moves.length();

                int shapeNum = i % SHAPES.length;
                String[] shape = SHAPES[shapeNum];
                memo.add(new Pair<>(shapeNum, currentMove));
                drop(shape);
                heights.add(maxHeight);

            }

            int patternLength = SHAPES.length;
            for (; patternLength < initialBlocks / 2; patternLength++) {
                boolean found = true;
                for (int i = 0; i < patternLength; ++i) {
                    if (!memo.get(blocksOffset + i).equals(memo.get(blocksOffset + patternLength + i))) {
                        found = false;
                        break;
                    }
                }

                if (found) {
                    System.out.println("Pattern: " + patternLength);
                    break;
                }
            }

            long patternHeight = heights.get(blocksOffset + patternLength) - heights.get(blocksOffset);
            long rest = (blocks - blocksOffset) % patternLength;
            long offsetPlusRestHeight = heights.get((int) (blocksOffset + rest) - 1);
            long patternsCount = (blocks - blocksOffset) / patternLength;

            return offsetPlusRestHeight + patternHeight * patternsCount;
        }

        void drop(String[] shape) {

            for (int i = chamber.size(); i < maxHeight + 3 + shape.length; ++i) {
                addEmptyLine();
            }
            Point point = new Point(2, maxHeight + 3 + shape.length - 1);

            while (true) {
                char jet = this.moves.charAt(currentStep++ % moves.length());

                Point jetMove = new Point();
                if (jet == '<') {
                    jetMove = new Point(-1, 0);
                }

                if (jet == '>') {
                    jetMove = new Point(1, 0);
                }
                point = move(point, jetMove, shape);

                Point newPoint = move(point, new Point(0, -1), shape);

                if (newPoint.equals(point)) {
                    set(point, shape);
                    maxHeight = Math.max(maxHeight, newPoint.y + 1);
                    break;
                }
                point = newPoint;
            }

        }

        void set(Point point, String[] shape) {
            for (int y = 0; y < shape.length; y++) {
                for (int x = 0; x < shape[0].length(); x++) {
                    if (shape[y].charAt(x) == '#') {
                        chamber.get(point.y - y)[point.x + x] = '#';
                    }
                }
            }
        }

        boolean check(Point point, String[] shape) {

            for (int y = 0; y < shape.length; y++) {
                int newY = point.y - y;

                for (int x = 0; x < shape[0].length(); x++) {
                    int newX = point.x + x;
                    if (shape[y].charAt(x) == '.') {
                        continue;
                    }
                    if (!(newX >= 0 && newX < LINE_WIDTH) || !(newY >= 0 && newY < chamber.size())) {
                        return false;
                    }
                    if (chamber.get(newY)[newX] != '.') {
                        return false;
                    }

                }
            }

            return true;
        }

        Point move(Point point, Point direction, String[] shape) {

            if (!check(new Point(point.x + direction.x, point.y + direction.y), shape)) {
                return point;
            }

            return new Point(point.x + direction.x, point.y + direction.y);
        }

        void printChamber() {
            for (int i = chamber.size() - 1; i >= 0; --i) {
                System.out.print("|");
                System.out.print(Joiner.on("").join(chamber.get(i)));
                System.out.print("|");
                System.out.println();
            }
            System.out.println("---------");
        }

        void addEmptyLine() {
            Character[] line = new Character[LINE_WIDTH];
            Arrays.fill(line, '.');
            chamber.add(line);
        }
    }

}
