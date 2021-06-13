package heuristics;

import game.Point;
import game.Puzzle;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class LinearConflict implements Heuristic {
    public int evaluate(Puzzle p) {
        int distance = new ManhattanDistance().evaluate(p);
        int size = p.getSize();
        for (int row = 0; row < size; row++) {
            int lc = 0;
            Set<Conflict> conflicts = new HashSet<>();
            for (int column = 0; column < size; column++) { // For each tile tj in ri
                conflicts.add(conflictRow(p, new Point(row, column)));
            }
            while (isConflict(conflicts)) { // While there is a non-zero C(tj, ri) value
                Conflict c = conflictWithMaxValue(conflicts);
                c.value = 0;
                for (Point tile : c.conflictualTiles) { // For every tile tj which had been in conflict with tk
                    Conflict conflictualTile = findByTile(conflicts, tile);
                    conflictualTile.value -= 1;
                    conflictualTile.conflictualTiles.remove(c.tile);
                }
                c.conflictualTiles.clear();
                lc += 1;
            }
            distance += 2 * lc;
        }
        for (int column = 0; column < size; column++) {
            int lc = 0;
            Set<Conflict> conflicts = new HashSet<>();
            for (int row = 0; row < size; row++) { // For each tile tj in ri
                conflicts.add(conflictColumn(p, new Point(row, column)));
            }
            while (isConflict(conflicts)) { // While there is a non-zero C(tj, ri) value
                Conflict c = conflictWithMaxValue(conflicts);
                c.value = 0;
                for (Point tile : c.conflictualTiles) { // For every tile tj which had been in conflict with tk
                    Conflict conflictualTile = findByTile(conflicts, tile);
                    conflictualTile.value -= 1;
                    conflictualTile.conflictualTiles.remove(c.tile);
                }
                c.conflictualTiles.clear();
                lc += 1;
            }
            distance += 2 * lc;
        }
        return distance;
    }

    static class Conflict {
        Point tile;
        Set<Point> conflictualTiles;
        int value;

        Conflict(Point tile, Set<Point> conflictualTiles, int value) {
            this.tile = tile;
            this.conflictualTiles = conflictualTiles;
            this.value = value;
        }
    }

    private boolean isConflict(Set<Conflict> conflicts) {
        for (Conflict conflict : conflicts) {
            if (conflict.value != 0) {
                return true;
            }
        }
        return false;
    }

    private Conflict conflictWithMaxValue(Set<Conflict> conflicts) {
        Conflict maxConflict = null;
        for (Conflict conflict : conflicts) {
            if (maxConflict == null) maxConflict = conflict;
            else if (maxConflict.value < conflict.value) maxConflict = conflict;
        }
        return maxConflict;
    }

    private Conflict findByTile(Set<Conflict> conflicts, Point tileToFind) {
        for (Conflict conflict : conflicts) {
            if (conflict.tile.equals(tileToFind)) return conflict;
        }
        return null;
    }

    private boolean inRightRow(Puzzle p, Point tile) {
        return p.getPiece(tile) / p.getSize() == tile.getRow();
    }

    private boolean inRightColumn(Puzzle p, Point tile) {
        return p.getPiece(tile) % p.getSize() == tile.getColumn();
    }

    // the number of tiles in row with which tile is in conflict
    private Conflict conflictRow(Puzzle p, Point tile) {
        int nbConflicts = 0;
        Set<Point> conflictualTiles = new HashSet<>();
        int size = p.getSize();
        int row = tile.getRow();
        int tileValue = p.getPiece(tile);
        if (tileValue / size != row) return new Conflict(tile, conflictualTiles, nbConflicts); // si la valeur n'est pas sur la bonne ligne, pas de conflit
        for (int column = 0; column < size; column++) {
            Point conflictualTile = new Point(row, column);
            if (inRightRow(p, conflictualTile)) {
                if (column < tile.getColumn() && p.getPiece(conflictualTile) > tileValue) {
                    conflictualTiles.add(conflictualTile);
                    nbConflicts++;
                }
                if (column > tile.getColumn() && p.getPiece(conflictualTile) < tileValue) {
                    conflictualTiles.add(conflictualTile);
                    nbConflicts++;
                }
            }
        }
        return new Conflict(tile, conflictualTiles, nbConflicts);
    }

    // the number of tiles in column with which tile is in conflict
    private Conflict conflictColumn(Puzzle p, Point tile) {
        int nbConflicts = 0;
        Set<Point> conflictualTiles = new HashSet<>();
        int size = p.getSize();
        int column = tile.getColumn();
        int tileValue = p.getPiece(tile);
        if (tileValue % size != column) return new Conflict(tile, conflictualTiles, nbConflicts); // si la valeur n'est pas sur la bonne colonne, pas de conflit
        for (int row = 0; row < size; row++) {
            Point conflictualTile = new Point(row, column);
            if (inRightColumn(p, conflictualTile)) {
                if (row < tile.getRow() && p.getPiece(conflictualTile) > tileValue) {
                    conflictualTiles.add(conflictualTile);
                    nbConflicts++;
                }
                if (row > tile.getRow() && p.getPiece(conflictualTile) < tileValue) {
                    conflictualTiles.add(conflictualTile);
                    nbConflicts++;
                }
            }
        }
        return new Conflict(tile, conflictualTiles, nbConflicts);
    }

    public static void main(String[] args) {
        Puzzle puzzle = new Puzzle(2);
        System.out.println(puzzle);
        System.out.println(new LinearConflict().evaluate(puzzle));
    }
}
