package si.sudoku.view;

import sac.graph.BestFirstSearch;
import sac.graph.GraphSearchAlgorithm;
import sac.graph.GraphSearchConfigurator;
import sac.graph.GraphState;
import si.sudoku.model.EmptyCellHeuristics;
import si.sudoku.model.Sudoku;

import java.util.List;

public class Main {

    public static void main(String[] args) {
	    //String sudokuAsString = "003020600900305001001806400008102900700000008006708200002609500800203009005010300";
        String sudokuAsString = "000000000000305001001806400008102900700000008006708200002609500800203009005010300";
        Sudoku s = new Sudoku();
        s.fromStringN3(sudokuAsString);
        System.out.println(s);
        System.out.println(s.isLegal());

        Sudoku.setHFunction(new EmptyCellHeuristics());
        GraphSearchConfigurator conf = new GraphSearchConfigurator();
        conf.setWantedNumberOfSolutions(Integer.MAX_VALUE);
        GraphSearchAlgorithm algo = new BestFirstSearch(s,conf);
        algo.execute();
        List<GraphState> solutions = algo.getSolutions();
        for (GraphState sol:
             solutions) {
            System.out.println("---");
            System.out.println(sol);
        }

        System.out.println("Time [ms]: " + algo.getDurationTime());
        System.out.println("closed : " + algo.getClosedStatesCount());
        System.out.println("open : " + algo.getOpenSet().size());
        System.out.println("Solutions : " + algo.getSolutions().size());
    }

}
