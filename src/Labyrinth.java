import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class Labyrinth {
	
	private final PrintStream ps;
	private File file;
	private BufferedReader reader;
	private int length;
	private int width;
	private int[][] maze;
	private String entrance;
	private int entranceX;
	private int entranceY;

	public Labyrinth() { this(System.out); }
	
	public Labyrinth(PrintStream stream) {
		ps = stream;
	}

	private void loadFile(String path) {
		try {
			file = new File(path);
		} catch (NullPointerException e) {
			e.printStackTrace(ps);
			ps.println("Error! File not found.");
		}
	}

	private void openFile() {
		try {
			reader =  new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		} catch (FileNotFoundException e) {
			e.printStackTrace(ps);
			ps.println("Error opening file!");
		}
	}

	private void closeFile() {
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace(ps);
			ps.println("Error closing file!");
		}
	}

	private boolean drawMaze() throws DrawException {
		int counter = 0;
		String line;

		try {
			line = reader.readLine();
			counter++;

			String[] lineSplit = line.trim().split(" ");
			if (lineSplit.length != 2) {
				throw new DrawException("1st line should contain labyrinth' dimensions");
			}
			length = Integer.parseInt(lineSplit[0]);
			width = Integer.parseInt(lineSplit[1]);

			line = reader.readLine();
			counter++;
			lineSplit = line.trim().split(" ");
			if (lineSplit.length != 2) {
				throw new DrawException("2nd line should contain entrance coordinates");
			}
			entranceX = Integer.parseInt(lineSplit[0]);
			entranceY = Integer.parseInt(lineSplit[1]);
			entrance = entranceX++ +","+ entranceY++;

			line = reader.readLine();
			counter++;

			//append dimensions
			int[][] paths = new int[length+2][width+2];//?

			int i=1, j;
			while( line != null &&  i <= length) {

				lineSplit = line.trim().split(" ");

				if ( lineSplit.length > width ) {
					throw new DrawException("Wrong dimensions given");
				} else {
					j = 1;
					for ( String s: lineSplit) {
						if (i==entranceX && j==entranceY)
							paths[i][j] = 0;//open the entrance
						else
							paths[i][j] = Integer.parseInt(s);
						j++;
					}
				}
				line = reader.readLine();
				counter++;
				i++;
			}

			//surround the map with 2's
			for ( i =0; i<=width+1; i++) {
				paths[length+1][i] = 2;
				paths[0][i] = 2;
			}
			for ( i =0; i<=length+1; i++) {
				paths[i][width+1] = 2;
				paths[i][0] = 2;
			}
			maze = paths;

		} catch (IOException e) {
			e.printStackTrace(ps);
			throw new DrawException("Line " + counter + ": Sudden end.", e.getMessage());
		}
		return true;
	}

	private boolean escapeMaze() throws DrawException {

		ps.println("entry: "+entrance);
		if (entranceX>=length || entranceY>=width || entranceX<0 || entranceY<0) {
			throw new DrawException("Entrance given is out of bounds!");
		}
		if (maze[entranceX][entranceY]!=0) {
			throw new DrawException("Entrance given was closed!");
		}
		ps.println("Thiseas has entered the labyrinth!");

		boolean sentinel = true;	// Thiseas still has a chance to escape
		boolean through = false;	// Thiseas made it
		int posX = entranceX;
		int posY = entranceY;

		PointStackImpl route = new PointStackImpl();
		route.push(new Point(posX, posY));

		while (!through && sentinel) {
			if (maze[posX+1][posY]==0) {
				maze[++posX][posY] = 2;
				route.push(new Point(posX, posY));
			} else if ( maze[posX][posY-1] == 0) {
				maze[posX][--posY] = 2;
				route.push(new Point(posX, posY));
			} else if ( maze[posX][posY+1] == 0) {
				maze[posX][++posY] = 2;
				route.push(new Point(posX, posY));
			} else if ( maze[posX-1][posY] == 0) {
				maze[--posX][posY] = 2;
				route.push(new Point(posX, posY));
			} else {
				if (!route.isEmpty()) //Thiseas reached a dead end and must go back
					route.pop();
				else //Thiseas has gone through all possible pathways
					sentinel = false;
			}

			if ( posX == 1 || posX == length || posY == 1 || posY == width )
				through = true;
			else if (sentinel) {
				posX = route.peek().getX();
				posY = route.peek().getY();
			}
		}

		if (!sentinel) {
			ps.println("Thiseas couldn't find any exits!");
			return false;
		}
		ps.println("Thiseas exited through: " + route.peek());
		return true;
	}

	public boolean solve(String path) throws DrawException {
		boolean ans = false;

		loadFile(path);
		openFile();

		if (drawMaze()) {
			ps.println("Thiseas has reached the labyrinth!");
			ans = escapeMaze();
		}

		closeFile();
		return ans;
	}
}