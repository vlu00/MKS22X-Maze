import java.util.*;
import java.io.*;

public class Maze{
  private char[][] maze;
  private boolean animate;
  private int rows, cols, startRow, startCol; //startRow and startCol are coordinates of S
  private String tempMaze; //To place characters into maze
  int[] changeRow = new int[] {-1, 0, 0, 1}; //possible row changes
  int[] changeCol = new int[] {0, 1, -1, 0}; //corresponding col changes

  public Maze(String filename) throws FileNotFoundException{
    animate = false;
    tempMaze = "";

    String line = ""; //Each individual line of maze txt file
    File text = new File(filename); //scans maze txt file in
    Scanner inf = new Scanner(text);

    while(inf.hasNextLine()){
      line = inf.nextLine();
      tempMaze += line; //long string of characters that make up the maze
      rows++; //each line of the maze is another row
    }

    cols = line.length(); //length of a line is a length of a row

    maze = new char [rows][cols]; //initialize 2D maze
    int i = 0; //index of tempMaze

    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++, i++) {
        maze[r][c] = tempMaze.charAt(i); //Fills in 2D maze
        if (tempMaze.charAt(i) == 'S') { //records coordinates of S
          startRow = r;
          startCol = c;
        }
      }
    }
  }

  public int solve() {
    maze[startRow][startCol] = '@'; //S starts out as @
    int s = solve(startRow, startCol, 0);
    if (s == -1) {
      maze[startRow][startCol] = '.'; //if no solution S becomes .
    }
    return s;
  }

  public int solve(int row, int col, int solution) {
    if(animate){
      clearTerminal();
      System.out.println(this);
      wait(20);
    }
    if (maze[row][col] == 'E') { //if you've reached the end, return path length
      return solution;
    }
    else {
      for (int i = 0; i < 4; i++) { //check all 4 moves
        char next = maze[row+changeRow[i]][col+changeCol[i]];
        if (next == ' ') { //if you can move there
          maze[row+changeRow[i]][col+changeCol[i]] = '@'; //add it to the path
          int s = solve(row+changeRow[i], col+changeCol[i], solution+1); //variable s prevents solve from repeating on the same square
          if (s != -1) { //if the square is part of the solution
            return s; //return length of path
          }
          else {
            maze[row+changeRow[i]][col+changeCol[i]] = '.'; //else mark it as not part of path
          }
        }
        if (next == 'E') { //if next one is E
          return solve(row+changeRow[i], col+changeCol[i], solution+1); //don't replace the E with a @ but continue until you're on E
        }
      }
    }
    return -1;
  }

  public String toString() {
    String display = "";
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        display += maze[r][c];
      }
      display += "\n";
    }
    return display;
  }

  private void wait(int millis){
    try {
      Thread.sleep(millis);
    }
    catch (InterruptedException e) {
    }
  }


  public void setAnimate(boolean b){
    animate = b;
  }


  public void clearTerminal(){
    System.out.println("\033[2J\033[1;1H");
  }
}
