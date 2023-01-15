package org.kromash.day04;

import org.kromash.common.Solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main extends Solution {
    public Main() {
        super(4);
    }

    public static void main(String[] args) {
        solve(Main.class);
    }

    public String partOne() {
        int contains = 0;

        for (Section[] pair : readInput()) {

            if (pair[0].contains(pair[1]) || pair[1].contains(pair[0])) {
                contains += 1;
            }
        }

        return String.valueOf(contains);
    }

    public String partTwo() {

        int overlaps = 0;

        for (Section[] pair : readInput()) {

            if (pair[0].overlaps(pair[1]) || pair[1].overlaps(pair[0])) {
                overlaps += 1;
            }
        }

        return String.valueOf(overlaps);
    }

    private List<Section[]> readInput() {
        List<Section[]> sections = new ArrayList<>();
        for (var line : readInputLines()) {
            String[] pair = line.split(",");

            sections.add(Arrays.stream(pair).map(Section::fromString).toArray(Section[]::new));
        }
        return sections;
    }

    record Section(int start, int end) {
        public static Section fromString(String section) {
            String[] bound = section.split("-");
            return new Section(Integer.parseInt(bound[0]), Integer.parseInt(bound[1]));
        }

        public boolean contains(Section section) {
            return this.start <= section.start && this.end >= section.end;
        }

        public boolean overlaps(Section section) {
            return (this.start <= section.start && section.start <= this.end) ||
                    (this.start <= section.end && section.end <= this.end);
        }
    }


}
