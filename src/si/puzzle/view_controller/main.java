package si.puzzle.view_controller;

import sac.graph.*;
import si.puzzle.model.Puzzle;
import si.puzzle.model.manhattan;
import si.puzzle.model.misplacedTiles;

import java.util.Arrays;
import java.util.List;

public class main {

    public static void main(String args[]) {
        final int size = 100;
        Puzzle[] testPuz = new Puzzle[size];
        Arrays.setAll(testPuz, p -> new Puzzle());
        for (Puzzle puz:
                testPuz) {
            puz.randomizeLayout();
        }

//        System.out.println(testPuz_manhatan[0]);
//        testPuz_manhatan[0].randomizeLayout();
//        System.out.println(testPuz_manhatan[0]);
//        System.out.println(testPuz_misplaced[0]);
//        System.out.println(testPuz_manhatan[1]);
//        System.out.println(testPuz_misplaced[1]);
//        System.out.println(testPuz_manhatan[2]);
//        System.out.println(testPuz_misplaced[2]);
        manhattan man = new manhattan();
        misplacedTiles mis = new misplacedTiles();

        GraphSearchAlgorithm algo;

        int meanClosedStates = 0, meanOpenStates = 0,meanPathDepth = 0;
        long meanDurationTime = 0;

        for (Puzzle puz:
                testPuz) {
            puz.setHFunction( man);
            algo = new AStar(puz);
            algo.execute();
            List<GraphState> sol = algo.getSolutions();
            meanPathDepth += sol.get(0).getMovesAlongPath().size();
            meanDurationTime += algo.getDurationTime();
            meanOpenStates += algo.getOpenSet().size();
            meanClosedStates += algo.getClosedStatesCount();
        }

        meanDurationTime /= (double) size;
        meanClosedStates /= (double) size;
        meanOpenStates /= (double) size;
        meanPathDepth /= (double) size;

        System.out.println("Mean Manhatan Time [ms]: " + meanDurationTime);
        System.out.println("Mean Manhatan Closed: " + meanClosedStates);
        System.out.println("Mean Manhatan open: " + meanOpenStates);
        System.out.println("Mean Manhatan PathDepth: " + meanPathDepth);

        meanClosedStates = 0;
        meanOpenStates = 0;
        meanPathDepth = 0;
        meanDurationTime = 0;

        for (Puzzle puz:
                testPuz) {
            puz.setHFunction(mis);
            algo = new AStar(puz);
            algo.execute();
            List<GraphState> sol = algo.getSolutions();
            meanPathDepth += sol.get(0).getMovesAlongPath().size();
            meanDurationTime += algo.getDurationTime();
            meanOpenStates += algo.getOpenSet().size();
            meanClosedStates += algo.getClosedStatesCount();
        }

        meanDurationTime /= (double) size;
        meanClosedStates /= (double) size;
        meanOpenStates /= (double) size;
        meanPathDepth /= (double) size;

        System.out.println("Mean Misplaced Time [ms]: " + meanDurationTime);
        System.out.println("Mean Misplaced Closed: " + meanClosedStates);
        System.out.println("Mean Misplaced open: " + meanOpenStates);
        System.out.println("Mean Misplaced PathDepth: " + meanPathDepth);


    }

}
