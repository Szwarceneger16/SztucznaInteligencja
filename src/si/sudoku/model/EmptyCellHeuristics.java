package si.sudoku.model;

import sac.State;
import sac.StateFunction;

public class EmptyCellHeuristics extends StateFunction {
    @Override
    public double calculate(State state) {
        Sudoku sudoku = (Sudoku) state;
        return sudoku.getZeros();
    }
}
