package org.kromash.day12;

import org.kromash.common.Solution;

import java.awt.*;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Main extends Solution {
    private static final List<Point> POSSIBLE_MOVES = List.of(
            new Point(0, 1),
            new Point(1, 0),
            new Point(0, -1),
            new Point(-1, 0));
    ArrayList<ArrayList<Integer>> elevationMap;
    ArrayList<ArrayList<Integer>> distance;
    Point start = null, end = null;

    public Main() {
        super(12);
    }

    public static void main(String[] args) {
        new Main().solve();
    }


    public boolean loadMap() {
        elevationMap = new ArrayList<>();
        distance = new ArrayList<>();
        int i = 0;
        for (var line : readInputLines()) {
            ArrayList<Integer> elevationLine = new ArrayList<>();
            ArrayList<Integer> distanceLine = new ArrayList<>();
            int j = 0;
            for (char c : line.toCharArray()) {
                int elevation = c - 'a';
                if (c == 'S') {
                    elevation = 0;
                    start = new Point(i, j);
                } else if (c == 'E') {
                    elevation = 'z' - 'a';
                    end = new Point(i, j);
                }
                distanceLine.add(Integer.MAX_VALUE);
                elevationLine.add(elevation);
                ++j;
            }
            distance.add(distanceLine);
            elevationMap.add(elevationLine);
            ++i;
        }
        if (start == null || end == null) {
            throw new RuntimeException("Start or end not found");
        }
        return true;
    }

    public String partOne() {

        loadMap();
        distance.get(start.x).set(start.y, 0);

        int width = elevationMap.get(0).size();
        int height = elevationMap.size();


        Deque<Point> queue = new LinkedList<>();
        queue.add(start);


        while (!queue.isEmpty()) {
            var point = queue.poll();
            int currDist = distance.get(point.x).get(point.y);
            int currElevation = elevationMap.get(point.x).get(point.y);
            for (var move : POSSIBLE_MOVES) {
                var next = new Point(point.x + move.x, point.y + move.y);

                if (next.x < 0 || next.y < 0 || next.x >= height || next.y >= width) {
                    continue;
                }
                int nextElevation = elevationMap.get(next.x).get(next.y);

                if (nextElevation - currElevation > 1) {
                    continue;
                }

                int nextDist = distance.get(next.x).get(next.y);
                if (currDist + 1 < nextDist) {
                    distance.get(next.x).set(next.y, currDist + 1);

                    queue.offer(next);

                }
            }
        }

        return String.valueOf(distance.get(end.x).get(end.y));
    }

    public String partTwo() {

        loadMap();
        distance.get(end.x).set(end.y, 0);

        int width = elevationMap.get(0).size();
        int height = elevationMap.size();


        Deque<Point> queue = new LinkedList<>();
        queue.add(end);


        int minDistToA = Integer.MAX_VALUE;
        while (!queue.isEmpty()) {
            var point = queue.poll();
            int currDist = distance.get(point.x).get(point.y);
            int currElevation = elevationMap.get(point.x).get(point.y);
            if (currElevation == 0 && currDist < minDistToA) {
                minDistToA = currDist;
            }
            for (var move : POSSIBLE_MOVES) {
                var next = new Point(point.x + move.x, point.y + move.y);

                if (next.x < 0 || next.y < 0 || next.x >= height || next.y >= width) {
                    continue;
                }
                int nextElevation = elevationMap.get(next.x).get(next.y);

                if (currElevation - nextElevation > 1) {
                    continue;
                }

                int nextDist = distance.get(next.x).get(next.y);
                if (currDist + 1 < nextDist) {
                    distance.get(next.x).set(next.y, currDist + 1);

                    queue.offer(next);

                }
            }
        }

        return String.valueOf(minDistToA);
    }


}
