package solvers;

import game.Point;
import game.Puzzle;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import static java.lang.Math.max;

public class DepthLimitedSearch implements Solver {
    private final int limit;

    public DepthLimitedSearch(int limit) {
        if (limit <= 0) throw new IllegalArgumentException("Limit must be strictly positive.");
        this.limit = limit;
    }

    public static void main(String[] args) {
        System.out.println(new DepthLimitedSearch(2).solve(new Puzzle(2, Arrays.asList(1, 3, 0, 2))));
    }

    public Output solve(Puzzle p) {
        if (p.isResolved()) return new Output(0, 0, 0, true);
        Stack<Puzzle> frontier = new Stack<>();
        frontier.add(p);
        Set<Puzzle> explored = new HashSet<>();
        int numberMoves = 0;
        int maxNumberFrontierNodes = 1;
        while (true) {
            if (frontier.size() == 0) return new Output(numberMoves, explored.size(), maxNumberFrontierNodes, false);
            Puzzle node = frontier.pop();
            if (node.isResolved())
                return new Output(numberMoves, frontier.size() + explored.size(), maxNumberFrontierNodes, true);
            explored.add(node);
            if (node.getNbMoves() != limit) {
                for (Point point : node.getSetOfMoves()) {
                    Puzzle child = node.getCopy();
                    child.move(point);
                    numberMoves++;

                    if (!explored.contains(child) && !frontier.contains(child)) {
                        frontier.push(child);
                        maxNumberFrontierNodes = max(maxNumberFrontierNodes, frontier.size());
                    }
                }
            }
//            System.out.println("Frontier:");
//            for (Puzzle puzzle : frontier) {
//                System.out.println(puzzle);
//            }
//
//            System.out.println("Explored:");
//            for (Puzzle puzzle : explored) {
//                System.out.println(puzzle);
//            }
//            System.out.println("------------------------");
        }
    }
}
