package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

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
        zOffsetSlider = new JSlider(JSlider.HORIZONTAL, 1, 100, 1);

// Create sliders for scale in x, y, z
        xScaleSlider = new JSlider(JSlider.HORIZONTAL, 25, 200, 25);
        yScaleSlider = new JSlider(JSlider.HORIZONTAL, 25, 200, 25);
        zScaleSlider = new JSlider(JSlider.HORIZONTAL, 25, 200, 25);

// Configure the new sliders
        sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.Y_AXIS));
        xScaleSlider.setMinorTickSpacing(1);
        xScaleSlider.setPaintTicks(true);
        yScaleSlider.setMajorTickSpacing(1);
        yScaleSlider.setMinorTickSpacing(1);
        yScaleSlider.setPaintTicks(true);


        xOffsetSlider.setMajorTickSpacing(10);
        xOffsetSlider.setMinorTickSpacing(1);
        xOffsetSlider.setPaintTicks(true);
        yOffsetSlider.setMajorTickSpacing(10);
        yOffsetSlider.setMinorTickSpacing(1);
        yOffsetSlider.setPaintTicks(true);

        // Add labels to the sliders
        JLabel xLabel = new JLabel("X Rotation:");
        JLabel yLabel = new JLabel("Y Rotation:");
        JLabel zLabel = new JLabel("Z Rotation:");
        JLabel xScaleLabel = new JLabel("X Scale:");
        JLabel yScaleLabel = new JLabel("Y Scale:");
        JLabel xOffsetLabel = new JLabel("X Offset:");
        JLabel yOffsetLabel = new JLabel("Y Offset:");

        // Add ChangeListener to the sliders
        xSlider.addChangeListener(e -> panel.setXRotation(xSlider.getValue()));
        ySlider.addChangeListener(e -> panel.setYRotation(ySlider.getValue()));
        zSlider.addChangeListener(e -> panel.setZRotation(zSlider.getValue()));
        xScaleSlider.addChangeListener(e -> panel.setXScale(xScaleSlider.getValue()));
        yScaleSlider.addChangeListener(e -> panel.setYScale(yScaleSlider.getValue()));
        xOffsetSlider.addChangeListener(e -> panel.setXOffset(xOffsetSlider.getValue()));
        yOffsetSlider.addChangeListener(e -> panel.setYOffset(yOffsetSlider.getValue()));

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
        sliderPanel.add(xOffsetLabel);
        sliderPanel.add(xOffsetSlider);
        sliderPanel.add(yOffsetLabel);
        sliderPanel.add(yOffsetSlider);

        // Add the Drawer panel and the slider panel to the frame
        this.add(panel, BorderLayout.CENTER);
        this.add(sliderPanel, BorderLayout.LINE_END);
        panel.invalidate();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);


        JButton button = new JButton("Rotation");

        // Add an ActionListener to the button
        button.addActionListener(e -> {
            // Reset the sliders to their default values
            xSlider.setValue(0);
            ySlider.setValue(0);
            zSlider.setValue(0);
            xScaleSlider.setValue(25);
            yScaleSlider.setValue(25);
            xOffsetSlider.setValue(100);
            yOffsetSlider.setValue(100);
            panel.setXOffset(50);
            panel.setYOffset(50);
            panel.setXRotation(10);
            // Number of frames in the animation


            Thread t = new Thread(() -> {
                try {
                    int frames = 1000;
                    for (int i = 0; i < frames; i++) {
                        var ax = panel.getAngleX();
                        var ay = panel.getAngleY();
                        var az = panel.getAngleZ();
                        Random rand = new Random();
                        panel.setAngleX(ax += 3);
                        panel.setAngleY(ay += 3);
                        panel.setAngleZ(az += 3);

                        var xo = panel.getXOffset();
                        var yo = panel.getYOffset();


                        if (i % 100 == 0) {
                            int randomXOffset = rand.nextInt(21) - 10; // Generates a random number between -10 and 10
                            int randomYOffset = rand.nextInt(21) - 10;
                            panel.setXOffset(xo += randomXOffset);
                            panel.setYOffset(yo += randomYOffset);
                        }

                        panel.initSceneVerticles();

                        Thread.sleep(100);

                        SwingUtilities.invokeAndWait(() -> {
                            panel.initSceneVerticles();
                            panel.repaint();
                        });
                    }
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            });

            t.start();

            panel.repaint();


        });

        // Add the button to the slider panel
        sliderPanel.add(button);


    }


    public static void main(String[] args) {
        new Main();
    }
}
