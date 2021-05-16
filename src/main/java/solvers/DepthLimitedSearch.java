package solvers;

import game.Point;
import game.Puzzle;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class DepthLimitedSearch {
    //TODO: renvoyer nombre de noeuds générés + nombre de noeuds stockés en mémoire
    public static Puzzle solve(Puzzle p, int limit) {
        if (p.isResolved()) return p;
        Stack<Puzzle> frontier = new Stack<>();
        frontier.add(p);
        Set<Puzzle> explored = new HashSet<>();
        while (true) {
            if (frontier.size() == 0) return null;
            Puzzle node = frontier.pop();
            if (node.isResolved()) return node;
            explored.add(node);
            if (node.getNbMoves() != limit) {
                for (Point point : node.getSetOfMoves()) {
                    Puzzle child = node.getCopy();
                    child.move(point);

                    if (!explored.contains(child) && !frontier.contains(child)) {
                        frontier.push(child);
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
        System.out.println(solve(new Puzzle(2, Arrays.asList(1, 3, 0, 2)), 2));
    }
}
