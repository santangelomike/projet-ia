package game;

import java.util.*;
import java.util.List;

public class Puzzle
{
    private int nbMoves;
    private int size;
    private List<List<Integer>> puzzle;
    private Point blankCoordinates;

    private Puzzle() {
        puzzle = new ArrayList<>();
        nbMoves = 0;
    }

    private void populateSize(int size) {
        if (size <= 1) throw new IllegalArgumentException("Size must be greater than 1.");
        this.size = size;
    }

    public Puzzle(int size) {
        this();
        populateSize(size);
        generateRandomPuzzle();
    }

    public Puzzle(int size, List<Integer> initialState) {
        this();
        populateSize(size);
        generatePuzzle(initialState);
    }

    private void storeBlankPosition() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (getPiece(i, j) == 0) {
                    blankCoordinates = new Point(i, j);
                    return;
                }
            }
        }
    }

    private void populatePuzzleFromList(List<Integer> l) {
        for (int i = 0; i < size; i++) {
            puzzle.add(l.subList(i * size, (i + 1) * size));
        }
    }

    private void generatePuzzle(List<Integer> initialState) {
        if (new TreeSet<>(initialState).size() != size * size) throw new IllegalArgumentException("Size of the initial state does not match the size specified (" + size + "), or there is multiple identical values in the initial state specified.");
        for (Integer i : initialState) {
            if (i < 0 || i >= size * size) throw new IllegalArgumentException("Puzzle contains illegal numbers.");
        }
        populatePuzzleFromList(initialState);
        storeBlankPosition();
    }

    private void generateRandomPuzzle() {
        ArrayList<Integer> l = new ArrayList<>();
        for (int i = 0; i < size * size; i++) {
            l.add(i);
        }
        Collections.shuffle(l);
        populatePuzzleFromList(l);
        storeBlankPosition();
        if (!isSolvable()) {
            puzzle = new ArrayList<>();
            blankCoordinates = new Point();
            generateRandomPuzzle();
        }
    }

    private Integer getPiece(int row, int column) {
        return puzzle.get(row).get(column);
    }

    private void replace(int row1, int column1, int row2, int column2) {
        Integer piece1 = getPiece(row1, column1);
        Integer piece2 = getPiece(row2, column2);
        puzzle.get(row1).set(column1, piece2);
        puzzle.get(row2).set(column2, piece1);
    }

    private boolean coordinateOutOfBounds(Point p) {
        return coordinateOutOfBounds(p.getRow(), p.getColumn());
    }

    private boolean coordinateOutOfBounds(int row, int column) {
        return row < 0 || row >= size || column < 0 || column >= size;
    }

    public void move(Point p) {
        move(p.getRow(), p.getColumn());
    }

    public int getNbMoves() {
        return nbMoves;
    }

    public void move(int row, int column) {
        if (coordinateOutOfBounds(row, column)) throw new IndexOutOfBoundsException("Coordinates specified don't match with the size of the puzzle");
        int x = blankCoordinates.getRow();
        int y = blankCoordinates.getColumn();
        if (Math.abs(x - row) + Math.abs(y - column) > 1) throw new IllegalArgumentException("The piece (" + row + ", " + column + ") you want to move can't be moved");
        replace(row, column, x, y);
        blankCoordinates.setRow(row);
        blankCoordinates.setColumn(column);
        nbMoves++;
    }

    public boolean isResolved() {
        int counter = 0;
        for (List<Integer> l : puzzle) {
            for (Integer i : l) {
                if (counter++ != i) return false;
            }
        }
        return true;
    }

    public Set<Point> getSetOfMoves() {
        int x = blankCoordinates.getRow(), y = blankCoordinates.getColumn();
        List<Point> coordinates = new ArrayList<>(Arrays.asList(new Point(x - 1, y), new Point(x + 1, y), new Point(x, y - 1), new Point(x, y + 1)));
        Set<Point> possibleMoves = new HashSet<>();
        for (Point p : coordinates) {
            if (!coordinateOutOfBounds(p)) possibleMoves.add(p);
        }
        return possibleMoves;
    }

    public Puzzle getCopy() {
        Puzzle p = new Puzzle();
        p.populateSize(size);
        for (List<Integer> l : puzzle) {
            p.puzzle.add(new ArrayList<>(l));
        }
        p.blankCoordinates = new Point(blankCoordinates.getRow(), blankCoordinates.getColumn());
        p.nbMoves = nbMoves;
        return p;
    }

    public boolean isSolvable() {
        boolean evenBlank = ((blankCoordinates.getRow() + blankCoordinates.getColumn()) % 2) == 0;
        int counterTransitions = 0;
        int counter = 0;
        Puzzle tmp = getCopy();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int value = tmp.getPiece(i, j);
                if (value != counter++) {
                    tmp.replace(i, j, value/size, value%size);
                    counterTransitions++;
                    counter -= 1;
                    if (j == 0) {
                        if (i != 0) {
                            i -= 1;
                            j = size - 1;
                        } else {
                            j -= 1;
                        }
                    } else {
                        j -= 1;
                    }

                }
            }
        }
        return (counterTransitions % 2 == 0) == evenBlank;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (List<Integer> l : puzzle) {
            String row = l.toString();
            row = row.replace(" ", "\t");
            row = row.replace("[", "");
            row = row.replace("]", "");
            row = row.replace(",", "");
            s.append(row).append("\n");
        }
        return s.toString().replaceAll("(?<![0-9])0(?![0-9])", "b");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Puzzle puzzle1 = (Puzzle) o;
        if (size != puzzle1.size) return false;
        for (int i = 0; i < size; i++) {
            if (!puzzle.get(i).equals(puzzle1.puzzle.get(i))) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, puzzle, blankCoordinates);
    }

    public static void main(String[] args) {
        Puzzle p = new Puzzle(4);
        System.out.println(p);
        System.out.println(p.getSetOfMoves());
        Puzzle p2 = p.getCopy(); // renvoie une copie indépendante
        Point move = (Point) p.getSetOfMoves().toArray()[0];
        System.out.println(p.equals(p2));
        p.move(move);
        System.out.println(p.equals(p2));
        System.out.println(move);
        System.out.println(p);
        System.out.println(p2);
    }
}
