package si.puzzle.model;

import sac.State;
import sac.StateFunction;

public class misplacedTiles extends StateFunction {
    @Override
    public double calculate(State state) {
        Puzzle puz = (Puzzle) state;
        int k = 0, n = puz.getN(),result = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ( puz.board[i][j] != k++ && puz.board[i][j] != 0) result++;
                //k++;
            }
        }
        return result;
    }
}
