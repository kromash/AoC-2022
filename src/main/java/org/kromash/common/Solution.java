package org.kromash.common;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import java.util.List;
import java.util.stream.Stream;

public abstract class Solution implements SolutionI {
    static Injector injector;
    int day;
    @Inject
    InputReader inputReader;

    public Solution(int day) {
        this.day = day;
    }

    protected static void initInjector() {
        injector = Guice.createInjector(new BasicModule());
    }

    public static <T extends Solution> void solve(Class<T> cls) {
        initInjector();
        injector.getInstance(cls).solve();
    }

    public abstract String partOne();

    public abstract String partTwo();

    public void solve() {
        System.out.printf("Day %d part one answer:\n%s\n", day, partOne());
        System.out.printf("Day %d part two answer:\n%s\n", day, partTwo());
    }

    public List<String> readInputLines() {
        return inputReader.readInputLines(day);
    }

    public Stream<String> getInputStream() {
        return readInputLines().stream();
    }

}
