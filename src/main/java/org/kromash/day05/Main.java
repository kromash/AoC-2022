package org.kromash.day05;

import org.kromash.common.Solution;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.stream.Collectors;

public class Main extends Solution {
    public Main() {
        super(5);
    }

    public static void main(String[] args) {
        new Main().solve();
    }

    public String partOne() {

        ListIterator<String> listIterator = readInputLines().listIterator();
        ArrayList<ArrayList<Character>> stacks = getStacks(listIterator);

        listIterator.next();

        while (listIterator.hasNext()) {
            var line = listIterator.next();
            String[] s = line.split(" ");

            int n = Integer.parseInt(s[1]);
            int from = Integer.parseInt(s[3]) - 1;
            int to = Integer.parseInt(s[5]) - 1;

            for (int i = 0; i < n; i++) {
                stacks.get(to).add(0, stacks.get(from).remove(0));
            }
        }

        return stacks.stream().map(s -> s.get(0).toString()).collect(Collectors.joining());
    }

    public String partTwo() {

        ListIterator<String> listIterator = readInputLines().listIterator();
        ArrayList<ArrayList<Character>> stacks = getStacks(listIterator);

        listIterator.next();

        while (listIterator.hasNext()) {
            var line = listIterator.next();
            String[] s = line.split(" ");

            int n = Integer.parseInt(s[1]);
            int from = Integer.parseInt(s[3]) - 1;
            int to = Integer.parseInt(s[5]) - 1;

            for (int i = 0; i < n; i++) {
                stacks.get(to).add(0, stacks.get(from).remove(n - i - 1));
            }
        }

        return stacks.stream().map(s -> s.get(0).toString()).collect(Collectors.joining());
    }

    private ArrayList<ArrayList<Character>> getStacks(ListIterator<String> listIterator) {
        ArrayList<ArrayList<Character>> stacks = new ArrayList<>();

        while (listIterator.hasNext()) {
            var line = listIterator.next();

            if (line.charAt(1) == '1')
                break;

            for (int i = 1, j = 0; i < line.length(); i += 4, j++) {
                char c = line.charAt(i);
                if (c != ' ') {

                    for (int k = stacks.size(); k <= j; k++) {
                        stacks.add(new ArrayList<>());
                    }
                    stacks.get(j).add(c);
                }
            }

        }
        return stacks;
    }
}
