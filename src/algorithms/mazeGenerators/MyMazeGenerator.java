package algorithms.mazeGenerators;
import java.util.ArrayList;
//import java.util.concurrent.ThreadLocalRandom;
import java.util.Random;

public class MyMazeGenerator extends AMazeGenerator {
    ArrayList<Position> walls;

    public MyMazeGenerator() {
        walls = new ArrayList<Position>();
    }

    /**
     * generates a new Maze according to prim's algorithm
     *
     * @param Rows    - Num of rows in the maze
     * @param Columns Num of coulmns in the maze
     * @return
     */
    @Override
    public Maze generate(int Rows, int Columns) {
        Maze maze = new Maze(Rows, Columns);
        Random random = new Random();
        int randomNumS = random.nextInt(Columns); // includes 0 and does not include the last number.
        maze.setStartPosition(new Position(0, randomNumS)); //Set a starting point from the first row.
        walls.add(maze.getStartPosition());
        Position current = new Position(0,0);

        while (walls.size() > 0) {
            if (walls.size() == 1)
                current = walls.remove(0);
            else
                current = walls.remove(random.nextInt(walls.size() - 1));

            while (!hasOneWall(maze, current)) {
                if (walls.size() > 1) {
                    current = walls.remove(random.nextInt(walls.size() - 1));
                }
                else {
                    if (walls.size() != 0)
                        current = walls.remove(0);
                    else break;
                }
            }

            maze.setPath(current);
            addAdj(current, maze);
        }

        boolean found = false;
        int randomNumG=0;
        while (!found) {
            randomNumG = random.nextInt(Columns); // includes 0 and does not include the last number.
            if (maze.isValidCell(Rows - 1, randomNumG))
                found = true;
        }
        maze.setGoalPosition(new Position(Rows - 1, randomNumG)); //Set a goal point from the last row.
       // maze.setGoalPosition(current);

        return maze;
        }
    /**
     * Add the adjacent walls to the ArrayList
     *
     * @param p-   current position
     * @param maze
     */
    public void addAdj(Position p, Maze maze) {
        if (walls != null) {
            // Down
            if (maze.isValidForGenerate(p.getRowIndex() + 1, p.getColumnIndex())) {
                Position down = new Position(p.getRowIndex() + 1, p.getColumnIndex());
                if (!walls.contains(down))
                    walls.add(down);
            }
            // Up
            if (maze.isValidForGenerate(p.getRowIndex() - 1, p.getColumnIndex())) {
                Position up = new Position(p.getRowIndex() - 1, p.getColumnIndex());
                if (!walls.contains(up))
                    walls.add(up);
            }
            //Right
            if (maze.isValidForGenerate(p.getRowIndex(), p.getColumnIndex() + 1)) {
                Position right = new Position(p.getRowIndex(), p.getColumnIndex() + 1);
                if (!walls.contains(right))
                    walls.add(right);
            }
            //Left
            if (maze.isValidForGenerate(p.getRowIndex(), p.getColumnIndex() - 1)) {
                Position left = new Position(p.getRowIndex(), p.getColumnIndex() - 1);
                if (!walls.contains(left))
                    walls.add(left);
            }
        }
    }

    /**
     * Verifies that the position is has only one path according to the prim algorithm
     *
     * @param m
     * @param toCheck-current position to check
     * @return
     */
    public boolean hasOneWall(Maze m, Position toCheck) {
        int counter = 0;
        if (m.isValidCell(toCheck.getRowIndex() + 1, toCheck.getColumnIndex()))
            counter++;
        if (m.isValidCell(toCheck.getRowIndex() - 1, toCheck.getColumnIndex()))
            counter++;
        if (m.isValidCell(toCheck.getRowIndex(), toCheck.getColumnIndex() + 1))
            counter++;
        if (m.isValidCell(toCheck.getRowIndex(), toCheck.getColumnIndex() - 1))
            counter++;
        if (counter > 1)
            return false;
        return true;
    }
}


