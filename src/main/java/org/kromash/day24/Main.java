package org.kromash.day24;

import org.kromash.common.Direction;
import org.kromash.common.Solution;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.Stream;

public class Main extends Solution {
    public Main() {
        super(24);
    }


    public static void main(String[] args) {
        solve(Main.class);
    }

    public String partOne() {
        var blizzardBasin = new BlizzardBasin(readInputLines());
        return String.valueOf(blizzardBasin.shortestPath());
    }

    public String partTwo() {
        var blizzardBasin = new BlizzardBasin(readInputLines());
        return String.valueOf(blizzardBasin.shortestPathTwice());
    }

    static class BlizzardBasin {
        int width, height;
        HashMap<Point, List<Blizzard>> blizzards;
        Set<Point> walls;
        Point start, end, leftUpper, rightLower;

        public BlizzardBasin(List<String> inputList) {
            int j = 0;
            blizzards = new HashMap<>();
            walls = new HashSet<>();
            for (String line : inputList) {
                width = Math.max(width, line.length());
                for (int i = 0; i < line.length(); i++) {
                    char c = line.charAt(i);
                    if (c == '.') {
                        continue;
                    }
                    if (c == '#') {
                        walls.add(new Point(i, j));
                        continue;
                    }
                    Point position = new Point(i, j);
                    blizzards.put(position, List.of(Blizzard.getFromArrow(position, c)));
                }
                ++j;
            }
            height = j;

            start = new Point(1, 0);
            end = new Point(width - 2, height - 1);

            leftUpper = new Point(1, 1);
            rightLower = new Point(width - 2, height - 2);
        }


        List<Point> possibleMoves(Point position) {
            List<Point> newPositions = new ArrayList<>();

            List<Point> moves = Stream.concat(Arrays.stream(Direction.values()).map(Direction::getMove),
                    Stream.of(new Point(0, 0))).toList();

            for (Point move : moves) {
                Point newPosition = new Point(position.x + move.x, position.y + move.y);
                if (walls.contains(newPosition)) {
                    continue;
                }
                if (blizzards.get(newPosition) != null && blizzards.get(newPosition).size() > 0) {
                    continue;
                }

                if (newPosition.x < 0 || newPosition.x >= width || newPosition.y < 0 || newPosition.y >= height) {
                    continue;
                }
                newPositions.add(newPosition);
            }

            return newPositions;
        }

        int dynamicPath(int minute, Set<Point> startPositions) {
            if (startPositions.contains(end)) {
                return minute;
            }
            moveBlizzards();

            Set<Point> nextPositions = new HashSet<>();

            for (Point position : startPositions) {
                nextPositions.addAll(possibleMoves(position));
            }

            return dynamicPath(minute + 1, nextPositions);
        }

        int shortestPath() {
            return dynamicPath(0, Set.of(start));
        }

        int shortestPathTwice() {
            int value = dynamicPath(0, Set.of(start));
            Point temp = start;
            start = end;
            end = temp;
            value = dynamicPath(value, Set.of(start));
            temp = start;
            start = end;
            end = temp;
            return dynamicPath(value, Set.of(start));
        }

        void moveBlizzards() {
            HashMap<Point, List<Blizzard>> newBlizzards = new HashMap<>();
            for (Blizzard blizzard : blizzards.values().stream().flatMap(List::stream).toList()) {
                blizzard.moveWrapping(leftUpper, rightLower);
                if (!newBlizzards.containsKey(blizzard.position)) {
                    newBlizzards.put(blizzard.position, new ArrayList<>());
                }
                newBlizzards.get(blizzard.position).add(blizzard);
            }
            blizzards = newBlizzards;
        }

        void print() {
            for (int j = 0; j < height; j++) {
                for (int i = 0; i < width; i++) {
                    char c = '.';

                    Point point = new Point(i, j);
                    if (blizzards.containsKey(point)) {
                        List<Blizzard> blizzardList = blizzards.get(point);
                        if (blizzardList.size() > 1) {
                            c = (char) ('0' + Math.min(blizzardList.size(), 9));
                        } else {
                            c = blizzardList.get(0).direction.getArrow();
                        }

                    } else if (walls.contains(point)) {
                        c = '#';
                    }

                    System.out.print(c);

                }
                System.out.println();
            }
        }

        static class Blizzard {
            Point position;
            Direction direction;

            Blizzard(Point position, Direction direction) {
                this.position = position;
                this.direction = direction;
            }

            static Blizzard getFromArrow(Point position, Character arrow) {
                Direction direction = switch (arrow) {
                    case '<' -> Direction.LEFT;
                    case '^' -> Direction.UP;
                    case '>' -> Direction.RIGHT;
                    case 'v' -> Direction.DOWN;
                    default -> throw new RuntimeException("Invalid arrow " + arrow);
                };

                return new Blizzard(position, direction);
            }

            void moveWrapping(Point leftUpper, Point rightLower) {
                Point move = direction.getMove();
                Point newPosition = new Point(position.x + move.x, position.y + move.y);
                if (newPosition.x < leftUpper.x) {
                    newPosition.x = rightLower.x;
                }
                if (newPosition.x > rightLower.x) {
                    newPosition.x = leftUpper.x;
                }
                if (newPosition.y < leftUpper.y) {
                    newPosition.y = rightLower.y;
                }
                if (newPosition.y > rightLower.y) {
                    newPosition.y = leftUpper.y;
                }
                position = newPosition;
            }

        }
    }


}
