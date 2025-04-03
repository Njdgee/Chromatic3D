package com.njdge.chromatic3d.spaces;

import org.jzy3d.analysis.AWTAbstractAnalysis;
import org.jzy3d.analysis.AnalysisLauncher;
import org.jzy3d.chart.factories.AWTChartFactory;
import org.jzy3d.colors.Color;
import org.jzy3d.maths.BoundingBox3d;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.painters.Font;
import org.jzy3d.plot3d.primitives.Point;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.rendering.canvas.Quality;
import org.jzy3d.plot3d.rendering.view.modes.ViewPositionMode;

public class RGBCubeDemo extends AWTAbstractAnalysis {
    public static void main(String[] args) throws Exception {
        AnalysisLauncher.open(new RGBCubeDemo());
    }

    @Override
    public void init() {
        // Define the ranges for R, G, B
        int[] range = new int[26];
        for (int i = 0; i < 26; i++) {
            range[i] = i * 10;
        }

        // Create the RGB cube
        Shape cube = new Shape();
        for (int r : range) {
            for (int g : range) {
                for (int b : range) {
                    Coord3d coord = new Coord3d(r, g, b);
                    Color color = new Color(r / 255.0f, g / 255.0f, b / 255.0f);
                    Point point = new Point(coord, color);
                    point.setWidth(25); // Set the point size
                    cube.add(point);
                }
            }
        }

        // Create a chart
        chart = AWTChartFactory.chart(Quality.Advanced());
        Font font = new Font("Arial",Font.TimesRoman_24.getStyle() , 24); // Change the size as needed
        chart.getView().getAxis().getLayout().setFont(font);
        chart.getAxisLayout().setXAxisLabel("Red");
        chart.getAxisLayout().setYAxisLabel("Green");
        chart.getAxisLayout().setZAxisLabel("Blue");
        chart.getView().setBoundManual(new BoundingBox3d(0, 255, 0, 255, 0, 255));
        chart.getScene().getGraph().add(cube);
        chart.getView().setViewPoint(new Coord3d(0.5, 0.5, 0.5), true);
        chart.getView().setViewPositionMode(ViewPositionMode.FREE);
    }
}