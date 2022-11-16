import exception.DrawException;
import util.Point;
import util.PointStack;

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
	private Point entrance;
	private Point exit;

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

	private void drawMaze() throws DrawException {
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

			entrance = new Point();
			entrance.setX(Integer.parseInt(lineSplit[0])+1);
			entrance.setY(Integer.parseInt(lineSplit[1])+1);

			line = reader.readLine();
			counter++;

			//append dimensions
			int[][] paths = new int[length+2][width+2];

			int i=1, j;
			while( line != null &&  i <= length) {

				lineSplit = line.trim().split(" ");

				if ( lineSplit.length > width ) {
					throw new DrawException("Wrong dimensions given");
				} else {
					j = 1;
					for ( String s: lineSplit) {
						if (i==entrance.getX() && j==entrance.getY())
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
			//2 indicates an impassable point
			for ( i =0; i<=width+1; i++) {
				paths[length+1][i] = 2;
				paths[0][i] = 2;
			}
			for ( i =0; i<=length+1; i++) {
				paths[i][width+1] = 2;
				paths[i][0] = 2;
			}
			maze = paths;

		} catch (NumberFormatException | IOException e) {
			e.printStackTrace(ps);
			throw new DrawException("Line " + counter, e.getMessage());
		}
	}

	private void escapeMaze() throws DrawException {

		ps.println("entry: " + entrance);
		int posX = entrance.getX();
		int posY = entrance.getY();
		if (posX>=length || posY>=width || posX<0 || posY<0) {
			throw new DrawException("Entrance given is out of bounds!");
		}
		if (maze[posX][posY]!=0) {
			throw new DrawException("Entrance given was closed!");
		}
		ps.println("Thiseas has entered the labyrinth!");

		boolean sentinel = true;	//Thiseas still has a chance to escape

		PointStack route = new PointStack();
		route.push(new Point(posX, posY));
		maze[posX][posY] = 2;	//cant escape from the entrance

		while (exit==null && sentinel) {
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
				if (!route.isEmpty()) {
					ps.println("Thiseas reached a dead end at " + route.pop());
				} else {
					sentinel = false;
					ps.println("Thiseas couldn't find any exits!");
				}
			}

			if ( posX == 1 || posX == length || posY == 1 || posY == width ) {
				exit = route.pop();
			} else if (sentinel) {
				posX = route.peek().getX();
				posY = route.peek().getY();
			}
		}
	}

	public boolean solve(String path) throws DrawException {
		boolean ans = false;

		loadFile(path);
		openFile();

		drawMaze();
		ps.println("Thiseas has reached the labyrinth!");
		escapeMaze();
		if (exit!=null) {
			ps.println("Thiseas exited through: " + exit);
			ans = true;
		}

		closeFile();
		return ans;
	}
}