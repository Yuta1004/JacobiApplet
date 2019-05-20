import java.applet.*;
import java.awt.*;

/* <applet code="Main.class" width="1000" height="700"></applet> */

public class Main extends Applet{
    // for Applet
    private int originX, originY, axisXLength, axisYLength;
    private int axisXStart, axisYStart, axisXEnd, axisYEnd;
    private double scaleX, scaleY;
    private Color black, white;
    private Color[] plotColors;
    private Font graphFont;

    // for Jacobi
    private double formulaCons[][] = {{5.0, 3.0, 1.0, 1.0, 1.0},
                           {1.0, 4.0, -2.0, 1.0, 1.0},
                           {1.0, -2.0, 3.0, -1.0, 1.0},
                           {1.0, 1.0, 2.0, 4.0, 1.0},
                           {1.0, 1.0, 1.0, -2.0, 5.0}};
    private double formulaAns[] = {10.0, 3.0, -1.0, -3.0, -5.0};
    private double initValues[] = {5.0, -5.0, 5.0, -5.0, 5.0};
    private JacobiCalculator jacobi;
    private int plotN;

    public void init(){
        // Init JacobiCalclator
        jacobi = new JacobiCalculator(formulaCons, formulaAns);
        jacobi.setInitValue(initValues);
        plotN = 50;

        // Set GraphInfo
        originX = 100;
        originY = 350;
        axisXLength = 850;
        axisYLength = 600;
        axisXStart = originX - 50;
        axisYStart = originY + axisYLength / 2;
        axisXEnd = originX + axisXLength;
        axisYEnd = originY - axisYLength / 2;
        scaleX = 15.0;
        scaleY = 50.0;

        // Set Color
        black = new Color(0, 0, 0);
        white = new Color(255, 255, 255);
        plotColors = new Color[5];
        plotColors[0] = new Color(255, 93, 93);
        plotColors[1] = new Color(207, 95, 255);
        plotColors[2] = new Color(93, 126, 255);
        plotColors[3] = new Color(93, 255, 94);
        plotColors[4] = new Color(93, 248, 255);

        // Set Font
        graphFont = new Font("TimesRoman", Font.PLAIN, 30);
    }

    public void paint(Graphics g){
        // Background
        g.setColor(white);
        g.fillRect(0, 0, 1000, 700);

        // Axis
        g.setFont(graphFont);
        g.setColor(black);
        g.drawString("0", originX - 35, originY + 35);
        g.drawLine(axisXStart, originY, axisXEnd, originY);
        g.drawLine(originX, axisYStart, originX, axisYEnd);
        g.drawLine(axisXEnd - 20, originY - 10, axisXEnd, originY);
        g.drawLine(axisXEnd - 20, originY + 10, axisXEnd, originY);
        g.drawLine(originX - 10, axisYEnd + 20, originX, axisYEnd);
        g.drawLine(originX + 10, axisYEnd + 20, originX, axisYEnd);

        // Graph Helper Line
        g.setColor(black);
        for(int y = -5; y <= 5; ++ y){
            if(y != 0){
                if(y % 5 == 0){
                    drawGraphLine(g, -1.5, y, 1.5, y);
                    drawGraphString(g, String.valueOf(y), -5, y + 0.17);
                }else{
                    drawGraphLine(g, -0.7, y, 0.7, y);
                }
            }
        }
        for(int x = 0; x <= plotN + 3; ++ x){
            if(x > 0 && x % 10 == 0){
                drawGraphLine(g, x, -0.2, x, 0.2);
                drawGraphString(g, String.valueOf(x), x - 1.5, -0.7);
            }else{
                drawGraphLine(g, x, -0.1, x, 0.1);
            }
        }

        // Plot
        Graphics2D g2 = (Graphics2D) g;
        for(int k = 0; k < plotN; ++ k){
            double nowKAns[] = jacobi.getAns(k);
            double nextKAns[] = jacobi.getAns(k + 1);
            for(int idx = 0; idx < formulaAns.length; ++ idx){
                g2.setColor(plotColors[idx % 5]);
                g2.setStroke(new BasicStroke(3.0f));
                drawGraphLine(g2, k, nowKAns[idx], k + 1, nextKAns[idx]); 
            } 
        }
    }

    // グラフ上の値を実際の座標に直して描画
    private void drawGraphLine(Graphics g, int x0, int y0, int x1, int y1){
        drawGraphLine(g, (double) x0, (double) y0, (double) x1, (double) y1);
    }

    private void drawGraphLine(Graphics g, double x0, double y0, double x1, double y1){
        int x0_i = originX + (int)(scaleX * x0);
        int y0_i = originY - (int)(scaleY * y0);
        int x1_i = originX + (int)(scaleX * x1);
        int y1_i = originY - (int)(scaleY * y1);
        g.drawLine(x0_i, y0_i, x1_i, y1_i);
    }

    private void drawGraphString(Graphics g, String viewStr, int x, int y){
        drawGraphString(g, viewStr, (double) x, (double) y);
    }

    private void drawGraphString(Graphics g, String viewStr, double x, double y){
        int x_i = originX + (int)(scaleX * x);
        int y_i = originY - (int)(scaleY * y);
        g.drawString(viewStr, x_i, y_i);
    }

}
