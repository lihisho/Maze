package algorithms.mazeGenerators;

public abstract class AMazeGenerator implements IMazeGenerator {
    /**
     * Creates a new maze. Abstract method.
     * @param Rows - Num of rows in the maze
     * @param Columns Num of coulmns in the maze
     * @return The new Maze
     */
    public abstract Maze generate(int Rows, int Columns);

    /**
     * This Method returns the time needed to generate the maze.
     * @param Rows - Num of rows in the maze
     * @param Columns Num of coulmns in the maze
     * @return long- Time taken to build a new maze.
     */
    public long measureAlgorithmTimeMillis(int Rows, int Columns){
        long startTime=0;
        startTime =System.currentTimeMillis();
        generate(Rows,Columns);
        return System.currentTimeMillis()-startTime;
    }
}
