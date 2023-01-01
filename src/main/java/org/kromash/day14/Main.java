package org.kromash.day14;

import org.kromash.common.Solution;

import java.awt.*;
import java.util.ArrayList;

public class Main extends Solution {

    Point leftBottom;
    Point rightTop;

    public Main() {
        super(14);
    }

    public static void main(String[] args) {
        new Main().solve();
    }

    ArrayList<ArrayList<Point>> loadRocks() {
        ArrayList<ArrayList<Point>> rocks = new ArrayList<>();


        for (var line : readInputLines()) {
            ArrayList<Point> rockLines = new ArrayList<>();
            String[] pointsStr = line.split(" -> ");
            for (String pointStr : pointsStr) {
                String[] coord = pointStr.split(",");
                Point point = new Point(Integer.parseInt(coord[0]), Integer.parseInt(coord[1]));
                rockLines.add(point);

                if (point.x > rightTop.x)
                    rightTop.x = point.x;
                if (point.y < rightTop.y)
                    rightTop.y = point.y;

                if (point.x < leftBottom.x)
                    leftBottom.x = point.x;
                if (point.y > leftBottom.y)
                    leftBottom.y = point.y;
            }
            rocks.add(rockLines);
        }

        return rocks;

    }


    Cave loadCave() {
        leftBottom = new Point(500, 0);
        rightTop = new Point(500, 0);
        ArrayList<ArrayList<Point>> rocks = loadRocks();


        Cave cave = new Cave(leftBottom, rightTop);

        for (ArrayList<Point> rockLines : rocks) {
            cave.addRocks(rockLines);
        }
        return cave;
    }

    Cave loadInfinityCave() {
        leftBottom = new Point(500, 0);
        rightTop = new Point(500, 0);
        ArrayList<ArrayList<Point>> rocks = loadRocks();


        ArrayList<Point> bottom = new ArrayList<>();
        int extraWidth = leftBottom.y;
        bottom.add(new Point(leftBottom.x - extraWidth, leftBottom.y + 2));
        bottom.add(new Point(rightTop.x + extraWidth, leftBottom.y + 2));
        rocks.add(bottom);

        leftBottom.x = bottom.get(0).x;
        leftBottom.y = bottom.get(0).y;
        rightTop.x = bottom.get(1).x;
        Cave cave = new Cave(leftBottom, rightTop);

        for (ArrayList<Point> rockLines : rocks) {
            cave.addRocks(rockLines);
        }
        return cave;
    }


    public String partOne() {
        Cave cave = loadCave();

        return String.valueOf(cave.simulate());
    }

    public String partTwo() {
        Cave cave = loadInfinityCave();

        return String.valueOf(cave.simulate());
    }

    class Cave {
        ArrayList<ArrayList<Character>> caveMap;
        Point leftTop;
        Point leftBottom;
        Point rightTop;

        Cave(Point leftBottom, Point rightTop) {
            this.leftBottom = leftBottom;
            this.rightTop = rightTop;
            int width = rightTop.x - leftBottom.x + 1;
            int height = leftBottom.y - rightTop.y + 1;

            leftTop = new Point(leftBottom.x, rightTop.y);
            caveMap = new ArrayList<>();
            for (int i = 0; i < height; ++i) {
                ArrayList<Character> caveLine = new ArrayList<>();

                for (int j = 0; j < width; ++j) {
                    caveLine.add('.');
                }

                caveMap.add(caveLine);
            }
        }

        int simulate() {
            int dropped = 0;
            while (dropSand()) {
                dropped += 1;
            }
            print();
            return dropped;
        }

        char get(Point point) {
            return caveMap.get(point.y - leftTop.y).get(point.x - leftTop.x);
        }

        char get(int x, int y) {
            return caveMap.get(y - leftTop.y).get(x - leftTop.x);
        }

        void set(Point point, char c) {
            caveMap.get(point.y - leftTop.y).set(point.x - leftTop.x, c);
        }

        boolean dropSand() {
            Point sand = new Point(500, 0);
            if (get(sand) != '.')
                return false;
            while (sand.x >= leftBottom.x && sand.x <= rightTop.x && sand.y < leftBottom.y) {
                if (get(sand.x, sand.y + 1) == '.') {
                    sand.y += 1;
                    continue;
                }

                if (sand.x > leftBottom.x && get(sand.x - 1, sand.y + 1) == '.') {
                    sand.y += 1;
                    sand.x -= 1;
                    continue;
                }
                if (sand.x == leftBottom.x) {
                    return false;
                }

                if (sand.x < rightTop.x && get(sand.x + 1, sand.y + 1) == '.') {
                    sand.y += 1;
                    sand.x += 1;
                    continue;
                }
                if (sand.x == rightTop.x) {
                    return false;
                }

                set(sand, 'o');
                return true;

            }

            return false;
        }

        void print() {
            for (ArrayList<Character> caveLine : caveMap) {

                for (Character cavePoint : caveLine) {
                    System.out.print(cavePoint);
                }
                System.out.println();
            }
        }

        void addRocks(ArrayList<Point> rockLines) {
            for (int i = 1; i < rockLines.size(); ++i)
                addRockLine(rockLines.get(i - 1), rockLines.get(i));
        }

        private void addRockLine(Point start, Point end) {
            Point lineLeftTop = new Point(Math.min(start.x, end.x), Math.min(start.y, end.y));
            Point lineRightBottom = new Point(Math.max(start.x, end.x), Math.max(start.y, end.y));

            for (int y = lineLeftTop.y; y <= lineRightBottom.y; ++y)
                for (int x = lineLeftTop.x; x <= lineRightBottom.x; ++x)
                    set(new Point(x, y), '#');


        }
    }


}
