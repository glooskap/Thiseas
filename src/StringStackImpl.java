import java.io.PrintStream;
import java.util.NoSuchElementException;

public class StringStackImpl implements StringStack{

	private List<String> list;
	
	public StringStackImpl() {
		this("StringStack");
	}
	
	public StringStackImpl(String name) {
		list = new List<String>(name);
	}
	
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	public void push(String item) {
		list.insert(item);
	}
	
	public String pop() throws NoSuchElementException {
		if (list.isEmpty())
			throw new NoSuchElementException(list.getName());
			
		return list.remove();
	}
	
	public String peek() throws NoSuchElementException {
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