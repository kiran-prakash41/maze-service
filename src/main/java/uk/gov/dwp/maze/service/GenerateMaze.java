package uk.gov.dwp.maze.service;

import uk.gov.dwp.maze.model.Grid;
import uk.gov.dwp.maze.model.Maze;

import java.util.*;

public class GenerateMaze {

  Deque<Grid> stack;
  Random random = new Random();
  private MazeService mazeService;

  public GenerateMaze(MazeService mazeService){
    this.mazeService = mazeService;
  }

  public Maze generate(int height, int width, Grid start, Grid end)  {
    stack = new ArrayDeque<>();
    int visitedCells = height * width;
    Maze maze = mazeService.initMaze(height, width);
    //initialize start, upper left, and finnish bottom right
    mazeService.markStart(start);
    mazeService.markEnd(end);

    Grid current = start;
    mazeService.visit(current);
    while(visitedCells > 1) {
      List<Grid> unvisitedNeighbours = mazeService.getUnvisitedNeighbours(current);

      if (!unvisitedNeighbours.isEmpty()) {
        int nextIndex = random.nextInt(unvisitedNeighbours.size());
        Grid next = unvisitedNeighbours.get(nextIndex);

        stack.push(current);
        mazeService.visit(next);

        int directionOfNeighbour = mazeService.getDirection(current, next);
        mazeService.removeWall(next, directionOfNeighbour);
        current = next;
        visitedCells--;
      }else if(stack.size() > 0){
        current = stack.pop();
      }
    }
    return maze;
  }

}

