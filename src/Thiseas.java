import java.io.PrintStream;

public class Thiseas {
	
	private static PrintStream ps = System.out;

	public static void main(String[] args) {
	
		Labyrinth Maze = new Labyrinth(ps);
		Maze.solve(args[0]);
	
	}
}