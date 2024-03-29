package solvers;

import game.Point;
import game.Puzzle;
import heuristics.Heuristic;
import heuristics.LinearConflict;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

import static java.lang.Math.max;

public class AStarSearch implements Solver {
    private final Heuristic heuristic;
    private boolean print = false;


    public AStarSearch(Heuristic h) {
        this.heuristic = h;
    }

    public AStarSearch(Heuristic h, boolean p) {
        this.heuristic = h;
        this.print = p;
    }


    public static void main(String[] args) {
        System.out.println(new AStarSearch(new LinearConflict()).solve(new Puzzle(3)));
    }

    public Output solve(Puzzle p) {
        PriorityQueue<Puzzle> frontier = new PriorityQueue<>(10, new Comparator<Puzzle>() {
            @Override
            public int compare(Puzzle o1, Puzzle o2) {
                return (heuristic.evaluate(o1) + o1.getPathCost()) - (heuristic.evaluate(o2) + o2.getPathCost());
            }
        });
        frontier.add(p);
        Set<Puzzle> explored = new HashSet<>();
        int numberMoves = 0;
        int maxNumberFrontierNodes = 1;
        while (true) {
            if (frontier.size() == 0) {
                return new Output(numberMoves, explored.size(), maxNumberFrontierNodes, false, 0);
            }
            Puzzle node = frontier.remove();
            explored.add(node);
            if (node.isResolved())
                return new Output(numberMoves, explored.size() + frontier.size(), maxNumberFrontierNodes, true, node.getPathCost());
            for (Point point : node.getSetOfMoves()) {
                Puzzle child = node.getCopy();
                child.move(point);
                numberMoves++;
                if (!explored.contains(child) && !frontier.contains(child)) {
                    frontier.add(child);
                    maxNumberFrontierNodes = max(maxNumberFrontierNodes, frontier.size());
                } else {
                    Puzzle toRemove = null;
                    for (Puzzle puzzle : frontier) {
                        if (puzzle.equals(child) && (heuristic.evaluate(puzzle) + puzzle.getPathCost()) > (heuristic.evaluate(child) + child.getPathCost())) {
                            toRemove = puzzle;
                            break;
                        }
                    }
                    if (toRemove != null) {
                        frontier.remove(toRemove);
                        frontier.add(child);
                    }
                }
            }

            if (!print) continue;

            System.out.println("Frontier:");
            for (Puzzle puzzle : frontier) {
                System.out.println("f(n): " + (heuristic.evaluate(puzzle) + puzzle.getPathCost()));
                System.out.println(puzzle);
            }

            System.out.println("Explored:");
            for (Puzzle puzzle : explored) {
                System.out.println("f(n): " + (heuristic.evaluate(puzzle) + puzzle.getPathCost()));
                System.out.println(puzzle);
            }
            System.out.println("------------------------");
        }
    }
}
