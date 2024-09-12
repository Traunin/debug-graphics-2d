package ru.vsu.cs.cg.sdm.examples;

import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class TestWindow extends JDialog {

    private JPanel contentPane;
    private static final int HEIGHT = 720;
    private static final int WIDTH = 1280;
    private final static TestPanel TEST_PANEL = new TestPanel(WIDTH, HEIGHT);
    public TestWindow() {
        setContentPane(contentPane);
        setModal(true);
        contentPane.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        contentPane.add(TEST_PANEL);
    }

    public static void main(String[] args) {
        TestWindow dialog = new TestWindow();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
