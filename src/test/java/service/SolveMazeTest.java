package service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.gov.dwp.maze.model.Grid;
import uk.gov.dwp.maze.service.MazeService;
import uk.gov.dwp.maze.service.SolveMaze;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SolveMazeTest {

    private SolveMaze solveMaze;

    @Mock
    private MazeService mazeService;

    @Before
    public void setUp(){
        solveMaze = new SolveMaze(mazeService);
    }

    @Test
    public void getDirectionsAvailable(){
        Grid grid = new Grid(0,0);
        List<Grid> gridList = buildGridList();
        given(mazeService.getOpenNeighbours(grid)).willReturn(gridList);
        given(mazeService.getDirection(grid, gridList.get(0))).willReturn(1);
        given(mazeService.getDirection(grid, gridList.get(1))).willReturn(2);
        String directionsAvailable = solveMaze.getDirectionsAvailable(grid);
        assertEquals(" Left : 3\t Up : 0\t", directionsAvailable);
    }

    @Test
    public void blankStringWhenNoDirectionsAvailable(){
        Grid grid = new Grid(0,0);
        List<Grid> gridList = buildGridList();
        given(mazeService.getOpenNeighbours(grid)).willReturn(new ArrayList());
        String directionsAvailable = solveMaze.getDirectionsAvailable(grid);
        assertEquals("", directionsAvailable);
    }

    @Test
    public void blankStringWhenThereAreOpenNeighboursNoDirectionsAvailable(){
        Grid grid = new Grid(0,0);
        given(mazeService.getOpenNeighbours(grid)).willReturn(null);
        String directionsAvailable = solveMaze.getDirectionsAvailable(grid);
        assertEquals("", directionsAvailable);
    }

    @Test
    public void move(){
        Grid grid = new Grid(0,0);
        Grid next = new Grid(1, 0);
        given(mazeService.getGridForDirection(grid, 1)).willReturn(next);
        Grid move = solveMaze.move(grid, 1);
        verify(mazeService).path(next);
        verify(mazeService).travelled(next);
        assertEquals(next, move);
    }

    @Test
    public void moveReturnsCurrentIfGridForDirectionIsNull(){
        Grid grid = new Grid(0,0);
        given(mazeService.getGridForDirection(grid, 1)).willReturn(null);
        Grid move = solveMaze.move(grid, 1);
        assertEquals(grid, move);
    }

    @Test
    public void moveReturnsCurrentIdDirectionIsLessThanZero(){
        Grid grid = new Grid(0,0);
        Grid move = solveMaze.move(grid, 5);
        assertEquals(grid, move);
    }

    @Test
    public void moveBackFromTravelledPath(){
        Grid grid = new Grid(0,0);
        Grid next = new Grid(1, 0);
        given(mazeService.getGridForDirection(grid, 1)).willReturn(next);
        Grid move = solveMaze.move(grid, 1);
        verify(mazeService).path(next);
        verify(mazeService).travelled(next);
        assertEquals(next, move);
        Grid current = new Grid(1,0);
        given(mazeService.getGridForDirection(current, 3)).willReturn(current);
        Grid reverseMove = solveMaze.move(current, 3);
        verify(mazeService).removePath(current);
        verify(mazeService).removeTravelled(current);
        assertEquals(current, reverseMove);
    }

    @Test
    public void removeLastGrid(){
        Grid grid = new Grid(0,0);
        Grid next = new Grid(1, 0);
        given(mazeService.getGridForDirection(grid, 1)).willReturn(next);
        solveMaze.move(grid, 1);
        solveMaze.removeLastGrid(grid);
        verify(mazeService).removePath(grid);
    }

    private List<Grid> buildGridList() {
        List<Grid> list = new ArrayList<Grid>();
        list.add(new Grid(0, 1));
        list.add(new Grid(1,0));
        return list;
    }
}
