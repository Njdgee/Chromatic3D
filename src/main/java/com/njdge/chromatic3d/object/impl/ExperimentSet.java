package com.njdge.chromatic3d.object.impl;

import lombok.Data;
import org.jzy3d.colors.Color;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.Point;
import org.jzy3d.plot3d.primitives.Polygon;

import java.util.List;
@Data
public class ExperimentSet {
    private final List<Point> points;
    private Polygon polygon;
    private final Color color;
    private String name;

    public ExperimentSet(String name, Color color) {
        points = new java.util.ArrayList<>();
        this.color = color;
        this.name = name;
    }

    public void addPoint(float r, float g, float b) {
        Point p = new Point(new Coord3d(r,g,b),color);
        p.setWidth(10);
        points.add(p);
    }

    public void generatePolygon() {
        if (points.size() != 3) {
            throw new IllegalArgumentException("List must have exactly 3 elements.");
        }
        polygon = new Polygon();
        for (Point p : points) {
            polygon.add(p);
        }
    }

    public Coord3d getGravityPoint() {
        float x = 0;
        float y = 0;
        float z = 0;
        for (Point p : points) {
            x += p.xyz.x;
            y += p.xyz.y;
            z += p.xyz.z;
        }
        return new Coord3d(x / points.size(), y / points.size(), z / points.size());
    }
}
