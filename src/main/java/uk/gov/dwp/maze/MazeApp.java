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
        mazeApp.generateMaze();
    }

    private void generateMaze() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the height of the grid ");
        int height = scanner.nextInt();

        System.out.print("Enter the width of the grid ");
        int width = scanner.nextInt();

        if(height <= 0 || width <= 0){
            throw new ArrayIndexOutOfBoundsException("Cannot build Maze for given height and width");
        }
        System.out.print("Enter the row for the start point ");

        int startX = scanner.nextInt();
        System.out.print("Enter the column for the start point ");

        int startY = scanner.nextInt();

        if(startX < 0 || startY < 0 || startX > height - 1 || startY > width - 1){
            throw new ArrayIndexOutOfBoundsException("The start grid index is out of bound");
        }
        Grid start = new Grid(startX, startY);

        System.out.print("Enter the row for the end point ");

        int endX = scanner.nextInt();
        System.out.print("Enter the column for the end point ");

        int endY = scanner.nextInt();
        if(endX < 0 || endY < 0 || endX > height - 1 || endY > width - 1){
            throw new ArrayIndexOutOfBoundsException("The end grid index is out of bound");
        }
        Grid end = new Grid(endX, endY);

        MazeService mazeService = new MazeService();

        GenerateMaze generateMaze = new GenerateMaze(mazeService);

        Maze maze = generateMaze.generate(height, width, start, end);

        PrintMaze printMaze = new PrintMaze();
        printMaze.print(height, width, maze);
        printCellDetails(scanner, maze, height, width);
        solveMaze(mazeService, printMaze, scanner, maze, start, height, width);
    }

    private void solveMaze(MazeService mazeService, PrintMaze printMaze, Scanner scanner, Maze maze, Grid start,
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

    private void printCellDetails(Scanner scanner, Maze maze, int height, int width){
        boolean queryCoordinates = true;
        while(queryCoordinates){
            System.out.println(" Select one of the two options \n1. Solve Maze\n 2. Query grid ");
            int optionSelected = scanner.nextInt();
            if(optionSelected == 1) break;
            System.out.print(" Enter row  ");

            int row = scanner.nextInt();
            System.out.print("Enter the column ");

            int col = scanner.nextInt();
            if(row < 0 || col < 0 || row > height - 1 || col > width - 1){
                throw new ArrayIndexOutOfBoundsException("There is no cell on the maze for the given values");
            }
            System.out.println(maze.getCell(row, col));
        }
        System.out.println(" ");
    }
}
