package solvers;

import game.Point;
import game.Puzzle;
import heuristics.Heuristic;
import heuristics.ManhattanDistance;

import java.util.*;

import static java.lang.Math.max;

public class AStarSearch implements Solver {
    private final Heuristic heuristic;

    public AStarSearch(Heuristic h) {
        this.heuristic = h;
    }

    public Output solve(Puzzle p) {
        PriorityQueue<Puzzle> frontier = new PriorityQueue<>(10, new Comparator<Puzzle>() {
            @Override
            public int compare(Puzzle o1, Puzzle o2) {
                return (heuristic.evaluate(o1) + o1.getNbMoves()) - (heuristic.evaluate(o2) + o2.getNbMoves());
            }
        });
        frontier.add(p);
        Set<Puzzle> explored = new HashSet<>();
        int numberMoves = 0;
        int maxNumberFrontierNodes = 1;
        while (true) {
            if (frontier.size() == 0) {
                return new Output(numberMoves, explored.size(), maxNumberFrontierNodes, false);
            }
            Puzzle node = frontier.remove();
            explored.add(node);
            if (node.isResolved()) return new Output(numberMoves, explored.size() + frontier.size(), maxNumberFrontierNodes, true);
            for (Point point : node.getSetOfMoves()) {
                Puzzle child = node.getCopy();
                child.move(point);
                numberMoves++;
                if (!explored.contains(child) && !frontier.contains(child)) {
                    frontier.add(child);
                    maxNumberFrontierNodes = max(maxNumberFrontierNodes, frontier.size());
                }
                else {
                    Puzzle toRemove = null;
                    for (Puzzle puzzle : frontier) {
                        if (puzzle.equals(child) && (heuristic.evaluate(puzzle) + puzzle.getNbMoves()) > (heuristic.evaluate(child) + child.getNbMoves())) {
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
            System.out.println("Frontier:");
            for (Puzzle puzzle : frontier) {
                System.out.println(puzzle);
            }

            System.out.println("Explored:");
            for (Puzzle puzzle : explored) {
                System.out.println(puzzle);
            }
            System.out.println("------------------------");
        }
    }

    public static void main(String[] args) {
        System.out.println(new GreedyBestFirstSearch(new ManhattanDistance()).solve(new Puzzle(2, Arrays.asList(1, 3, 0, 2))));
    }
}
