package org.kromash.day20;

import org.kromash.common.Solution;

import java.util.ArrayList;

public class Main extends Solution {
    public Main() {
        super(20);
    }

    public static void main(String[] args) {
        new Main().solve();
    }

    public String partOne() {

        ArrayList<Long> numbers = new ArrayList<>();
        for (var line : readInputLines()) {
            numbers.add(Long.parseLong(line));
        }

        var mixer = new MixerLinkedList(numbers);
        mixer.mix();
        return String.valueOf(mixer.getValue());
    }

    public String partTwo() {

        long decryptionKey = 811589153L;
        ArrayList<Long> numbers = new ArrayList<>();
        for (var line : readInputLines()) {
            numbers.add(Long.parseLong(line) * decryptionKey);
        }

        var mixer = new MixerLinkedList(numbers);

        for (int i = 1; i <= 10; i++) {
            mixer.mix();
        }

        return String.valueOf(mixer.getValue());
    }

}
