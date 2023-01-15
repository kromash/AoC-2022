package org.kromash.day19;

import org.javatuples.Triplet;
import org.kromash.common.Solution;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Resources {
    public int ore, clay, obsidian, geode;

    Resources() {
        ore = 0;
        clay = 0;
        obsidian = 0;
        geode = 0;
    }

    Resources(Resources r) {
        ore = r.ore;
        clay = r.clay;
        obsidian = r.obsidian;
        geode = r.geode;
    }

    public Resources(int ore, int clay, int obsidian, int geode) {
        this.ore = ore;
        this.clay = clay;
        this.obsidian = obsidian;
        this.geode = geode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Resources resources)) {
            return false;
        }
        return ore == resources.ore && clay == resources.clay && obsidian == resources.obsidian &&
                geode == resources.geode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ore, clay, obsidian, geode);
    }

    @Override
    public String toString() {
        return "Resources{" +
                "ore=" + ore +
                ", clay=" + clay +
                ", obsidian=" + obsidian +
                ", geode=" + geode +
                '}';
    }

    public Resources add(Resources resources) {
        return new Resources(this.ore + resources.ore,
                this.clay + resources.clay,
                this.obsidian + resources.obsidian,
                this.geode + resources.geode);
    }

    public Resources subtract(Resources resources) {
        return new Resources(this.ore - resources.ore,
                this.clay - resources.clay,
                this.obsidian - resources.obsidian,
                this.geode - resources.geode);
    }

    public boolean hasEnough(Resources required) {
        return this.ore >= required.ore && this.clay >= required.clay && this.obsidian >= required.obsidian &&
                this.geode >= required.geode;
    }
}

class Blueprint {
    static Pattern BLUEPRINT_PATTERN = Pattern.compile(
            "Blueprint (\\d+): Each ore robot costs (\\d+) ore. Each clay robot costs (\\d+) ore. " +
                    "Each obsidian robot costs (\\d+) ore and (\\d+)" +
                    " clay. Each geode robot costs (\\d+) ore and (\\d+) obsidian.");
    int id;
    Resources oreRobot, clayRobot, obsidianRobot, geodeRobot;
    Resources robots;
    int maxGeodes;
    int maxOreRequired;
    Map<Triplet<Integer, Resources, Resources>, Integer> memo;

    Blueprint(String line) {
        Matcher matcher = BLUEPRINT_PATTERN.matcher(line);
        if (matcher.find()) {
            id = Integer.parseInt(matcher.group(1));
            oreRobot = new Resources(Integer.parseInt(matcher.group(2)), 0, 0, 0);
            clayRobot = new Resources(Integer.parseInt(matcher.group(3)), 0, 0, 0);
            obsidianRobot = new Resources(Integer.parseInt(matcher.group(4)), Integer.parseInt(matcher.group(5)), 0, 0);
            geodeRobot = new Resources(Integer.parseInt(matcher.group(6)), 0, Integer.parseInt(matcher.group(7)), 0);
        } else {
            throw new RuntimeException("Couldn't parse blueprint " + line);
        }
        robots = new Resources(1, 0, 0, 0);

    }

    int maxGeodes(int timeLeft, Resources currentResources, Resources robots) {
        if (timeLeft == 0 || currentResources.geode + timeLeft * (robots.geode + timeLeft / 2 + 1) < maxGeodes) {
            if (currentResources.geode > maxGeodes) {
                maxGeodes = currentResources.geode;
                System.out.println(maxGeodes);
            }
            return currentResources.geode;
        }
        var key = new Triplet<>(timeLeft, currentResources, robots);
        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        Resources newResources = currentResources.add(robots);
        int maxGeodesLocal = newResources.geode;
        if (currentResources.hasEnough(geodeRobot)) {
            robots.geode += 1;
            maxGeodesLocal = Math.max(maxGeodes(timeLeft - 1, newResources.subtract(geodeRobot), robots),
                    maxGeodesLocal);
            robots.geode -= 1;
        }

        if (currentResources.hasEnough(obsidianRobot) && currentResources.obsidian < (timeLeft * geodeRobot.obsidian)) {
            robots.obsidian += 1;
            maxGeodesLocal = Math.max(maxGeodes(timeLeft - 1, newResources.subtract(obsidianRobot), robots),
                    maxGeodesLocal);
            robots.obsidian -= 1;
        }

        if (currentResources.hasEnough(clayRobot) && currentResources.clay < (timeLeft * obsidianRobot.clay)) {
            robots.clay += 1;
            maxGeodesLocal = Math.max(maxGeodes(timeLeft - 1, newResources.subtract(clayRobot), robots),
                    maxGeodesLocal);
            robots.clay -= 1;
        }

        if (currentResources.hasEnough(
                oreRobot) && currentResources.ore + (robots.ore * timeLeft) < timeLeft * maxOreRequired) {
            robots.ore += 1;
            maxGeodesLocal = Math.max(maxGeodes(timeLeft - 1, newResources.subtract(oreRobot), robots), maxGeodesLocal);
            robots.ore -= 1;
        }
        maxGeodesLocal = Math.max(maxGeodesLocal, maxGeodes(timeLeft - 1, newResources, robots));

        memo.put(key, maxGeodesLocal);
        return maxGeodesLocal;
    }

    int getLargestNumberOfGeodes(int minutes) {
        Resources resources = new Resources();
        Resources robots = new Resources(1, 0, 0, 0);

        maxGeodes = 0;
        maxOreRequired = Math.max(Math.max(obsidianRobot.ore, clayRobot.ore), geodeRobot.ore);
        memo = new HashMap<>();

        maxGeodes(minutes, resources, robots);
        return maxGeodes;
    }

    int getQualityLevel(int minutes) {
        getLargestNumberOfGeodes(minutes);
        return id * maxGeodes;
    }

    @Override
    public String toString() {
        return "Blueprint{" +
                "num=" + id +
                "\noreRobot=" + oreRobot +
                "\nclayRobot=" + clayRobot +
                "\nobsidianRobot=" + obsidianRobot +
                "\ngeodeRobot=" + geodeRobot +
                '}';
    }
}

public class Main extends Solution {
    public Main() {
        super(19);
    }


    public static void main(String[] args) {
        solve(Main.class);
    }

    public String partOne() {

        int result = 0;
        int minutes = 24;

        for (var line : readInputLines()) {
            var blueprint = new Blueprint(line);
            System.out.println(blueprint);
            System.out.println();
            result += blueprint.getQualityLevel(minutes);

        }
        return String.valueOf(result);
    }

    public String partTwo() {

        int result = 1;
        int minutes = 32;
        for (var line : readInputLines()) {

            var blueprint = new Blueprint(line);
            System.out.println(blueprint);
            System.out.println();
            if (blueprint.id > 3) {
                continue;
            }
            result *= blueprint.getLargestNumberOfGeodes(minutes);

        }
        return String.valueOf(result);
    }

}
