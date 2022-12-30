package org.kromash.day18;

import org.javatuples.Triplet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

class Grid {

    List<Triplet<Integer, Integer, Integer>> SIDES = List.of(new Triplet<>(1, 0, 0),
        new Triplet<>(0, 1, 0),
        new Triplet<>(0, 0, 1),
        new Triplet<>(-1, 0, 0),
        new Triplet<>(0, -1, 0),
        new Triplet<>(0, 0, -1));

    Set<Triplet<Integer, Integer, Integer>> CROSS_SIDES;

    Map<Triplet<Integer, Integer, Integer>, Integer> grid;
    Set<Triplet<Integer, Integer, Integer>> toCheck;
    Set<Triplet<Integer, Integer, Integer>> group;

    Set<Triplet<Integer, Integer, Integer>> visited, outside, insideBubble;

    Triplet<Integer, Integer, Integer> min, max;
    boolean touchedOutside;

    Grid(List<String> inputLines) {
        this.grid = readGrid(inputLines);

        CROSS_SIDES = new HashSet<>();
        for (var side1 : SIDES) {
            CROSS_SIDES.add(side1);
            for (var side2 : SIDES) {
                if ((side1.getValue0() != 0 && side2.getValue0() != 0) ||
                    (side1.getValue1() != 0 && side2.getValue1() != 0) ||
                    (side1.getValue2() != 0 && side2.getValue2() != 0)) {
                    continue;
                }
                CROSS_SIDES.add(new Triplet<>(side1.getValue0() + side2.getValue0(),
                    side1.getValue1() + side2.getValue1(),
                    side1.getValue2() + side2.getValue2()));
            }
        }
    }

    Map<Triplet<Integer, Integer, Integer>, Integer> readGrid(List<String> inputLines) {
        Map<Triplet<Integer, Integer, Integer>, Integer> grid = new HashMap<>();

        for (var line : inputLines) {
            String[] coord = line.split(",");
            Triplet<Integer, Integer, Integer> t = new Triplet<>(Integer.parseInt(coord[0]),
                Integer.parseInt(coord[1]),
                Integer.parseInt(coord[2]));
            int neighbours = 0;

            for (var side : SIDES) {
                var p = new Triplet<>(t.getValue0() + side.getValue0(),
                    t.getValue1() + side.getValue1(), t.getValue2() + side.getValue2());
                if (grid.containsKey(p)) {
                    grid.put(p, grid.get(p) + 1);
                    neighbours += 1;
                }
            }

            grid.put(t, neighbours);
        }

        return grid;
    }

    int countSides() {
        int result = 0;
        for (int v : grid.values()) {
            result += 6 - v;
        }
        return result;
    }

    int countSidesWithBubble() {
        int result = 0;
        for (int v : grid.values()) {
            result += 6 - v;
        }
        for (var t : grid.keySet()) {
            for (var side : SIDES) {
                var p = new Triplet<>(t.getValue0() + side.getValue0(),
                    t.getValue1() + side.getValue1(), t.getValue2() + side.getValue2());
                if (insideBubble.contains(p)) {
                    result -= 1;
                }
            }
        }

        return result;
    }

    void addGroup(Triplet<Integer, Integer, Integer> t) {
        toCheck.remove(t);
        group.add(t);

        for (var side : CROSS_SIDES) {
            var p = new Triplet<>(t.getValue0() + side.getValue0(),
                t.getValue1() + side.getValue1(), t.getValue2() + side.getValue2());
            if (toCheck.contains(p)) {
                addGroup(p);
            }
        }

    }

    Set<Triplet<Integer, Integer, Integer>> getGroupNeighbours() {
        Set<Triplet<Integer, Integer, Integer>> neighbours = new HashSet<>();

        for (var t : group) {
            for (var side : SIDES) {
                var p = new Triplet<>(t.getValue0() + side.getValue0(),
                    t.getValue1() + side.getValue1(), t.getValue2() + side.getValue2());
                if (!grid.containsKey(p)) {
                    neighbours.add(p);
                }
            }
        }

        return neighbours;
    }

    boolean isOutside(Triplet<Integer, Integer, Integer> t) {
        return t.getValue0() < min.getValue0() || t.getValue1() < min.getValue1() ||
            t.getValue2() < min.getValue2() || t.getValue0() > max.getValue0() ||
            t.getValue1() > max.getValue1() || t.getValue2() > max.getValue2();
    }

    private void march(Triplet<Integer, Integer, Integer> s) {
        Queue<Triplet<Integer, Integer, Integer>> q = new PriorityQueue<>();
        Set<Triplet<Integer, Integer, Integer>> localVisited = new HashSet<>();
        q.add(s);

        while (!q.isEmpty()) {
            var t = q.poll();
            if (localVisited.contains(t)) {
                continue;
            }
            localVisited.add(t);
            if (grid.containsKey(t)) {
                continue;
            }
            visited.add(t);
            if (isOutside(t)) {
                touchedOutside = true;
                continue;
            }

            for (var side : SIDES) {
                var p = new Triplet<>(t.getValue0() + side.getValue0(),
                    t.getValue1() + side.getValue1(), t.getValue2() + side.getValue2());
                if (visited.contains(p) || localVisited.contains(p)) {
                    continue;
                }
                q.add(p);
            }

        }
    }

    private void addInsideBubble() {
        System.out.println("Group: " + group);
        if (group.size() < 6) {
            return;
        }

        min = new Triplet<>(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
        max = new Triplet<>(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);

        for (var point : group) {
            min = min.setAt0(Math.min(min.getValue0(), point.getValue0()));
            min = min.setAt1(Math.min(min.getValue1(), point.getValue1()));
            min = min.setAt2(Math.min(min.getValue2(), point.getValue2()));
            max = max.setAt0(Math.max(max.getValue0(), point.getValue0()));
            max = max.setAt1(Math.max(max.getValue1(), point.getValue1()));
            max = max.setAt2(Math.max(max.getValue2(), point.getValue2()));
        }
        outside = new HashSet<>();

        for (var p : getGroupNeighbours()) {
            if (outside.contains(p) || insideBubble.contains(p)) {
                continue;
            }
            visited = new HashSet<>();
            touchedOutside = false;
            march(p);

            if (touchedOutside) {
                outside.addAll(visited);
            } else {
                insideBubble.addAll(visited);
            }
        }

    }

    int countOutsideSides() {
        toCheck = new HashSet<>(grid.keySet());

        insideBubble = new HashSet<>();
        while (!toCheck.isEmpty()) {
            group = new HashSet<>();
            addGroup(toCheck.iterator().next());

            addInsideBubble();

        }

        return countSidesWithBubble();
    }
}
