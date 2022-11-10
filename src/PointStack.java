import java.io.PrintStream;
import java.util.NoSuchElementException;

/**
 * Defines the methods for a Stack that handles a 2D point
 */
public interface PointStack {

    /**
     * @return true if the stack is empty
     */
    boolean isEmpty();

    /**
     * Push a Point to the stack
     */
    void push(Point item);

    /**
     * remove and return the item on the top of the stack
     * @return the item on the top of the stack
     * @throws NoSuchElementException if the stack is empty
     */
    Point pop() throws NoSuchElementException;

    /**
     * return without removing the item on the top of the stack
     * @return the item on the top of the stack
     * @throws NoSuchElementException if the stack is empty
     */
    Point peek() throws NoSuchElementException;

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
