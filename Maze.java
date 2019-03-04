import java.util.*;
import java.io.*;

public class Maze{
  private char[][] maze;
  private boolean animate;
  private int rows, cols, startRow, startCol;
  private String tempMaze;
  int[] changeRow = new int[] {-1, 0, 0, 1};
  int[] changeCol = new int[] {0, 1, -1, 0};

  public Maze(String filename) throws FileNotFoundException{
    animate = false;
    rows = 0;
    cols = 0;
    tempMaze = "";

    String line = "";
    File text = new File(filename);
    Scanner inf = new Scanner(text);

    while(inf.hasNextLine()){
      line = inf.nextLine();
      tempMaze += line;
      rows++;
    }

    cols = line.length();

    maze = new char [rows][cols];
    int i = 0;

    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++, i++) {
        maze[r][c] = tempMaze.charAt(i);
        if (tempMaze.charAt(i) == 'S') {
          startRow = r;
          startCol = c;
        }
      }
    }
  }

  public int solve() {
    maze[startRow][startCol] = '@';
    return solve(startRow, startCol, 1);
  }

  public int solve(int row, int col, int solution) {
    if(animate){
      clearTerminal();
      System.out.println(this);
      System.out.println(solution);
      System.out.println("row " + row);
      System.out.println("col" + col);
      wait(500);
    }

    if (maze[row][col] == 'E') {
      return solution + 1;
    }
    else {
      for (int i = 0; i < 4; i++) {
        char next = maze[row+changeRow[i]][col+changeCol[i]];
        if (next == ' ') {
          maze[row+changeRow[i]][col+changeCol[i]] = '@';
          if (solve(row+changeRow[i], col+changeCol[i], solution+1) != -1) {
            int s = solve(row+changeRow[i], col+changeCol[i], solution+1);
            return s;
          }
          else {
            maze[row+changeRow[i]][col+changeCol[i]] = '.';
          }
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

  public static void main(String[]args){
      try{
        Maze f = new Maze("data1.dat");
        System.out.println(f);
        f.setAnimate(true);

        System.out.println(f.solve());
        System.out.println(f);
      }catch(FileNotFoundException e){
        System.out.println("Invalid filename: ");
      }
    }


}
