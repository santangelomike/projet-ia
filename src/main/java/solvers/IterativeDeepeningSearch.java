package solvers;

import game.Point;
import game.Puzzle;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import static java.lang.Math.max;

public class IterativeDeepeningSearch implements Solver {

    private final boolean print;

    public IterativeDeepeningSearch(boolean print) {
        this.print = print;
    }

    public static void main(String[] args) {
        //System.out.println(new IterativeDeepeningSearch().solve(new Puzzle(2, Arrays.asList(1, 3, 0, 2))));
    }

    public Output solve(Puzzle puzzle) {
        int numberGeneratedNodes = 0;
        int numberNodesInMemory = 0;
        int maxNumberFrontierNodes = 0;
        for (int i = 1; true; i++) {
            OutputDLS outputDLS = solve(puzzle, i);
            Output output = outputDLS.getOutput();
            numberGeneratedNodes += output.getNumberGeneratedNodes();
            numberNodesInMemory = max(numberNodesInMemory, output.getNumberNodesInMemory());
            maxNumberFrontierNodes = max(maxNumberFrontierNodes, output.getMaxNumberFrontierNodes());
            if (output.isPuzzleResolved()) {
                return new Output(numberGeneratedNodes, numberNodesInMemory, maxNumberFrontierNodes, true, output.getPathCost());
            } else if (!outputDLS.mayHaveChildren()) {
                return new Output(numberGeneratedNodes, numberNodesInMemory, maxNumberFrontierNodes, false, 0);
            }
        }
    }

    public OutputDLS solve(Puzzle p, int limit) {
        System.out.println("limit = " + limit);
        if (p.isResolved()) {
            Output o = new Output(0, 0, 0, true, 0);
            return new OutputDLS(true, o);
        }
        Stack<Puzzle> frontier = new Stack<>();
        frontier.add(p);
        Set<Puzzle> explored = new HashSet<>();
        int numberMoves = 0;
        int maxNumberFrontierNodes = 1;
        boolean mayHaveChildren = false;
        while (true) {
            if (frontier.size() == 0) {
                Output o = new Output(numberMoves, explored.size(), maxNumberFrontierNodes, false, 0);
                return new OutputDLS(mayHaveChildren, o);
            }
            Puzzle node = frontier.pop();
            if (node.isResolved()) {
                Output o = new Output(numberMoves, frontier.size() + explored.size(), maxNumberFrontierNodes, true, node.getPathCost());
                return new OutputDLS(true, o);
            }
            explored.add(node);
            if ((node.getNbMoves() + 1) != limit) {
                for (Point point : node.getSetOfMoves()) {
                    Puzzle child = node.getCopy();
                    child.move(point);
                    numberMoves++;

                    if (!explored.contains(child) && !frontier.contains(child)) {
                        frontier.push(child);
                        maxNumberFrontierNodes = max(maxNumberFrontierNodes, frontier.size());
                    }
                }
            } else {
                mayHaveChildren = true;
            }

            if (!print) continue;

            System.out.println("Frontier:");
            for (Puzzle puzzle : frontier) {
                System.out.println("depth of this node: " + puzzle.getNbMoves());
                System.out.println(puzzle);
            }

            System.out.println("Explored:");
            for (Puzzle puzzle : explored) {
                System.out.println("depth of this node: " + puzzle.getNbMoves());
                System.out.println(puzzle);
            }
            System.out.println("------------------------");
        }
    }

    static class OutputDLS {
        private final boolean mayHaveChildren;
        private final Output output;

        OutputDLS(boolean mayHaveChildren, Output output) {
            this.mayHaveChildren = mayHaveChildren;
            this.output = output;
        }

        public Output getOutput() {
            return this.output;
        }

        public boolean mayHaveChildren() {
            return this.mayHaveChildren;
        }
    }
}
