package algorithms.mazeGenerators;
//import java.util.concurrent.ThreadLocalRandom;
import java.util.Random;

public class SimpleMazeGenerator extends AMazeGenerator{
    /**
     * Generates a new maze.
     * @param Rows - Num of rows in the maze
     * @param Columns Num of coulmns in the maze
     * @return The new maze
     */
    public Maze generate(int Rows, int Columns){
        Maze maze = new Maze(Rows,Columns);
        maze.setStartPosition(new Position(0,0)); //Set a starting point from the first row.
        maze.setGoalPosition(new Position(Rows-1,Columns-1)); //Set a goal point from the last row.
        maze = generate(maze, maze.getStartPosition());
        maze.setRandomZeros();
        return maze;
    }

    /**
     * Recursive function for building a path in the maze.
     * @param maze
     * @param curr
     *
     */
    public Maze generate (Maze maze,Position curr){
        maze.setPath(curr);
        Random rand =new Random();
        if (curr == maze.getGoalPosition())
            return maze;
        int direction = rand.nextInt(2); // includes 0 and does not include the last number.
        if (direction == 0) {
            if (maze.isValidForGenerate(curr.getRowIndex() + 1, curr.getColumnIndex())) {
                curr = new Position(curr.getRowIndex() + 1, curr.getColumnIndex());
                generate(maze, curr);
            }
            else if (maze.isValidForGenerate(curr.getRowIndex(), curr.getColumnIndex() + 1)) {
                curr = new Position(curr.getRowIndex(), curr.getColumnIndex() + 1);
                generate(maze, curr);
            }
        }
        else if (direction == 1) {
            if (maze.isValidForGenerate(curr.getRowIndex(), curr.getColumnIndex() + 1)) {
                curr = new Position(curr.getRowIndex(), curr.getColumnIndex() + 1);
                generate(maze, curr);
            }
            else if (maze.isValidForGenerate(curr.getRowIndex() + 1, curr.getColumnIndex())) {
                curr = new Position(curr.getRowIndex() + 1, curr.getColumnIndex());
                generate(maze, curr);
            }
        }
        return maze;
    }

}
