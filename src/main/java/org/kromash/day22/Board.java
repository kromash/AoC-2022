package org.kromash.day22;

import com.google.common.collect.Streams;
import org.javatuples.Pair;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


class Board {
    ArrayList<ArrayList<Character>> map;
    Point point;
    Direction direction;
    List<Move> moves;
    ArrayList<Pair<Integer, Integer>> rowLimits;
    ArrayList<Pair<Integer, Integer>> columLimits;
    int width, height;

    Board(Iterator<String> inputStream) {

        map = new ArrayList<>();
        width = 0;
        while (inputStream.hasNext()) {
            String line = inputStream.next();

            if (line.equals("")) {
                break;
            }
            var row = new ArrayList<>(line.chars().mapToObj(c -> (char) c).collect(Collectors.toList()));
            map.add(row);
            width = Math.max(width, row.size());
        }


        height = map.size();
        rowLimits = new ArrayList<>();
        columLimits = new ArrayList<>();
        for (int rowIdx = 0; rowIdx < height; rowIdx++) {
            ArrayList<Character> row = map.get(rowIdx);
            int lower = 0;
            int upper = row.size() - 1;
            int i = 0;
            for (; i < upper; i++) {
                if (row.get(i) == '.' || row.get(i) == '#') {
                    lower = i;
                    break;
                }
            }

            for (; i < upper; i++) {
                if (row.get(i) == ' ') {
                    upper = i - 1;
                    break;
                }
            }


            rowLimits.add(new Pair<>(lower, upper));
        }
        for (int colIdx = 0; colIdx < width; colIdx++) {
            int lower = 0;
            int upper = height - 1;
            int i = 0;
            for (; i < upper; i++) {
                if (map.get(i).size() <= colIdx) {
                    continue;
                }
                if (map.get(i).get(colIdx) == '.' || map.get(i).get(colIdx) == '#') {
                    lower = i;
                    break;
                }
            }
            for (; i < upper; i++) {
                if (map.get(i).size() <= colIdx || map.get(i).get(colIdx) == ' ') {
                    upper = i - 1;
                    break;
                }
            }

            columLimits.add(new Pair<>(lower, upper));
        }

        String path = inputStream.next();
        direction = Direction.RIGHT;
        point = new Point(rowLimits.get(0).getValue0(), 0);

        String[] stepsArray = path.split("[RL]");
        String[] rotationsArray = Arrays.stream(path.split("([0-9+]+)+")).filter(str -> !str.isEmpty())
                .toArray(String[]::new);
        Stream<String> steps = Arrays.stream(path.split("[RL]"));
        Stream<String> rotations = Arrays.stream(path.split("([0-9+]+)+")).filter(str -> !str.isEmpty());

        moves = new ArrayList<>(
                Streams.zip(steps, rotations, Stream::of).flatMap(Function.identity()).map(Move::new).toList());
        if (stepsArray.length > rotationsArray.length) {
            moves.add(new Move(stepsArray[stepsArray.length - 1]));
        }
    }

    void goThroughPath() {
        for (Move move : moves) {
            if (move.isRotation()) {
                rotate(move);
            } else {
                moveForward(move);
            }
        }
    }

    private void rotate(Move move) {
        direction = direction.rotate(move.move.charAt(0));
    }

    private void moveForward(Move move) {
        Point moveStep = this.direction.getMove();

        for (int i = 0; i < move.getSteps(); i++) {
            Point newPoint = new Point(point.x + moveStep.x, point.y + moveStep.y);

            if (isWall(newPoint)) {

                if (isOutside(newPoint)) {
                    Point wrappingPoint = getWrappingPoint(point, moveStep);

                    if (isWall(wrappingPoint)) {
                        break;
                    }
                    this.direction = getWrappingDirection(point, moveStep);
                    moveStep = this.direction.getMove();
                    this.point = wrappingPoint;
                } else {
                    break;
                }

            } else {
                point = newPoint;
            }
        }


    }

    // returns wrapping point or the same point if not possible
    protected Point getWrappingPoint(Point point, Point moveStep) {
        if (moveStep.x == 1) {
            return new Point(rowLimits.get(point.y).getValue0(), point.y);
        }

        if (moveStep.x == -1) {
            return new Point(rowLimits.get(point.y).getValue1(), point.y);
        }

        if (moveStep.y == 1) {
            return new Point(point.x, columLimits.get(point.x).getValue0());
        }

        if (moveStep.y == -1) {
            return new Point(point.x, columLimits.get(point.x).getValue1());
        }

        throw new RuntimeException("Illegal wrapping point " + point + " " + moveStep);
    }

    protected Direction getWrappingDirection(Point point, Point moveStep) {
        return direction;
    }

    protected boolean isWall(Point point) {
        if (point.y < 0 || point.y >= map.size()) {
            return true;
        }
        ArrayList<Character> line = map.get(point.y);

        if (point.x < 0 || point.x >= line.size()) {
            return true;
        }

        return line.get(point.x) != '.';
    }

    void printBoard() {
        for (int i = 0; i < height; i++) {

            for (int j = 0; j < width; j++) {
                if (point.y == i && point.x == j) {
                    switch (direction) {
                        case UP -> System.out.print('^');
                        case RIGHT -> System.out.print('>');
                        case LEFT -> System.out.print('<');
                        case DOWN -> System.out.print('v');
                    }
                } else {
                    if (!isOutside(new Point(j, i))) {
                        System.out.print(map.get(i).get(j));
                    } else {
                        System.out.print(' ');
                    }
                }

            }
            System.out.println();
        }
    }

    protected boolean isOutside(Point point) {
        if (point.y < 0 || point.y >= height) {
            return true;
        }
        //ArrayList<Character> line = map.get(point.y);

        if (point.x < 0 || point.x >= width) {
            return true;
        }

        var rowLimit = rowLimits.get(point.y);
        var columnLimit = columLimits.get(point.x);
        return point.x < rowLimit.getValue0() || point.x > rowLimit.getValue1() ||
                point.y < columnLimit.getValue0() || point.y > columnLimit.getValue1();
    }

    int getPositionValue() {
        return 1000 * (point.y + 1) + 4 * (point.x + 1) + direction.getValue();
    }

    public enum Direction {
        RIGHT(0), DOWN(1), LEFT(2), UP(3);

        private final int value;

        Direction(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public Point getMove() {
            return switch (this) {
                case RIGHT -> new Point(1, 0);
                case DOWN -> new Point(0, 1);
                case LEFT -> new Point(-1, 0);
                case UP -> new Point(0, -1);
            };
        }

        public Direction rotate(Direction rotation) {
            if (rotation == Direction.LEFT) {
                return rotate('L');
            }
            if (rotation == Direction.RIGHT) {
                return rotate('R');
            }

            throw new RuntimeException("Invalid rotation");
        }

        public Direction opposite() {
            return this.rotate(Direction.RIGHT).rotate(Direction.RIGHT);
        }

        public Direction rotate(Character rotation) {
            assert rotation == 'R' || rotation == 'L';
            int rotationValue = rotation == 'R' ? 1 : -1;
            int value = (getValue() + rotationValue) % 4;
            if (value == -1) {
                value = 3;
            }
            for (Direction direction : values()) {
                if (direction.getValue() == value) {
                    return direction;
                }
            }

            throw new RuntimeException("Invalid rotation state");
        }
    }

    public record Move(String move) {
        boolean isRotation() {
            return move.equals("R") || move.equals("L");
        }

        int getSteps() {
            return Integer.parseInt(move);
        }
    }


}
