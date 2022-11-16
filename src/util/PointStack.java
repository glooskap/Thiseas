package util;

import struct.List;
import struct.Stack;

import java.io.PrintStream;
import java.util.NoSuchElementException;

/**
 * Implements a Stack to handle 2D points
 */
public class PointStack implements Stack<Point> {

    private List<Point> list;

    public PointStack() {
        this("PointStack");
    }

    public PointStack(String name) {
        list = new List<>(name);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void push(Point item) {
        list.insertEnd(item);
    }

    public Point pop() throws NoSuchElementException {
        if (list.isEmpty())
            throw new NoSuchElementException(list.getName());

        return list.removeEnd();
    }

    public Point peek() throws NoSuchElementException {
        if (list.isEmpty())
            throw new NoSuchElementException(list.getName());

        return list.getLast();
    }

    public void printStack(PrintStream stream) {
        list.print(stream);
    }

    public int size() {
        return list.size();
    }

    public String getName() {
        return list.getName();
    }

}