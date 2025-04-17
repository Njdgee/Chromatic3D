package com.njdge.chromatic3d;

import com.njdge.chromatic3d.object.EnvironmentManager;
import com.njdge.chromatic3d.object.impl.ExperimentSet;
import org.jzy3d.chart.AWTChart;
import org.jzy3d.chart.Chart;
import org.jzy3d.colors.Color;
import org.jzy3d.maths.BoundingBox3d;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.LineStrip;
import org.jzy3d.plot3d.primitives.Point;
import org.jzy3d.plot3d.primitives.Polygon;
import org.jzy3d.plot3d.primitives.Shape;

import java.util.List;

import static org.jzy3d.colors.Color.BLACK;

public class Utils {

    public static LineStrip createAxisLine(Coord3d start, Coord3d end) {
        LineStrip line = new LineStrip();
        line.setWidth(3);
        line.add(new Point(start, BLACK));
        line.add(new Point(end, BLACK));
        return line;
    }


    public static Shape getBackground(double backgroundAlpha) {
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
                    Color color = new Color(r / 255.0f, g / 255.0f, b / 255.0f, backgroundAlpha);
                    Point point = new Point(coord, color);
                    point.setWidth(25); // Set the point size
                    cube.add(point);
                }
            }
        }
        return cube;
    }

    public static String getEquation(Polygon polygon) {
        Coord3d normal = calculateNormal(polygon);
        Coord3d pointCoord = polygon.getPoints().get(0).getCoord();
        return String.format("%.3fx %.3fy %.3fz = %.3f", normal.x, normal.y, normal.z, normal.dot(pointCoord));
    }

    public static Coord3d calculateNormal(Polygon polygon) {
        Coord3d p1 = polygon.get(0).xyz;
        Coord3d p2 = polygon.get(1).xyz;
        Coord3d p3 = polygon.get(2).xyz;

        Coord3d v1 = p2.sub(p1);
        Coord3d v2 = p3.sub(p1);

        return v1.cross(v2).normalizeTo(1.0F);
    }
    public static Coord3d  average(Coord3d a,Coord3d b ,Coord3d c) {
        double x = (a.x + b.x + c.x)/3;
        double y = (a.y + b.y + c.y)/3;
        double z = (a.z + b.z + c.z)/3;

        return new Coord3d(x,y,z);
    }

    public static void adjustBoundingBox(EnvironmentManager manager, Chart chart) {
        List<ExperimentSet> experimentSets = manager.getExperimentSets();

        // 初始化最小值和最大值
        double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE, minZ = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE, maxY = Double.MIN_VALUE, maxZ = Double.MIN_VALUE;

        // 遍歷所有點，計算範圍
        for (ExperimentSet experimentSet : experimentSets) {
            for (Point point : experimentSet.getPoints()) {
                Coord3d coord = point.getCoord();
                minX = Math.min(minX, coord.x);
                minY = Math.min(minY, coord.y);
                minZ = Math.min(minZ, coord.z);
                maxX = Math.max(maxX, coord.x);
                maxY = Math.max(maxY, coord.y);
                maxZ = Math.max(maxZ, coord.z);
            }
        }

        // 設置邊界，增加一點緩衝區以避免邊界過於緊湊
        double padding = 10.0;
        BoundingBox3d boundingBox = new BoundingBox3d(
                minX - padding, maxX + padding,
                minY - padding, maxY + padding,
                minZ - padding, maxZ + padding
        );

        // 應用到圖表
        chart.getView().setBoundManual(boundingBox);
    }
}
