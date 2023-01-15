package org.kromash.day23;

import org.javatuples.Pair;
import org.kromash.common.Solution;

import java.awt.*;
import java.util.List;
import java.util.*;

public class Main extends Solution {
    public Main() {
        super(23);
    }


    public static void main(String[] args) {
        solve(Main.class);
    }

    public String partOne() {


        Grove grove = new Grove(readInputLines());

        for (int i = 0; i < 10; i++) {
            grove.runRound();
        }


        return String.valueOf(grove.emptyGround());
    }

    public String partTwo() {

        Grove grove = new Grove(readInputLines());

        int round = 0;
        while (grove.runRound()) {
            round++;
        }


        return String.valueOf(round + 1);
    }

    class Grove {
        Set<Point> positions;

        List<MoveRule> moveRules;

        List<Point> adjacents;
        int round;

        Grove(List<String> lines) {
            int y = 0;
            positions = new HashSet<>();
            for (String line : lines) {
                for (int x = 0; x < line.length(); x++) {
                    char c = line.charAt(x);
                    if (c == '#') {
                        positions.add(new Point(x, y));
                    }
                }

                y += 1;
            }

            moveRules = List.of(
                    new MoveRule(new Point(0, -1)),
                    new MoveRule(new Point(0, 1)),
                    new MoveRule(new Point(-1, 0)),
                    new MoveRule(new Point(1, 0)));

            adjacents = List.of(new Point(0, -1), new Point(1, -1), new Point(1, 0),
                    new Point(1, 1), new Point(0, 1), new Point(-1, 1), new Point(-1, 0),
                    new Point(-1, -1));

            round = 0;
        }

        public boolean runRound() {
            boolean moved = false;
            Map<Point, List<Point>> from = new HashMap<>();
            for (Point position : positions) {
                if (!hasNeighbour(position)) {
                    continue;
                }

                for (int i = 0; i < moveRules.size(); i++) {
                    MoveRule moveRule = moveRules.get((round + i) % moveRules.size());
                    if (canGo(position, moveRule)) {
                        Point newPoint = new Point(position.x + moveRule.moveStep.x,
                                position.y + moveRule.moveStep.y);

                        from.computeIfAbsent(newPoint, k -> new ArrayList<>());
                        from.get(newPoint).add(position);
                        break;
                    }
                }
            }

            for (var entry : from.entrySet()) {
                if (entry.getValue().size() == 1) {
                    moved = true;
                    positions.add(entry.getKey());
                    positions.remove(entry.getValue().get(0));

                }
            }
            round++;
            return moved;
        }

        boolean canGo(Point point, MoveRule moveRule) {
            for (var offset : moveRule.needFree()) {
                if (positions.contains(new Point(point.x + offset.x, point.y + offset.y))) {
                    return false;
                }
            }
            return true;
        }

        boolean hasNeighbour(Point point) {
            for (Point adjacent : adjacents) {
                if (positions.contains(new Point(point.x + adjacent.x, point.y + adjacent.y))) {
                    return true;
                }
            }
            return false;
        }

        public int emptyGround() {
            Pair<Point, Point> limits = getLimits();
            return ((limits.getValue1().x - limits.getValue0().x + 1) * (limits.getValue1().y - limits.getValue0().y + 1)) -
                    positions.size();
        }

        Pair<Point, Point> getLimits() {
            Pair<Point, Point> limits = new Pair<>(new Point(Integer.MAX_VALUE, Integer.MAX_VALUE),
                    new Point(Integer.MIN_VALUE, Integer.MIN_VALUE));
            for (Point position : positions) {
                if (position.x < limits.getValue0().x) {
                    limits.getValue0().x = position.x;
                }
                if (position.y < limits.getValue0().y) {
                    limits.getValue0().y = position.y;
                }

                if (position.x > limits.getValue1().x) {
                    limits.getValue1().x = position.x;
                }
                if (position.y > limits.getValue1().y) {
                    limits.getValue1().y = position.y;
                }
            }


            return limits;
        }

        void printGrove() {
            Pair<Point, Point> limits = getLimits();


            for (int y = limits.getValue0().y; y <= limits.getValue1().y; y++) {
                for (int x = limits.getValue0().x; x <= limits.getValue1().x; x++) {
                    if (positions.contains(new Point(x, y))) {
                        System.out.print("#");
                    } else {
                        System.out.print(".");
                    }
                }
                System.out.println();
            }
        }

        record MoveRule(Point moveStep) {
            List<Point> needFree() {
                if (moveStep.y != 0) {
                    return List.of(new Point(-1, moveStep.y), moveStep, new Point(1, moveStep.y));
                }
                if (moveStep.x != 0) {
                    return List.of(new Point(moveStep.x, -1), moveStep, new Point(moveStep.x, 1));
                }

                throw new RuntimeException("Illegal moveStep " + moveStep);
            }
        }
    }


}
