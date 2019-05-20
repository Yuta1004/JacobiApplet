import java.util.ArrayList;

public class JacobiCalculator{

    private int formulaN, nowK;
    private final ArrayList<ArrayList<Double>> x, formulaCons, calcAns;
    private final ArrayList<Double> formulaAns;

    JacobiCalculator(double[][] argFormulaCons, double[] argFormulaAns){
        // ArrayList初期化
        x = new ArrayList<ArrayList<Double>>();
        calcAns = new ArrayList<ArrayList<Double>>();
        formulaCons = new ArrayList<ArrayList<Double>>();
        formulaAns = new ArrayList<Double>();

        // 式初期化
        nowK = 0;
        formulaN = argFormulaCons.length;
        for(int f_idx = 0; f_idx < formulaN; ++ f_idx){
            formulaAns.add(argFormulaAns[f_idx]);
            formulaCons.add(new ArrayList<Double>());
            for(int c_idx = 0; c_idx < formulaN; ++ c_idx){
                formulaCons.get(f_idx).add(argFormulaCons[f_idx][c_idx]);
            }
        }
    }

    public void setInitValue(double[] argInitValues){
        // 初期値が設定済だった
        if(nowK != 0){
            return;
        }

        // 初期値セット
        calcAns.add(new ArrayList<Double>());
        for(int idx = 0; idx < formulaN; ++ idx){
            calcAns.get(0).add(argInitValues[idx]);
        }
        nowK = 1;
    }

    public double[] getAns(int k){
        double retValue[] = new double[formulaN];
        calJacobi(k);
        for(int idx = 0; idx < formulaN; ++ idx){
            retValue[idx] = calcAns.get(k).get(idx);
        } 
        return retValue;
    }

    // ヤコビ法
    private void calJacobi(int toK){
        for(; nowK <= toK; ++ nowK){
            int k = nowK; 
            calcAns.add(new ArrayList<Double>());

            // 計算本体
            for(int i = 0; i < formulaN; ++ i){
                double cal_result = 0;

                for(int j = 0; j < formulaN; ++ j){
                    if(i == j) continue;
                    cal_result += formulaCons.get(i).get(j) * calcAns.get(k - 1).get(j);
                }
                cal_result = formulaAns.get(i) - cal_result;
                cal_result /= formulaCons.get(i).get(i);

                calcAns.get(k).add(cal_result);
            }
        }
    }
}
