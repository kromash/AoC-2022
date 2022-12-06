package org.kromash.day06;

import org.kromash.common.Solution;

import java.util.HashMap;
import java.util.Map;

public class Main extends Solution {
    public Main() {
        super(6);
    }

    public static void main(String[] args) {
        new Main().solve();
    }

    public String partOne() {
        int result =  findMarker(readInputLines().get(0), 4);

        return String.valueOf(result);
    }

    public String partTwo() {
        int result =  findMarker(readInputLines().get(0), 14);

        return String.valueOf(result);
    }

    private int findMarker(String line, int marker_len) {
        int result = 0;

        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < marker_len - 1; ++i) {
            char c = line.charAt(i);
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        for (int i = marker_len - 1; i < line.length(); ++i) {

            char c_start = line.charAt(i - (marker_len - 1));
            char c_curr = line.charAt(i);
            map.put(c_curr, map.getOrDefault(c_curr, 0) + 1);
            if (map.size() == marker_len) {
                result = i + 1;
                break;
            }
            if (map.get(c_start) == 1) {
                map.remove(c_start);
            } else {
                map.put(c_start, map.get(c_start) - 1);
            }
        }
        return result;


    }


}
