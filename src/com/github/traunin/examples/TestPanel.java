package com.github.traunin.examples;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import javax.swing.JPanel;
import com.github.traunin.debug_graphics_2d.DebugGraphics2D;

public class TestPanel extends JPanel {
    private final int width;
    private final int height;
    GeneralPath leafPath = new GeneralPath();

    public TestPanel(int width, int height) {
        this.width = width;
        this.height = height;

        leafPath.moveTo(0, 0);
        leafPath.curveTo(20, 0, 20, -40, 50, -40);
        leafPath.curveTo(30, -30, 30, 0, 0, 0);
        leafPath.closePath();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        DebugGraphics2D g2d = new DebugGraphics2D((Graphics2D) g);
        g2d.startDeferringMarkers();
        g2d.fillRect(20, 20, 300, 100);
        g2d.translate(50, 50);
        g2d.setColor(Color.green);
        g2d.fill(leafPath);
        g2d.setColor(Color.blue);
        g2d.fillOval(300, 300, 100, 100);
        g2d.setDebugColor(Color.yellow);
        g2d.disableDebugging();
        g2d.fillRect(100, 100, 100, 100);
        g2d.drawDeferredMarkers();
    }
}
