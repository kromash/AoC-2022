package org.kromash.day22;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class BoardCube extends Board {

    HashMap<Point, Side> sides = new HashMap<>();

    BoardCube(Iterator<String> inputStream) {
        super(inputStream);
        calculateSides();
    }

    private Direction goingDirection(Point moveStep) {
        Direction direction = null;
        if (moveStep.x == 1) {
            direction = Direction.RIGHT;
        }
        if (moveStep.x == -1) {
            direction = Direction.LEFT;
        }
        if (moveStep.y == 1) {
            direction = Direction.DOWN;
        }
        if (moveStep.y == -1) {
            direction = Direction.UP;
        }
        return direction;
    }

    protected Point getWrappingPoint(Point point, Point moveStep) {
        Direction direction = goingDirection(moveStep);
        Side side = getSide(point);

        Side neighbour = side.neighbours.get(direction);
        Direction incomingDirection = getIncomingDirection(side, neighbour);

        Point relativePoint = side.getRelativePoint(point);

        Point newRelativePoint = new Point(relativePoint);
        if (direction == incomingDirection) {

            if (direction == Direction.UP || direction == Direction.DOWN) {
                newRelativePoint = side.mirrorYRelative(relativePoint);
            }
            if (direction == Direction.LEFT || direction == Direction.RIGHT) {
                newRelativePoint = side.mirrorXRelative(relativePoint);
            }
        } else if (direction == incomingDirection.opposite()) { // RIGHT LEFT
            if (direction == Direction.UP || direction == Direction.DOWN) {
                newRelativePoint = side.mirrorXRelative(relativePoint);
            }
            if (direction == Direction.LEFT || direction == Direction.RIGHT) {
                newRelativePoint = side.mirrorYRelative(relativePoint);
            }
        } else if (direction.rotate(
                Direction.RIGHT) == incomingDirection) {
            newRelativePoint = side.rotateRelative(relativePoint, Direction.LEFT);
            if (incomingDirection == Direction.UP || incomingDirection == Direction.DOWN) {
                newRelativePoint = side.mirrorXRelative(newRelativePoint);
            }
            if (incomingDirection == Direction.LEFT || incomingDirection == Direction.RIGHT) {
                newRelativePoint = side.mirrorYRelative(newRelativePoint);
            }
        } else if (direction.rotate(Direction.LEFT) == incomingDirection) {
            newRelativePoint = side.rotateRelative(relativePoint, Direction.RIGHT);
            if (incomingDirection == Direction.UP || incomingDirection == Direction.DOWN) {
                newRelativePoint = side.mirrorXRelative(newRelativePoint);
            }
            if (incomingDirection == Direction.LEFT || incomingDirection == Direction.RIGHT) {
                newRelativePoint = side.mirrorYRelative(newRelativePoint);
            }
        }


        return neighbour.getAbsolutePoint(newRelativePoint);
    }

    protected Direction getWrappingDirection(Point point, Point moveStep) {
        Direction direction = goingDirection(moveStep);
        Side side = getSide(point);

        Side neighbour = side.neighbours.get(direction);
        Direction incomingDirection = getIncomingDirection(side, neighbour);
        return incomingDirection;
    }

    Direction getIncomingDirection(Side from, Side to) {
        for (Direction direction : Direction.values()) {
            if (to.neighbours.get(direction) != null && to.neighbours.get(direction)
                    .equals(from)) {
                return direction.opposite();
            }
        }
        throw new RuntimeException(from + " and " + to + " are not neighbours.");
    }

    Side getSide(Point point) {
        for (Side side : sides.values()) {
            if (side.contains(point)) {
                return side;
            }
        }
        throw new RuntimeException("Point " + point + " doesn't belong to any side.");
    }

    void calculateSides() {
        int sideLength = columLimits.size();

        for (var limit : columLimits) {
            int width = limit.getValue1() - limit.getValue0() + 1;
            if (width < sideLength) {
                sideLength = width;
            }
        }

        for (var limit : rowLimits) {
            int width = limit.getValue1() - limit.getValue0() + 1;
            if (width < sideLength) {
                sideLength = width;
            }
        }

        for (int y = 0; y < height; y += sideLength) {
            for (int x = 0; x < width; x += sideLength) {
                var point = new Point(x, y);
                if (!isOutside(point)) {
                    Side side = new Side(point, new Point(x + sideLength - 1, y + sideLength - 1));
                    sides.put(point, side);
                    Side left = sides.get(new Point(x - sideLength, y));
                    if (left != null) {
                        left.neighbours.put(Direction.RIGHT, side);
                        side.neighbours.put(Direction.LEFT, left);
                    }
                    Side up = sides.get(new Point(x, y - sideLength));
                    if (up != null) {
                        up.neighbours.put(Direction.DOWN, side);
                        side.neighbours.put(Direction.UP, up);
                    }

                }
            }
        }

        while (!neighboursFilled()) {
            for (Side side : sides.values()) {
                for (Direction direction : Direction.values()) {
                    if (side.neighbours.get(direction) == null) {
                        Side neighbour = getNeighbour(side, direction);
                        side.neighbours.put(direction, neighbour);

                    }
                }
            }
        }


    }

    boolean neighboursFilled() {
        for (Side side : sides.values()) {
            for (Direction direction : Direction.values()) {
                if (side.neighbours.get(direction) == null)
                    return false;
            }
        }
        return true;
    }

    Side getNeighbour(Side side, Direction direction) {
        if (side == null) {
            return null;
        }
        Side neighbour = side.neighbours.get(direction);
        if (neighbour != null) {
            return neighbour;
        }
        neighbour = getNeighbourRelative(side, direction.rotate(Direction.LEFT),
                Direction.RIGHT);
        if (neighbour != null) {
            return neighbour;
        }
        neighbour = getNeighbourRelative(side, direction.rotate(Direction.RIGHT),
                Direction.LEFT);
        return neighbour;
    }

    Side getNeighbourRelative(Side side, Direction neighbourDirection, Direction sideDirection) {
        Side directNeighbour = side.neighbours.get(neighbourDirection);
        if (directNeighbour == null) {
            return null;
        }
        Direction neighbourSide = null;

        for (Direction direction : Direction.values()) {
            if (directNeighbour.neighbours.get(direction) != null && directNeighbour.neighbours.get(direction)
                    .equals(side)) {
                neighbourSide = direction;
                break;
            }
        }

        if (neighbourSide == null) {
            return null;
        }

        return directNeighbour.neighbours.get(neighbourSide.rotate(sideDirection.opposite()));

    }

    static class Side {
        Map<Direction, Side> neighbours;
        Point leftUpper, rightLower;
        int width;

        Side(Point leftUpper, Point rightLower) {
            this.neighbours = new HashMap<>();
            this.leftUpper = leftUpper;
            this.rightLower = rightLower;

            this.width = rightLower.x - leftUpper.x + 1;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("Side{" +
                    "leftUpper=" + leftUpper + ", neighbours={");
            for (var direction : Direction.values()) {
                Side neighbour = neighbours.get(direction);
                sb.append(direction.name()).append("=")
                        .append(neighbour != null ? neighbour.leftUpper : "null").append(" ");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append("}}");
            return sb.toString();
        }

        public boolean contains(Point point) {
            return leftUpper.x <= point.x && leftUpper.y <= point.y &&
                    rightLower.x >= point.x && rightLower.y >= point.y;
        }

        Point getRelativePoint(Point point) {
            return new Point(point.x - leftUpper.x, point.y - leftUpper.y);
        }

        Point getAbsolutePoint(Point relativePoint) {
            return new Point(relativePoint.x + leftUpper.x, relativePoint.y + leftUpper.y);
        }

        Point rotateRelative(Point point, Direction direction) {
            if (direction == Direction.RIGHT) {
                // x = 2 y = 0 -> x = 3 y = 2
                // x = 3 y = 1 -> x = 2 y = 3
                // x = 0 y = 3 -> x = 0 y = 0
                return new Point(width - point.y - 1, point.x);
            }
            if (direction == Direction.LEFT) {
                return new Point(point.y, width - point.x - 1);
            }

            throw new RuntimeException("Invalid direction " + direction);

        }

        Point mirrorXRelative(Point point) {
            return new Point(width - point.x - 1, point.y);
        }

        Point mirrorYRelative(Point point) {
            return new Point(point.x, width - point.y - 1);
        }


        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            Side side = (Side) o;
            return leftUpper.equals(side.leftUpper) && rightLower.equals(side.rightLower);
        }

        @Override
        public int hashCode() {
            return Objects.hash(leftUpper, rightLower);
        }
    }
}
