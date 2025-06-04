package com.njdge.chromatic3d;

import org.jzy3d.analysis.AnalysisLauncher;

import java.util.Arrays;
import java.util.Scanner;

public class Predictor {
    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//
//        // 讀取使用者輸入
//        System.out.print("Enter weight in grams (e.g. 0.80): ");
//        double weight = scanner.nextDouble();
//
//        System.out.print("Enter pH value (e.g. 5.2): ");
//        double pH = scanner.nextDouble();
//
//        // 預測 RGB
//        int r = predictR(weight, pH);
//        int g = predictG(weight, pH);
//        int b = predictB(weight, pH);
//
//        // 輸出結果
//        System.out.printf("Predicted RGB: (%d, %d, %d)%n", clamp(r), clamp(g), clamp(b));
    }

    //R = -65.44 * w + -4.45 * pH + 153.29
    //G = -103.13 * w + 0.31 * pH + 149.28
    //B = -77.92 * w + -3.38 * pH + 167.26
    // RGB 預測函數
    public static double predictR(double weight, double pH) {
        return Math.round(-65.44 * weight - 4.45 * pH + 153.29);
    }

    public static double predictG(double weight, double pH) {
        return Math.round(-103.13 * weight - 0.31 * pH + 149.28);
    }

    public static double predictB(double weight, double pH) {
        return Math.round(-77.92 * weight - 3.38 * pH + 167.26);
    }

    // 確保 RGB 值在 0–255 範圍內
    public static int clamp(int value) {
        return Math.max(0, Math.min(255, value));
    }
}
