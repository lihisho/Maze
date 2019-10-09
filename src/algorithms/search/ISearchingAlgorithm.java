package algorithms.search;

public interface ISearchingAlgorithm {

    /**
     * @param domain - the problem to be fixed
     * @return solution of the problem
     */
    public Solution solve(ISearchable domain);

    /**
     * @return how many nodes we passed
     */
    public int getNumberOfNodesEvaluated();
    public String getName();

}
