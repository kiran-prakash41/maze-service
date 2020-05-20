package uk.gov.dwp.maze.model;

public class Grid {
  private int row;
  private int col;

  public Grid(int row, int col) {
    this.row=row;
    this.col=col;
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  public String toString() {
    return "("+row+","+col+")";
  }

  public boolean equals(Object o) {
    if (o instanceof Grid) {
      Grid other = (Grid)o;
      return (row==other.row) && (col==other.col);
    }
    return false;
  }

}
