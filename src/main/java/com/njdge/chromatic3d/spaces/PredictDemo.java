package com.njdge.chromatic3d.spaces;

import com.njdge.chromatic3d.Predictor;
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

public class PredictDemo extends AWTAbstractAnalysis {
    public static void main(String[] args) throws Exception {
        AnalysisLauncher.open(new PredictDemo());
    }

    @Override
    public void init() {
        // Create the RGB points for concentrations 0.1–1.3 and pH values 0–14
        Shape points = new Shape();
//        for (double concentration = 0.1; concentration <= 2; concentration += 0.05) { // Finer increment for concentration
//            for (double pH = 0; pH <= 14; pH += 0.05) { // Finer increment for pH
//                int r = Predictor.clamp((int) Predictor.predictR(concentration, pH));
//                int g = Predictor.clamp((int) Predictor.predictG(concentration, pH));
//                int b = Predictor.clamp((int) Predictor.predictB(concentration, pH));
//
//                Coord3d coord = new Coord3d(r, g, b); // Use RGB values as coordinates
//                Color color = new Color(r / 255.0f, g / 255.0f, b / 255.0f); // Normalize RGB values to [0, 1]
//                Point point = new Point(coord, color);
//                point.setWidth(10); // Set the point size
//                points.add(point);
//            }
//        }

        // Create a chart
        chart = AWTChartFactory.chart(Quality.Advanced());
        Font font = new Font("Arial", Font.TimesRoman_24.getStyle(), 24); // Change the size as needed
        chart.getView().getAxis().getLayout().setFont(font);
        chart.getAxisLayout().setXAxisLabel("Red");
        chart.getAxisLayout().setYAxisLabel("Green");
        chart.getAxisLayout().setZAxisLabel("Blue");
        chart.getView().setBoundManual(new BoundingBox3d(0, 255, 0, 255, 0, 255)); // Set coordinate system size
        chart.getScene().getGraph().add(points);
        chart.getView().setViewPoint(new Coord3d(0.5, 0.5, 0.5), true);
        chart.getView().setViewPositionMode(ViewPositionMode.FREE);
    }
}