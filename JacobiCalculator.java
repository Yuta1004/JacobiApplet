import java.util.ArrayList;

public class JacobiCalculator{

    private int formulaN;
    private final ArrayList<ArrayList<Double>> x, formulaCons;
    private final ArrayList<Double> formulaAns;

    JacobiCalculator(double[][] argFormulaCons, double[] argFormulaAns){
        // ArrayList初期化
        x = new ArrayList<ArrayList<Double>>();
        formulaCons = new ArrayList<ArrayList<Double>>();
        formulaAns = new ArrayList<Double>();

        // 式初期化
        formulaN = argFormulaCons.length;
        for(int f_idx = 0; f_idx < formulaN; ++ f_idx){
            formulaAns.add(argFormulaAns[f_idx]);
            formulaCons.add(new ArrayList<Double>());
            for(int c_idx = 0; c_idx < formulaN; ++ c_idx){
                formulaCons.get(f_idx).add(argFormulaCons[f_idx][c_idx]);
            }
        }
    }

}
