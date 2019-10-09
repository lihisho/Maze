package algorithms.search;

import java.util.ArrayList;

public interface ISearchable {
    /**
     *
     * @return Astate as start of the problem
     */
    public AState getStartState();

    /**
     *
     * @return Astate as goal of the problem
     */
    public AState getGoalState();


    /**
     *
     * @param s as the current state
     * @return array list of all legal states we can continue from s
     */
    public ArrayList<AState> getAllPossibleStates(AState s);
}
