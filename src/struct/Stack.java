package struct;

import java.io.PrintStream;
import java.util.NoSuchElementException;

public interface Stack<T> {

    /**
     * @return true if the stack is empty
     */
    boolean isEmpty();

    /**
     * Push an object to the stack
     */
    void push(T item);

    /**
     * remove and return the object on the top of the stack
     * @return the item on the top of the stack
     * @throws NoSuchElementException if the stack is empty
     */
    T pop() throws NoSuchElementException;

    /**
     * return without removing the object on the top of the stack
     * @return the item on the top of the stack
     * @throws NoSuchElementException if the stack is empty
     */
    T peek() throws NoSuchElementException;

    /**
     * print the contents of the stack using the provided stream <br>
     * e.g. printStack(System.out) <br>
     * traversing top to bottom
     */
    void printStack(PrintStream stream);

    /**
     * return the size of the stack, 0 if empty
     * @return the number of items currently in the stack
     */
    int size();

}
