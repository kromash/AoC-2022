package org.kromash.day20;

import java.util.ArrayList;
import java.util.List;

class Node<T> {
    T element;
    Node<T> next;
    Node<T> prev;

    public Node(T element, Node<T> next, Node<T> prev) {
        this.element = element;
        this.next = next;
        this.prev = prev;
    }

    @Override
    public String toString() {
        return "Node{" +
            "element=" + element +
            ", next=" + (next == null ? "null" : next.element) +
            ", prev=" + (prev == null ? "null" : prev.element) +
            '}';
    }
}

class CircularList<T> {
    Node<T> head;
    private int size;

    CircularList() {
        size = 0;
    }

    public void addLast(T element) {
        Node<T> tmp = new Node<>(element, head, null);
        if (head != null) {
            tmp.prev = head.prev;
            head.prev.next = tmp;
        }

        if (head == null) {
            head = tmp;
            head.next = tmp;
        }

        head.prev = tmp;
        size++;
    }

    int getSize() {
        return size;
    }

    void moveRight(Node<T> node) {
        Node<T> tmp = node.next;

        tmp.next.prev = node;

        node.next = tmp.next;
        tmp.prev = node.prev;
        node.prev.next = tmp;
        tmp.next = node;
        node.prev = tmp;
    }

    void moveLeft(Node<T> node) {
        moveRight(node.prev);
    }

    List<Node<T>> toNodeList() {
        List<Node<T>> result = new ArrayList<>();
        if (head == null) {
            return result;
        }
        Node<T> node = head;

        for (int i = 0; i < size; i++) {
            result.add(node);
            node = node.next;
        }

        return result;
    }

    List<T> toList() {
        List<T> result = new ArrayList<>();
        if (head == null) {
            return result;
        }
        Node<T> node = head;

        result.add(node.element);
        node = node.next;
        while (node != head) {
            result.add(node.element);
            node = node.next;
        }
        return result;
    }
}

public class MixerLinkedList {
    CircularList<Long> list;
    List<Node<Long>> initialNodes;
    List<Integer> idxToAdd = List.of(1000, 2000, 3000);

    MixerLinkedList(ArrayList<Long> numbers) {
        list = new CircularList<>();
        for (var v : numbers) {
            list.addLast(v);
        }
        initialNodes = list.toNodeList();
    }

    public void mix() {

        for (var node : initialNodes) {
            long steps;
            if (node.element > 0) {
                steps = node.element % (long) (list.getSize() - 1);
                for (int i = 0; i < steps; i++) {
                    list.moveRight(node);
                }
            } else if (node.element < 0) {
                steps = Math.abs(node.element) % (long) (list.getSize() - 1);
                for (int i = 0; i < steps; i++) {
                    list.moveLeft(node);
                }
            }
        }
    }

    public long getValue() {
        List<Long> mixed = list.toList();

        long result = 0;
        int zeroIdx = mixed.indexOf(0L);
        for (int idx : idxToAdd) {
            long value = mixed.get((zeroIdx + idx) % list.getSize());
            result += value;
        }

        return result;
    }
}
