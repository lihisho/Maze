package algorithms.mazeGenerators;

public interface IMazeGenerator {
    public Maze generate(int Rows, int Columns);
    public long measureAlgorithmTimeMillis(int Rows, int Columns);
}
