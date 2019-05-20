class JacobiCalculatorTest{
    // 式
    static double a[][] = {{5.0, 3.0, 1.0, 1.0, 1.0},
                           {1.0, 4.0, -2.0, 1.0, 1.0},
                           {1.0, -2.0, 3.0, -1.0, 1.0},
                           {1.0, 1.0, 2.0, 4.0, 1.0},
                           {1.0, 1.0, 1.0, -2.0, 5.0}};
    static double b[] = {10.0, 3.0, -1.0, -3.0, -5.0};
    static double initValues[] = {1.0, 1.0, 1.0, 1.0, 1.0};

    public static void main(String args[]){
        JacobiCalculator jacobi = new JacobiCalculator(a, b);
        jacobi.setInitValue(initValues);
        double ans[] = jacobi.getAns(500);

        for(int idx = 0; idx < 5; ++ idx){
            System.out.print(ans[idx] + " ");
        }
        System.out.println();
    }

}
