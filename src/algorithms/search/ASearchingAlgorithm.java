package algorithms.search;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {

    public abstract Solution solve(ISearchable domain);
    public abstract int getNumberOfNodesEvaluated();
    public abstract String getName();

}
