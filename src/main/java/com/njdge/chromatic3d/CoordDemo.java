package com.njdge.chromatic3d;

import com.njdge.chromatic3d.object.EnvironmentManager;
import com.njdge.chromatic3d.object.impl.ExperimentSet;
import org.jzy3d.analysis.AWTAbstractAnalysis;
import org.jzy3d.analysis.AnalysisLauncher;
import org.jzy3d.chart.factories.AWTChartFactory;
import org.jzy3d.colors.Color;
import org.jzy3d.maths.BoundingBox3d;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.painters.Font;
import org.jzy3d.plot3d.rendering.canvas.Quality;
import org.jzy3d.plot3d.rendering.view.modes.ViewPositionMode;

import java.util.List;

import static com.njdge.chromatic3d.Utils.getEquation;

public class CoordDemo extends AWTAbstractAnalysis {
    public static void main(String[] args) throws Exception {
        AnalysisLauncher.open(new CoordDemo());
    }

    @Override
    public void init() {

        EnvironmentManager rgbCoord3D = getRgbCoord3D();
//
//        // Calculate angle between the two polygons
//        double angle = rgbCoord3D.calculateAngleBetweenPolygons(0, 1);
//        System.out.println("Angle between polygons: " + angle + " degrees");

        // Create a chart
        chart = AWTChartFactory.chart(Quality.Advanced());
        Font font = new Font("Arial",Font.TimesRoman_24.getStyle() , 24); // Change the size as needed
        chart.getView().getAxis().getLayout().setFont(font);
        chart.getAxisLayout().setXAxisLabel("Red");
        chart.getAxisLayout().setYAxisLabel("Green");
        chart.getAxisLayout().setZAxisLabel("Blue");
        chart.getView().setBoundManual(new BoundingBox3d(0, 255, 0, 255, 0, 255));
        chart.getScene().getGraph().add(rgbCoord3D.getShape());
        chart.getView().setViewPositionMode(ViewPositionMode.FREE);
        chart.getView().setViewPoint(new Coord3d(0.5, 0.5, 0.5), true);
        System.out.println(generateReport(rgbCoord3D));
    }

    private static EnvironmentManager getRgbCoord3D() {
        EnvironmentManager manager = new EnvironmentManager();
        manager.setShowBackground(false);
        manager.setShowEquation(false);
        manager.setShowDegrees(true);
        manager.setShowDistance(false);
        manager.setShowDistanceText(false);
        manager.setShowGravityPoint(true);
        manager.setShowMovePath(true);

        manager.setBackgroundAlpha(0.06f);
        ExperimentSet a = new ExperimentSet("A", new Color(255, 0, 0, 0.6f));
        ExperimentSet b = new ExperimentSet("B", new Color(0, 255, 0, 0.6f));
        ExperimentSet c = new ExperimentSet("C", new Color(0, 0, 255, 0.6f));

//        // Add points (test01)
//        a.addPoint(19, 52, 95);//中性(28degreeC/pH 7.5)
//        a.addPoint(25, 56, 41);//鹼性(19degreeC/pH 13.6)
//        a.addPoint(151, 68, 116);//酸性(18degreeC/pH 0.3)
//
//        b.addPoint(27, 25, 26); //偏酸性(28degreeC/pH 5.2)
//        b.addPoint(18, 23, 21); //鹼性(19degreeC/pH 13.6)
//        b.addPoint(48, 28, 30);//酸性(18degreeC/pH 0.3)
        // Add points
        //0.5g
        a.addPoint(37, 78, 119);//中性(28degreeC/pH 7.5)
        a.addPoint(48, 60, 47);//鹼性(19degreeC/pH 13.6)
        a.addPoint(129, 61, 103);//酸性(18degreeC/pH 0.3)
        //1.0g
        b.addPoint(23, 31, 79); //偏酸性(28degreeC/pH 5.2)
        b.addPoint(27, 37, 26); //鹼性(19degreeC/pH 13.6)
        b.addPoint(95, 27, 71);//酸性(18degreeC/pH 0.3)
        //1.5g
        c.addPoint(18, 17, 67); //偏酸性(28degreeC/pH 5.2)
        c.addPoint(25, 37, 24); //鹼性(19degreeC/pH 13.6)
        c.addPoint(108, 27, 28);//酸性(18degreeC/pH 0.3)


        manager.addExperimentSet(a);
        manager.addExperimentSet(b);
        manager.addExperimentSet(c);

        return manager;
    }

    public static String generateReport(EnvironmentManager manager) {
        List<ExperimentSet> experimentSets = manager.getExperimentSets();
        StringBuilder report = new StringBuilder();
        report.append("Experiment Set Analysis Report\n");
        report.append("================================\n");

        for (ExperimentSet experimentSet : experimentSets) {
            report.append("Experiment Set: ").append(experimentSet.getName()).append("\n");
            report.append("Polygon Color: ").append(experimentSet.getColor()).append("\n");
            report.append("Equation: ").append(getEquation(experimentSet.getPolygon())).append("\n");
            report.append("Gravity Point: ").append(experimentSet.getGravityPoint()).append("\n");
            report.append("Polygon Area: ").append(manager.getPolygonArea(experimentSet)).append("\n");
            report.append("Distance from Origin: ").append(manager.getDistanceFromOrigin(experimentSet)).append("\n");
            report.append("Brightness Percentage: ").append(manager.getBrightnessPercentage(experimentSet)).append("%\n");
            // Add other calculations here as needed
            report.append("--------------------------------\n");
        }
        report.append("Angle between polygons: ").append(manager.getAngleBetweenPolygons(experimentSets.get(0), experimentSets.get(1))).append(" degrees\n");
        report.append("================================\n");

        return report.toString();
    }
}