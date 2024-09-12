package ru.vsu.cs.cg.sdm.debug_graphics2d;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * {@code DebugGraphics2d} is a superset of {@link Graphics2D}. It allows you to debug more
 * efficiently by drawing points at the shape's corners. Control whether debugging is enabled by
 * calling {@code disableDebugging} and {@code enableDebugging}. Use {@code setDebugColor} to change
 * the color of the marker and {@code setDebugMarkerSize} to change the size. When drawing multiple
 * objects,
 */
public class DebugGraphics2D extends DebugGraphics2DBase {
    private final Graphics2D g2d;
    private Color debugColor = Color.RED;
    private int debugMarkerSize = 6;
    private boolean debuggingEnabled = true;
    private boolean deferMarkers = false;
    Queue<Marker> deferredMarkers = new LinkedList<>();
    public DebugGraphics2D(Graphics2D g2d) {
        super(g2d);
        this.g2d = g2d;
    }

    @Override
    public void fillRect(int x, int y, int width, int height) {
        super.fillRect(x, y, width, height);
        if (debuggingEnabled) {
            drawMarkersAtRectCorners(x, y, width, height);
        }
    }

    @Override
    public void fillOval(int x, int y, int width, int height) {
        super.fillOval(x, y, width, height);
        if (debuggingEnabled) {
            drawMarkersAtRectCorners(x, y, width, height);
        }
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {
        super.drawLine(x1, y1, x2, y2);
        if (debuggingEnabled) {
            drawCircleMarker(x1, y1);
            drawCircleMarker(x2, y2);
        }
    }

    @Override
    public void draw(Shape s) {
        super.draw(s);
        if (debuggingEnabled) {
            if (s instanceof GeneralPath path) {
                drawMarkersOnPath(path);
            }
        }
    }

    @Override
    public void fill(Shape s) {
        super.fill(s);
        if (debuggingEnabled) {
            if (s instanceof GeneralPath path) {
                drawMarkersOnPath(path);
            }
        }
    }

    public void drawMarkersAtRectCorners(int x, int y, int width, int height) {
        drawCircleMarker(x, y);
        drawCircleMarker(x + width, y);
        drawCircleMarker(x + width, y + height);
        drawCircleMarker(x, y + height);
    }

    public void drawMarkersOnPath(GeneralPath path) {
        PathIterator iterator = path.getPathIterator(null);
        double[] coords = new double[6];

        while (!iterator.isDone()) {
            int segType = iterator.currentSegment(coords);

            switch(segType) {
                case PathIterator.SEG_MOVETO, PathIterator.SEG_LINETO:
                    drawCircleMarker(coords[0], coords[1]);
                    break;
                case PathIterator.SEG_QUADTO:
                    drawCircleMarker(coords[0], coords[1]);
                    drawCircleMarker(coords[2], coords[3]);
                    break;
                case PathIterator.SEG_CUBICTO:
                    drawCircleMarker(coords[0], coords[1]);
                    drawCircleMarker(coords[2], coords[3]);
                    drawCircleMarker(coords[4], coords[5]);
                    break;
            }

            iterator.next();
        }
    }

    public void drawCircleMarker(double x, double y) {
        drawCircleMarker((int) x, (int) y);
    }

    public void drawCircleMarker(int x, int y) {
        drawCircleMarker(x, y, debugMarkerSize);
    }

    private void drawCircleMarker(Marker marker) {
        drawCircleMarker(
            marker.x(),
            marker.y(),
            marker.size(),
            marker.color(),
            marker.transform()
        );
    }

    public void drawCircleMarker(int x, int y, int size) {
        drawCircleMarker(x, y, size, debugColor);
    }

    public void drawCircleMarker(int x, int y, int size, Color color) {
        drawCircleMarker(x, y, size, color, g2d.getTransform());
    }

    public void drawCircleMarker(int x, int y, int size, Color color, AffineTransform transform) {
        // applying transform every time is inefficient
        // TODO: optimize transform application
        if (deferMarkers) {
            deferredMarkers.add(new Marker(
                    x,
                    y,
                    size,
                    color,
                    transform
            ));
            return;
        }
        g2d.setTransform(transform);
        Color currentColor = g2d.getColor();
        g2d.setColor(color);
        g2d.fillOval(x - size / 2, y - size / 2, size, size);
        g2d.setColor(currentColor);
    }

    public Color getDebugColor() {
        return debugColor;
    }

    public void setDebugColor(Color debugColor) {
        this.debugColor = debugColor;
    }

    public int getDebugMarkerSize() {
        return debugMarkerSize;
    }

    public void setDebugMarkerSize(int debugMarkerSize) {
        this.debugMarkerSize = debugMarkerSize;
    }

    public void enableDebugging() {
        debuggingEnabled = true;
    }

    public void disableDebugging() {
        debuggingEnabled = false;
    }

    public void startDeferringMarkers() {
        deferMarkers = true;
    }

    public void stopDeferringMarkers() {
        deferMarkers = false;
    }

    public void drawDeferredMarkers() {
        stopDeferringMarkers();
        while (!deferredMarkers.isEmpty()) {
            drawCircleMarker(deferredMarkers.poll());
        }
    }

    private record Marker(int x, int y, int size, Color color, AffineTransform transform) {}
}
