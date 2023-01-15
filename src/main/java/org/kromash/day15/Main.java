package org.kromash.day15;

import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeSet;
import org.kromash.common.Solution;

import java.awt.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main extends Solution {
    public Main() {
        super(15);
    }


    public static void main(String[] args) {
        solve(Main.class);
    }


    int getRowToCheck() {
        return 2000000;
    }

    int getMaxY() {
        return 4000000;
    }

    public String partOne() {

        int rowToCheck = getRowToCheck();
        Pattern linePattern = Pattern.compile(
                "Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)");

        RangeSet<Integer> rangeSet = TreeRangeSet.create();

        for (var line : readInputLines()) {
            Matcher matcher = linePattern.matcher(line);
            if (matcher.find()) {
                Point sensor = new Point(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
                Point beacon = new Point(Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)));


                int r = Math.abs(sensor.x - beacon.x) + Math.abs(sensor.y - beacon.y);

                int dist = Math.abs(sensor.y - rowToCheck);
                int lineCover = r - dist;
                if (lineCover < 0)
                    continue;

                int coverStartX = sensor.x - lineCover;
                int coverEndX = sensor.x + lineCover;


                rangeSet.add(Range.closed(coverStartX, coverEndX));

            }

        }

        int result = 0;
        for (Range range : rangeSet.asRanges()) {
            result += (Integer) range.upperEndpoint() - (Integer) range.lowerEndpoint();
        }

        return String.valueOf(result);
    }

    public String partTwo() {

        int maxY = getMaxY();
        Pattern linePattern = Pattern.compile(
                "Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)");
        ArrayList<RangeSet<Integer>> coveredLines = new ArrayList<>();

        for (int i = 0; i <= maxY; ++i) {
            coveredLines.add(TreeRangeSet.create());
        }


        for (var line : readInputLines()) {
            Matcher matcher = linePattern.matcher(line);
            if (matcher.find()) {
                Point sensor = new Point(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
                Point beacon = new Point(Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)));


                int r = Math.abs(sensor.x - beacon.x) + Math.abs(sensor.y - beacon.y);

                for (int y = Math.max(0, sensor.y - r); y <= Math.min(sensor.y + r, maxY); ++y) {
                    int dist = Math.abs(sensor.y - y);
                    int lineCover = r - dist;
                    if (lineCover < 0)
                        continue;
                    int coverStartX = Math.max(0, sensor.x - lineCover);
                    int coverEndX = Math.min(maxY, sensor.x + lineCover);
                    coveredLines.get(y).add(Range.closed(coverStartX, coverEndX));
                }


            }

        }
        long y = 0;
        long x = 0;
        for (RangeSet<Integer> rangeLine : coveredLines) {
            java.util.List<Range<Integer>> ranges = rangeLine.asRanges().stream().toList();
            if (ranges.size() > 1) {
                x = ranges.get(0).upperEndpoint() + 1;
                break;

            }
            ++y;

        }

        return String.valueOf(4000000L * x + y);
    }


}
