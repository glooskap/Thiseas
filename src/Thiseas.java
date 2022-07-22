import java.io.PrintStream;

public class Thiseas {
	
	private static final PrintStream ps = System.out;

	public static void main(String[] args) {

		if (args.length != 1) {
			ps.println("No arguements provided.\n"
					+ "Please read README file for input instructions.");
			return;
		}
	
		Labyrinth Maze = new Labyrinth(ps);
		ps.println(Maze.solve(args[0]) ? "White sails should be risen!" : "Black sails should be risen!");
	}
}