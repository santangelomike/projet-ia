package heuristics;

import game.Puzzle;

public interface Heuristic {
    int evaluate(Puzzle p);
}
