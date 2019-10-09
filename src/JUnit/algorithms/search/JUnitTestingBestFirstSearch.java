package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.IMazeGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JUnitTestingBestFirstSearch {
    @Test
    public void testIllegalRow() throws Exception {
        Maze maze=new Maze(-1,5);
        assertNotNull(maze);

    }@Test
    public void testMaze1() throws Exception{
        IMazeGenerator mg = new MyMazeGenerator();
        Maze maze= new Maze(1,1);
        SearchableMaze searchableMaze = new SearchableMaze(maze);
        BestFirstSearch bbfs= new BestFirstSearch();
        Solution solution=bbfs.solve(searchableMaze);
        assertEquals(1,solution.pathSolution.size());
    }

    @Test
    public void testName() {
        IMazeGenerator mg = new MyMazeGenerator();
        Maze maze= new Maze(1,1);
        SearchableMaze searchableMaze = new SearchableMaze(maze);
        BestFirstSearch bbfs= new BestFirstSearch();

    }
}