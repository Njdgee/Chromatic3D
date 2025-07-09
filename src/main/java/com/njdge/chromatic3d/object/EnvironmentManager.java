package com.njdge.chromatic3d.object;

import com.njdge.chromatic3d.ChartType;
import com.njdge.chromatic3d.Predictor;
import com.njdge.chromatic3d.object.impl.ExperimentSet;
import lombok.Data;
import org.jzy3d.colors.Color;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.painters.Font;
import org.jzy3d.plot3d.primitives.*;
import org.jzy3d.plot3d.text.drawable.DrawableText;

import java.util.ArrayList;
import java.util.List;

import static com.njdge.chromatic3d.Predictor.*;
import static com.njdge.chromatic3d.Utils.*;
import static org.jzy3d.colors.Color.BLACK;
import static org.jzy3d.colors.Color.RED;

@Data
public class EnvironmentManager {
    private final List<ExperimentSet> experimentSets;
    private boolean showBackground, showPolygon,
            showEquation, showDegrees,
            showDistance, showGravityPoint,
            showDistanceText, showMovePath,
            showName, showAxisLine,
            showNormalVector,showPredict,
            showCross,showPredictColor
            = true;
    private ChartType type = ChartType.ORIGINAL_SIZE;
    private float backgroundAlpha = 0.5f;
    private Point predictPoint;
    private double averageCosTheta, averageAngle;

    public EnvironmentManager() {
        experimentSets = new ArrayList<>();
    }

    public void addExperimentSet(ExperimentSet experimentSet) {
        experimentSets.add(experimentSet);
        experimentSet.generatePolygon();
    }

