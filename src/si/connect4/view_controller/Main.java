package si.connect4.view_controller;

import org.eclipse.swt.internal.C;
import sac.StateFunction;
import sac.game.AlphaBetaPruning;
import sac.game.GameSearchAlgorithm;
import sac.game.MinMax;
import si.connect4.model.AdvanceHeuristics;
import si.connect4.model.Connect4;
import si.connect4.model.SimpleHeuristics;

import java.util.Map;
import java.util.Scanner;

public class Main {

    private static boolean checkWin(StateFunction sf, Connect4 state) {
        if (sf.calculate(state) > 10e9) {
            System.out.println("Wygrana gracza X !!!");
            return true;
        } else if (sf.calculate(state) < -10e9) {
            System.out.println("Wygrana gracza 0 !!!");
            return true;
        }
        return false;
    }

    static final boolean _DEBUG_ = false;
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);

        StateFunction smp = new AdvanceHeuristics();
        Connect4.setHFunction(smp);
        Connect4 conn = new Connect4();

        System.out.println(conn);
//        conn.makeMove(3);
//        System.out.println(conn);
//        conn.makeMove(5);
//        System.out.println(conn);
//        conn.makeMove(3);
//        System.out.println(conn);
//        conn.makeMove(3);
//        System.out.println(conn);

        System.out.println("(C)Człowiek czy (K)Komputer ");
        boolean startujeCzlowiek = true;
        if (scanner.nextLine().toLowerCase().charAt(0) == 'k') startujeCzlowiek = false;
//        System.out.println(conn.hashCode());

        if (startujeCzlowiek) {
            System.out.println("Podaj kolumne: ");
            while (!conn.makeMove(scanner.nextInt())) {
                System.out.println("Podaj PRAWIDLOWA kolumne: ");
            }
            System.out.println(conn);
            if (_DEBUG_) {
                System.out.println("Moje H" + conn.getH());
                System.out.println("moja heurystyka" + smp.calculate(conn));
            }
        }

        GameSearchAlgorithm algo = new AlphaBetaPruning();
        Map<String,Double> score;

        double SI_result = 0;
        while (true) {
            //tura komputera
            System.out.println("============ KOMPUTER =================");
            algo.setInitial(conn);
            algo.execute();
            String bestMove = algo.getFirstBestMove();
            conn.makeMove(Integer.parseInt(bestMove));
            if (_DEBUG_) {
                System.out.println(algo.getMovesScores());

                System.out.println("Moje H= " + conn.getH());
                System.out.println("moja heurystyka= " + smp.calculate(conn));
            }
            //System.out.println("KOMPUTER");
            System.out.println(conn);

            if (checkWin(smp,conn)) break;

            //tura czlowieka
            System.out.println("============ GRACZ =================");
            System.out.println("Podaj kolumne: ");
            while (!conn.makeMove(scanner.nextInt())) {
                System.out.println("Podaj PRAWIDLOWA kolumne: ");
            }
            //System.out.println("CZLOWIEK");
            System.out.println(conn);
            if (_DEBUG_) {
                System.out.println("Moje H= " + conn.getH());
                System.out.println("moja heurystyka= " + smp.calculate(conn));
            }

            if (checkWin(smp,conn)) break;
        }
    }
}
