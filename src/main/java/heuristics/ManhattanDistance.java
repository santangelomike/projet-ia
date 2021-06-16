package heuristics;

import game.Puzzle;

public class ManhattanDistance implements Heuristic {
    public static void main(String[] args) {
        Puzzle puzzle = new Puzzle(2);
        System.out.println(puzzle);
        System.out.println(new ManhattanDistance().evaluate(puzzle));
    }

    public int evaluate(Puzzle p) {
        int distance = 0;
        int size = p.getSize();

        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                int value = p.getPiece(row, column);
                if (value != 0) {
                    distance += Math.abs(row - value / size);
                    distance += Math.abs(column - value % size);
                }
            }
        }

        return distance;
    }
}
