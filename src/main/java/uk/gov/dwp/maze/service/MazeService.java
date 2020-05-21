package uk.gov.dwp.maze.service;

import uk.gov.dwp.maze.model.Cell;
import uk.gov.dwp.maze.model.Grid;
import uk.gov.dwp.maze.model.Maze;

import java.util.ArrayList;
import java.util.List;

public class MazeService {

  public static final int LEFT = 3;
  public static final int RIGHT = 1;
  public static final int UP = 0;
  public static final int DOWN = 2;
  Maze maze;

  public Maze initMaze(int height, int width){
    if(height <= 0 || width <= 0){
      throw new ArrayIndexOutOfBoundsException("Cannot construct maze for height " + height + " width " + width);
    }
    maze = new Maze();
    Cell[][] cells = new Cell[height][width];
    //initialize array
    for(int h = 0; h< height; h++)
      for(int w = 0; w<width; w++)
        cells[h][w] = new Cell(h, w);

    //set neighbours
    for(int h = 0; h< height; h++){
      for(int w = 0; w< width; w++){
        Cell neighbours[] = new Cell[4];

        neighbours[0] = h-1 >= 0 ? cells[h-1][w] : null;
        neighbours[2] = h+1 < height ? cells[h+1][w] : null;

        neighbours[3] = w-1 >= 0 ? cells[h][w-1] : null;
        neighbours[1] = w+1 < width ? cells[h][w+1] : null;

        cells[h][w].setNeighbours(neighbours);
      }
    }
    maze.setCells(cells);
   return maze;
  }

  public void removeWall(Grid grid, int direction){
    removeWall(grid.getRow(), grid.getCol(),direction);
  }

  private void removeWall(int row, int col, int direction){
    maze.getCell(row, col).removeWall(direction);
  }

  public void visit(Grid grid){
    maze.getCell(grid).setVisit(true);
  }

  public void path(Grid grid){
    maze.getCell(grid).setPath(true);
  }

  public void travelled(Grid grid){
    maze.getCell(grid).setTravelled(true);
  }

  public void markStart(Grid grid){
    maze.getCell(grid).setStart(true);
  }

  public List<Grid> getOpenNeighbours(Grid grid){
    List<Grid> list = new ArrayList<Grid>();

    Cell currentCell = maze.getCell(grid);
    boolean[] currentCellWalls = currentCell.getWalls();
    Cell[] neighbours = currentCell.getNeighbours();

    for(int i=0;i<neighbours.length;i++){
      Cell cell = neighbours[i];
      if(cell != null && !currentCellWalls[i] && !cell.isTravelled() && !cell.isStart()){
        list.addAll(getAllGridsForDirection(grid, i));
      }
    }
    return list;
  }

  private List<Grid> getAllGridsForDirection(Grid grid, int i) {
    int row = grid.getRow();
    int col = grid.getCol();
    List<Grid> list = new ArrayList<Grid>();
    Grid c = null;
    if(i == UP){
      c = new Grid(row-1,col);
    } else if(i == DOWN){
      c = new Grid(row+1, col);
    } else if(i == RIGHT){
      c = new Grid(row,col+1);
    } else if(i == LEFT){
      c = new Grid(row,col-1);
    }

    if(c!= null)
      list.add(list.size(),c);
    return list;
  }

  public List<Grid> getUnvisitedNeighbours(Grid grid){
    List<Grid> list = new ArrayList<Grid>();

    Cell[] neighbours = maze.getCell(grid).getNeighbours();

    for(int i=0;i<neighbours.length;i++){
      if(neighbours[i] != null && ! neighbours[i].isVisit()){
        list.addAll(getAllGridsForDirection(grid, i));
      }
    }
    return list;
  }

  public int getDirection(Grid a, Grid b){
    int x = a.getRow() - b.getRow();
    int y = a.getCol() - b.getCol();
    if(y == 1){
      return MazeService.RIGHT;
    }else if (y == -1){
      return MazeService.LEFT;
    }
    if(x == 1){
      return MazeService.DOWN;
    }else if(x == -1){
      return MazeService.UP;
    }
    return 0;
  }

  public void markEnd(Grid grid) {
    maze.getCell(grid).setEnd(true);
  }

  public void removePath(Grid grid) {
    maze.getCell(grid).setPath(false);
  }

  public void removeTravelled(Grid grid) {
    maze.getCell(grid).setTravelled(false);
  }

  public Grid getGridForDirection(Grid grid, int direction) {
    Cell cell = maze.getCell(grid);
    Grid next = null;
    if(!cell.getWalls()[direction]) {
      Cell neighbor = cell.getNeighbours()[direction];
      if (neighbor != null) {
        next = new Grid(neighbor.getWidth(), neighbor.getHeight());
      }
    }
    return next;
  }

  public boolean isEnd(Grid current) {
    return maze.getCell(current).isEnd();
  }

  public void setMaze(Maze maze) {
    this.maze = maze;
  }

  public void getMaze(Maze maze) {
    this.maze = maze;
  }
}
