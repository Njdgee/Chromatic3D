package com.njdge.chromatic3d.object;

import com.njdge.chromatic3d.object.impl.ExperimentSet;
import lombok.Data;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.painters.Font;
import org.jzy3d.plot3d.primitives.LineStrip;
import org.jzy3d.plot3d.primitives.Point;
import org.jzy3d.plot3d.primitives.Polygon;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.text.drawable.DrawableText;

import java.util.ArrayList;
import java.util.List;

import static com.njdge.chromatic3d.Utils.*;
import static org.jzy3d.colors.Color.BLACK;
import static org.jzy3d.colors.Color.RED;

@Data
public class EnvironmentManager {
    private final List<ExperimentSet> experimentSets;
    private boolean showBackground, showEquation, showDegrees, showDistance, showGravityPoint,showDistanceText,showMovePath,showName,showAxisLine = true;
    private float backgroundAlpha = 0.5f;

    public EnvironmentManager() {
        experimentSets = new ArrayList<>();
    }

    public void addExperimentSet(ExperimentSet experimentSet) {
        experimentSets.add(experimentSet);
        experimentSet.generatePolygon();
    }

    public Shape getShape() {
        Shape shape = new Shape();
        if (showBackground) {
            shape.add(getBackground(backgroundAlpha));
        }

        for (ExperimentSet experimentSet : experimentSets) {
            shape.add(experimentSet.getPolygon());
            if (showEquation) {
                DrawableText text = new DrawableText(getEquation(experimentSet.getPolygon()), experimentSet.getPolygon().getPoints().get(0).getCoord(), BLACK);
                Font font = new Font("Arial", Font.TimesRoman_24.getStyle(), 24); // Change the size as needed
                text.setDefaultFont(font);
                shape.add(text);
            }
            if (showGravityPoint) {
                Coord3d gravityPoint = experimentSet.getGravityPoint();
                Point point = new Point(gravityPoint, BLACK);
                point.setWidth(10);
                shape.add(point);
            }

            for (Point point : experimentSet.getPoints()) {
                shape.add(point);
            }

            if (showName) {
                Coord3d gravityPoint = experimentSet.getGravityPoint();
                // 調整文字位置，增加 Z 軸偏移量
                DrawableText text = new DrawableText(experimentSet.getName(), gravityPoint.add(3, 3, 5), BLACK);
                Font font = new Font("Arial", Font.TimesRoman_24.getStyle(), 24); // Change the size as needed
                text.setDefaultFont(font);
                shape.add(text);
            }
        }

        if (showDegrees) {
            DrawableText text = new DrawableText("Angle between polygons: " + getAngleBetweenPolygons(experimentSets.get(0), experimentSets.get(1)) + "°", new Coord3d(0, 0, 120), BLACK);
            Font font = new Font("Arial", Font.TimesRoman_24.getStyle(), 24); // Change the size as needed
            text.setDefaultFont(font);
            shape.add(text);
        }

        if (showDistance) {
            for (ExperimentSet experimentSet : experimentSets) {
                Coord3d origin = new Coord3d(0, 0, 0);
                Coord3d gravityPoint = experimentSet.getGravityPoint();
                LineStrip line = new LineStrip();
                line.add(new Point(origin, BLACK));
                line.add(new Point(gravityPoint, BLACK));
                line.setWidth(4);
                shape.add(line);

                if(showDistanceText) {
                    DrawableText text = new DrawableText("Distance: " + getDistanceFromOrigin(experimentSet), gravityPoint.add(new Coord3d(10, 10, 0)), BLACK);
                    Font font = new Font("Arial", Font.TimesRoman_24.getStyle(), 24); // Change the size as needed
                    text.setDefaultFont(font);
                    shape.add(text);
                }

            }
        }

        if (showMovePath) {
            for (int i = 0; i < 3; i++) {
                LineStrip line = new LineStrip();
                Coord3d previousCoord = null;

                for (ExperimentSet experimentSet : experimentSets) {
                    Coord3d currentCoord = experimentSet.getPoints().get(i).getCoord();

                    if (previousCoord != null) {
                        if (currentCoord.y < previousCoord.y) {
                            LineStrip redLine = new LineStrip();
                            redLine.add(new Point(previousCoord, RED));
                            redLine.add(new Point(currentCoord, RED));
                            redLine.setColor(BLACK);
                            redLine.setWidth(4);
                            shape.add(redLine);
                        } else {
                            line.add(new Point(previousCoord, RED));
                            line.add(new Point(currentCoord, RED));
                        }
                    }

                    previousCoord = currentCoord; // 更新上一個節點
                }

                line.setWidth(4);
                shape.add(line);
            }
        }
        if(showAxisLine) {
            shape.add(createAxisLine(new Coord3d(0, 0, 0), new Coord3d(255, 0, 0))); // X axis
            shape.add(createAxisLine(new Coord3d(0, 0, 0), new Coord3d(0, 255, 0))); // Y axis
            shape.add(createAxisLine(new Coord3d(0, 0, 0), new Coord3d(0, 0, 255))); // Z axis
        }


        return shape;
    }

