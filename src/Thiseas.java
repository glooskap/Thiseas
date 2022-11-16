import exception.DrawException;

import java.io.PrintStream;

public class Thiseas {
	
	private static final PrintStream ps = System.out;

	public static void main(String[] args) {

		if (args.length != 1) {
			ps.println("No input provided.\r\n"
					+ "Please consult README file for input instructions.");
			return;
		}
	
		Labyrinth Maze = new Labyrinth(ps);
		try {
			ps.println(Maze.solve(args[0]) ? "White sails should be risen!" : "Black sails should be risen!");
		} catch(DrawException e) {
			e.printStackTrace(ps);
			ps.println("\r\nPlease consult README file for input instructions.");
		} catch(Exception e) {
			e.printStackTrace(ps);
		}
	}
}