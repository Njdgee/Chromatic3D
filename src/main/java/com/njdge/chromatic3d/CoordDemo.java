package com.njdge.chromatic3d;

import com.njdge.chromatic3d.object.EnvironmentManager;
import com.njdge.chromatic3d.object.impl.ExperimentSet;
import org.jzy3d.analysis.AWTAbstractAnalysis;
import org.jzy3d.analysis.AnalysisLauncher;
import org.jzy3d.chart.factories.AWTChartFactory;
import org.jzy3d.colors.Color;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.painters.Font;
import org.jzy3d.plot3d.rendering.canvas.Quality;
import org.jzy3d.plot3d.rendering.view.modes.ViewPositionMode;

import java.util.List;

import static com.njdge.chromatic3d.Utils.*;

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
        Font font = new Font("Arial", Font.TimesRoman_24.getStyle(), 24); // Change the size as needed
        chart.getView().getAxis().getLayout().setFont(font);
        chart.getAxisLayout().setXAxisLabel("Red");
        chart.getAxisLayout().setYAxisLabel("Green");
        chart.getAxisLayout().setZAxisLabel("Blue");
        adjustBoundingBox(rgbCoord3D, chart);
//        chart.getView().setBoundManual(new BoundingBox3d(0, 255, 0, 255, 0, 255));
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
        manager.setShowName(true);
        manager.setShowAxisLine(false);

        manager.setBackgroundAlpha(0.06f);
        ExperimentSet a = new ExperimentSet("0.50", new Color(255, 0, 0, 0.65f));   // Bright Red
        ExperimentSet b = new ExperimentSet("0.60", new Color(0, 255, 0, 0.65f));   // Bright Green
        ExperimentSet c = new ExperimentSet("0.70", new Color(0, 0, 255, 0.65f));   // Bright Blue
        ExperimentSet d = new ExperimentSet("0.80", new Color(255, 140, 0, 0.65f)); // Darker Orange
        ExperimentSet e = new ExperimentSet("0.90", new Color(128, 0, 128, 0.65f)); // Purple
        ExperimentSet f = new ExperimentSet("1.00", new Color(0, 255, 255, 0.65f)); // Cyan
        ExperimentSet g = new ExperimentSet("1.10", new Color(255, 215, 0, 0.65f)); // Gold
//        // Add points (test01)
//        a.addPoint(19, 52, 95);//中性(28degreeC/pH 7.5)
//        a.addPoint(25, 56, 41);//鹼性(19degreeC/pH 13.6)
//        a.addPoint(151, 68, 116);//酸性(18degreeC/pH 0.3)
//
//        b.addPoint(27, 25, 26); //偏酸性(28degreeC/pH 5.2)
//        b.addPoint(18, 23, 21); //鹼性(19degreeC/pH 13.6)
//        b.addPoint(48, 28, 30);//酸性(18degreeC/pH 0.3)
        // Add points (test02)
