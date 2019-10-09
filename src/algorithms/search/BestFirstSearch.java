package algorithms.search;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class BestFirstSearch extends BreadthFirstSearch {
    /**
     * constructor define priority queue
     */
    public BestFirstSearch() {
        open = new PriorityQueue<AState>((x,y) -> x.cost - y.cost);
    }

    @Override
    public String getName() {
        return "BestFirstSearch";
    }
}



