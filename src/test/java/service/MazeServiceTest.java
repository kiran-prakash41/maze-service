package service;

import org.junit.Before;
import org.junit.Test;
import uk.gov.dwp.maze.exception.UnableToBuildMazeException;
import uk.gov.dwp.maze.model.Cell;
import uk.gov.dwp.maze.model.Grid;
import uk.gov.dwp.maze.model.Maze;
import uk.gov.dwp.maze.service.MazeService;

import java.util.List;

import static org.junit.Assert.*;

public class MazeServiceTest {

    private MazeService mazeService;
    Maze maze;
    @Before
    public void setUp() throws UnableToBuildMazeException {
        mazeService = new MazeService();
        maze = mazeService.initMaze(5, 8);
    }

    @Test
    public void initTest(){
        Cell cell = maze.getCell(0, 1);
        assertEquals(4, cell.getNeighbours().length);
        assertFalse(cell.isVisit());
        assertFalse(cell.isTravelled());
        assertFalse(cell.isPath());
        assertFalse(cell.isStart());
        assertFalse(cell.isEnd());
        assertEquals(5, maze.getCells().length);
        assertEquals(8, maze.getCells()[0].length);
    }

    @Test(expected=UnableToBuildMazeException.class)
    public void initWithException() throws UnableToBuildMazeException {
        mazeService = new MazeService();
        maze = mazeService.initMaze(0, 0);
    }

    @Test
    public void removeWall(){
        mazeService.removeWall(new Grid(1, 1), 0);
        assertFalse(maze.getCell(1,1).getWalls()[0]);
    }

    @Test
    public void visitGrid(){
        mazeService.visit(new Grid(2, 1));
        assertTrue(maze.getCell(2,1).isVisit());
    }

    @Test
    public void setPath(){
        mazeService.path(new Grid(2, 3));
        assertTrue(maze.getCell(2,3).isPath());
    }

    @Test
    public void setTravelled(){
        mazeService.travelled(new Grid(2, 1));
        assertTrue(maze.getCell(2,1).isTravelled());
    }

    @Test
    public void markStart(){
        mazeService.markStart(new Grid(0, 1));
        assertTrue(maze.getCell(0,1).isStart());
    }

    @Test
    public void getOpenNeighbours(){
        Grid current = new Grid(1, 1);
        mazeService.removeWall(current, 0);
        mazeService.removeWall(current, 3);
        mazeService.removeWall(current, 1);

        List<Grid> openNeighbours = mazeService.getOpenNeighbours(current);
        assertEquals(3, openNeighbours.size());
    }

    @Test
    public void getOpenNeighboursThoseNotTravelled(){
        Grid current = new Grid(1, 1);
        mazeService.removeWall(current, 0);
        mazeService.removeWall(current, 3);
        mazeService.removeWall(current, 1);
        mazeService.travelled(new Grid(0,1));

        List<Grid> openNeighbours = mazeService.getOpenNeighbours(current);
        assertEquals(2, openNeighbours.size());
    }

    @Test
    public void getUnvisitedNeighbours(){
        Grid current = new Grid(1, 1);
        List<Grid> unvisitedNeighbours = mazeService.getUnvisitedNeighbours(current);
        assertEquals(4, unvisitedNeighbours.size());
    }

    @Test
    public void getEmptyWhenAllNeighboursAreVisted(){
        Grid current = new Grid(1, 1);
        mazeService.visit(new Grid(0,1));
        mazeService.visit(new Grid(1,0));
        List<Grid> unvisitedNeighbours = mazeService.getUnvisitedNeighbours(current);
        assertEquals(2, unvisitedNeighbours.size());
    }

    @Test
    public void getDirection(){
        Grid current = new Grid(1, 1);
        Grid next = new Grid(2, 1);
        int direction = mazeService.getDirection(current, next);
        assertEquals(0, direction);
    }

    @Test
    public void markEnd(){
        mazeService.markEnd(new Grid(0, 1));
        assertTrue(maze.getCell(0,1).isEnd());
    }

    @Test
    public void checkIfIsEnd(){
        Grid grid = new Grid(0, 1);
        mazeService.markEnd(grid);
        assertTrue(mazeService.isEnd(grid));
    }
    @Test
    public void removePath(){
        Grid grid = new Grid(0, 1);
        mazeService.path(grid);
        mazeService.removePath(grid);
        assertFalse(maze.getCell(grid).isPath());
    }

    @Test
    public void removeTravelled(){
        Grid grid = new Grid(0, 1);
        mazeService.travelled(grid);
        mazeService.removeTravelled(grid);
        assertFalse(maze.getCell(grid).isTravelled());
    }

    @Test
    public void nullGridIsReturnedWhenNoNeighbourExistInThatDirection(){
        Grid grid = new Grid(0, 1);
        mazeService.travelled(grid);
        assertNull(mazeService.getGridForDirection(grid, 0));
    }

    @Test
    public void gridIsReturnedWhenNeighbourExistsInThatDirection(){
        Grid grid = new Grid(0, 1);
        mazeService.travelled(grid);
        assertNotNull(mazeService.getGridForDirection(grid, 1));
    }
}
