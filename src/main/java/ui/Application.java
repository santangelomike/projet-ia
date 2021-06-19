package ui;

import game.Puzzle;
import heuristics.LinearConflict;
import heuristics.ManhattanDistance;
import heuristics.NumberOfMisplacedTiles;
import solvers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Application {

    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            try {
                System.out.println("A: Generate and solve a random puzzle");
                System.out.println("B: Generate and solve a custom puzzle");
                System.out.println("Q: Exit");

                char input = in.next().toCharArray()[0];

                switch (input) {
                    case 'a':
                    case 'A':
                        randomPuzzle();
                        break;
                    case 'b':
                    case 'B':
                        userEnteredPuzzle();
                        break;
                    case 'q':
                    case 'Q':
                        System.exit(0);
                        break;
                    default:
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public static int getPuzzleSizeFromInput() {
        System.out.println("Please provide the puzzle size:");

        while (!in.hasNextInt()) {
            System.err.print("Please provide an integer greater or equal to 2:\n");
            in.next();
        }

        return in.nextInt();
    }

    public static boolean shouldExecute(String nameOfSolver) {
        char answer;

        System.out.println();
        System.out.println("Do you want to execute " + nameOfSolver + " ? (y/n):");

        while (true) {
            answer = in.next().toCharArray()[0];
            if (answer == 'y') {
                System.out.println();
                System.out.println("Executing " + nameOfSolver + "...");
                System.out.println();
                return true;
            } else if (answer == 'n') {
                return false;
            } else {
                System.out.println("Please answer by 'y' or 'n':");
            }
        }
    }

    public static void solvePuzzle(Puzzle puzzle) {
        System.out.println(puzzle);

        char answer;
        boolean print;

        System.out.println("Do you want to print frontiers and explored paths ? (y/n):");

        while (true) {
            answer = in.next().toCharArray()[0];
            if (answer == 'y') {
                print = true;
                break;
            } else if (answer == 'n') {
                print = false;
                break;
            } else {
                System.out.println("Please answer by 'y' or 'n':");
            }
        }

        LinearConflict linearConflict = new LinearConflict();
        ManhattanDistance manhattanDistance = new ManhattanDistance();
        NumberOfMisplacedTiles numberOfMisplacedTiles = new NumberOfMisplacedTiles();

        if (shouldExecute("AStarSearch with LinearConflict")) {
            System.out.println(new AStarSearch(linearConflict, print).solve(puzzle));
        }

        if (shouldExecute("AStarSearch with ManhattanDistance")) {
            System.out.println(new AStarSearch(manhattanDistance, print).solve(puzzle));
        }

        if (shouldExecute("AStarSearch with NumberOfMisplacedTiles")) {
            System.out.println(new AStarSearch(numberOfMisplacedTiles, print).solve(puzzle));
        }

        if (shouldExecute("BreadthFirstSearch")) {
            System.out.println(new BreadthFirstSearch(print).solve(puzzle));
        }

        if (shouldExecute("DepthFirstSearch")) {
            System.out.println(new DepthFirstSearch(print).solve(puzzle));
        }

        if (shouldExecute("DepthLimitedSearch")) {
            System.out.println("Please provide the depth limited search limit:");

            while (!in.hasNextInt()) {
                System.err.print("Please provide a positive integer:\n");
                in.next();
            }

            System.out.println(new DepthLimitedSearch(in.nextInt(), print).solve(puzzle));
        }

        if (shouldExecute("GreedyBestFirstSearch with LinearConflict")) {
            System.out.println(new GreedyBestFirstSearch(linearConflict, print).solve(puzzle));
        }

        if (shouldExecute("GreedyBestFirstSearch with ManhattanDistance")) {
            System.out.println(new GreedyBestFirstSearch(manhattanDistance, print).solve(puzzle));
        }

        if (shouldExecute("GreedyBestFirstSearch with NumberOfMisplacedTiles")) {
            System.out.println(new GreedyBestFirstSearch(numberOfMisplacedTiles, print).solve(puzzle));
        }

        if (shouldExecute("IterativeDeepeningSearch")) {
            System.out.println(new IterativeDeepeningSearch().solve(puzzle));
        }

        if (shouldExecute("UniformCostSearch")) {
            System.out.println(new UniformCostSearch(print).solve(puzzle));
        }
    }

    public static void randomPuzzle() {
        int size = getPuzzleSizeFromInput();
        System.out.println("Generating random puzzle of size " + size);
        Puzzle puzzle = new Puzzle(size);
        solvePuzzle(puzzle);
    }

    public static void printCurrentPuzzle(List<Integer> puzzle, int size) {
        StringBuilder puzzleString = new StringBuilder();

        for (int i = 0; i < size * size; i++) {

            if (i < puzzle.size()) {
                puzzleString.append(puzzle.get(i));
            } else if (i == puzzle.size()) {
                puzzleString.append("_");
            } else {
                puzzleString.append("x");
            }

            if ((i + 1) % size == 0) {
                puzzleString.append("\n");
            }
        }

        System.out.println(puzzleString);
    }

    public static List<Integer> getPuzzleFromInput(int size) {

        List<Integer> puzzle = new ArrayList<>();
        List<Integer> possibilities = new ArrayList<>();

        for (int i = 0; i < size * size; i++) {
            possibilities.add(i);
        }

        while (puzzle.size() < size * size) {
            printCurrentPuzzle(puzzle, size);

            System.out.println("Possibilities: " + possibilities);

            while (!in.hasNextInt()) {
                System.err.print("Please provide an integer between: " + possibilities + "\n");
                in.next();
            }

            puzzle.add(possibilities.remove(possibilities.indexOf(in.nextInt())));
        }

        return puzzle;
    }

    public static void userEnteredPuzzle() {
        int size = getPuzzleSizeFromInput();
        System.out.println("Generating custom puzzle of size " + size);
        Puzzle puzzle = new Puzzle(size, getPuzzleFromInput(size));
        solvePuzzle(puzzle);
    }
}