//        //0.5g
//        a.addPoint(37, 78, 119);//中性(28degreeC/pH 7.5)
//        a.addPoint(48, 60, 47);//鹼性(19degreeC/pH 13.6)
//        a.addPoint(129, 61, 103);//酸性(18degreeC/pH 0.3)
//        //1.0g
//        b.addPoint(23, 31, 79); //中性(28degreeC/pH 5.2)
//        b.addPoint(27, 37, 26); //鹼性(19degreeC/pH 13.6)
//        b.addPoint(95, 27, 71);//酸性(18degreeC/pH 0.3)
//        //1.5g
//        c.addPoint(18, 17, 67); //中性(28degreeC/pH 5.2)
//        c.addPoint(25, 37, 24); //鹼性(19degreeC/pH 13.6)
//        c.addPoint(108, 27, 28);//酸性(18degreeC/pH 0.3)
//
//        // Add points (test03)
//        //0.15g
//        a.addPoint(average(new Coord3d(66, 113, 98), new Coord3d(69, 114, 99), new Coord3d(68, 115, 99)));//中性(28degreeC/pH 7.5)
//        a.addPoint(average(new Coord3d(87, 108, 68), new Coord3d(89, 110, 69), new Coord3d(89, 111, 70)));//鹼性(19degreeC/pH 13.6)
//        a.addPoint(average(new Coord3d(83, 95, 95), new Coord3d(84, 95, 95), new Coord3d(84, 96, 96)));//酸性(18degreeC/pH 0.3)
//        //0.30g
//        b.addPoint(average(new Coord3d(55, 107, 91), new Coord3d(52, 103, 86), new Coord3d(56, 108, 91))); //中性(28degreeC/pH 5.2)
//        b.addPoint(average(new Coord3d(85, 102, 62), new Coord3d(84, 102, 65), new Coord3d(82, 100, 62))); //鹼性(19degreeC/pH 13.6)
//        b.addPoint(average(new Coord3d(82, 93, 110), new Coord3d(82, 93, 100), new Coord3d(82, 97, 104)));//酸性(18degreeC/pH 0.3)
//        //0.45g
//        c.addPoint(average(new Coord3d(51, 103, 99), new Coord3d(17, 84, 90), new Coord3d(13, 78, 82))); //中性(28degreeC/pH 5.2)
//        c.addPoint(average(new Coord3d(45, 65, 32), new Coord3d(46, 65, 30), new Coord3d(43, 62, 27))); //鹼性(19degreeC/pH 13.6)
//        c.addPoint(average(new Coord3d(48, 53, 77), new Coord3d(47, 53, 74), new Coord3d(47, 51, 73)));//酸性(18degreeC/pH 0.3)
//        //0.60g
//        d.addPoint(average(new Coord3d(4, 57, 64), new Coord3d(4, 57, 63), new Coord3d(5, 60, 66))); //中性(28degreeC/pH 5.2)
//        d.addPoint(average(new Coord3d(25, 45, 18), new Coord3d(26, 47, 19), new Coord3d(24, 44, 19))); //鹼性(19degreeC/pH 13.6)
//        d.addPoint(average(new Coord3d(35, 39, 67), new Coord3d(36, 41, 70), new Coord3d(36, 40, 70)));//酸性(18degreeC/pH 0.3)
//        //0.75g
//        e.addPoint(average(new Coord3d(17, 73, 76), new Coord3d(17, 73, 76), new Coord3d(16, 72, 69))); //中性(28degreeC/pH 5.2)
//        e.addPoint(average(new Coord3d(45, 64, 27), new Coord3d(46, 65, 28), new Coord3d(46, 65, 28))); //鹼性(19degreeC/pH 13.6)
//        e.addPoint(average(new Coord3d(49, 55, 75), new Coord3d(49, 55, 76), new Coord3d(49, 56, 76)));//酸性(18degreeC/pH 0.3)
//        //0.90g
//        f.addPoint(average(new Coord3d(3, 49, 61), new Coord3d(4, 52, 65), new Coord3d(4, 52, 65))); //中性(28degreeC/pH 5.2)
//        f.addPoint(average(new Coord3d(22, 35, 17), new Coord3d(21, 34, 15), new Coord3d(22, 35, 15))); //鹼性(19degreeC/pH 13.6)
//        f.addPoint(average(new Coord3d(18, 22, 56), new Coord3d(17, 22, 56), new Coord3d(18, 22, 58)));//酸性(18degreeC/pH 0.3)
//        //1.05g
//        g.addPoint(average(new Coord3d(3, 40, 55), new Coord3d(3, 39, 52), new Coord3d(3, 37, 50))); //中性(28degreeC/pH 5.2)
//        g.addPoint(average(new Coord3d(13, 30, 11), new Coord3d(16, 29, 10), new Coord3d(15, 29, 10))); //鹼性(19degreeC/pH 13.6)
//        g.addPoint(average(new Coord3d(15, 18, 52), new Coord3d(15, 18, 52), new Coord3d(17, 19, 56)));//酸性(18degreeC/pH 0.3)
//
//        manager.addExperimentSet(a);
//        manager.addExperimentSet(b);
//        manager.addExperimentSet(c);
//        manager.addExperimentSet(d);
//        manager.addExperimentSet(e);
//        manager.addExperimentSet(f);
//        manager.addExperimentSet(g);
        // Add points (test04)
        //0.50g
        a.addPoint(average(new Coord3d(61, 105, 118), new Coord3d(58, 104, 120), new Coord3d(55, 100, 118)));//中性(28degreeC/pH 7.5)
        a.addPoint(average(new Coord3d(75, 93, 73), new Coord3d(76, 95, 74), new Coord3d(79, 97, 78)));//鹼性(19degreeC/pH 13.6)
        a.addPoint(average(new Coord3d(133, 102, 124), new Coord3d(135, 102, 122), new Coord3d(132, 98, 117)));//酸性(18degreeC/pH 0.3)
        //0.60g
        b.addPoint(average(new Coord3d(48, 87, 117), new Coord3d(52, 91, 112), new Coord3d(64, 101, 129))); //中性(28degreeC/pH 5.2)
        b.addPoint(average(new Coord3d(68, 86, 68), new Coord3d(68, 85, 66), new Coord3d(63, 82, 67))); //鹼性(19degreeC/pH 13.6)
        b.addPoint(average(new Coord3d(132, 75, 99), new Coord3d(132, 75, 99), new Coord3d(124, 72, 99)));//酸性(18degreeC/pH 0.3)
        //0.70g
        c.addPoint(average(new Coord3d(48, 79, 104), new Coord3d(47, 79, 106), new Coord3d(51, 82, 109))); //中性(28degreeC/pH 5.2)
        c.addPoint(average(new Coord3d(58, 76, 55), new Coord3d(57, 75, 55), new Coord3d(60, 78, 59))); //鹼性(19degreeC/pH 13.6)
        c.addPoint(average(new Coord3d(132, 82, 108), new Coord3d(130, 80, 105), new Coord3d(125, 77, 101)));//酸性(18degreeC/pH 0.3)
        //0.80g
        d.addPoint(average(new Coord3d(42, 78, 110), new Coord3d(49, 84, 114), new Coord3d(44, 79, 110))); //中性(28degreeC/pH 5.2)
        d.addPoint(average(new Coord3d(67, 85, 63), new Coord3d(62, 81, 63), new Coord3d(59, 77, 56))); //鹼性(19degreeC/pH 13.6)
        d.addPoint(average(new Coord3d(119, 71, 96), new Coord3d(116, 68, 93), new Coord3d(120, 70, 97)));//酸性(18degreeC/pH 0.3)
        //0.90g
        e.addPoint(average(new Coord3d(41, 66, 103), new Coord3d(40, 70, 110), new Coord3d(44, 69, 108))); //中性(28degreeC/pH 5.2)
        e.addPoint(average(new Coord3d(52, 67, 46), new Coord3d(55, 70, 49), new Coord3d(52, 68, 47))); //鹼性(19degreeC/pH 13.6)
        e.addPoint(average(new Coord3d(102, 50, 76), new Coord3d(112, 55, 82), new Coord3d(112, 56, 84)));//酸性(18degreeC/pH 0.3)
        //1.00g
        f.addPoint(average(new Coord3d(30, 44, 89), new Coord3d(28, 42, 89), new Coord3d(25, 39, 88))); //中性(28degreeC/pH 5.2)
        f.addPoint(average(new Coord3d(33, 48, 32), new Coord3d(30, 46, 30), new Coord3d(32, 47, 30))); //鹼性(19degreeC/pH 13.6)
        f.addPoint(average(new Coord3d(111, 47, 78), new Coord3d(111, 49, 82), new Coord3d(116, 41, 78)));//酸性(18degreeC/pH 0.3)
        //1.10g
        g.addPoint(average(new Coord3d(20, 31, 77), new Coord3d(20, 30, 78), new Coord3d(25, 37, 84))); //中性(28degreeC/pH 5.2)
        g.addPoint(average(new Coord3d(26, 31, 25), new Coord3d(24, 39, 24), new Coord3d(28, 41, 25))); //鹼性(19degreeC/pH 13.6)
        g.addPoint(average(new Coord3d(107, 31, 68), new Coord3d(113, 35, 71), new Coord3d(111, 32, 70)));//酸性(18degreeC/pH 0.3)

        manager.addExperimentSet(a);
        manager.addExperimentSet(b);
        manager.addExperimentSet(c);
        manager.addExperimentSet(d);
        manager.addExperimentSet(e);
        manager.addExperimentSet(f);
        manager.addExperimentSet(g);

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
        for (int i = 0; i < experimentSets.size(); i++) {
            if (i + 1 < experimentSets.size()) {
                int j = i + 1;
                report.append("Angles between " + experimentSets.get(i).getName() + " and " + experimentSets.get(j).getName() + ": ").append(manager.getAngleBetweenPolygons(experimentSets.get(i), experimentSets.get(i + 1))).append("degrees\n");
            }
        }
        report.append("--------------------------------\n");

        for (int i = 0; i < experimentSets.size(); i++) {
            if (i + 1 < experimentSets.size()) {
                int j = i + 1;
                report.append("Cos Theta between " + experimentSets.get(i).getName() + " and " + experimentSets.get(j).getName() + ": ").append(Math.abs(Math.cos(Math.toRadians(manager.getAngleBetweenPolygons(experimentSets.get(i), experimentSets.get(i + 1)))))).append("\n");
            }
        }
        report.append("--------------------------------\n");
        int count = 0;
        double totalCosTheta = 0.0;
        double totalAngle = 0.0;

        for (int i = 0; i < experimentSets.size(); i++) {
            for (int j = i + 1; j < experimentSets.size(); j++) {
                double angle = manager.getAngleBetweenPolygons(experimentSets.get(i), experimentSets.get(j));
                double cosTheta = Math.cos(Math.toRadians(angle));
                totalCosTheta += cosTheta;
                totalAngle += angle;
                count++;
            }
        }

        double averageCosTheta = count > 0 ? totalCosTheta / count : 0;
        double averageAngle = count > 0 ? totalAngle / count : 0;

        report.append("Average Cos Theta for all combinations: ").append(averageCosTheta).append("\n");
        report.append("Average Angle for all combinations: ").append(averageAngle).append(" degrees\n");
        report.append("================================\n");

        return report.toString();
    }

}