package siJava.puzzle.model;

import sac.graph.GraphState;
import sac.graph.GraphStateImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Puzzle extends GraphStateImpl {
    private static final boolean DEBUG = false;

    public enum direction {
        LEFT, RIGHT, UP, DOWN;

        static private Random gen = new Random(1);
        static private ArrayList<direction> _directions = new ArrayList<>(Arrays.asList(direction.values()));

        static public direction getRandomDirection(int x, int y,int n) {
            n--;
            ArrayList<direction> directions = new ArrayList<>(_directions);

            if (x == 0) directions.remove(direction.LEFT);
            else if (x == n) directions.remove(direction.RIGHT);
            if (y == 0) directions.remove(direction.UP);
            else if (y == n) directions.remove(direction.DOWN);

            int res = gen.nextInt(Integer.MAX_VALUE)%directions.size();
            return directions.get(res);
        }
    };

    private int n;
    //private int n2;
    int posx,posy;

    byte[][] board;

    public Puzzle() {
        this.n = 3;
        //this.n2 = (int) Math.pow(n,2);
        this.board = new byte[n][n];
        this.posx = 0;
        this.posy = 0;

        byte k = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = k++;
            }
        }
        if (DEBUG)
            System.out.print(this.toString());
    }

    public Puzzle(int n) {
        this.n = n;
        //this.n2 = (int) Math.pow(n,2);
        this.board = new byte[n][n];

        byte k = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = k++;
            }
        }
        if (DEBUG)
            System.out.print(this.toString());
    }

    public Puzzle(String input) {
        this.n = (int) Math.sqrt(input.length());

        this.board = new byte[n][n];
        int k = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = Byte.valueOf(input.substring(k,k+1));
                k++;
            }
        }
        if (DEBUG)
            System.out.print(this.toString());
    }

    public int getN() {
        return n;
    }

    public Puzzle(Puzzle pz) {
        this.n = pz.n;
        this.board = new byte[this.n][this.n];
        this.posy = pz.posy;
        this.posx = pz.posx;

        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                board[i][j] = pz.board[i][j];
            }
        }
    }

    public void randomizeLayout() {
        this.randomizeLayout(1000);
    }

    public void randomizeLayout(int randomCount) {
        Random generator = new Random();

        byte temp;
        direction way;
        for (int i = 0; i < randomCount; i++) {
            way = direction.getRandomDirection(posx,posy,this.n); // rand.nextInt(Integer.MAX_VALUE)%4

            temp = board[posy][posx];
            switch (way) {
                case RIGHT:
                    board[posy][posx] = board[posy][posx+1];
                    board[posy][posx+1] = temp;
                    posx++;
                    break;
                case LEFT:
                    board[posy][posx] = board[posy][posx-1];
                    board[posy][posx-1] = temp;
                    posx--;
                    break;
                case UP:
                    board[posy][posx] = board[posy-1][posx];
                    board[posy-1][posx] = temp;
                    posy--;
                    break;
                case DOWN:
                    board[posy][posx] = board[posy+1][posx];
                    board[posy+1][posx] = temp;
                    posy++;
                    break;
            }
            //System.out.print(this.toString());
        }
    }

    public boolean move(direction dr) {
        if (posx == 0 && dr == direction.LEFT) return false;
        else if (posx == (this.n - 1) && dr == direction.RIGHT) return false;
        else if (posy == 0 && dr == direction.UP) return false;
        else if (posy == (this.n - 1) && dr == direction.DOWN) return false;

        byte temp = board[posy][posx];
        switch (dr) {
            case RIGHT:
                board[posy][posx] = board[posy][posx+1];
                board[posy][posx+1] = temp;
                posx++;
                this.setMoveName("R");
                break;
            case LEFT:
                board[posy][posx] = board[posy][posx-1];
                board[posy][posx-1] = temp;
                posx--;
                this.setMoveName("L");
                break;
            case UP:
                board[posy][posx] = board[posy-1][posx];
                board[posy-1][posx] = temp;
                posy--;
                this.setMoveName("U");
                break;
            case DOWN:
                board[posy][posx] = board[posy+1][posx];
                board[posy+1][posx] = temp;
                posy++;
                this.setMoveName("D");
                break;
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result.append(this.board[i][j]);
                result.append(',');
            }
            result.append('\n');
        }
        result.append('\n');
        return result.toString();
    }

    @Override
    public List<GraphState> generateChildren() {
        List<GraphState> children = new ArrayList<GraphState>();

        for (direction dir:
             direction.values()) {
            Puzzle child = new Puzzle(this);

            if (child.move(dir) ) {
                children.add(child);
            }
        }

        return children;
    }

    @Override
    public boolean isSolution() {
        int k = 0;
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if(board[i][j] != k++) return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        byte[] linear = new byte[this.n * this.n];
        int k=0;
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                linear[k++] = board[i][j];
            }
        }
        return Arrays.hashCode(linear);
    }
}
