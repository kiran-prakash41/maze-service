package model;

import org.junit.Before;
import org.junit.Test;
import uk.gov.dwp.maze.model.Cell;

import static org.junit.Assert.*;

public class CellTest {

    Cell cell;
    @Before
    public void setUp(){
        cell = new Cell(0,0);
    }

    @Test
    public void removeWallInUpwardDirection(){
        cell.setWalls(new boolean[]{true, true, true, true});
        cell.removeWall(0);
        assertFalse(cell.getWalls()[0]);
    }

    @Test
    public void removeWallInRightDirection(){
        cell.setWalls(new boolean[]{true, true, true, true});
        cell.removeWall(1);
        assertFalse(cell.getWalls()[1]);
    }

    @Test
    public void isNotVisited(){
        assertFalse(cell.isVisit());
    }

    @Test
    public void isVisited(){
        cell.setVisit(true);
        assertTrue(cell.isVisit());
    }

    @Test
    public void isTravelled(){
        cell.setTravelled(true);
        assertTrue(cell.isTravelled());
    }

    @Test
    public void isNotTravelled(){
        assertFalse(cell.isTravelled());
    }
    @Test
    public void isPath(){
        cell.setPath(true);
        assertTrue(cell.isPath());
    }

    @Test
    public void isNotPath(){
        assertFalse(cell.isPath());
    }

    @Test
    public void noNeighboursOnInit(){
        assertNull(cell.getNeighbours()[0]);
        assertNull(cell.getNeighbours()[1]);
        assertNull(cell.getNeighbours()[2]);
        assertNull(cell.getNeighbours()[3]);
    }

    @Test
    public void checkNeighbours(){
        cell.setNeighbours(new Cell[]{null, new Cell(1, 0), new Cell(0, 1), null});
        assertNull(cell.getNeighbours()[0]);
        assertNotNull(cell.getNeighbours()[1]);
        assertNotNull(cell.getNeighbours()[2]);
        assertNull(cell.getNeighbours()[3]);
    }
}
