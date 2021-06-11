package solvers;

import game.Puzzle;

import java.util.Arrays;

public class AStarSearch implements Solver {
    public Output solve(Puzzle p) {
        /*if (p.isResolved()) return new Output(0, 0, 0, true);
        Stack<Puzzle> frontier = new Stack<>();
        frontier.add(p);
        Set<Puzzle> explored = new HashSet<>();
        int numberMoves = 0;
        int maxNumberFrontierNodes = 1;
        while (true) {
        if (frontier.size() == 0)
        return new Output(numberMoves, explored.size(), maxNumberFrontierNodes, false);
        Puzzle node = frontier.pop();



        System.out.println("Frontier:");
        for (Puzzle puzzle : frontier) {
        System.out.println(puzzle);
        }

        System.out.println("Explored:");
        for (Puzzle puzzle : explored) {
        System.out.println(puzzle);
        }
        System.out.println("------------------------");
        }*/
        System.out.println(p.manhattanDistance());
        return new Output(1,1,1,false);
    }

    public static void main(String[] args) {
        System.out.println(new AStarSearch().solve(new Puzzle(2, Arrays.asList(1, 3, 2, 0))));
    }
}
