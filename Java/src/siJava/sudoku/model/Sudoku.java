package siJava.sudoku.model;

import sac.graph.GraphState;
import sac.graph.GraphStateImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sudoku extends GraphStateImpl {

    private final int n;
    private final int n2;

    private byte[][] board;
    private int zeros;

    public Sudoku() {
        this.n = 3;
        this.n2 = (int) Math.pow(n,2);
        this.zeros = n2*n2;
        board = new byte[n2][n2];

        for (int i = 0; i < n2; i++) {
            for (int j = 0; j < n2; j++) {
                board[i][j] = 0;
            }
        }
    }

    public Sudoku(Sudoku toCopy) {
        this.n = 3;
        this.n2 = (int) Math.pow(n,2);
        board = new byte[n2][n2];

        for (int i = 0; i < n2; i++) {
            for (int j = 0; j < n2; j++) {
                board[i][j] = toCopy.board[i][j];
            }
        }
        this.zeros = toCopy.zeros;
    }

    public boolean isLegal() {
        byte[] group = new byte[n2];

        //rows
        for (int i = 0; i < n2; i++) {
            for (int j = 0; j < n2; j++) {
                group[j] = board[i][j];
            }
            if (!isGroupLegal(group)) {
                return false;
            }
        }

        //columns
        for (int i = 0; i < n2; i++) {
            for (int j = 0; j < n2; j++) {
                group[j] = board[j][i];
            }
            if (!isGroupLegal(group)) {
                return false;
            }
        }

        //squares
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int q= 0;
                for (int k = 0; k < n; k++) {
                    for (int l = 0; l < n; l++) {
                        group[q++] = board[i*n + k][j*n + l];
                    }
                }
                if (!isGroupLegal(group)) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isGroupLegal(byte[] group) {
        boolean[] visited = new boolean[n2];
        for (int i = 0; i < n2; i++) {
            visited[i] = false;
        }
        for (int i = 0; i < n2; i++) {
            if(group[i] == 0) {
                continue;
            }
            if (visited[group[i] - 1]) {
                return false;
            }
            visited[group[i] - 1] = true;
        }
        return true;
    }

    private void refreshZeros() {
        this.zeros = 0;
        for (int i = 0; i < n2; i++) {
            for (int j = 0; j < n2; j++) {
                if (board[i][j] == 0){
                    zeros++;
                }
            }
        }
    }

    public void fromStringN3(String inputText) {
        int k = 0;
        for (int i = 0; i < n2; i++) {
            for (int j = 0; j < n2; j++) {
                board[i][j] = Byte.valueOf(inputText.substring(k,k+1));
                k++;
            }
        }
        refreshZeros();
        return;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < n2; i++) {
            for (int j = 0; j < n2; j++) {
                result.append(board[i][j]);
                result.append(',');
            }
            result.append('\n');
        }
        result.append('\n');
        result.append(this.zeros);
        return result.toString();
    }

    public int getZeros() {
        return zeros;
    }

    @Override
    public int hashCode() {
        byte[] linear = new byte[n2 * n2];
        int k=0;
        for (int i = 0; i < n2; i++) {
            for (int j = 0; j < n2; j++) {
                linear[k++] = board[i][j];
            }
        }
        return Arrays.hashCode(linear);
    }

    @Override
    public List<GraphState> generateChildren() {
        List<GraphState> children = new ArrayList<GraphState>();

        int i = 0, j =0;

        zeroSeeker:
        for (i = 0; i < n2; i++) {
            for (j = 0; j < n2; j++) {
                if(board[i][j] == 0) {
                    break zeroSeeker;
                }
            }
        }

        if( i == n2)
            return children;

        for (int k = 0; k < n2; k++) {
            Sudoku child = new Sudoku(this);
            child.board[i][j] = (byte) (k+1);

            if (child.isLegal() ) {
                children.add(child);
                child.zeros--;
            }
        }

        return children;
    }

    @Override
    public boolean isSolution() {
        return ((this.zeros == 0) && (isLegal()));
    }
}
