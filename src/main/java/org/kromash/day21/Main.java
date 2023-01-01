package org.kromash.day21;

import org.kromash.common.Solution;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main extends Solution {
    public Main() {
        super(21);
    }

    public static void main(String[] args) {
        new Main().solve();
    }

    public String partOne() {
        OperationTree tree = new OperationTree();

        for (var line : readInputLines()) {

            tree.addNode(line);

        }

        return String.valueOf(tree.getValue("root"));
    }

    public String partTwo() {

        OperationTree tree = new OperationTree();

        for (var line : readInputLines()) {

            tree.addNode(line);

        }

        return String.valueOf(tree.whatToYell());
    }

    record Node(String name, String left, String right, String operation, Long value) {
    }

    class OperationTree {
        Map<String, Node> map;
        Map<String, Boolean> hasHumanMap;
        Pattern LINE_PATTERN = Pattern.compile("([a-z]+): (.*)");
        Pattern OPERATION_PATTERN = Pattern.compile("([a-z]+) ([*+-/]+) ([a-z]+)");

        OperationTree() {
            map = new HashMap<>();
            hasHumanMap = new HashMap<>();
        }

        void addNode(String line) {
            Matcher matcher = LINE_PATTERN.matcher(line);
            Node node = null;
            if (matcher.find()) {
                String name = matcher.group(1);
                String operation = matcher.group(2);
                try {
                    long value = Long.parseLong(operation);
                    node = new Node(name, null, null, null, value);

                } catch (NumberFormatException nfe) {
                    Matcher operationMatcher = OPERATION_PATTERN.matcher(operation);

                    if (operationMatcher.find()) {
                        node = new Node(name, operationMatcher.group(1), operationMatcher.group(3),
                            operationMatcher.group(2), null);
                    }
                }
            }
            assert node != null;
            map.put(node.name, node);
        }

        long getValue(String nodeId) {
            Node node = map.get(nodeId);
            if (node.value != null) {
                return node.value;
            }

            long left = getValue(node.left);
            long right = getValue(node.right);

            switch (node.operation) {
                case "+":
                    return left + right;
                case "*":
                    return left * right;
                case "/":
                    return left / right;
                case "-":
                    return left - right;
            }
            throw new RuntimeException("Invalid operation");
        }

        private boolean hasHuman(String nodeName) {

            if (hasHumanMap.containsKey(nodeName)) {
                return hasHumanMap.get(nodeName);
            }
            if (isHuman(nodeName)) {
                return true;
            }
            Node node = map.get(nodeName);
            if (node.value != null) {
                return false;
            }

            boolean value = hasHuman(node.left) || hasHuman(node.right);

            hasHumanMap.put(nodeName, value);

            return value;
        }

        boolean isHuman(String nodeName) {
            return nodeName.equals("humn");
        }
        Long getValueOrNull(String nodeName) {
            if(hasHuman(nodeName)) {
                return null;
            }

            return getValue(nodeName);
        }
        long whatToYell(String nodeName, long needed) {
            if (isHuman(nodeName)) {
                return needed;
            }
            Node node = map.get(nodeName);
            Long left = getValueOrNull(node.left);
            Long right = getValueOrNull(node.right);
            System.out.println(left + " " + node.operation + " " + right + " = " + needed);

            switch (node.operation) {
                case "+":
                    if (left == null) {
                        return whatToYell(node.left, needed - right);
                    } else {
                        return whatToYell(node.right, needed - left);
                    }
                case "*":
                    if (left == null) {
                        return whatToYell(node.left, needed / right);
                    } else {
                        return whatToYell(node.right, needed / left);
                    }
                case "/":
                    if (left == null) {
                        return whatToYell(node.left, needed * right);
                    } else {
                        return whatToYell(node.right, left / needed);
                    }
                case "-":
                    if (left == null) {
                        return whatToYell(node.left, needed + right);
                    } else {
                        return whatToYell(node.right, left - needed);
                    }
                    //return left - right;
            }
            throw new RuntimeException("Invalid operation");
        }
        long whatToYell() {
            Node root = map.get("root");
            Long left = getValueOrNull(root.left);
            Long right = getValueOrNull(root.right);

            if (left == null) {
                return whatToYell(root.left, right);
            } else {
                return whatToYell(root.right, left);
            }
        }
    }
}
