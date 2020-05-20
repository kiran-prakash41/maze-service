package uk.gov.dwp.maze.service;

import uk.gov.dwp.maze.exception.UnableToBuildMazeException;
import uk.gov.dwp.maze.model.Grid;
import uk.gov.dwp.maze.model.Maze;

import java.util.List;
import java.util.Random;
import java.util.Stack;

public class GenerateMaze {

  Stack<Grid> stack;
  Random random = new Random();
  private MazeService mazeService;

  public GenerateMaze(MazeService mazeService){
    this.mazeService = mazeService;
  }

  public Maze generate(int height, int width, Grid start, Grid end)  {
    stack = new Stack<Grid>();
    int visitedCells = height * width;
    Maze maze = null;
    try {
      maze = mazeService.initMaze(height, width);
    } catch (UnableToBuildMazeException e) {
      e.printStackTrace();
      return null;
    }
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

