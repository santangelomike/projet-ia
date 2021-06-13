package heuristics;

import game.Puzzle;

public class NumberOfMisplacedTiles implements Heuristic {
    public static void main(String[] args) {
        Puzzle puzzle = new Puzzle(3);
        System.out.println(puzzle);
        System.out.println(new NumberOfMisplacedTiles().evaluate(puzzle));
    }

    public int evaluate(Puzzle p) {
        int distance = 0;
        int size = p.getSize();

        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                int value = p.getPiece(row, column);
                distance += (row != value / size || column != value % size) ? 1 : 0;
            }
        }

        return distance;
    }
}
