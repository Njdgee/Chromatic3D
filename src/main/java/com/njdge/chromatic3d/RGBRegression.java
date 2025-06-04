package com.njdge.chromatic3d;

import Jama.Matrix;
import java.util.*;

public class RGBRegression {

    public static void main(String[] args) {
        List<double[]> x = new ArrayList<>();
        List<Double> yR = new ArrayList<>();
        List<Double> yG = new ArrayList<>();
        List<Double> yB = new ArrayList<>();

        // 實驗資料完整輸入
        addSample(x, yR, yG, yB, 0.50, 7.5, 58.0, 103.0, 118.7);
        addSample(x, yR, yG, yB, 0.50, 13.6, 76.7, 95.0, 76.3);
        addSample(x, yR, yG, yB, 0.50, 0.3, 133.3, 100.7, 121.0);

        addSample(x, yR, yG, yB, 0.60, 5.2, 54.7, 93.0, 119.3);
        addSample(x, yR, yG, yB, 0.60, 13.6, 66.3, 84.3, 67.0);
        addSample(x, yR, yG, yB, 0.60, 0.3, 129.3, 74.0, 99.0);

        addSample(x, yR, yG, yB, 0.70, 5.2, 48.7, 80.0, 106.3);
        addSample(x, yR, yG, yB, 0.70, 13.6, 58.3, 76.3, 56.3);
        addSample(x, yR, yG, yB, 0.70, 0.3, 129.0, 79.7, 104.7);

        addSample(x, yR, yG, yB, 0.80, 5.2, 45.0, 80.3, 111.3);
        addSample(x, yR, yG, yB, 0.80, 13.6, 62.7, 81.0, 60.7);
        addSample(x, yR, yG, yB, 0.80, 0.3, 118.3, 69.7, 95.3);

        addSample(x, yR, yG, yB, 0.90, 5.2, 41.7, 68.3, 107.0);
        addSample(x, yR, yG, yB, 0.90, 13.6, 53.0, 68.3, 47.3);
        addSample(x, yR, yG, yB, 0.90, 0.3, 108.7, 53.7, 80.7);

        addSample(x, yR, yG, yB, 1.00, 5.2, 27.7, 41.7, 88.7);
        addSample(x, yR, yG, yB, 1.00, 13.6, 31.7, 47.0, 30.7);
        addSample(x, yR, yG, yB, 1.00, 0.3, 112.7, 45.7, 79.3);

        addSample(x, yR, yG, yB, 1.10, 5.2, 21.7, 32.7, 79.7);
        addSample(x, yR, yG, yB, 1.10, 13.6, 26.0, 37.0, 24.7);
        addSample(x, yR, yG, yB, 1.10, 0.3, 110.3, 32.7, 69.7);

        // 建立三個模型
        double[] coefR = fit(x, yR);
        double[] coefG = fit(x, yG);
        double[] coefB = fit(x, yB);

        // 輸出結果
        System.out.printf("R = %.2f * w + %.2f * pH + %.2f%n", coefR[0], coefR[1], coefR[2]);
        System.out.printf("G = %.2f * w + %.2f * pH + %.2f%n", coefG[0], coefG[1], coefG[2]);
        System.out.printf("B = %.2f * w + %.2f * pH + %.2f%n", coefB[0], coefB[1], coefB[2]);

        // 示範預測
        double w = 0.75;
        double pH = 5.2;
        int predR = predict(coefR, w, pH);
        int predG = predict(coefG, w, pH);
        int predB = predict(coefB, w, pH);

        System.out.printf("預測 RGB：(%d, %d, %d)%n", predR, predG, predB);
    }

    private static void addSample(List<double[]> x, List<Double> yR, List<Double> yG, List<Double> yB,
                                  double w, double pH, double r, double g, double b) {
        x.add(new double[]{w, pH});
        yR.add(r);
        yG.add(g);
        yB.add(b);
    }

    public static double[] fit(List<double[]> x, List<Double> y) {
        int n = x.size();
        double[][] X = new double[n][3];
        double[][] Y = new double[n][1];

        for (int i = 0; i < n; i++) {
            X[i][0] = x.get(i)[0]; // w%
            X[i][1] = x.get(i)[1]; // pH
            X[i][2] = 1.0;         // bias
            Y[i][0] = y.get(i);
        }

        Matrix Xmat = new Matrix(X);
        Matrix Ymat = new Matrix(Y);
        Matrix coef = Xmat.transpose().times(Xmat).inverse().times(Xmat.transpose()).times(Ymat);

        return coef.getColumnPackedCopy(); // [w%, pH, intercept]
    }

    public static int predict(double[] coef, double w, double pH) {
        double result = coef[0] * w + coef[1] * pH + coef[2];
        return Math.max(0, Math.min(255, (int) Math.round(result))); // Clamp to [0, 255]
    }
}
