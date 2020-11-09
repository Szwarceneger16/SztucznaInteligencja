package si.connect4.view_controller;

import org.eclipse.swt.internal.C;
import sac.game.AlphaBetaPruning;
import sac.game.GameSearchAlgorithm;
import si.connect4.model.Connect4;
import si.connect4.model.SimpleHeuristics;

import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);

        Connect4.setHFunction(new SimpleHeuristics());
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
            while (!conn.makeMove(scanner.nextInt()-1)) {
                System.out.println("Podaj PRAWIDLOWA kolumne: ");
            }
            System.out.println(conn);
        }

        GameSearchAlgorithm algo = new AlphaBetaPruning();
        algo.setInitial(conn);
        algo.execute();
        Map<String,Double> score;


        double SI_result = 0;
        while (true) {
            //tura komputera
            conn.makeMove(Integer.valueOf(algo.getFirstBestMove()));
            score = algo.getMovesScores();
            score.forEach( (arg1,arg2) -> {
                System.err.println(arg1+", "+arg2);
            });
            System.out.println("KOMPUTER");
            System.out.println(conn);

            //tura czlowieka
            System.out.println("Podaj kolumne: ");
            while (!conn.makeMove(scanner.nextInt()-1)) {
                System.out.println("Podaj PRAWIDLOWA kolumne: ");
            }
            System.out.println("CZLOWIEK");
            System.out.println(conn);
            algo.setInitial(conn);
        }
    }
}
