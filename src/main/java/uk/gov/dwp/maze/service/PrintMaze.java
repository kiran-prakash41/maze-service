package uk.gov.dwp.maze.service;

import uk.gov.dwp.maze.model.Cell;
import uk.gov.dwp.maze.model.Maze;

import static uk.gov.dwp.maze.service.MazeService.DOWN;
import static uk.gov.dwp.maze.service.MazeService.LEFT;
import static uk.gov.dwp.maze.service.MazeService.RIGHT;
import static uk.gov.dwp.maze.service.MazeService.UP;

public class PrintMaze {

    public static final String NEW_LINE = "\n";
    public static final String WALL_CHAR = "X";
    public static final String HSPACE = "    ";
    public static final String WALL = "XXXX";

    Cell[][] cells;

    public String print(int height, int width, Maze maze){
        cells = maze.getCells();
        StringBuilder output = new StringBuilder();

        for(int h=0;h<height;h++){
            output = getTopAndBottom(output, h, width, UP);
            output.append(NEW_LINE);

            //interior
            for(int w=0;w<width;w++){
                output.append(cells[h][w].getWalls()[LEFT] ? WALL_CHAR : " ");
                if(cells[h][w].isStart()){
                    output.append(" S ");
                }else if(cells[h][w].isEnd()){
                    output.append(" F ");
                }else if(cells[h][w].isPath()) {
                    output.append(" * ");
                }else{
                    output.append("   ");
                }
                output.append(w == width-1 && cells[h][w].getWalls()[RIGHT] ? " "+ WALL_CHAR : " ");
            }
            output.append(NEW_LINE);

            //bottom
            if(h==height-1){
                output = getTopAndBottom(output, h, width, DOWN);
            }
        }
        output.append("\n");
        String outputMaze = output.toString();
        System.out.print(outputMaze);
        return outputMaze;
    }
    private StringBuilder getTopAndBottom(StringBuilder s, int h, int width, int direction) {
        for(int w=0;w<width;w++){
            if(w==0){
                s.append(WALL_CHAR);
            }
            s.append(cells[h][w].getWalls()[direction] ? WALL : HSPACE);
            s.append(WALL_CHAR);
        }
        return s;
    }
}
