package utils;

import java.util.Objects;

public class Pair<L, R> {
    protected L left;
    protected R right;

    protected Pair() {
    }

    public Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public L getLeft() {
        return left;
    }

    public R getRight() {
        return right;
    }

    public String toString() {
        return "(" + left + ", " + right + ")";
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }
}
