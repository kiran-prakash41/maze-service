package uk.gov.dwp.maze.exception;

public class UnableToBuildMazeException extends Exception {

    public UnableToBuildMazeException(String errorMessage){
        super(errorMessage);
    }
}
