package solvers;

import game.Point;
import game.Puzzle;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import static java.lang.Math.max;

public class BreadthFirstSearch implements Solver {

    private boolean print = false;

    public BreadthFirstSearch() {
    }

    public BreadthFirstSearch(boolean print) {
        this.print = print;
    }

    public static void main(String[] args) {
        System.out.println(new BreadthFirstSearch().solve(new Puzzle(2)));
    }

    public Output solve(Puzzle p) {
        if (p.isResolved()) {
            return new Output(0, 0, 0, true, 0);
        }
        Queue<Puzzle> frontier = new LinkedList<>();
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
            for (Point point : node.getSetOfMoves()) {
                Puzzle child = node.getCopy();
                child.move(point);
                numberMoves++;
                if (!explored.contains(child) && !frontier.contains(child)) {
                    if (child.isResolved()) {
                        return new Output(numberMoves, explored.size() + frontier.size(), maxNumberFrontierNodes, true, child.getPathCost());
                    }
                    frontier.add(child);
                    maxNumberFrontierNodes = max(frontier.size(), maxNumberFrontierNodes);
                }
            }

            if (!print) continue;

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
}