    public double getAngleBetweenPolygons(ExperimentSet a, ExperimentSet b) {

        Polygon poly1 = a.getPolygon();
        Polygon poly2 = b.getPolygon();

        Coord3d normal1 = calculateNormal(poly1);
        Coord3d normal2 = calculateNormal(poly2);

        double dotProduct = normal1.dot(normal2);
        double magnitude1 = normal1.distance(new Coord3d(0, 0, 0));
        double magnitude2 = normal2.distance(new Coord3d(0, 0, 0));

        return Double.parseDouble(String.format("%.4f", Math.acos(dotProduct / (magnitude1 * magnitude2)) * (180.0 / Math.PI)));
    }


    public double getDistanceFromOrigin(ExperimentSet a) {
        return Double.parseDouble(String.format("%.4f", a.getGravityPoint().distance(new Coord3d(0, 0, 0))));
    }

    public double getBrightnessPercentage(ExperimentSet experimentSet) {
        Coord3d gravityPoint = experimentSet.getGravityPoint();
        double r = gravityPoint.x;
        double g = gravityPoint.y;
        double b = gravityPoint.z;
        double luma = 0.2126 * r + 0.7152 * g + 0.0722 * b;
        return (luma / 255.0) * 100;
    }

    public double  getPolygonArea(ExperimentSet experimentSet) {
        Polygon polygon = experimentSet.getPolygon();
        Coord3d p1 = polygon.get(0).xyz;
        Coord3d p2 = polygon.get(1).xyz;
        Coord3d p3 = polygon.get(2).xyz;

        Coord3d v1 = p2.sub(p1);
        Coord3d v2 = p3.sub(p1);

        Coord3d crossProduct = v1.cross(v2);
        return Double.parseDouble(String.format("%.4f",  crossProduct.distance(new Coord3d(0, 0, 0)) / 2));
    }

//    public void addPoint(float r, float g, float b, Color color) {
//        Coord3d coord = new Coord3d(r, g, b);
//        Point point = new Point(coord, color);
//        points.add(point);
//    }
//
//    public void addPoints(ExperiSet experimentSet) {
//        points.addAll(experimentSet.getPoints());
//    }

//    public void addPolygon(float[] r, float[] g, float[] b) {
//        if (r.length != 3 || g.length != 3 || b.length != 3) {
//            throw new IllegalArgumentException("Each array must have exactly 3 elements.");
//        }
//        Polygon polygon = new Polygon();
//        for (int i = 0; i < 3; i++) {
//            Coord3d coord = new Coord3d(r[i], g[i], b[i]);
//            Color color = new Color(r[i] / 255.0f, g[i] / 255.0f, b[i] / 255.0f);
//            polygon.add(new Point(coord, color));
//        }
//        polygons.add(polygon);
//    }

//    public void addPolygon(List<Point> points) {
//        if (points.size() != 3) {
//            throw new IllegalArgumentException("List must have exactly 3 elements.");
//        }
//        Polygon polygon = new Polygon();
//        for (Point p : points) {
//            polygon.add(p);
//        }
//        polygons.add(polygon);
//    }

}