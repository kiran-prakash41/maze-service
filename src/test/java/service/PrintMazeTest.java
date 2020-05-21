package service;

import org.junit.Before;
import org.junit.Test;
import uk.gov.dwp.maze.model.Grid;
import uk.gov.dwp.maze.model.Maze;
import uk.gov.dwp.maze.service.MazeService;
import uk.gov.dwp.maze.service.PrintMaze;

import static org.junit.Assert.assertEquals;

public class PrintMazeTest {

    PrintMaze printMaze;

    private static String EXPECTED_OUTPUT = "XXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
            "X S  X    X    X    X    X\n" +
            "XXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
            "X *  X    X    X    X    X\n" +
            "XXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
            "X    X    X    X    X    X\n" +
            "XXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
            "X    X    X    X    X    X\n" +
            "XXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
            "X    X    X    X    X F  X\n" +
            "XXXXXXXXXXXXXXXXXXXXXXXXXX\n";

    @Before
    public void setUp(){
        printMaze = new PrintMaze();
    }
    @Test
    public void print() {
        MazeService mazeService = new MazeService();
        Maze maze = mazeService.initMaze(5, 5);
        mazeService.markStart(new Grid(0, 0));
        mazeService.markEnd(new Grid(4, 4));
        mazeService.visit(new Grid(1, 0));
        mazeService.path(new Grid(1, 0));
        String print = printMaze.print(5, 5, maze);
        assertEquals(EXPECTED_OUTPUT, print);
    }
}
