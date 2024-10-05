package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Drawer extends JPanel {

    static Vector[] vertices = {
            new Vector(2, 1, -1), // 0 vertex
            new Vector(2, 6, -1), // 1 vertex
            new Vector(4, 6, -1), // 2 vertex
            new Vector(4, -6, -1), // 3 vertex
            new Vector(2, -6, -1), // 4 vertex
            new Vector(2, -1, -1), // 5 vertex
            new Vector(-2, -1, -1), // 6 vertex
            new Vector(-2, -6, -1), // 7 vertex
            new Vector(-4, -6, -1), // 8 vertex
            new Vector(-4, 6, -1), // 9 vertex
            new Vector(-2, 6, -1), // 10 vertex
            new Vector(-2, 1, -1), // 11 vertex
            new Vector(2, 1, 1), // 12 vertex
            new Vector(2, 6, 1), // 13 vertex
            new Vector(4, 6, 1), // 14 vertex
            new Vector(4, -6, 1), // 15 vertex
            new Vector(2, -6, 1), // 16 vertex
            new Vector(2, -1, 1), // 17 vertex
            new Vector(-2, -1, 1), // 18 vertex
            new Vector(-2, -6, 1), // 19 vertex
            new Vector(-4, -6, 1), // 20 vertex
            new Vector(-4, 6, 1), // 21 vertex
            new Vector(-2, 6, 1), // 22 vertex
            new Vector(-2, 1, 1), // 23 vertex
    };

    static int[][] edges = {
            {0, 1},
            {1, 2},
            {2, 3},
            {3, 4},
            {4, 5},
            {5, 6},
            {6, 7},
            {7, 8},
            {8, 9},
            {9, 10},
            {10, 11},
            {11, 0},
            {12, 13},
            {13, 14},
            {14, 15},
            {15, 16},
            {16, 17},
            {17, 18},
            {18, 19},
            {19, 20},
            {20, 21},
            {21, 22},
            {22, 23},
            {23, 12},
            {12, 0},
            {13, 1},
            {14, 2},
            {15, 3},
            {16, 4},
            {17, 5},
            {18, 6},
            {19, 7},
            {20, 8},
            {21, 9},
            {22, 10},
            {23, 11},

    };

    static List<Vector> sceneVerticles = new ArrayList<>();

    static private int Ox3d = 100;

    static private int Oy3d = 100;

    static private double Originx = 0;
    static private double Originy = 0;
    static private double Originz = 0;


    private double angleX;
    private double angleY;
    private double angleZ;

    private double sx;
    private double sy;
    private double sz;
    private double offsetX;
    private double offsetY;

    public static double getOriginy() {
        return Originy;
    }

    public static void setOriginy(double originy) {
        Originy = originy;
    }

    public static double getOriginz() {
        return Originz;
    }

    public static void setOriginz(double originz) {
        Originz = originz;
    }

    public static double getOriginx() {
        return Originx;
    }

    public static void setOriginx(double originx) {
        var delta = originx - Originx;
        Originx = originx;

    }

    public void initSceneVerticles() {
        // The rest of the method remains the same

        // Clear the sceneVerticles list before adding new vertices
        sceneVerticles.clear();

        // Loop through each vertex
        for (int i = 0; i < vertices.length; i++) {
            Vector vertex = vertices[i];

            // Apply rotation transformations
            vertex = Matrix.multiplyVector(Matrix.getRotationX(angleX, 0, 0, 0), vertex);
            vertex = Matrix.multiplyVector(Matrix.getRotationX(angleX), vertex);
            vertex = Matrix.multiplyVector(Matrix.getRotationY(angleY), vertex);
            vertex = Matrix.multiplyVector(Matrix.getRotationY(angleY, 0, 0, 0), vertex);
            vertex = Matrix.multiplyVector(Matrix.getRotationZ(angleZ, 0, 0, 0), vertex);
            vertex = Matrix.multiplyVector(Matrix.getRotationZ(angleZ), vertex);

            // Apply scaling transformation
            vertex = Matrix.multiplyVector(Matrix.getScale(sx, sy, sz), vertex);

            // Apply translation transformation
            vertex = Matrix.multiplyVector(Matrix.getTranslation(offsetX, offsetY, 1), vertex);

            // Add the transformed vertex to the sceneVerticles list
            sceneVerticles.add(vertex);
        }


    }

    public void setAngleX(double angleX) {
        this.angleX = angleX;
    }

    public void setAngleY(double angleY) {
        this.angleY = angleY;
    }

    public void setAngleZ(double angleZ) {
        this.angleZ = angleZ;
    }

    public double getAngleX() {
        return angleX;
    }

    public double getAngleY() {
        return angleY;
    }

    public double getAngleZ() {
        return angleZ;
    }

    @Override
    public void paint(Graphics g) {
        Oy3d = this.getHeight() / 2;
        Ox3d = this.getWidth() / 2;

        super.paint(g);

        g.setColor(Color.BLACK);
        long startTime = System.nanoTime();

        for (var edge : edges) {
            g.drawLine(
                    convertX(sceneVerticles.get(edge[0]).getX()),
                    convertY(sceneVerticles.get(edge[0]).getY()),
                    convertX(sceneVerticles.get(edge[1]).getX()),
                    convertY(sceneVerticles.get(edge[1]).getY())
            );
        }
        g.drawOval(convertX(getOriginx() * 50), convertY(getOriginy() * 50), 5, 5);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);  // divide by 1000000 to get milliseconds.
        System.out.println("Drawing operation took " + duration + " nanoseconds");
    }

    public int convertX(double x3d) {
        int result = Ox3d + (int) (x3d);
        return result;
    }

    public int convertY(double y3d) {
        int result = Oy3d - (int) (y3d);
        return result;
    }

    public Drawer() {
        this.sx = 25;
        this.sy = 25;
        this.sz = 0;
        this.offsetX = 0;
        this.offsetY = 0;
        this.angleX = 0;
        this.angleY = 0;
        this.angleZ = 0;
        initSceneVerticles();
    }


    public void setXRotation(double value) {
        this.angleX = value;
        initSceneVerticles();
        repaint();
    }

    public void setYRotation(double value) {
        this.angleY = value;
        initSceneVerticles();
        repaint();

    }

    public void setZRotation(double value) {
        this.angleZ = value;
        initSceneVerticles();
        repaint();

    }

    public void setXScale(double value) {
        this.sx = value;
        initSceneVerticles();
        repaint();
    }

    public void setYScale(double value) {
        this.sy = value;
        initSceneVerticles();
        repaint();
    }

    public void setXOffset(double value) {
        value = value / 25;
        var delta = value - offsetX;
        this.offsetX = value;

        for (var vertex : vertices) {
            vertex.setX(vertex.getX() + delta);
        }


        initSceneVerticles();
    }

    public void setYOffset(double value) {
        value = value / 25;
        var delta = value - offsetY;
        this.offsetY = value;

        for (var vertex : vertices) {
            vertex.setY(vertex.getY() + delta);
        }

        initSceneVerticles();
    }


    public double getXOffset() {
        return this.offsetX;
    }

    public double getYOffset() {
        return this.offsetY;
    }


}
