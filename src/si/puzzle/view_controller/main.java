package si.puzzle.view_controller;

import sac.graph.*;
import si.puzzle.model.Puzzle;
import si.puzzle.model.manhattan;
import si.puzzle.model.misplacedTiles;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class main {

    public static void main(String args[]) {
        Puzzle puz = new Puzzle();
        System.out.println(puz);
        puz.randomizeLayout();
        System.out.println(puz);
        manhattan man = new manhattan();
        misplacedTiles mis = new misplacedTiles();

        GraphSearchAlgorithm algo;

        puz.setHFunction( man);
        algo = new AStar(puz);
        algo.execute();
        List<GraphState> sol = algo.getSolutions();
        System.out.println(puz);
        System.out.println(sol.get(0).getMovesAlongPath().size());
        System.out.println(algo.getDurationTime());
        System.out.println(algo.getOpenSet().size());
        System.out.println(algo.getClosedStatesCount());
//        Scanner scanner = new Scanner(System.in);
//
//        String dir;
//
//        int count = 0;
//        while (true) {
//
//            do {
//                System.out.println("Podaj ruch: ");
//                dir = scanner.nextLine().substring(0,1);
//            } while (dir == "a" || dir == "d" || dir == "w" || dir == "s");
//            count++;
//            System.out.flush();
//            switch (dir)
//            {
//                case "d":
//                    puz.move(Puzzle.direction.LEFT);
//                    break;
//                case "a":
//                    puz.move(Puzzle.direction.RIGHT);
//                    break;
//                case "s":
//                    puz.move(Puzzle.direction.UP);
//                    break;
//                case "w":
//                    puz.move(Puzzle.direction.DOWN);
//                    break;
//            }
//            System.out.println(puz);
//        }

        /*final int size = 100;
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
        System.out.println("Mean Misplaced PathDepth: " + meanPathDepth);*/


    }

}
