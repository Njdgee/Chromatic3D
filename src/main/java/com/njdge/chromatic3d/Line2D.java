package com.njdge.chromatic3d;

import org.jzy3d.chart.AWTChart;
import org.jzy3d.chart.factories.AWTChartFactory;
import org.jzy3d.colors.Color;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot2d.primitives.LineSerie2d;

import java.util.ArrayList;
import java.util.List;
import org.jzy3d.plot3d.rendering.legends.overlay.Legend;
import org.jzy3d.plot3d.rendering.legends.overlay.LineLegendLayout;
import org.jzy3d.plot3d.rendering.legends.overlay.OverlayLegendRenderer;

public class Line2D {

    public static void main(String[] args) {
        AWTChartFactory factory = new AWTChartFactory();
        AWTChart chart = (AWTChart) factory.newChart();

        // Add 2D series
        LineSerie2d neutralLine = new LineSerie2d("Neutral");
        LineSerie2d alkalineLine = new LineSerie2d("Alkaline");
        LineSerie2d acidicLine = new LineSerie2d("Acidic");

        neutralLine.setColor(Color.GREEN);
        alkalineLine.setColor(Color.BLUE);
        acidicLine.setColor(Color.RED);

        double[] concentrations = {0.15, 0.30, 0.45, 0.60, 0.75, 0.90, 1.05};

        // Add points to each line
        addPoints(neutralLine, concentrations, new Coord3d[]{
                average(new Coord3d(66, 113, 98), new Coord3d(69, 114, 99), new Coord3d(68, 115, 99)),
                average(new Coord3d(55, 107, 91), new Coord3d(52, 103, 86), new Coord3d(56, 108, 91)),
                average(new Coord3d(51, 103, 99), new Coord3d(17, 84, 90), new Coord3d(13, 78, 82)),
                average(new Coord3d(4, 57, 64), new Coord3d(4, 57, 63), new Coord3d(5, 60, 66)),
                average(new Coord3d(17, 73, 76), new Coord3d(17, 73, 76), new Coord3d(16, 72, 69)),
                average(new Coord3d(3, 49, 61), new Coord3d(4, 52, 65), new Coord3d(4, 52, 65)),
                average(new Coord3d(3, 40, 55), new Coord3d(3, 39, 52), new Coord3d(3, 37, 50))
        });

        addPoints(alkalineLine, concentrations, new Coord3d[]{
                average(new Coord3d(87, 108, 68), new Coord3d(89, 110, 69), new Coord3d(89, 111, 70)),
                average(new Coord3d(85, 102, 62), new Coord3d(84, 102, 65), new Coord3d(82, 100, 62)),
                average(new Coord3d(45, 65, 32), new Coord3d(46, 65, 30), new Coord3d(43, 62, 27)),
                average(new Coord3d(25, 45, 18), new Coord3d(26, 47, 19), new Coord3d(24, 44, 19)),
                average(new Coord3d(45, 64, 27), new Coord3d(46, 65, 28), new Coord3d(46, 65, 28)),
                average(new Coord3d(22, 35, 17), new Coord3d(21, 34, 15), new Coord3d(22, 35, 15)),
                average(new Coord3d(13, 30, 11), new Coord3d(16, 29, 10), new Coord3d(15, 29, 10))
        });

        addPoints(acidicLine, concentrations, new Coord3d[]{
                average(new Coord3d(83, 95, 95), new Coord3d(84, 95, 95), new Coord3d(84, 96, 96)),
                average(new Coord3d(82, 93, 110), new Coord3d(82, 93, 100), new Coord3d(82, 97, 104)),
                average(new Coord3d(48, 53, 77), new Coord3d(47, 53, 74), new Coord3d(47, 51, 73)),
                average(new Coord3d(35, 39, 67), new Coord3d(36, 41, 70), new Coord3d(36, 40, 70)),
                average(new Coord3d(49, 55, 75), new Coord3d(49, 55, 76), new Coord3d(49, 56, 76)),
                average(new Coord3d(18, 22, 56), new Coord3d(17, 22, 56), new Coord3d(18, 22, 58)),
                average(new Coord3d(15, 18, 52), new Coord3d(15, 18, 52), new Coord3d(17, 19, 56))
        });

        chart.add(neutralLine);
        chart.add(alkalineLine);
        chart.add(acidicLine);

        // Legend
        List<Legend> legends = new ArrayList<>();
        legends.add(new Legend(neutralLine.getName(), neutralLine.getColor()));
        legends.add(new Legend(alkalineLine.getName(), alkalineLine.getColor()));
        legends.add(new Legend(acidicLine.getName(), acidicLine.getColor()));

        OverlayLegendRenderer legendRenderer = new OverlayLegendRenderer(legends);
        LineLegendLayout layout = legendRenderer.getLayout();
        layout.getMargin().setWidth(10);
        layout.getMargin().setHeight(10);
        layout.setBackgroundColor(Color.WHITE);
        layout.setFont(new java.awt.Font("Helvetica", java.awt.Font.PLAIN, 11));

        chart.addRenderer(legendRenderer);

        // Open as 2D chart
        chart.view2d();
        chart.open();
    }

    private static void addPoints(LineSerie2d line, double[] xValues, Coord3d[] yValues) {
        for (int i = 0; i < xValues.length; i++) {
            line.add(xValues[i], yValues[i].y);
        }
    }

    private static Coord3d average(Coord3d... coords) {
        double x = 0, y = 0, z = 0;
        for (Coord3d coord : coords) {
            x += coord.x;
            y += coord.y;
            z += coord.z;
        }
        int count = coords.length;
        return new Coord3d(x / count, y / count, z / count);
    }
}