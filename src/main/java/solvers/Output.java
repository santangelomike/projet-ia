package solvers;

import game.Point;
import utils.Pair;

public class Output extends Pair<Integer, Integer> {
    private final boolean puzzleResolved;

    public Output(Integer nbFrontierNodes, Integer nbExploredNodes, boolean puzzleResolved) {
        super(nbFrontierNodes, nbExploredNodes);
        this.puzzleResolved = puzzleResolved;
    }

    public int getNbFrontierNodes() {
        return left;
    }

    public int getNbExploredNodes() {
        return right;
    }

    public boolean isPuzzleResolved() {
        return puzzleResolved;
    }

    public String toString() {
        return "{ number of frontier nodes: " + left + ", number of explored nodes: " + right + ", puzzle is" + (puzzleResolved ? "" : " not") + " resolved }";
    }
}
