package algorithms.search;

import java.util.*;

public class DepthFirstSearch extends ASearchingAlgorithm {

    private HashSet<AState> closed;
    private Stack<AState> open;
    private int nodesEvaluated;

    /**
     * Constructor for DFS algorithm
     */
    public DepthFirstSearch() {
        open = new Stack<AState>();
        closed = new HashSet<AState>();
        nodesEvaluated = 0;
    }

    /**
     * Returns a path from the start position to the goal position.
     *
     * @param domain - The problem to be fixed.
     * @return Solution - a path of all states.
     */
    @Override
    public Solution solve(ISearchable domain) {
        ArrayList<AState> result = new ArrayList<>();
        ArrayList<AState> children;
        AState curr;
        AState start = domain.getStartState();
        AState end = domain.getGoalState();


        open.add(start);
        nodesEvaluated++;

        while (open.size() != 0) {
            curr = open.pop();
            closed.add(curr);
            if (curr.equals(end)) {//we reach the end
                result.add(0, curr);//restore the path,add to the head of the list
                curr = curr.comeFrom;
                while (curr != null) {
                    result.add(0, curr);
                    curr = curr.comeFrom;
                }
                break;
            }//if
            else {
                children = domain.getAllPossibleStates(curr);
                for (int i = 0; i < children.size(); i++) {
                    if (!closed.contains(children.get(i))) {
                        if (!open.contains(children.get(i))) {
                            children.get(i).comeFrom = curr;
                            open.push(children.get(i));
                            nodesEvaluated++;
                        }
                    }
                }
            }//else
        }
        Solution solution = new Solution(result);
        return solution;
    }


    /**
     * Returns the number of nodes Evaluated during the search.
     * @return num of nodes.
     */
    @Override
    public int getNumberOfNodesEvaluated() {
        return nodesEvaluated;
    }

    /**
     * Returns the name of the searching algorithm.
     * @return string - name of algorithm.
     */
    public String getName(){
        return "DepthFirstSearch";
    }




}
