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
            System.out.print("Please provide an integer greater or equal to 2:\n");
            in.next();
        }

        return in.nextInt();
    }

    public static void solvePuzzle(Puzzle puzzle) {
        System.out.println(puzzle);

        LinearConflict linearConflict = new LinearConflict();
        ManhattanDistance manhattanDistance = new ManhattanDistance();
        NumberOfMisplacedTiles numberOfMisplacedTiles = new NumberOfMisplacedTiles();

        System.out.println(new AStarSearch(linearConflict).solve(puzzle));
        System.out.println(new AStarSearch(manhattanDistance).solve(puzzle));
        System.out.println(new AStarSearch(numberOfMisplacedTiles).solve(puzzle));

        System.out.println(new BreadthFirstSearch().solve(puzzle));

        System.out.println(new DepthFirstSearch().solve(puzzle));

//        System.out.println(new DepthLimitedSearch().solve(puzzle));

        System.out.println(new GreedyBestFirstSearch(linearConflict).solve(puzzle));
        System.out.println(new GreedyBestFirstSearch(manhattanDistance).solve(puzzle));
        System.out.println(new GreedyBestFirstSearch(numberOfMisplacedTiles).solve(puzzle));

        System.out.println(new IterativeDeepeningSearch().solve(puzzle));

        System.out.println();
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
                System.out.print("Please provide an integer between: " + possibilities + "\n");
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
