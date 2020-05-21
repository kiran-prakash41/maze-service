import org.junit.Test;
import org.mockito.Mockito;
import uk.gov.dwp.maze.MazeApp;
import uk.gov.dwp.maze.model.Grid;
import uk.gov.dwp.maze.model.Maze;
import uk.gov.dwp.maze.service.MazeService;

import static org.junit.Assert.assertTrue;

public class MazeAppTest {

    @Test
    public void testGenerate(){
        MazeApp mazeApp = new MazeApp();
        MazeService mazeService = new MazeService();
        Maze maze = mazeApp.generateMaze(3, 3, new Grid(1, 2), new Grid(2, 2), mazeService);
        assertTrue(maze.getCell(1,2).isStart());
        assertTrue(maze.getCell(2,2).isEnd());
        assertTrue(maze.getCell(2,1).isVisit());
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void throwsExceptionQueryingCellDoesNotExistIntheMaze(){
        MazeApp mazeApp = new MazeApp();
        MazeService mazeService = new MazeService();
        Maze maze = mazeApp.generateMaze(3, 1, new Grid(1, 0), new Grid(0, 0), mazeService);
        mazeApp.printCellDetails(maze, 3, 1, 2, 1);
    }
}
