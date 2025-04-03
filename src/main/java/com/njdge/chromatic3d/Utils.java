package com.njdge.chromatic3d;

import org.jzy3d.colors.Color;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.LineStrip;
import org.jzy3d.plot3d.primitives.Point;
import org.jzy3d.plot3d.primitives.Polygon;
import org.jzy3d.plot3d.primitives.Shape;

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
}
