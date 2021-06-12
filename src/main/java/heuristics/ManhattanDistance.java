package heuristics;

import game.Puzzle;

import java.util.ArrayList;
import java.util.List;

public class ManhattanDistance implements Heuristic {
    public int evaluate(Puzzle p) {
        int hn = 0;

        List<List<Integer>> goal = new ArrayList<>();

        int counter = 0;
        for (List<Integer> l : p.getPuzzle()) {
            List<Integer> list = new ArrayList<>();
            for (Integer i : l) {
                list.add(counter);
                counter++;
            }
            goal.add(list);
        }

        // the manhattan distance
        // int val: n
        // val coord: (x1,y1)
        // find goalVal
        // goal coord: (x2,y2)
        // distance: |x1-x2| + |y1-y2|
        for (int i = 0; i < p.getSize(); i++) {
            for (int j = 0; j < p.getSize(); j++) {
                int val = p.getPiece(i, j);

                // val coord
                int x1 = i;
                int y1 = j;
                // goal coord
                int x2 = 0;
                int y2 = 0;

                // find the goal coordinates
                for (int l = 0; l < p.getSize(); l++) {
                    for (int m = 0; m < p.getSize(); m++) {
                        int goalVal = goal.get(l).get(m);
                        if (val == goalVal) {
                            x2 = l;
                            y2 = m;
                            break;
                        }
                    }
                }
                // calculates current value's manhattan distance
                hn += Math.abs(x1 - x2) + Math.abs(y1 - y2);
                //System.out.println(Math.abs(x1 - x2) + Math.abs(y1 - y2));
            }
        }

        return hn;
    }

    public static void main(String[] args) {
        Puzzle puzzle = new Puzzle(2);
        System.out.println(puzzle);
        System.out.println(new ManhattanDistance().evaluate(puzzle));
    }
}
