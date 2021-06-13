package game;

import utils.Pair;

public class Point extends Pair<Integer, Integer> {
    public Point(Integer row, Integer column) {
        super(row, column);
    }

    public Point() {
    }

    public int getRow() {
        return left;
    }

    public void setRow(int row) {
        left = row;
    }

    public int getColumn() {
        return right;
    }

    public void setColumn(int column) {
        right = column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point o2 = (Point) o;
        return o2.left.equals(left) && o2.right.equals(right);
    }
}
