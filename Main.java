import java.applet.*;
import java.awt.*;
import java.awt.event.*;

/* <applet code="Main.class" width="1000" height="800"></applet> */

public class Main extends Applet implements AdjustmentListener{
    // for Applet
    private int originX, originY, axisXLength, axisYLength;
    private int axisXStart, axisYStart, axisXEnd, axisYEnd;
    private double scaleX, scaleY;
    private Color black, white, gray;
    private Color[] plotColors;
    private Font graphFont, graphHelpFont;
    private Scrollbar plotNScrollbar;
    private String graphTitle;

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
        plotN = 30;

        // Set GraphInfo
        originX = 100;
        originY = 400;
        axisXLength = 850;
        axisYLength = 600;
        axisXStart = originX - 50;
        axisYStart = originY + axisYLength / 2;
        axisXEnd = originX + axisXLength;
        axisYEnd = originY - axisYLength / 2;
        scaleX = 800.0 / plotN;
        scaleY = 50.0;
        graphTitle = "ヤコビ法における解の収束グラフ";

        // Set Color
        black = new Color(0, 0, 0);
        white = new Color(255, 255, 255);
        gray = new Color(200, 200, 200);
        plotColors = new Color[5];
        plotColors[0] = new Color(255, 93, 93);
        plotColors[1] = new Color(207, 95, 255);
        plotColors[2] = new Color(93, 126, 255);
        plotColors[3] = new Color(59, 173, 77);
        plotColors[4] = new Color(163, 185, 0);

        // Set Font
        graphFont = new Font("TimesRoman", Font.PLAIN, 30);
        graphHelpFont = new Font("TimesRoman", Font.PLAIN, 25);

        // Init ScrollBar
        setLayout(null);
        plotNScrollbar = new Scrollbar(Scrollbar.HORIZONTAL, 30, 5, 1, 175);
        plotNScrollbar.setBounds(250, 70, 500, 20);
        plotNScrollbar.addAdjustmentListener(this);
        add(plotNScrollbar);
    }

    public void adjustmentValueChanged(AdjustmentEvent e){
        plotN = plotNScrollbar.getValue();
        scaleX = 800.0 / plotN;
        repaint();
    }

    public void paint(Graphics g){
        // Background
        g.setColor(white);
        g.fillRect(0, 0, 1000, 800);

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

        // Graph Helper Line(y)
        for(int y = -5; y <= 5; ++ y){
            if(y != 0){
                // Normal
                int yPos = originY - (int)(scaleY * y);
                g.setColor(gray);
                g.drawLine(30, yPos, 970, yPos);

                // Bold
                g.setColor(black);
                if(y % 5 == 0){
                    g.drawLine(originX - 30, yPos, originX + 30, yPos);
                    drawCenteringString(g, graphFont, String.valueOf(y), originX - 40, yPos);
                }else{
                    g.drawLine(originX - 15, yPos, originX + 15, yPos);
                }
            }
        }
        drawCenteringString(g, graphHelpFont, "y", originX, axisXStart);

        // Graph Helper Line(k)
        for(int x = 0; x <= plotN + 1; ++ x){
            g.setColor(black);
            if(x > 0 && x % 10 == 0){
                drawGraphLine(g, x, -0.2, x, 0.2);
                drawGraphCenteringString(g, graphFont, String.valueOf(x), x, -0.5);
            }else{
                drawGraphLine(g, x, -0.1, x, 0.1);
            }
        }
        drawCenteringString(g, graphHelpFont, "k", axisXEnd + 20, originY - 7);
        drawCenteringString(g, graphHelpFont, "繰り返し回数", 500, 650);

        // Set Graph Title
        g.setFont(graphFont);
        graphTitle = graphTitle.split(" ", 0)[0];
        graphTitle += " (k <= " + String.valueOf(plotN) + ")";
        drawCenteringString(g, graphFont, graphTitle, 500, 30);

        // Plot
        Graphics2D g2 = (Graphics2D) g;
        for(int k = 0; k < plotN; ++ k){
            double nowKAns[] = jacobi.getAns(k);
            double nextKAns[] = jacobi.getAns(k + 1);
            for(int idx = 0; idx < formulaAns.length; ++ idx){
                g2.setColor(plotColors[idx % 5]);
                g2.setStroke(new BasicStroke(3.0f));
                drawGraphLine(g2, k, nowKAns[idx], k + 1, nextKAns[idx]);
                drawGraphOval(g2, k, nowKAns[idx], 10.0);
                drawGraphOval(g2, k + 1, nextKAns[idx], 10.0);
            }
        }

        // Legends
        for(int idx = 0; idx < formulaAns.length; ++ idx){
            int x = 700 + (idx % 2) * 100;
            int y = 130 + (idx / 2) * 30;
            g2.setColor(plotColors[idx % 5]);
            g2.setStroke(new BasicStroke(5.0f));
            g.drawLine(x, y, x + 40, y);
            drawCenteringString(g, graphFont, "x" + String.valueOf(idx + 1), x + 70, y - 10);
        }

        // View Init Values
        String viewInitValueStr = "初期値 : (";
        for(int idx = 0; idx < formulaAns.length; ++ idx){
            viewInitValueStr += String.valueOf(initValues[idx]);
            if(idx < formulaAns.length - 1){
                viewInitValueStr += ", ";
            }
        }
        g2.setColor(black);
        drawCenteringString(g, graphHelpFont, viewInitValueStr + ")", 500, 750);
    }

    // グラフ上の値を実際の座標に直して描画(線)
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

    // グラフ上の値を実際の座標に直して描画(テキスト)
    private void drawGraphCenteringString(Graphics g, Font font, String viewStr, int x, int y){
        drawGraphCenteringString(g, font, viewStr, (double) x, (double) y);
    }

    private void drawGraphCenteringString(Graphics g, Font font, String viewStr, double x, double y){
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();
        int x_i = originX + (int)(scaleX * x);
        int y_i = originY - (int)(scaleY * y);
        x_i -= fm.stringWidth(viewStr) / 2;
        y_i += fm.getHeight() / 2;
        g.drawString(viewStr, x_i, y_i);
    }

    // グラフ上の値を実際に値に直して描画(丸)
    private void drawGraphOval(Graphics g, int x, int y, int size){
        drawGraphOval(g, (double) x, (double) y, (double) size);
    }

    private void drawGraphOval(Graphics g, double x, double y, double size){
        int x_i = originX + (int)(scaleX * x) - (int)size / 2;
        int y_i = originY - (int)(scaleY * y) - (int)size / 2;
        g.fillOval(x_i, y_i, (int) size, (int) size);
    }

    // テキストをセンタリングして描画
    private void drawCenteringString(Graphics g, Font font, String viewStr, int x, int y){
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();
        x -= fm.stringWidth(viewStr) / 2;
        y += fm.getHeight() / 2;
        g.drawString(viewStr, x, y);
    }

}
