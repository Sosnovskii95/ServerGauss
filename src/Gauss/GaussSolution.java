package Gauss;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;

public class GaussSolution {
    private double tempHx;
    private double tempHy;
    private double tempHz;

    private double Hx;
    private double Hy;
    private double Hz;

    private String textIntegralFunc;

    public GaussSolution(double tempHx, double tempHy, double tempHz, double hx, double hy, double hz, String textIntegralFunc) {
        this.tempHx = tempHx;
        this.tempHy = tempHy;
        this.tempHz = tempHz;
        Hx = hx;
        Hy = hy;
        Hz = hz;
        this.textIntegralFunc = textIntegralFunc;
    }

    public double Solution() {
        double result = 0;

        Argument x = new Argument("x", leftFindFunc(tempHx, tempHx + Hx) +
                rightFinFunc(tempHx, tempHx + Hx));

        Argument y = new Argument("y", leftFindFunc(tempHy, tempHy + Hy) +
                rightFinFunc(tempHy, tempHy + Hy));

        Argument z = new Argument("z", leftFindFunc(tempHz, tempHz + Hz) +
                rightFinFunc(tempHz, tempHz + Hz));

        double res = findInterval(tempHx, tempHx + Hx) * findInterval(tempHy, tempHy + Hy) * findInterval(tempHz, tempHz + Hz);

        Expression e = new Expression(textIntegralFunc, x, y, z);

        result = res * e.calculate();


        return result;
    }

    private double leftFindFunc(double a, double b) {
        double result = 0;

        result = ((a + b) / 2) - ((b - a) / (2 * Math.sqrt(3)));

        return result;
    }

    private double rightFinFunc(double a, double b) {
        double result = 0;

        result = ((a + b) / 2) + ((b - a) / (2 * Math.sqrt(3)));

        return result;
    }

    private double findInterval(double a, double b) {
        return (b - a) / 2;
    }
}
