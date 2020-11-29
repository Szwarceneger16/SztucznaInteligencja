package siJava.connect4.model;

import sac.State;
import sac.StateFunction;

public class AdvanceHeuristics extends StateFunction {

    static double weights[] = {0,0,0,0};
    static double center = Connect4.x / 2.0;

    {
        int floor = 5;
        for (int i = 0; i < weights.length-1; i++) {
            weights[i+1] = Math.pow(floor,i);
        }
    }

    @Override
    public double calculate(State state) {
        Connect4 conn = (Connect4) state;
//        boolean auth = false;
//        System.out.println("Jestem tutaj");
        double value = 0,tempValue = 0;

        int count=0;
        for (int i = 0; i < conn.x; i++) {
            if (conn.board[i][conn.y-1] == Connect4.Token.O) return Double.NEGATIVE_INFINITY;
            else if (conn.board[i][conn.y-1] == Connect4.Token.X) return Double.POSITIVE_INFINITY;
        }

        for (int i = 0; i < conn.x; i++) { //poziom
            for (int j = 0; j < conn.y-1; j++) { //pion

                //poziom
                if (i < conn.x-3) {

                    for (int k = 0; k < 4; k++) {
                        if (conn.board[i+k][j] == Connect4.Token.O && tempValue > 0) {
                            tempValue = 0;
                            break;
                        }
                        else if (conn.board[i+k][j] == Connect4.Token.O) tempValue--;
                        else if (conn.board[i+k][j] == Connect4.Token.X && tempValue < 0) {
                            tempValue = 0;
                            break;
                        }
                        else if (conn.board[i+k][j] == Connect4.Token.X) tempValue++;
                    }
                    if (tempValue == 4) return Double.POSITIVE_INFINITY;
                    if (tempValue == -4) return Double.NEGATIVE_INFINITY;

                    tempValue = Math.copySign(weights[Math.abs( (int) tempValue)],tempValue);
                    if (tempValue != 0) {
                        tempValue *= 1 + (Math.abs(center - i) / (double) Connect4.x)*2;
                    }

                    value += tempValue;
                    tempValue = 0;
                }

                // prawy skos
                if (i < conn.x-3 && j < conn.y-3) {

                    for (int k = 0; k < 4; k++) {
                        if (conn.board[i+k][j+k] == Connect4.Token.O && tempValue > 0) {
                            tempValue = 0;
                            break;
                        }
                        else if (conn.board[i+k][j+k] == Connect4.Token.O) tempValue--;
                        else if (conn.board[i+k][j+k] == Connect4.Token.X && tempValue < 0) {
                            tempValue=0;
                            break;
                        }
                        else if (conn.board[i+k][j+k] == Connect4.Token.X) tempValue++;
                    }
                    if (tempValue == 4) return Double.POSITIVE_INFINITY;
                    if (tempValue == -4) return Double.NEGATIVE_INFINITY;

                    if ( j == Connect4.y-5 && (tempValue == 2 || tempValue == -2)) {
                        tempValue += Math.copySign(1,tempValue);
                    }

                    tempValue = Math.copySign(weights[Math.abs( (int) tempValue)],tempValue);
                    if (tempValue != 0) {
                        tempValue *= 1 + (Math.abs(center - i) / (double) Connect4.x)*2;
                    }

                    value += tempValue;
                    tempValue = 0;
                }

                // pionowo
                if (j < conn.y-3) {

                    for (int k = 0; k < 4; k++) {
                        if (conn.board[i][j+k] == Connect4.Token.O && tempValue > 0) {
                            tempValue=0;
                            break;
                        }
                        else if (conn.board[i][j+k] == Connect4.Token.O) tempValue--;
                        else if (conn.board[i][j+k] == Connect4.Token.X && tempValue < 0) {
                            tempValue=0;
                            break;
                        }
                        else if (conn.board[i][j+k] == Connect4.Token.X) tempValue++;
                    }
                    if (tempValue == 4) return Double.POSITIVE_INFINITY;
                    if (tempValue == -4) return Double.NEGATIVE_INFINITY;

                    if ( j == Connect4.y-5 && (tempValue == 2 || tempValue == -2)) {
                        tempValue += Math.copySign(1,tempValue);
                    }

                    tempValue = Math.copySign(weights[Math.abs( (int) tempValue)],tempValue);
                    if (tempValue != 0) {
                        tempValue *= 1 + (Math.abs(center - i) / (double) Connect4.x)*2;
                    }

                    value += tempValue;
                    tempValue = 0;
                }

                //lewy skos
                if (i > 2 && j < conn.y-3) {

                    for (int k = 0; k < 4; k++) {
                        if (conn.board[i-k][j+k] == Connect4.Token.O && tempValue > 0) {
                            tempValue = 0;
                            break;
                        }
                        else if (conn.board[i-k][j+k] == Connect4.Token.O) tempValue--;
                        else if (conn.board[i-k][j+k] == Connect4.Token.X && tempValue < 0) {
                            tempValue=0;
                            break;
                        }
                        else if (conn.board[i-k][j+k] == Connect4.Token.X) tempValue++;
                    }
                    if (tempValue == 4) return Double.POSITIVE_INFINITY;
                    if (tempValue == -4) return Double.NEGATIVE_INFINITY;

                    if ( j == Connect4.y-5 && (tempValue == 2 || tempValue == -2)) {
                        tempValue += Math.copySign(1,tempValue);
                    }

                    tempValue = Math.copySign(weights[Math.abs( (int) tempValue)],tempValue);
                    if (tempValue != 0) {
                        tempValue *= 1 + (Math.abs(center - i) / (double) Connect4.x)*2;
                    }

                    value += tempValue;
                    tempValue = 0;
                }

            }
        }
        return value;
    }
}
