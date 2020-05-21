package service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.gov.dwp.maze.model.Grid;
import uk.gov.dwp.maze.model.Maze;
import uk.gov.dwp.maze.service.GenerateMaze;
import uk.gov.dwp.maze.service.MazeService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GenerateMazeTest {

    private GenerateMaze generateMaze;

    @Mock
    private MazeService mazeService;

    @Mock
    private Maze maze;

    @Before
    public void setUp(){
        generateMaze = new GenerateMaze(mazeService);
    }

    @Test
    public void draw(){
        Grid start = new Grid(0, 0);
        Grid end = new Grid(2, 2);
        given(mazeService.initMaze(3, 3)).willReturn(maze);
        given(mazeService.getUnvisitedNeighbours(any(Grid.class))).willReturn(buildGridList());
        generateMaze.generate(3, 3, start, end);
        verify(mazeService, times(8)).getUnvisitedNeighbours(any(Grid.class));
        verify(mazeService).markStart(start);
        verify(mazeService).markEnd(end);
        verify(mazeService, times(8)).getDirection(any(Grid.class), any(Grid.class));
        verify(mazeService, times(8)).removeWall(any(Grid.class), anyInt());
        verify(mazeService, times(9)).visit(any(Grid.class));
    }

    private List<Grid> buildGridList() {
        List<Grid> list = new ArrayList<Grid>();
        list.add(new Grid(0, 1));
        list.add(new Grid(1,0));
        return list;
    }
}
