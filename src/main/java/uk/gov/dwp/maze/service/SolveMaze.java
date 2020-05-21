package uk.gov.dwp.maze.service;

import uk.gov.dwp.maze.model.Grid;
import uk.gov.dwp.maze.model.Maze;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Stack;

import static uk.gov.dwp.maze.service.MazeService.*;

public class SolveMaze{

  private MazeService mazeService;
  Deque<Grid> travelGrid = new ArrayDeque<>();

  public SolveMaze(MazeService mazeService) {
    this.mazeService = mazeService;
  }

  public String getDirectionsAvailable(Grid grid){
    List<Grid> openNeighbours = mazeService.getOpenNeighbours(grid);
    StringBuilder directions = new StringBuilder();
    if(openNeighbours != null && !openNeighbours.isEmpty()) {
      for (Grid neighbour : openNeighbours) {
        int direction = mazeService.getDirection(grid, neighbour);
        switch ((direction + 2) % 4) {
          case UP:
            directions.append(" Up : 0\t");
            break;
          case RIGHT:
            directions.append(" Right : 1\t");
            break;
          case DOWN:
            directions.append(" Down : 2\t");
            break;
          case LEFT:
            directions.append(" Left : 3\t");
            break;
          default:
        }
      }
    }
    return directions.toString();
  }

  public Grid move(Grid current, int direction) {
    if(direction < 0 || direction > 4) return current;
    Grid grid = mazeService.getGridForDirection(current, direction);
    if(grid != null){
      if(travelGrid.contains(grid)){
        mazeService.removePath(current);
        mazeService.removeTravelled(current);
        travelGrid.remove(current);
      }else {
        mazeService.path(grid);
        mazeService.travelled(grid);
        travelGrid.push(grid);
      }
    }else{
      grid = current;
    }
    return grid;
  }

  public boolean isEnd(Grid current) {
    return mazeService.isEnd(current);
  }

  public Grid removeLastGrid(Grid current) {
    mazeService.removePath(current);
    current = travelGrid.pop();
    return current;
  }
}
