package siJava;

import sac.graph.AStar;
import sac.graph.GraphSearchAlgorithm;
import sac.graph.GraphState;
import siJava.puzzle.model.Puzzle;
import siJava.puzzle.model.misplacedTiles;

import java.util.List;

public class testMain {
    public enum Kolor {

        CZERWONY,
        ZIELONY,
        NIEBIESKI;

        static public Kolor createColor(int n) {
            Kolor color;

            switch (n) {
                case 1:
                    color = Kolor.CZERWONY;
                    break;
                case 2:
                    color = Kolor.NIEBIESKI;
                    break;
                default:
                    color = Kolor.ZIELONY;
                    break;
            }

            return color;
        }

    }

    public static void main(String[] args) {

        //Puzzle puz = new Puzzle("360247581");
        //Puzzle puz = new Puzzle("541276038");
        Puzzle puz = new Puzzle();
        System.out.println(puz);
        puz.randomizeLayout();
        System.out.println(puz);

        puz.setHFunction(new misplacedTiles());
        GraphSearchAlgorithm algo = new AStar(puz);
        algo.execute();
        //System.out.println(puz);
        List<GraphState> solutions = algo.getSolutions();
        for (GraphState sol:
                solutions) {
            System.out.println("Solution:");
            System.out.println(sol);
            System.out.println("Path : " + sol.getMovesAlongPath());
        }

        System.out.println("Time [ms]: " + algo.getDurationTime());
        System.out.println("closed : " + algo.getClosedStatesCount());
        System.out.println("open : " + algo.getOpenSet().size());
        System.out.println("Solutions : " + algo.getSolutions().size());
//        System.out.println(Kolor.createColor(1));
//        System.out.println(Kolor.createColor(2));
    }

//    public static String czyLadny(Kolor kolor) {
//        String czyLadny = (kolor.ladny) ? "ladny" : "brzydki";
//
//        return "Kolor "+kolor.toString()+" jest "+czyLadny;
//    }
}
