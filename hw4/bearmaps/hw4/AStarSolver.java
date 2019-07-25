package bearmaps.hw4;
import bearmaps.proj2ab.DoubleMapPQ;
import bearmaps.proj2ab.ExtrinsicMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex>{
    private SolverOutcome outcome;
    private double solutionWeight;
    private int statesExplored;
    private List<Vertex> solution;
    private double timeSpent;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch sw = new Stopwatch();
        solution = new LinkedList<>();
        Map<Vertex, Double> distTo = new HashMap<>();
        Map<Vertex, Vertex> edgeTo = new HashMap<>();
        ExtrinsicMinPQ<Vertex> pq = new DoubleMapPQ<>();
        distTo.put(start, 0.0);
        pq.add(start, distTo.get(start) + input.estimatedDistanceToGoal(start, end));
        while (pq.size() != 0 && !pq.getSmallest().equals(end) && sw.elapsedTime() < timeout) {
            Vertex p = pq.removeSmallest();
            statesExplored += 1;
            for (WeightedEdge<Vertex> e : input.neighbors(p)) {
                Vertex q = e.to();
                double w = e.weight();
                if (distTo.get(p) + w < distTo.get(q)) {
                    distTo.put(q, distTo.get(p) + w);
                    if (pq.contains(q)) {
                        pq.changePriority(q, distTo.get(q) + input.estimatedDistanceToGoal(q, end));
                    } else {
                        pq.add(q, distTo.get(q) + input.estimatedDistanceToGoal(q, end));
                    }
                }
            }
        }
        timeSpent = sw.elapsedTime();
        if (pq.size() == 0 || timeSpent > timeout) {
            outcome = SolverOutcome.UNSOLVABLE;
        } else if (pq.getSmallest().equals(end)) {
            outcome = SolverOutcome.SOLVED;
            Vertex v = pq.getSmallest();
            solutionWeight = distTo.get(v);
            List<Vertex> reversedsolution = new LinkedList<>();
            while (v != null) {
                reversedsolution.add(v);
                v = edgeTo.get(v);
            }
            for (int i = reversedsolution.size() - 1; i >= 0; i--) {
                solution.add(reversedsolution.get(i));
            }
        } else {
            outcome = SolverOutcome.TIMEOUT;
        }


    }

    @Override
    public SolverOutcome outcome() {
        return outcome;
    }
    @Override
    public List<Vertex> solution() {
        return solution;
    }
    @Override
    public double solutionWeight() {
        return solutionWeight;
    }
    @Override
    public int numStatesExplored() {
        return statesExplored;
    }
    @Override
    public double explorationTime() {
        return timeSpent;
    }

}
