package algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;

public class Solution implements Serializable{

    ArrayList<AState> pathSolution;

    /**\
     * Counstructor for the solotution path.
     * @param list - of nodes in the solution path.
     */
    public Solution(ArrayList<AState> list){
        if (list !=null )
            pathSolution = list;
        else
            pathSolution = new ArrayList<AState>();
    }

    /**
     * Getter for the solution list.
     * @return list of states in the path.
     */
    public ArrayList<AState> getSolutionPath() {
        return pathSolution;
    }
}
