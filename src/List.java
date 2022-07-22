import java.io.PrintStream;

public class List<T> {
	
	private Node<T> first;
	private Node<T> last;
	private String name;
	private int N;
	
	public List() {
		this("list");
	}
	
	public List(String a) {
		name = a;
		first = last = null;
		N = 0;
	}
	
	public T getFirst() {
		return first.data;
	}
	
	public T getLast() {
		return last.data;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void insert(T Item) {
		
		Node<T> node = new Node<>(Item);
		if ( isEmpty() )
			first = last = node;
		else {
			node.next = first;
			first = node;
		}
		N++;
		
	}
	
	public T remove() {
		
		T removedItem = last.data;
		
		if ( first == last )
			first = last = null;
		else
			first = first.next;
		
		N--;
		return removedItem;
		
	}
	
	public void print(PrintStream stream) {
		
		if (isEmpty()) {
			stream.printf("Empty %s\n", name);
			return;
		}
			
		stream.printf("The %s is: ", name);
		Node<T> current = first;

		while (current != null) {
			stream.printf("%s ", current.data);
			current = current.next;
		}

		stream.println("\n");
		
	}
	
	public String getName() {
		return name;
	}
	
	public int size() {
		return N;
	}
	
	public boolean isEmpty() {
		return first == null;
	}
	
}
