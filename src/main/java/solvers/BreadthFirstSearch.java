package solvers;

import game.Point;
import game.Puzzle;

import java.util.*;

public class BreadthFirstSearch {
    public static Puzzle solve(Puzzle p) {
        if (p.isResolved()) {
            return p;
        }
        Queue<Puzzle> frontier = new LinkedList<>();
        frontier.add(p);
        Set<Puzzle> explored = new HashSet<>();
        while (true) {
            if (frontier.size() == 0) {
                return null;
            }
            Puzzle node = frontier.remove();
            explored.add(node);
            for (Point point : node.getSetOfMoves()) {
                Puzzle child = node.getCopy();
                child.move(point);
                if (!explored.contains(child) && !frontier.contains(child)) {
                    if (child.isResolved()) {
                        return child;
                    }
                    frontier.add(child);
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
        solve(new Puzzle(2, Arrays.asList(1, 3, 0, 2)));
    }
}