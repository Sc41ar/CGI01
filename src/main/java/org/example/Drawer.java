package org.example;

import lombok.Getter;
import lombok.Setter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
@Setter
public class Drawer extends JPanel {

    private List<Model> objects = new ArrayList<>();

    static List<Vector> sceneVerticles = new ArrayList<>();

    static private int Ox3d = 100;

    static private int Oy3d = 100;

    private double angleX;
    private double angleY;
    private double angleZ;

    private double sx;
    private double sy;
    private double sz;
    private double offsetX;
    private double offsetY;
    private double offsetZ;

    private double[][] rotationMatrix;
    private double[][] projectionMatrix;

    private double[][] zBuffer;
    private int[] colouredBuffer;//???
    private BufferedImage image;

    public void initSceneVerticles(boolean isDoubleRotation) {
        // The rest of the method remains the same

        // Clear the sceneVerticles list before adding new vertices
        sceneVerticles.clear();

        Vector center = calculateCenter(0);

        for (Model object : objects) {
            var vertices = object.getVertices();
            // Loop through each vertex
            for (int i = 0; i < vertices.length; i++) {
                Vector vertex = vertices[i];

                if (isDoubleRotation) {
                    vertex = Matrix.multiplyVector(Matrix.getRotationX(angleX, center.getX(), center.getX(), center.getZ()), vertex);
                    vertex = Matrix.multiplyVector(Matrix.getRotationY(angleY, center.getX(), center.getY(), center.getZ()), vertex);
                    vertex = Matrix.multiplyVector(Matrix.getRotationZ(angleZ, center.getX(), center.getY(), center.getZ()), vertex);
                }
                // Apply rotation transformations
                vertex = Matrix.multiplyVector(Matrix.getRotationX(angleX), vertex);
                vertex = Matrix.multiplyVector(Matrix.getRotationY(angleY), vertex);
                vertex = Matrix.multiplyVector(Matrix.getRotationZ(angleZ), vertex);

                // Apply scaling transformation
                vertex = Matrix.multiplyVector(Matrix.getScale(sx, sy, sz), vertex);

                // Apply translation transformation
                vertex = Matrix.multiplyVector(Matrix.getTranslation(offsetX, offsetY, offsetZ), vertex);

                // Add the transformed vertex to the sceneVerticles list
                sceneVerticles.add(vertex);
            }
        }


    }

    //вычисление центра i-того объекта
    private Vector calculateCenter(int objectIndex) {
        double sumX = 0;
        double sumY = 0;
        double sumZ = 0;

        var vertices = objects.get(objectIndex).getVertices();

        for (Vector vertex : vertices) {
            sumX += vertex.getX();
            sumY += vertex.getY();
            sumZ += vertex.getZ();
        }

        int numVertices = vertices.length;
        double centerX = sumX / numVertices;
        double centerY = sumY / numVertices;
        double centerZ = sumZ / numVertices;

        return new Vector(centerX, centerY, centerZ);
    }

    public void saveImageToDesktop(BufferedImage image, String fileName) {

        // Создаем файл для сохранения изображения
        File outputFile = new File("", fileName);

        try {
            // Сохраняем изображение в формате PNG
            ImageIO.write(image, "png", outputFile);
            System.out.println("Изображение сохранено на рабочем столе: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении изображения: " + e.getMessage());
        }
    }

