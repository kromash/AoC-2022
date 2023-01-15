package org.kromash.day07;

import org.kromash.common.Solution;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Main extends Solution {
    public Main() {
        super(7);
    }


    public static void main(String[] args) {
        solve(Main.class);
    }

    public String partOne() {

        int result = 0;

        String operation = "";
        Stack<String> dirStack = new Stack<>();
        Stack<Long> sizeStack = new Stack<>();
        for (var line : readInputLines()) {
            if (line.startsWith("$")) {
                operation = line.substring(2);

                if (operation.startsWith("cd")) {
                    String dir = line.substring(5);
                    if (dir.equals("..")) {
                        dirStack.pop();
                        long size = sizeStack.pop();
                        if (size < 100000) {
                            result += size;
                        }
                        sizeStack.push(sizeStack.pop() + size);
                    } else {
                        dirStack.add(dir);
                        sizeStack.add(0L);
                    }

                    continue;
                }
                continue;

            }
            if (operation.equals("ls")) {
                String[] pair = line.split(" ");
                if (pair[0].equals("dir"))
                    continue;
                long size = Long.parseLong(pair[0]);

                sizeStack.push(sizeStack.pop() + size);


            }

        }


        while (!sizeStack.empty()) {
            long size = sizeStack.pop();
            if (size < 100000) {
                result += size;
            }
            if (!sizeStack.empty()) {
                sizeStack.push(sizeStack.pop() + size);
            }
        }

        return String.valueOf(result);
    }

    public String partTwo() {

        long MAX_SPACE_DEL = 30000000L;

        String operation = "";
        Stack<String> dirStack = new Stack<>();
        Stack<Long> sizeStack = new Stack<>();
        long totalSpace = 0;

        List<Long> dirSizes = new ArrayList<>();

        for (var line : readInputLines()) {
            if (line.startsWith("$")) {
                operation = line.substring(2);

                if (operation.startsWith("cd")) {
                    String dir = line.substring(5);
                    if (dir.equals("..")) {
                        dirStack.pop();
                        long size = sizeStack.pop();

                        dirSizes.add(size);

                        sizeStack.push(sizeStack.pop() + size);
                    } else {
                        dirStack.add(dir);
                        sizeStack.add(0L);
                    }

                    continue;
                }
                continue;

            }
            if (operation.equals("ls")) {
                String[] pair = line.split(" ");
                if (pair[0].equals("dir"))
                    continue;
                long size = Long.parseLong(pair[0]);
                totalSpace += size;

                sizeStack.push(sizeStack.pop() + size);


            }

        }

        while (!sizeStack.empty()) {
            long size = sizeStack.pop();
            dirSizes.add(size);
            if (!sizeStack.empty()) {
                sizeStack.push(sizeStack.pop() + size);
            }
        }

        long minDir = 3 * MAX_SPACE_DEL;
        long freeSpace = 70000000 - totalSpace;
        for (long size : dirSizes) {
            if (freeSpace + size > MAX_SPACE_DEL) {
                if (size < minDir) {
                    minDir = size;
                }
            }
        }

        return String.valueOf(minDir);
    }


}
