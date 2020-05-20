package uk.gov.dwp.maze.model;

public class Maze {

    private Cell[][] cells;

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    public Cell getCell(int row, int col) {
        return cells[row][col];
    }

    public Cell getCell(Grid grid) {
        return cells[grid.getRow()][grid.getCol()];
    }
}
