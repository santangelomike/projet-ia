package solvers;

import game.Point;
import game.Puzzle;

import java.util.*;

public class UniformCostSearch {
    public static Puzzle solve(Puzzle p) {
        PriorityQueue<Puzzle> frontier = new PriorityQueue<>(10, new Comparator<Puzzle>() {
            @Override
            public int compare(Puzzle o1, Puzzle o2) {
                return o1.getNbMoves() - o2.getNbMoves();
            }
        });
        frontier.add(p);
        Set<Puzzle> explored = new HashSet<>();
        while (true) {
            if (frontier.size() == 0) {
                return null;
            }
            Puzzle node = frontier.remove();
            explored.add(node);
            if (node.isResolved()) return node;
            for (Point point : node.getSetOfMoves()) {
                Puzzle child = node.getCopy();
                child.move(point);
                if (!explored.contains(child) && !frontier.contains(child)) {
                    frontier.add(child);
                }
                else {
                    Puzzle toRemove = null;
                    for (Puzzle puzzle : frontier) {
                        if (puzzle.equals(child) && puzzle.getNbMoves() > child.getNbMoves()) {
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
        System.out.println(solve(new Puzzle(2, Arrays.asList(1, 3, 0, 2))));
    }
}
