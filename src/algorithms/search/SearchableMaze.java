package algorithms.search;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;

public class SearchableMaze implements ISearchable{
    private Maze maze;
    private AState startState;
    private AState endState;

    /**
     * constructor- init the fields with start and end position. puts 0 in MazeState fields as first init
     * @param m
     */
    public SearchableMaze(Maze m){
        maze=m;
        startState=new MazeState(maze.getStartPosition(),null,0);
        endState=new MazeState(maze.getGoalPosition(),null,0);
    }

    @Override
    public AState getGoalState() {
        return endState;
    }

    public AState getStartState() {
        return startState;
    }

    /**
     * insert all the possible moves from s to a list, including diagonals
     * @param s as the current state
     * @return ArrayList of all the possible moves
     */
    @Override
    public ArrayList<AState> getAllPossibleStates(AState s) {
        ArrayList<AState> result = new ArrayList<>();
        MazeState mState = (MazeState) s;
        if (mState == null)
            return null;
        int i = mState.getPosition().getRowIndex();
        int j = mState.getPosition().getColumnIndex();
        if (maze.isValidCell(i - 1, j))
            result.add(new MazeState(maze.getPosition(i - 1, j), mState, mState.getCost() + 10));
        if ((maze.isValidCell(i, j + 1) || maze.isValidCell(i - 1, j)) && maze.isValidCell(i - 1, j + 1))//diagonal up-right
            result.add(new MazeState(maze.getPosition(i - 1, j + 1), mState, mState.getCost() + 15));
        if (maze.isValidCell(i, j + 1))
            result.add(new MazeState(maze.getPosition(i, j + 1), mState, mState.getCost() + 10));
        if ((maze.isValidCell(i, j + 1) || maze.isValidCell(i + 1, j)) && maze.isValidCell(i + 1, j + 1))//diagonal down-right
            result.add(new MazeState(maze.getPosition(i + 1, j + 1), mState, mState.getCost() + 15));
        if (maze.isValidCell(i + 1, j))
            result.add(new MazeState(maze.getPosition(i + 1, j), mState, mState.getCost() + 10));// cost+1??? what is cost?!
        if ((maze.isValidCell(i, j - 1) || maze.isValidCell(i + 1, j)) && maze.isValidCell(i + 1, j - 1))//diagonal down-left
            result.add(new MazeState(maze.getPosition(i + 1, j - 1), mState, mState.getCost() + 15));
        if (maze.isValidCell(i, j - 1))
            result.add(new MazeState(maze.getPosition(i, j - 1), mState, mState.getCost() + 10));
        if ((maze.isValidCell(i, j - 1) || maze.isValidCell(i - 1, j)) && maze.isValidCell(i - 1, j - 1))//diagonal up-left
            result.add(new MazeState(maze.getPosition(i - 1, j - 1), mState, mState.getCost() + 15));

        return result;

    }




}
