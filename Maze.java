import java.util.*;
import java.io.*;

public class Maze{
  private char[][] maze;
  private boolean animate;
  private int rows, cols;
  private String tempMaze;

  public Maze(String filename) throws FileNotFoundException{
    rows = 0;
    cols = 0;
    tempMaze = "";

    String line = "";
    File text = new File("Maze1.txt");
    Scanner inf = new Scanner(text);

    while(inf.hasNextLine()){
      line = inf.nextLine();
      maze += line;
      System.out.println(line);
      rows++;
    }

    rows = rows - 1;
    cols = line.length();

    String[][] board = new String [rows][cols];
    int i = 0;

    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++, i++) {
        board[r][c] = "" + maze.charAt(i);
      }
    }
  }

  public String toString() {
    String display = "";
    for (int r = 0; r < rows; row++) {
      for (int c = 0; c < cols; c++) {
        display += board[r][c];
      }
      display += "\n";
    }
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
