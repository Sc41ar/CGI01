package org.example;

public class Matrix {
    public static double[][] multiply(double[][] a, double[][] b) {
        int n = a.length;
        double[][] m = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    m[i][j] += a[i][k] * b[k][j];
                }
            }
        }

        return m;
    }

    public static Vector multiplyVector(double[][] m, Vector v) {
        return new Vector(
                m[0][0] * v.getX() + m[0][1] * v.getY() + m[0][2] * v.getZ() + m[0][3] * v.getW(),
                m[1][0] * v.getX() + m[1][1] * v.getY() + m[1][2] * v.getZ() + m[1][3] * v.getW(),
                m[2][0] * v.getX() + m[2][1] * v.getY() + m[2][2] * v.getZ() + m[2][3] * v.getW(),
                m[3][0] * v.getX() + m[3][1] * v.getY() + m[3][2] * v.getZ() + m[3][3] * v.getW()
        );
    }

    public static double[][] getScale(double sx, double sy, double sz) {
        return new double[][]{
                {sx, 0, 0, 0},
                {0, sy, 0, 0},
                {0, 0, sz, 0},
                {0, 0, 0, 1},
        };
    }


    public static double[][] getTranslation(double dx, double dy, double dz) {
        return new double[][]{
                {1, 0, 0, dx},
                {0, 1, 0, dy},
                {0, 0, 1, dz},
                {0, 0, 0, 1},
        };
    }

    public static double[][] getRotationX(double angle) {
        double rad = Math.PI / 180 * angle;

        return new double[][]{
                {1, 0, 0, 0},
                {0, Math.cos(rad), -Math.sin(rad), 0},
                {0, Math.sin(rad), Math.cos(rad), 0},
                {0, 0, 0, 1},
        };
    }

    public static double[][] getRotationX(double angle, double cx, double cy, double cz) {
        double rad = Math.PI / 180 * angle;
        double[][] translationMatrix = getTranslation(-cx, -cy, -cz);
        double[][] rotationMatrix = new double[][]{
                {1, 0, 0, 0},
                {0, Math.cos(rad), -Math.sin(rad), 0},
                {0, Math.sin(rad), Math.cos(rad), 0},
                {0, 0, 0, 1},
        };
        double[][] inverseTranslationMatrix = getTranslation(cx, cy, cz);
        return multiply(inverseTranslationMatrix, multiply(rotationMatrix, translationMatrix));
    }

    public static double[][] getRotationY(double angle, double cx, double cy, double cz) {
        double rad = Math.PI / 180 * angle;
        double[][] translationMatrix = getTranslation(-cx, -cy, -cz);
        double[][] rotationMatrix = new double[][]{
                {Math.cos(rad), 0, Math.sin(rad), 0},
                {0, 1, 0, 0},
                {-Math.sin(rad), 0, Math.cos(rad), 0},
                {0, 0, 0, 1},
        };
        double[][] inverseTranslationMatrix = getTranslation(cx, cy, cz);
        return multiply(inverseTranslationMatrix, multiply(rotationMatrix, translationMatrix));
    }

    public static double[][] getRotationZ(double angle, double cx, double cy, double cz) {
        double rad = Math.PI / 180 * angle;
        double[][] translationMatrix = getTranslation(-cx, -cy, -cz);
        double[][] rotationMatrix = new double[][]{
                {Math.cos(rad), -Math.sin(rad), 0, 0},
                {Math.sin(rad), Math.cos(rad), 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1},
        };
        double[][] inverseTranslationMatrix = getTranslation(cx, cy, cz);
        return multiply(inverseTranslationMatrix, multiply(rotationMatrix, translationMatrix));
    }


    public static double[][] getRotationY(double angle) {
        double rad = Math.PI / 180 * angle;

        return new double[][]{
                {Math.cos(rad), 0, Math.sin(rad), 0},
                {0, 1, 0, 0},
                {-Math.sin(rad), 0, Math.cos(rad), 0},
                {0, 0, 0, 1},
        };
    }

    public static double[][] getRotationZ(double angle) {
        double rad = Math.PI / 180 * angle;

        return new double[][]{
                {Math.cos(rad), -Math.sin(rad), 0, 0},
                {Math.sin(rad), Math.cos(rad), 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1},
        };
    }
}
