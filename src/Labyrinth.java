import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Labyrinth {
	
	private final PrintStream ps;
	private File file;
	private BufferedReader reader;
	private int length;
	private int width;
	private String[][] maze;
	private String entrance;

	public Labyrinth() { this(System.out); }
	
	public Labyrinth(PrintStream stream) {
		ps = stream;
	}

	private void loadFile(String path) {
		try {
			file = new File(path);
		} catch (NullPointerException e) {
			ps.println("Error! File not found.");
		}
	}

	private void openFile() {
		try {
			reader =  new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		} catch (FileNotFoundException e) {
			ps.println("Error opening file!");
		}
	}

	private void closeFile() {
		try {
			reader.close();
		} catch (IOException e) {
			ps.println("Error closing file!");
		}
	}
	
	private Boolean drawMaze() {
		
		int counter = 0;
		String line;

		try {
			line = reader.readLine();
			counter++;
			
			String[] lt;
			lt = line.trim().split(" ");
			length = Integer.parseInt(lt[0]);
			width = Integer.parseInt(lt[1]);
			
			line = reader.readLine();
			counter++;
			lt = line.trim().split(" ");

			try {
				entrance = (Integer.parseInt(lt[0])) + "," + (Integer.parseInt(lt[1]));
			} catch (NumberFormatException e) {
				ps.println("Error! Invalid entrance given");
				return false;
			}

			line = reader.readLine();
			counter++;
			
			int i, j;
			i = 1;

			//append dimensions
			String[][] paths = new String[length+2][width+2];
			
			while( line != null &&  i <= length) {
				
				lt = line.trim().split(" ");
				
				if ( lt.length > width ) {
					ps.println("Error! Wrong dimensions given");
					return false;
				}
				else {
					j = 1;
					for ( String s: lt) {
						paths[i][j] = s;
						j++;
					}
				}
				line = reader.readLine();
				counter++;
				i++;
			}

			//surround the map with 2's
			for ( i =0; i<=width+1; i++) {
				paths[length+1][i] = "2";
				paths[0][i] = "2";
			}
			for ( i =0; i<=length+1; i++) {
				paths[i][width+1] = "2";
				paths[i][0] = "2";
			}
			maze = paths;
			ps.println(Arrays.deepToString(maze));

		} catch (IOException e) {
			ps.println("Error! Line " + counter + ": Sudden end.");
			return false;
		}
		return true;
	}
	
	private boolean escape() {
		
		if ( entrance == null ) {
			ps.println("Error! Invalid entrance given");
			return false;
		}

		int y = entrance.indexOf(',');
		int x = Integer.parseInt(entrance.substring(0,y))+1;
		y = Integer.parseInt(entrance.substring(y+1))+1;

		ps.println("entry:"+x+","+y);
		ps.println(maze[x][y]);
		if ( x >= length || y >= width || x < 0 || y < 0 ) {
			ps.println("Error! Entrance is out of bounds!");
			return false;
		}
		if ( maze[x][y].equals("E")) {
			ps.println("Error! Entrance given was closed!"); //entrance coordinates do not correspond to an E on the map
			return false;
		}

		ps.println("Thiseas has entered the labyrinth!");
				
		boolean sentinel = true;	// Thiseas still has a chance to escape
		boolean through = false;	// Thiseas made it
		int posX = x;
		int posY = y;
				
		StringStackImpl labyStack = new StringStackImpl();
		labyStack.push(posX+","+posY);
				
		while ( !through && sentinel) {
			if ( maze[posX+1][posY].equals("0")) {
				maze[++posX][posY] = "2";
				labyStack.push(posX+","+posY);
			} else if ( maze[posX][posY-1].equals("0")) {
				maze[posX][--posY] = "2";
				labyStack.push(posX+","+posY);
			} else if ( maze[posX][posY+1].equals("0")) {
				maze[posX][++posY] = "2";
				labyStack.push(posX+","+posY);
			} else if ( maze[posX-1][posY].equals("0")) {
				maze[--posX][posY] = "2";
				labyStack.push(posX+","+posY);
			} else {
				if (!labyStack.isEmpty()) //Thiseas reached a dead end
					labyStack.pop();
				else //Thiseas has gone through all possible pathways
					sentinel = false;
			}
					
			if ( posX == 1 || posX == length || posY == 1 || posY == width )
				through = true;
			else if (sentinel) {
				posY = labyStack.peek().indexOf(',');
				posX = Integer.parseInt(labyStack.peek().substring(0,posY));
				posY = Integer.parseInt(labyStack.peek().substring(posY+1));
			}
		}
				
		if (!sentinel) {
			ps.println("Thiseas couldn't find any exits!");
			return false;
		}
		ps.println("Thiseas exited through: " + labyStack.peek());
		return true;
	}

	public boolean solve(String path) {
		boolean ans = false;

		loadFile(path);
		openFile();
		
		if (drawMaze()) {
			ps.println("Thiseas has reached the labyrinth!");
			ans = escape();
		}

		closeFile();
		return ans;
	}
}