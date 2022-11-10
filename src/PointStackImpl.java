import java.io.PrintStream;
import java.util.NoSuchElementException;

public class PointStackImpl implements PointStack {

    private List<Point> list;

    public PointStackImpl() {
        this("PointStack");
    }

    public PointStackImpl(String name) {
        list = new List<>(name);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void push(Point item) {
        list.insert(item);
    }

    public Point pop() throws NoSuchElementException {
        if (list.isEmpty())
            throw new NoSuchElementException(list.getName());

        return list.remove();
    }

    public Point peek() throws NoSuchElementException {
        if (list.isEmpty())
            throw new NoSuchElementException(list.getName());

        return list.getFirst();
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