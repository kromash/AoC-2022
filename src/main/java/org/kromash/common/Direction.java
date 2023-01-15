package org.kromash.common;

import java.awt.*;

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

    public char getArrow() {
        return switch (this.value) {
            case 0 -> '>';
            case 1 -> 'v';
            case 2 -> '<';
            case 3 -> '^';
            default -> throw new IllegalStateException("Unexpected value: " + this.value);
        };
    }
}