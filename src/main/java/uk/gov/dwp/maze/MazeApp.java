package uk.gov.dwp.maze;

import uk.gov.dwp.maze.model.Grid;
import uk.gov.dwp.maze.model.Maze;
import uk.gov.dwp.maze.service.GenerateMaze;
import uk.gov.dwp.maze.service.MazeService;
import uk.gov.dwp.maze.service.PrintMaze;
import uk.gov.dwp.maze.service.SolveMaze;

import java.util.Scanner;

public class MazeApp {

    public static void main(String[] args) {
        MazeApp mazeApp = new MazeApp();
        Scanner scanner = new Scanner(System.in);

        int height = readIntValue(scanner, "Enter the height of the grid ");

        int width = readIntValue(scanner, "Enter the width of the grid ");

        if(height <= 0 || width <= 0){
            throw new ArrayIndexOutOfBoundsException("Cannot build Maze for given height and width");
        }
        int startX = readIntValue(scanner, "Enter the row for the start point ");
        int startY = readIntValue(scanner, "Enter the column for the start point ");

        if(startX < 0 || startY < 0 || startX > height - 1 || startY > width - 1){
            throw new ArrayIndexOutOfBoundsException("The start grid index is out of bound");
        }
        Grid start = new Grid(startX, startY);

        int endX = readIntValue(scanner, "Enter the row for the end point ");
        int endY = readIntValue(scanner, "Enter the column for the end point ");
        if(endX < 0 || endY < 0 || endX > height - 1 || endY > width - 1){
            throw new ArrayIndexOutOfBoundsException("The end grid index is out of bound");
        }
        Grid end = new Grid(endX, endY);
        MazeService mazeService = new MazeService();
        PrintMaze printMaze = new PrintMaze();
        Maze maze = mazeApp.generateMaze(height, width, start, end, mazeService);
        printMaze.print(height, width, maze);

        while(true) {
            System.out.println(" Select one of the two options\n1. Solve Maze\n2. Query grid ");
            int optionSelected = scanner.nextInt();
            if(optionSelected == 1) break;
            int row = readIntValue(scanner, "Enter row  ");
            int col = readIntValue(scanner, "Enter the column ");
            mazeApp.printCellDetails(maze, height, width, row, col);
        }
        mazeApp.solveMaze(mazeService, printMaze, scanner, maze, start, height, width);
    }

    public Maze generateMaze(int height, int width, Grid start, Grid end, MazeService mazeService) {
        GenerateMaze generateMaze = new GenerateMaze(mazeService);
        return generateMaze.generate(height, width, start, end);
    }

    private static int readIntValue(Scanner scanner, String s) {
        System.out.print(s);
        return scanner.nextInt();
    }

    void solveMaze(MazeService mazeService, PrintMaze printMaze, Scanner scanner, Maze maze, Grid start,
                          int height, int width){
        printMaze.print(height, width, maze);
        boolean solved = false;
        SolveMaze solveMaze = new SolveMaze(mazeService);
        Grid current = start;
        while(!solved){
            String directionsAvailable = solveMaze.getDirectionsAvailable(current);
            if(directionsAvailable.equals("")){
                System.out.println("No directions available once moved to the selected direction");
                current = solveMaze.removeLastGrid(current);
            }else{
                System.out.println("Directions available for grid " + directionsAvailable);
                System.out.println("Enter one of the direction from above to move in the maze ");
                int direction = scanner.nextInt();
                current = solveMaze.move(current, direction);
            }
            solved = solveMaze.isEnd(current);
            printMaze.print(height, width, maze);
        }

        System.out.println("You have successfully solved the maze !!!!");
    }

    public void printCellDetails(Maze maze, int height, int width, int row, int col){
        if(row < 0 || col < 0 || row > height - 1 || col > width - 1){
            throw new ArrayIndexOutOfBoundsException("There is no cell on the maze for the given values");
        }
        System.out.println(maze.getCell(row, col));
    }
}
