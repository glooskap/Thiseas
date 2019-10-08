import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class Labyrinth {
	
	private PrintStream ps;
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
			
			entrance = (Integer.parseInt(lt[0])+1) + "," + (Integer.parseInt(lt[1])+1); //apothikeusi sintetagmenon isodou
																						//gia metepita elegxo
			line = reader.readLine();
			counter++;
			
			int i, j;
			i = 1;
			
			String[][] paths = new String[length+2][width+2];  //kuklos apo akiri timi(2) gia dieukolinsi se elegxous
			
			while( line != null &&  i <= length) {
				
				lt = line.trim().split(" ");
				
				if ( lt.length > width ) {
					ps.println("Error! Wrong dimensions given"); //parapano stoixeia apo auta pou dothikan
					return false;								//stin proti grammi tou arxeiou
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
			
			for ( i =0; i<=width+1; i++) {			//fill me 2aria
				paths[length+1][i] = "2";
				paths[0][i] = "2";
			}
			for ( i =0; i<=length+1; i++) {
				paths[i][width+1] = "2";
				paths[i][0] = "2";
			}
			maze = paths;

		} catch (IOException e) {
			ps.println("Error! Line " + counter + ": Sudden end.");
		}
		
		return true;

	}
	
	private void escape() {
		
		
		if ( entrance == null )
			ps.println("Error! No valid entrance given");
		else {
			int y = entrance.indexOf(',');
			int x = Integer.parseInt(entrance.substring(0,y));
			y = Integer.parseInt(entrance.substring(y+1));		//x,y sintetagmenes eisodou
			
			ps.println("entry:"+x+","+y);
			if ( x >= length || y >= width || x < 0 || y < 0 )	//out of bounds eisodos
				ps.println("Error! No valid entrance given");
			
			else if ( !maze[x][y].equals("E"))
				ps.println("Entrance given was closed!");		//i sintetagmenes pou dothikan sti deuteri grammi
																//tou arxeiou den antistixoun se E
			else {
				ps.println("Thiseas has entered the labyrinth!");
				
				Boolean sentinel = true;	// iparxei akoma elpida eksodou
				Boolean through = false;	// o Thiseas ta katafere
				int posX = x;
				int posY = y;
				
				StringStackImpl laby = new StringStackImpl();
				laby.push(posX+","+posY);
				
				while ( !through && sentinel) {
					
					if ( maze[posX+1][posY].equals("0")) { //elegxos kato
						maze[++posX][posY] = "2";
						laby.push(posX+","+posY);
						
					} else if ( maze[posX][posY-1].equals("0")) {	//elegxos aristera
						maze[posX][--posY] = "2";
						laby.push(posX+","+posY);
						
					} else if ( maze[posX][posY+1].equals("0")) {	//elegxos deksia
						maze[posX][++posY] = "2";
						laby.push(posX+","+posY);
						
					} else if ( maze[posX-1][posY].equals("0")) {	//elegxos pano
						maze[--posX][posY] = "2";
						laby.push(posX+","+posY);
						
					} else {
						if (!laby.isEmpty())	//adieksodo
							laby.pop();
						else					//kamia elpida
							sentinel = false;
					}
					
					if ( posX == 1 || posX == length || posY == 1 || posY == width )
						through = true;
					else if (sentinel) {
						posY = laby.peek().indexOf(',');
						posX = Integer.parseInt(laby.peek().substring(0,posY));
						posY = Integer.parseInt(laby.peek().substring(posY+1));
					}
					
					//laby.printStack(ps);
				}
				
				if (sentinel)
					ps.println("Thiseas exited through: " + laby.peek());// + posX + "," + posY );
				else
					ps.println("Thiseas couldn't find any exits!");
			}
		}
		
		
		
	}

	public void solve(String path) {
		
		loadFile(path);
		
		openFile();
		
		if (drawMaze()) {
			ps.println("Thiseas has reached the labyrinth!");
		
			escape();
			
			closeFile();
		}
		
	}
	

}