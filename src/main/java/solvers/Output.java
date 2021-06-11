package solvers;

public class Output {
    private final boolean puzzleResolved;
    private final Integer numberGeneratedNodes;
    private final Integer numberNodesInMemory;
    private final Integer maxNumberFrontierNodes;

    public Output(Integer numberGeneratedNodes, Integer numberNodesInMemory, Integer maxNumberFrontierNodes, boolean puzzleResolved) {
        this.numberGeneratedNodes = numberGeneratedNodes;
        this.numberNodesInMemory = numberNodesInMemory;
        this.maxNumberFrontierNodes = maxNumberFrontierNodes;
        this.puzzleResolved = puzzleResolved;
    }

    public int getNumberGeneratedNodes() {
        return numberGeneratedNodes;
    }

    public int getNumberNodesInMemory() {
        return numberNodesInMemory;
    }

    public int getMaxNumberFrontierNodes() {
        return maxNumberFrontierNodes;
    }

    public boolean isPuzzleResolved() {
        return puzzleResolved;
    }

    public String toString() {
        return "{ number of generated nodes: " + numberGeneratedNodes + ", number of nodes in memory: " + numberNodesInMemory + ", max number of frontier nodes: " + maxNumberFrontierNodes + ", puzzle is" + (puzzleResolved ? "" : " not") + " resolved }";
    }
}
