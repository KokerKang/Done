package bearmaps.proj2c;
import java.util.List;

import bearmaps.proj2ab.DoubleMapPQ;
import edu.princeton.cs.algs4.Stopwatch;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Henry Kang
 * AStarSolver Algorithm.*/
public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    /**Distance map from front. */
    private HashMap<Vertex, Double> disTo;

    /**Distance map from end.*/
    private HashMap<Vertex, Double> disToEnd;

    /**Edge from front.*/
    private HashMap<Vertex, Vertex> edgeTo;

    /** List of solution. */
    private List<Vertex> solution;

    /** Number of States.*/
    private int numStatesExplored;

    /** Time for exploration. */
    private double explorationTime;

    /** Solution of weight.*/
    private double solutionWeight;

    /** Fringe variable. */
    private DoubleMapPQ<Vertex> pq;

    /** Out come value. */
    private SolverOutcome outcome;

    /** Setting the infinite distance. */
    private final double infinite = Double.POSITIVE_INFINITY;


    /** @param input input graph.
     *  @param start the start point.
     *  @param end  the end point.
     *  @param timeout the limited time.
     *  AStarSolver Algorithm.*/
    public AStarSolver(AStarGraph<Vertex> input, Vertex start,
                       Vertex end, double timeout) {
        disTo = new HashMap<>();
        edgeTo = new HashMap<>();
        disToEnd = new HashMap<>();
        solutionWeight = 0;
        numStatesExplored = 0;
        pq = new DoubleMapPQ<>();
        explorationTime = 0;
        solution = new ArrayList<>();

        pq.add(start, input.estimatedDistanceToGoal(start, end));
        disTo.put(start, 0.0);
        edgeTo.put(start, null);
        Stopwatch sw = new Stopwatch();


        while (pq.size() > 0) {
            Vertex temp = pq.getSmallest();
            numStatesExplored++;
            if (temp.equals(end)) {
                solutionHelper(end);
                outcome = SolverOutcome.SOLVED;
                explorationTime = sw.elapsedTime();
                return;
            }
            temp = pq.removeSmallest();
            relax(input, end, temp);

            explorationTime = sw.elapsedTime();
            if (explorationTime >= timeout) {
                outcome = SolverOutcome.TIMEOUT;
                return;
            } else {
                outcome = SolverOutcome.UNSOLVABLE;
                explorationTime = sw.elapsedTime();
            }
        }

    }
    /** To checking relax.
     * @param input input graph.
     * @param end the end point.
     * @param adjacent the edges.*/
    public void relax(AStarGraph<Vertex> input, Vertex end, Vertex adjacent) {
        for (WeightedEdge<Vertex> e : input.neighbors(adjacent)) {
            Vertex f = e.from();
            Vertex t = e.to();
            double w = e.weight();
            double tempDis = disTo.get(f) + w;

            if (!disTo.containsKey(t)) {
                disTo.put(t, infinite);
            }
            if (!disToEnd.containsKey(t)) {
                disToEnd.put(t, input.estimatedDistanceToGoal(t, end));
            }
            if (tempDis < disTo.get(t)) {
                disTo.put(t, tempDis);
                edgeTo.put(t, f);
                if (pq.contains(t)) {
                    pq.changePriority(t, disTo.get(t) + disToEnd.get(t));
                } else {
                    pq.add(t, disTo.get(t) + disToEnd.get(t));
                }
            }
        }
    }

    /** To fill out the solution list.
     * @param end the end point to check out.*/
    private void solutionHelper(Vertex end) {
        solution = new ArrayList<>();
        solutionWeight = disTo.get(end);
        Vertex temp = end;
        while (temp != null) {
            solution.add(0, temp);
            temp = edgeTo.get(temp);
        }
    }
    /**
     * @return Outcome value.*/
    public SolverOutcome outcome() {
        return outcome;
    }
    /** @return return solution.*/
    public List<Vertex> solution() {
        return solution;
    }
    /** @return return solution weight.*/
    public double solutionWeight() {
        return solutionWeight;
    }

    /** @return return number of how many we states.*/
    public int numStatesExplored() {
        return numStatesExplored;
    }

    /** @return return the exploration time.*/
    public double explorationTime() {
        return explorationTime;
    }
}
