package org.kromash.day08;

import org.kromash.common.Solution;

import java.util.ArrayList;

public class Main extends Solution {
    public Main() {
        super(8);
    }

    public static void main(String[] args) {
        new Main().solve();
    }

    public String partOne() {

        ArrayList<ArrayList<Integer>> trees = new ArrayList<>();
        ArrayList<ArrayList<Integer>> visible = new ArrayList<>();
        for (var line : readInputLines()) {
            ArrayList<Integer> treeLine = new ArrayList<>();
            ArrayList<Integer> visibleLine = new ArrayList<>();
            for (char tree : line.toCharArray()) {
                treeLine.add(Integer.parseInt(String.valueOf(tree)));
                visibleLine.add(0);
            }
            trees.add(treeLine);
            visible.add(visibleLine);
        }


        for (int i = 0; i < trees.size(); i++) {
            int currMax = -1;
            for (int j = 0; j < trees.get(i).size(); j++) {
                int h = trees.get(i).get(j);
                if (h > currMax) {
                    currMax = h;
                    visible.get(i).set(j, 1);
                }
            }
        }

        for (int i = 0; i < trees.size(); i++) {
            int currMax = -1;
            for (int j = 0; j < trees.get(i).size(); j++) {
                int h = trees.get(j).get(i);
                if (h > currMax) {
                    currMax = h;

                    visible.get(j).set(i, 1);
                }
            }
        }

        for (int i = 0; i < trees.size(); i++) {
            int currMax = -1;
            for (int j = trees.get(i).size() - 1; j >= 0; j--) {
                int h = trees.get(i).get(j);
                if (h > currMax) {
                    currMax = h;
                    visible.get(i).set(j, 1);
                }
            }
        }

        for (int i = 0; i < trees.size(); i++) {
            int currMax = -1;
            for (int j = trees.get(i).size() - 1; j >= 0; j--) {
                int h = trees.get(j).get(i);
                if (h > currMax) {
                    currMax = h;
                    visible.get(j).set(i, 1);
                }
            }
        }

        int result = visible
                .stream()
                .flatMapToInt(a -> a.stream().mapToInt(Integer::intValue))
                .sum();
        return String.valueOf(result);
    }

    public String partTwo() {

        ArrayList<ArrayList<Integer>> trees = new ArrayList<>();
        for (var line : readInputLines()) {
            ArrayList<Integer> treeLine = new ArrayList<>();
            for (char tree : line.toCharArray()) {
                treeLine.add(Integer.parseInt(String.valueOf(tree)));
            }
            trees.add(treeLine);
        }
        int result = 0;
        for (int i = 1; i < trees.size() - 1; i++) {
            for (int j = 1; j < trees.size() - 1; j++) {
                int currTree = trees.get(i).get(j);
                int multi = 1;
                int count = 0;
                for (int k = i + 1; k < trees.size(); ++k) {
                    int tree = trees.get(k).get(j);
                    if (currTree > tree) {
                        count += 1;
                    } else {
                        count += 1;
                        break;
                    }

                }
                multi *= count;
                count = 0;
                for (int k = j + 1; k < trees.get(i).size(); ++k) {
                    int tree = trees.get(i).get(k);
                    if (currTree > tree) {
                        count += 1;
                    } else {
                        count += 1;
                        break;
                    }

                }
                multi *= count;
                count = 0;
                for (int k = i - 1; k >= 0; --k) {
                    int tree = trees.get(k).get(j);
                    if (currTree > tree) {
                        count += 1;
                    } else {
                        count += 1;
                        break;
                    }

                }
                multi *= count;

                count = 0;
                for (int k = j - 1; k >= 0; --k) {
                    int tree = trees.get(i).get(k);
                    if (currTree > tree) {
                        count += 1;
                    } else {
                        count += 1;
                        break;
                    }

                }
                multi *= count;

                if (multi > result) {
                    result = multi;
                }
            }
        }

        return String.valueOf(result);
    }


}
