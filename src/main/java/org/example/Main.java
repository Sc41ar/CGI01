package org.example;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    private Drawer panel;

    private JSlider xSlider;
    private JSlider ySlider;
    private JSlider zSlider;
    private JSlider xScaleSlider;
    private JSlider yScaleSlider;
    private JSlider zScaleSlider;
    private JSlider xOffsetSlider;
    private JSlider yOffsetSlider;
    private JSlider zOffsetSlider;


    public Main() {
        this.setSize(1000, 900);
        panel = new Drawer();
        // Create a new panel to hold the sliders
        JPanel sliderPanel = new JPanel();
        sliderPanel.setBackground(Color.LIGHT_GRAY);
        sliderPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create sliders for rotation in x, y, z
        xSlider = new JSlider(JSlider.HORIZONTAL, 0, 360, 0);
        ySlider = new JSlider(JSlider.HORIZONTAL, 0, 360, 0);
        zSlider = new JSlider(JSlider.HORIZONTAL, 0, 360, 0);

// Create sliders for offset in x and y
        xOffsetSlider = new JSlider(JSlider.HORIZONTAL, -500, 500, 0);
        yOffsetSlider = new JSlider(JSlider.HORIZONTAL, -500, 500, 0);
        zOffsetSlider = new JSlider(JSlider.HORIZONTAL, -500, 500, 0);

// Create sliders for scale in x, y, z
        xScaleSlider = new JSlider(JSlider.HORIZONTAL, 10, 100, 10);
        yScaleSlider = new JSlider(JSlider.HORIZONTAL, 10, 100, 10);
        zScaleSlider = new JSlider(JSlider.HORIZONTAL, 10, 100, 10);

// Configure the new sliders
        sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.Y_AXIS));
        xScaleSlider.setMinorTickSpacing(1);
        xScaleSlider.setPaintTicks(true);
        yScaleSlider.setMajorTickSpacing(1);
        yScaleSlider.setMinorTickSpacing(1);
        yScaleSlider.setPaintTicks(true);
        zScaleSlider.setMajorTickSpacing(1);
        zScaleSlider.setMinorTickSpacing(1);
        zScaleSlider.setPaintTicks(true);

        xOffsetSlider.setMajorTickSpacing(10);
        xOffsetSlider.setMinorTickSpacing(1);
        xOffsetSlider.setPaintTicks(true);
        yOffsetSlider.setMajorTickSpacing(10);
        yOffsetSlider.setMinorTickSpacing(1);
        yOffsetSlider.setPaintTicks(true);
        zOffsetSlider.setMajorTickSpacing(10);
        zOffsetSlider.setMinorTickSpacing(1);
        zOffsetSlider.setPaintTicks(true);

        // Add labels to the sliders
        JLabel xLabel = new JLabel("X Rotation:");
        JLabel yLabel = new JLabel("Y Rotation:");
        JLabel zLabel = new JLabel("Z Rotation:");
        JLabel xScaleLabel = new JLabel("X Scale:");
        JLabel yScaleLabel = new JLabel("Y Scale:");
        JLabel zScaleLabel = new JLabel("Z Scale:");
        JLabel xOffsetLabel = new JLabel("X Offset:");
        JLabel yOffsetLabel = new JLabel("Y Offset:");
        JLabel zOffsetLabel = new JLabel("Z Offset:");

        // Add ChangeListener to the sliders
        xSlider.addChangeListener(e -> panel.setXRotation(xSlider.getValue()));
        ySlider.addChangeListener(e -> panel.setYRotation(ySlider.getValue()));
        zSlider.addChangeListener(e -> panel.setZRotation(zSlider.getValue()));
        xScaleSlider.addChangeListener(e -> panel.setXScale(xScaleSlider.getValue()));
        yScaleSlider.addChangeListener(e -> panel.setYScale(yScaleSlider.getValue()));
        zScaleSlider.addChangeListener(e -> panel.setZScale(zScaleSlider.getValue()));
        xOffsetSlider.addChangeListener(e -> {
            panel.setXOffset(xOffsetSlider.getValue());
            panel.repaint();
        });
        yOffsetSlider.addChangeListener(e -> {
            panel.setYOffset(yOffsetSlider.getValue());
            panel.repaint();
        });
        zOffsetSlider.addChangeListener(e -> {
            panel.setZOffset(zOffsetSlider.getValue());
            panel.repaint();
        });

        // Add labels and sliders to the new panel
        sliderPanel.add(xLabel);
        sliderPanel.add(xSlider);
        JSlider xOriginSlider = new JSlider(JSlider.HORIZONTAL, -500, 500, 0);
        JSlider yOriginSlider = new JSlider(JSlider.HORIZONTAL, -500, 500, 0);

        // Configure the new sliders
        xOriginSlider.setMajorTickSpacing(10);
        xOriginSlider.setMinorTickSpacing(1);
        xOriginSlider.setPaintTicks(true);
        yOriginSlider.setMajorTickSpacing(10);
        yOriginSlider.setMinorTickSpacing(1);
        yOriginSlider.setPaintTicks(true);
        sliderPanel.add(yLabel);
        sliderPanel.add(ySlider);
        sliderPanel.add(ySlider);
        sliderPanel.add(zLabel);
        sliderPanel.add(zSlider);
        sliderPanel.add(xScaleLabel);
        sliderPanel.add(xScaleSlider);
        sliderPanel.add(yScaleLabel);
        sliderPanel.add(yScaleSlider);
        sliderPanel.add(zScaleLabel);
        sliderPanel.add(zScaleSlider);
        sliderPanel.add(xOffsetLabel);
        sliderPanel.add(xOffsetSlider);
        sliderPanel.add(yOffsetLabel);
        sliderPanel.add(yOffsetSlider);
        sliderPanel.add(zOffsetLabel);
        sliderPanel.add(zOffsetSlider);

        // Add the Drawer panel and the slider panel to the frame
        this.add(panel, BorderLayout.CENTER);
        this.add(sliderPanel, BorderLayout.LINE_END);


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        panel.initImage();
    }


    public static void main(String[] args) {
        new Main();
    }
}
