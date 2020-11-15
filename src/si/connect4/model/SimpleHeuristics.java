package si.connect4.model;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import sac.State;
import sac.StateFunction;

public class SimpleHeuristics extends StateFunction {

    @Override
    public double calculate(State state) {
        Connect4 conn = (Connect4) state;
        boolean auth = false;
//        System.out.println("Jestem tutaj");

        int k =0;
        for (int i = 0; i < conn.x; i++) {
            if (conn.board[i][conn.y-1] == Connect4.Token.O) return Double.NEGATIVE_INFINITY;
            else if (conn.board[i][conn.y-1] == Connect4.Token.X) return Double.POSITIVE_INFINITY;
        }

        for (int i = 0; i < conn.x; i++) { //poziom
            for (int j = 0; j < conn.y; j++) { //pion
                if (conn.board[i][j] == Connect4.Token.EMPTY) continue;

                if (i < conn.x-3) { //poziom
                    if (conn.board[i][j] == conn.board[i+1][j]) auth = true;
                    for (k = 1; k < 3; k++) {
                        if (!auth) break;
                        if (!(auth && conn.board[i+k][j] == conn.board[i+k+1][j])) auth = false;
                    }
                    if (auth) {
                        if (conn.board[i][j] == Connect4.Token.O) return Double.NEGATIVE_INFINITY;
                        else return Double.POSITIVE_INFINITY;
                    }
                }
                auth = false;
                if (i < conn.x-3 && j < conn.y-3) { // prawy skos
                    if (conn.board[i][j] == conn.board[i+1][j+1]) auth = true;
                    for (k = 1; k < 3; k++) {
                        if (!auth) break;
                        if (!(auth && conn.board[i+k][j+k] == conn.board[i+k+1][j+k+1])) auth = false;
                    }
                    if (auth) {
                        if (conn.board[i][j] == Connect4.Token.O) return Double.NEGATIVE_INFINITY;
                        else return Double.POSITIVE_INFINITY;
                    }
                }
                auth = false;
                if (j < conn.y-3) { // pionowo
                    if (conn.board[i][j] == conn.board[i][j+1]) auth = true;
                    for (k = 1; k < 3; k++) {
                        if (!auth) break;
                        if (!(auth && conn.board[i][j+k] == conn.board[i][j+k+1])) auth = false;
                    }
                    if (auth) {
                        if (conn.board[i][j] == Connect4.Token.O) return Double.NEGATIVE_INFINITY;
                        else return Double.POSITIVE_INFINITY;
                    }
                }
                auth = false;
                if (i > 2 && j < conn.y-3) { //lewy skos
                    if (conn.board[i][j] == conn.board[i-1][j+1]) auth = true;
                    for (k = 1; k < 3; k++) {
                        if (!auth) break;
                        if (!(auth && conn.board[i-k][j+k] == conn.board[i-k-1][j+k+1])) auth = false;
                    }
                    if (auth) {
                        if (conn.board[i][j] == Connect4.Token.O) return Double.NEGATIVE_INFINITY;
                        else return Double.POSITIVE_INFINITY;
                    }
                }
                auth = false;

            }
        }
        return 0;
    }
}
