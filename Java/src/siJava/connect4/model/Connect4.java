package siJava.connect4.model;

import sac.game.GameState;
import sac.game.GameStateImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Connect4 extends GameStateImpl {

    enum Token {
        X, O, EMPTY;

    }

    static public final int x = 10,y = 10;
    Token[][] board = new Token[x][y];
    {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                board[i][j] = Token.EMPTY;
            }
        }
    }

    public Connect4() {
        this.setMaximizingTurnNow(true);
    }

    public Connect4(Connect4 in) {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                this.board[i][j] = in.board[i][j];
            }
        }
        this.setMaximizingTurnNow(in.isMaximizingTurnNow());
    }

    public boolean makeMove(int column) {
        if (column < 0 || column >= x) return false;

        for (int j = 0; j < y; j++) {
            if (board[column][j] == Token.EMPTY) {
                if (this.isMaximizingTurnNow()) {
                    board[column][j] = Token.X;
                    this.setMaximizingTurnNow(false);
                }else {
                    board[column][j] = Token.O;
                    this.setMaximizingTurnNow(true);
                }

                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = y-1; i >= 0; i--) {
            result.append(i);
            result.append(" |");
            for (int j = 0; j < x; j++) {
                result.append(this.board[j][i] == Token.EMPTY ? " " : this.board[j][i].name());
                result.append('|');
            }
            result.append('\n');
        }
        result.append("  ");
        for (int i = 0; i < x; i++) {
            result.append(" ");
            result.append(i);
        }

        result.append('\n');
        return result.toString();

    }

    @Override
    public List<GameState> generateChildren() {
        List<GameState> childrens = new ArrayList<>();

        Connect4 child;
        for (int i = 0; i < Connect4.x; i++) {
            child = new Connect4(this);
            child.makeMove(i);
            child.setMoveName(Integer.toString(i));
            childrens.add(child);
        }
//        System.err.println(childrens);

        return childrens;
    }

    @Override
    public int hashCode() {
        Token[] linear = new Token[x * y];
        int k=0;
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                linear[k++] = board[i][j];
            }
        }
        return Arrays.hashCode(linear);
    }

}
