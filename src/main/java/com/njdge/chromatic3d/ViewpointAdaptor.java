package com.njdge.chromatic3d;

import org.jzy3d.chart.Chart;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.rendering.view.View;

public class ViewpointAdaptor {
    private final Chart chart;

    public ViewpointAdaptor(Chart chart) {
        this.chart = chart;
        startMonitoring();
    }

    private void startMonitoring() {
        new Thread(() -> {
            while (true) {
                try {
                    View view = chart.getView();
                    Coord3d viewpoint = view.getViewPoint();
                    System.out.println("Current Viewpoint: " + viewpoint);
                    Thread.sleep(1000); // Output every second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}