    @Override
    public void paint(Graphics g) {
        Oy3d = this.getHeight() / 2;
        Ox3d = this.getWidth() / 2;

        super.paint(g);

        g.setColor(Color.BLACK);
        long startTime = System.nanoTime();

//        var gra = image.createGraphics();
//        gra.clearRect(0, 0, image.getWidth(), image.getHeight());
//        gra.dispose();

        for (var object : objects) {
            Vector[] vertices = object.getVertices();
            Vector[] transformedVertices = new Vector[vertices.length];

            // Применение трансформаций
            for (int i = 0; i < vertices.length; i++) {
                Vector vertex = vertices[i];
                vertex = Matrix.multiplyVector(Matrix.getRotationX(angleX), vertex);
                vertex = Matrix.multiplyVector(Matrix.getRotationY(angleY), vertex);
                vertex = Matrix.multiplyVector(Matrix.getRotationZ(angleZ), vertex);
                vertex = Matrix.multiplyVector(Matrix.getScale(sx, sy, sz), vertex);
                vertex = Matrix.multiplyVector(Matrix.getTranslation(offsetX, offsetY, offsetZ), vertex);
                transformedVertices[i] = vertex;
            }

            // Растеризация треугольников
            for (int[] face : object.getFaces()) {
                Vector v1 = transformedVertices[face[0]];
                Vector v2 = transformedVertices[face[1]];
                Vector v3 = transformedVertices[face[2]];


                if (image != null) {
                    Graphics2D graphics2D = image.createGraphics();
                    drawTriangle(graphics2D, zBuffer, v1, v2, v3, object.getColor());
                    graphics2D.dispose();
                }
            }

//            for (var edge : object.getEdges()) {
//                var firstProjection = project(sceneVerticles.get(edge[0]).getX(),
//                      sceneVerticles.get(edge[0]).getY(),
//                      sceneVerticles.get(edge[0]).getZ());
//
//                var secondProjection = project(sceneVerticles.get(edge[1]).getX(),
//                      sceneVerticles.get(edge[1]).getY(),
//                      object.getVertices()[edge[1]].getZ());
//
//                if (image != null) {
//                    Graphics2D graphics2D = image.createGraphics();
//                    drawLine(graphics2D, zBuffer, firstProjection, secondProjection, object.getVertices()[edge[0]].getZ(), object.getVertices()[edge[1]].getZ());
//                    graphics2D.dispose();
//                }
//            }

            for (var edge : object.getEdges()) {
                var firstProjection = project(sceneVerticles.get(edge[0]).getX(),
                      sceneVerticles.get(edge[0]).getY(),
                      sceneVerticles.get(edge[0]).getZ());

                var secondProjection = project(sceneVerticles.get(edge[1]).getX(),
                      sceneVerticles.get(edge[1]).getY(),
                      sceneVerticles.get(edge[1]).getZ());

                Graphics2D graphics2D = image.createGraphics();
                graphics2D.drawLine((int)firstProjection[0], (int)firstProjection[1], (int)secondProjection[0], (int)secondProjection[1]);
                graphics2D.dispose();
            }
        }

        int width = getWidth();
        int height = getHeight();

        g.drawImage(image, 00, 00, null);
        saveImageToDesktop(image, "image.png");


        // Ось X
        g.setColor(Color.RED);
        g.drawLine(0, height / 2, width, height / 2);

        // Ось Y
        g.setColor(Color.GREEN);
        g.drawLine(width / 2, 0, width / 2, height);

        g.setColor(Color.BLUE);
        g.drawLine(0, height, width, 0);

        // Подписи
        g.setColor(Color.BLACK);
        g.drawString("X", width / 2 + 20, height / 2);
        g.drawString("Y", width / 2, height / 2 - 20);
        g.drawString("Z", width / 2, height / 2 + 20);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);  // divide by 1000000 to get milliseconds.
        System.out.println("Drawing operation took " + duration + " nanoseconds");
    }

    private double[] project(double x, double y, double z) {
        Vector point = new Vector(x, y, z, 1);
        Vector rotatedPoint = Matrix.multiplyVector(rotationMatrix, point);
        Vector projectedPoint = Matrix.multiplyVector(projectionMatrix, rotatedPoint);
        return new double[]{projectedPoint.getX(), projectedPoint.getY()};
    }

    private void drawTriangle(Graphics g, double[][] zBuffer, Vector v1, Vector v2, Vector v3, Color color) {
        double[] p1 = project(v1.getX(), v1.getY(), v1.getZ());
        double[] p2 = project(v2.getX(), v2.getY(), v2.getZ());
        double[] p3 = project(v3.getX(), v3.getY(), v3.getZ());

        int minX = (int) Math.min(p1[0], Math.min(p2[0], p3[0]));
        int maxX = (int) Math.max(p1[0], Math.max(p2[0], p3[0]));
        int minY = (int) Math.min(p1[1], Math.min(p2[1], p3[1]));
        int maxY = (int) Math.max(p1[1], Math.max(p2[1], p3[1]));

        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                double alpha = (double) ((y - p2[1]) * (p3[0] - p2[0]) - (x - p2[0]) * (p3[1] - p2[1])) /
                      ((p1[1] - p2[1]) * (p3[0] - p2[0]) - (p1[0] - p2[0]) * (p3[1] - p2[1]));
                double beta = (double) ((y - p1[1]) * (p3[0] - p1[0]) - (x - p1[0]) * (p3[1] - p1[1])) /
                      ((p2[1] - p1[1]) * (p3[0] - p1[0]) - (p2[0] - p1[0]) * (p3[1] - p1[1]));
                double gamma = 1.0 - alpha - beta;

                if (alpha >= 0 && beta >= 0 && gamma >= 0) {
                    double z = alpha * v1.getZ() + beta * v2.getZ() + gamma * v3.getZ();
                    if (x >= 0 && x < zBuffer.length && y >= 0 && y < zBuffer[0].length) {
                        if (z > zBuffer[x][y]) {
                            zBuffer[x][y] = z;
                            g.setColor(color);
                            g.drawLine(x, y, x, y);
                        }
                    }
                }
            }
        }
    }

    private static void drawLine(Graphics2D g, double[][] zBuffer, double[] p1, double[] p2, double z1, double z2) {
        double x1 = p1[0], y1 = p1[1];
        double x2 = p2[0], y2 = p2[1];

        double dx = Math.abs(x2 - x1);
        double dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        double err = dx - dy;

        while (true) {
            if (x1 >= 0 && x1 < zBuffer.length && y1 >= 0 && y1 < zBuffer[0].length) {
                double z = z1 + (z2 - z1) * (Math.abs(x1 - x2) / (double) dx);
                if (z > zBuffer[(int) x1][(int) y1]) {
                    zBuffer[(int) x1][(int) y1] = z;
                    g.setColor(Color.BLACK);
                    g.drawLine((int) x1, (int) y1, (int) x1, (int) y1);
                }
            }

            if (x1 == x2 && y1 == y2) break;
            double e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
    }


    private static int[][] generateTetrahedronEdges(int i) {
        int offset = 4 * 0;
        return new int[][]{
              {offset, 1 + offset}, {offset, 2 + offset}, {offset, 3 + offset},
              {1 + offset, 2 + offset}, {1 + offset, 3 + offset}, {2 + offset, 3 + offset}
        };
    }

    private static int[][] generateTetrahedronFaces(int i) {
        int offset = 4 * 0;
        return new int[][]{
              {offset, 1 + offset, 2 + offset}, {offset, 1 + offset, 3 + offset}, {offset, 2 + offset, 3 + offset},
              {1 + offset, 2 + offset, 3 + offset}
        };
    }

    private static Vector[] generateTetrahedronVertices(int index) {
        double offset = index * 2; // Offset to separate tetrahedrons
        Random random = new Random();
        offset = random.nextDouble(50) - offset;


//        return new Vector[]{
//              new Vector(random.nextInt(4) - 2, +random.nextDouble(30) - offset, random.nextInt(4) - 2 + random.nextDouble(30) - offset, random.nextInt(4) - 2 + random.nextDouble(30) - offset),
//              new Vector(random.nextInt(4) - 2 + random.nextDouble(30) - offset, random.nextInt(4) - 2 + random.nextDouble(30) - offset, random.nextInt(4) - 2 + random.nextDouble(30) - offset),
//              new Vector(random.nextInt(4) - 2 + random.nextDouble(30) - offset, random.nextInt(4) - 2 + random.nextDouble(30) - offset, random.nextInt(4) - 2 + random.nextDouble(30) - offset),
//              new Vector(random.nextInt(4) - 2 + random.nextDouble(30) - offset, random.nextInt(4) - 2 + random.nextDouble(30) - offset, random.nextInt(4) - 2 + random.nextDouble(30) - offset)
//        };
        return new Vector[]{
              new Vector(random.nextInt(4) - 2, +random.nextDouble(30) - offset, random.nextInt(4) - 2 + random.nextDouble(30) - offset, random.nextInt(4) - 2 + random.nextDouble(30) - offset),
              new Vector(random.nextInt(4) - 2 + random.nextDouble(30) - offset, random.nextInt(4) - 2 + random.nextDouble(30) - offset, random.nextInt(4) - 2 + random.nextDouble(30) - offset),
              new Vector(random.nextInt(4) - 2 + random.nextDouble(30) - offset, random.nextInt(4) - 2 + random.nextDouble(30) - offset, random.nextInt(4) - 2 + random.nextDouble(30) - offset),
              new Vector(random.nextInt(4) - 2 + random.nextDouble(30) - offset, random.nextInt(4) - 2 + random.nextDouble(30) - offset, random.nextInt(4) - 2 + random.nextDouble(30) - offset)
        };
    }


    public Drawer() {
        super();

        double angleRad = Math.toRadians(0);

        Color[] colors = {Color.MAGENTA, Color.GREEN, Color.RED, Color.BLUE, Color.cyan};

        //Static define of objects count
        for (int i = 0; i < 5; i++) {
            Vector[] vertices = generateTetrahedronVertices(i);
            int[][] edges = generateTetrahedronEdges(i);
            int[][] faces = generateTetrahedronFaces(i);
            objects.add(new Model(vertices, edges, faces, colors[i]));
        }


        colouredBuffer = new int[getWidth()];


        this.sx = 5;
        this.sy = 5;
        this.sz = 5;
        this.angleX = 0;
        this.angleY = 0;
        this.angleZ = 0;
        this.offsetZ = 0;
        this.offsetX = 50;
        this.offsetY = 50;

        rotationMatrix = new double[][]{
              {Math.cos(angleRad), 0, Math.sin(angleRad), 0},
              {0, 1, 0, 0},
              {-Math.sin(angleRad), 0, Math.cos(angleRad), 0},
              {0, 0, 0, 1}
        };

        double sqrt3 = Math.sqrt(3);

        // Матрица ортографического проецирования
        projectionMatrix = new double[][]{
              {1, 0, -1 / sqrt3, 0},
              {0, 1, -1 / sqrt3, 0},
              {0, 0, 0, 0},
              {0, 0, 0, 1}
        };

        initSceneVerticles(false);
    }

    public void setZOffset(double value) {
        value = value / 10;
        var delta = value - offsetZ;
        this.offsetZ = value;

        for (var object : objects) {
            for (var vertex : object.getVertices()) {
                vertex.setZ(vertex.getZ() + delta);
            }
        }


        initSceneVerticles(false);
    }


    public void setXRotation(double value) {
        this.angleX = value;
        initSceneVerticles(false);
        repaint();
    }

    public void setYRotation(double value) {
        this.angleY = value;
        initSceneVerticles(false);
        repaint();

    }

    public void setZRotation(double value) {
        this.angleZ = value;
        initSceneVerticles(false);
        repaint();

    }

    public void setXScale(double value) {
        this.sx = value;
        initSceneVerticles(false);
        repaint();
    }

    public void setYScale(double value) {
        this.sy = value;
        initSceneVerticles(false);
        repaint();
    }

    public void setXOffset(double value) {
        value = value / 10;
        var delta = value - offsetX;
        this.offsetX = value;
        for (var object : objects) {
            for (var vertex : object.getVertices()) {
                vertex.setX(vertex.getX() + delta);
            }
        }

        initSceneVerticles(false);
    }

    public void setYOffset(double value) {
        value = value / 10;
        var delta = value - offsetY;
        this.offsetY = value;

        for (var object : objects) {
            for (var vertex : object.getVertices()) {
                vertex.setY(vertex.getY() + delta);
            }
        }
        initSceneVerticles(false);
    }

    public void setZScale(int value) {
        this.sz = value;
        initSceneVerticles(false);
        repaint();
    }

    public void initImage() {
        zBuffer = new double[getWidth()][getHeight()];
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                zBuffer[i][j] = Double.NEGATIVE_INFINITY;
            }
        }
        image = new BufferedImage(getWidth() + 1, getHeight() + 1, BufferedImage.TYPE_INT_ARGB);
    }
}
