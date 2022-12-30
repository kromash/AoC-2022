package org.kromash.day16;

import org.kromash.common.Solution;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Valve {
    String name;
    int rate;
    Set<String> tunnels;
    static final Pattern VALVE_PATTERN = Pattern.compile(
        "Valve ([A-Z]{2}) has flow rate=(\\d+); tunnels? leads? to valves? ([ ,A-Z]*)");

    Valve(String name, int rate, String[] tunnels) {
        this.name = name;
        this.rate = rate;
        this.tunnels = Set.of(tunnels);
    }

    public static Valve fromLine(String line) {
        Matcher matcher = VALVE_PATTERN.matcher(line);

        if (matcher.find()) {
            return new Valve(
                matcher.group(1),
                Integer.parseInt(matcher.group(2)),
                matcher.group(3).split(", ")
            );
        }
        return null;
    }
}

class Volcano {
    Map<String, Valve> valveMap;
    Map<String, Map<String, Integer>> distances;

    int TIME = 30;

    public Volcano(Map<String, Valve> valveMap) {
        this.valveMap = valveMap;
        computeDistances();
    }

    private void computeDistances() {
        distances = new HashMap<>();
        for (String node : valveMap.keySet()) {
            distances.put(node, new HashMap<>());
            for (String mid : valveMap.keySet()) {
                int dist = TIME + 1;
                if (valveMap.get(node).tunnels.contains(mid)) {
                    dist = 1;
                }
                distances.get(node).put(mid, dist);
            }
            distances.get(node).put(node, 0);
        }

        for (String k : valveMap.keySet()) {
            for (String i : valveMap.keySet()) {
                for (String j : valveMap.keySet()) {
                    int distIJ = distances.get(i).get(j);
                    int distIK = distances.get(i).get(k);
                    int distJK = distances.get(j).get(k);

                    if (distIJ > distIK + distJK) {
                        distances.get(i).put(j, distIK + distJK);
                        distances.get(j).put(i, distIK + distJK);
                    }
                }
            }
        }

    }

    public int getMaxPressure() {
        Set<String> toVisit = new HashSet<>();
        for (Map.Entry<String, Valve> entry : valveMap.entrySet()) {
            if (entry.getValue().rate > 0) {
                toVisit.add(entry.getKey());
            }
        }

        return recursiveMax("AA", TIME, 0, toVisit);
    }

    public int getMaxPressureWithHelp() {

        String start = "AA";

        Set<String> toVisit = new HashSet<>();
        for (Map.Entry<String, Valve> entry : valveMap.entrySet()) {
            if (entry.getValue().rate > 0) {
                toVisit.add(entry.getKey());
            }
        }

        String[] set = new String[toVisit.size()];
        set = toVisit.toArray(set);
        int n = set.length;

        int max = 0;
        for (int i = 0; i < (1 << n) / 2; i++) {
            Set<String> toVisitHuman = new HashSet<>();

            Set<String> toVisitElephant = new HashSet<>();

            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) > 0) {
                    toVisitHuman.add(set[j]);
                } else {
                    toVisitElephant.add(set[j]);
                }
            }
            if (toVisitHuman.size() == 0 || toVisitElephant.size() == 0) {
                continue;
            }

            int human = recursiveMax(start, TIME - 4, 0, toVisitHuman);
            int elephant = recursiveMax(start, TIME - 4, 0, toVisitElephant);

            max = Math.max(max, human + elephant);
        }
        return max;
    }

    int recursiveMax(String node, int time, int pressure, Set<String> toVisit) {

        if (time < 2 || toVisit.size() == 0) {
            return pressure;
        }
        Valve currValve = valveMap.get(node);
        if (currValve.rate > 0) {
            time -= 1;
            pressure += currValve.rate * time;
            toVisit.remove(node);

        }
        int maxPressure = pressure;
        for (String next : new HashSet<>(toVisit)) {
            int retPressure = recursiveMax(next, time - distances.get(node).get(next), pressure, toVisit);

            maxPressure = Math.max(maxPressure, retPressure);
        }
        toVisit.add(node);
        return maxPressure;
    }

}

public class Main extends Solution {
    public Main() {
        super(16);
    }

    public static void main(String[] args) {
        new Main().solve();
    }

    Map<String, Valve> readValveMap() {
        Map<String, Valve> valveMap = new HashMap<>();
        for (var line : readInputLines()) {
            Valve valve = Valve.fromLine(line);
            assert valve != null;
            valveMap.put(valve.name, valve);
        }

        return valveMap;
    }

    public String partOne() {

        Volcano volcano = new Volcano(readValveMap());

        return String.valueOf(volcano.getMaxPressure());
    }

    public String partTwo() {

        Volcano volcano = new Volcano(readValveMap());

        return String.valueOf(volcano.getMaxPressureWithHelp());
    }

}