    public Shape getShape() {
        Shape shape = new Shape();
        if (showPredictColor) {
            for (double concentration = 0.1; concentration <= 2; concentration += 0.05) {
                for (double pH = 0; pH <= 14; pH += 0.05) {
                    int r = Predictor.clamp((int) Predictor.predictR(concentration, pH));
                    int g = Predictor.clamp((int) Predictor.predictG(concentration, pH));
                    int b = Predictor.clamp((int) Predictor.predictB(concentration, pH));

                    Coord3d coord = new Coord3d(r, g, b);
                    Color color = new Color(r / 255.0f, g / 255.0f, b / 255.0f);
                    Point point = new Point(coord, color);
                    point.setWidth(10);
                    shape.add(point);
                }
            }
        }
        if (showBackground) {
            shape.add(getBackground(backgroundAlpha));
        }
        for (ExperimentSet experimentSet : experimentSets) {
            if (showPolygon) {
                shape.add(experimentSet.getPolygon());
                for (Point point : experimentSet.getPoints()) {
                    shape.add(point);
                }
            }
            if (showEquation) {
                DrawableText text = new DrawableText(getEquation(experimentSet.getPolygon()), experimentSet.getPolygon().getPoints().get(0).getCoord().add(3,0,3), BLACK);
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
            if (showName) {
                Coord3d gravityPoint = experimentSet.getGravityPoint();
                DrawableText text = new DrawableText(experimentSet.getName(), gravityPoint.add(3, 3, 8), BLACK);
                Font font = new Font("Arial", Font.TimesRoman_24.getStyle(), 24); // Change the size as needed
                text.setDefaultFont(font);
                shape.add(text);
            }
        }


        int count = 0;
        double totalCosTheta = 0.0;
        double totalAngle = 0.0;

        for (int i = 0; i < experimentSets.size(); i++) {
            for (int j = i + 1; j < experimentSets.size(); j++) {
                double angle = getAngleBetweenPolygons(experimentSets.get(i), experimentSets.get(j));
                double cosTheta = Math.cos(Math.toRadians(angle));
                totalCosTheta += cosTheta;
                totalAngle += angle;
                count++;
            }
        }

        this.averageCosTheta = count > 0 ? totalCosTheta / count : 0;
        this.averageAngle = count > 0 ? totalAngle / count : 0;

        if (showDegrees) {
            DrawableText text = new DrawableText("Angle between polygons (average): " + getAverageAngle() + "°", new Coord3d(0, 30, 135), BLACK);
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

                if (showDistanceText) {
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

                    previousCoord = currentCoord;
                }

                line.setWidth(4);
                shape.add(line);
            }
        }
        if (showAxisLine) {
            shape.add(createAxisLine(new Coord3d(0, 0, 0), new Coord3d(255, 0, 0))); // X axis
            shape.add(createAxisLine(new Coord3d(0, 0, 0), new Coord3d(0, 255, 0))); // Y axis
            shape.add(createAxisLine(new Coord3d(0, 0, 0), new Coord3d(0, 0, 255))); // Z axis
        }

        if (showCross) {
            Coord3d fixedPoint = new Coord3d(50, 60, 50);
            for (ExperimentSet experimentSet : experimentSets) {
                Polygon polygon = experimentSet.getPolygon();
                if (polygon.getPoints().size() >= 3) {
                    Coord3d p1 = polygon.getPoints().get(0).getCoord();
                    Coord3d p2 = polygon.getPoints().get(1).getCoord();
                    Coord3d p3 = polygon.getPoints().get(2).getCoord();

                    Coord3d v1 = p2.sub(p1);
                    Coord3d v2 = p3.sub(p1);

                    Coord3d normal = v1.cross(v2).normalizeTo(1.0F);

                    float len = 100;
                    float d = -normal.dot(p1);
                    float t = -(normal.dot(fixedPoint) + d) / normal.dot(normal);

                    if (t > 0) {
                        len = t;
                    }
                    Coord3d adjustedNormal = normal.normalizeTo(len);

                    LineStrip normalVectorLine = new LineStrip();
                    normalVectorLine.add(new Point(fixedPoint, RED)); // Start at fixedPoint
                    normalVectorLine.add(new Point(fixedPoint.add(adjustedNormal), Color.RED)); // End at adjusted direction
                    Point point = new Point(fixedPoint.add(adjustedNormal), BLACK, 10);
                    normalVectorLine.setWidth(5);
                    shape.add(normalVectorLine);
                    shape.add(point);
                }
                if (experimentSets.size() == 2) {
                    Polygon polygon1 = experimentSets.get(0).getPolygon();
                    Polygon polygon2 = experimentSets.get(1).getPolygon();

                    if (polygon1.getPoints().size() >= 3 && polygon2.getPoints().size() >= 3) {
                        Coord3d p1_1 = polygon1.getPoints().get(0).getCoord();
                        Coord3d p2_1 = polygon1.getPoints().get(1).getCoord();
                        Coord3d p3_1 = polygon1.getPoints().get(2).getCoord();
                        Coord3d v1_1 = p2_1.sub(p1_1);
                        Coord3d v2_1 = p3_1.sub(p1_1);
                        Coord3d normal1 = v1_1.cross(v2_1).normalizeTo(1.0F);

                        Coord3d p1_2 = polygon2.getPoints().get(0).getCoord();
                        Coord3d p2_2 = polygon2.getPoints().get(1).getCoord();
                        Coord3d p3_2 = polygon2.getPoints().get(2).getCoord();
                        Coord3d v1_2 = p2_2.sub(p1_2);
                        Coord3d v2_2 = p3_2.sub(p1_2);
                        Coord3d normal2 = v1_2.cross(v2_2).normalizeTo(1.0F);

                        double dotProduct = normal1.dot(normal2);
                        double magnitude1 = normal1.distance(new Coord3d(0, 0, 0));
                        double magnitude2 = normal2.distance(new Coord3d(0, 0, 0));
                        double angle = Math.acos(dotProduct / (magnitude1 * magnitude2)) * (180.0 / Math.PI);

                        Coord3d midpoint = new Coord3d(50, 63, 56);
                        DrawableText angleText = new DrawableText(String.format("Angle: %.2f°", angle), midpoint, RED);
                        Font font = new Font("Arial", Font.TimesRoman_24.getStyle(), 24);
                        angleText.setDefaultFont(font);
                        shape.add(angleText);
                    }
                }
            }
        }


        if (showNormalVector) {
            for (int i = 0; i < experimentSets.size(); i++) {
                Polygon polygon1 = experimentSets.get(i).getPolygon();
                Coord3d normal1 = calculateNormal(polygon1).normalizeTo(100);

                double x1 = Math.abs(normal1.x);
                double y1 = Math.abs(normal1.y);
                double z1 = Math.abs(normal1.z);
                Coord3d adjustedNormal1 = new Coord3d(x1, y1, z1).mul(40);

                Coord3d origin = new Coord3d(0, 0, 0);

                LineStrip normalVectorLine1 = new LineStrip();
                normalVectorLine1.add(new Point(origin, BLACK));
                normalVectorLine1.add(new Point(adjustedNormal1, BLACK));
                normalVectorLine1.setWidth(3);
                shape.add(normalVectorLine1);

                DrawableText text = new DrawableText(experimentSets.get(i).getName(), adjustedNormal1.add(-0.5, 0.9, 0.8), new Color(0,0,0,0.3));
                Font font = new Font("Arial", Font.TimesRoman_24.getStyle(), 36);
                text.setDefaultFont(font);
                Point point1 = new Point(adjustedNormal1, experimentSets.get(i).getColor(), 14);
                shape.add(point1);
                shape.add(text);

                if (i + 1 < experimentSets.size()) {
                    Polygon polygon2 = experimentSets.get(i + 1).getPolygon();
                    Coord3d normal2 = calculateNormal(polygon2).normalizeTo(100);

                    double x2 = Math.abs(normal2.x);
                    double y2 = Math.abs(normal2.y);
                    double z2 = Math.abs(normal2.z);
                    Coord3d adjustedNormal2 = new Coord3d(x2, y2, z2);

                    LineStrip normalVectorLine2 = new LineStrip();
                    normalVectorLine2.add(new Point(origin, BLACK));
                    normalVectorLine2.add(new Point(adjustedNormal2, BLACK));
                    normalVectorLine2.setWidth(3);
                    shape.add(normalVectorLine2);

                    Point point2 = new Point(adjustedNormal2, experimentSets.get(i + 1).getColor(), 14);
                    shape.add(point2);

                    double dotProduct = adjustedNormal1.dot(adjustedNormal2);
                    double magnitude1 = adjustedNormal1.distance(origin);
                    double magnitude2 = adjustedNormal2.distance(origin);
                    double angle = Math.acos(dotProduct / (magnitude1 * magnitude2)) * (180.0 / Math.PI);

                    double proportion1 = 0.4 + (0.5 * Math.random());
                    double proportion2 = 0.4 + (0.5 * Math.random());

                    Coord3d scaledPoint1 = adjustedNormal1.mul(proportion1);
                    Coord3d scaledPoint2 = adjustedNormal2.mul(proportion2);

                    LineStrip arc = new LineStrip();
                    int arcPoints = 50;
                    for (int j = 0; j <= arcPoints; j++) {
                        double t = (double) j / arcPoints;

                        Coord3d arcPoint = scaledPoint1.mul(1 - t).add(scaledPoint2.mul(t)).normalizeTo(100);
                        arc.add(new Point(arcPoint, new Color(1,0,0,0.3)));
                    }
                    arc.setWidth(4);
                    shape.add(arc);

                    Coord3d midpoint = scaledPoint1.add(scaledPoint2).div(2).normalizeTo(100);
                    Point midpointPoint = new Point(midpoint, RED, 10);
                    DrawableText angleText = new DrawableText(String.format("%.2f°", angle), midpoint.add(0, 0.9, 0.8), RED);
                    angleText.setDefaultFont(font);
                    shape.add(angleText);
                    shape.add(midpointPoint);
                }
            }
        }

        if (showPredict) {
            shape.add(predictPoint);
        }



        return shape;
    }

    public void setPredict(double weight,double pH) {
        Coord3d coord3d = new Coord3d(predictR(weight,pH),predictG(weight,pH),predictB(weight,pH));
        System.out.println(coord3d);
        this.predictPoint = new Point(coord3d);
        predictPoint.setWidth(16);
        predictPoint.setColor(BLACK);
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

    public double getPolygonArea(ExperimentSet experimentSet) {
        Polygon polygon = experimentSet.getPolygon();
        Coord3d p1 = polygon.get(0).xyz;
        Coord3d p2 = polygon.get(1).xyz;
        Coord3d p3 = polygon.get(2).xyz;

        Coord3d v1 = p2.sub(p1);
        Coord3d v2 = p3.sub(p1);

        Coord3d crossProduct = v1.cross(v2);
        return Double.parseDouble(String.format("%.4f", crossProduct.distance(new Coord3d(0, 0, 0)) / 2));
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