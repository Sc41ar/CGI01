package org.example;

public class Vector {
    private double x, y, z, w;

    // Static method to add two vectors
    public static Vector add(Vector v1, Vector v2) {
        return new Vector(
                v1.x + v2.x,
                v1.y + v2.y,
                v1.z + v2.z,
                v1.w + v2.w
        );
    }

    // Method to get the length of the vector
    public double getLength() {
        return Math.sqrt(
                this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w
        );
    }

    // Method to normalize the vector
    public Vector normalize() {
        double length = this.getLength();

        this.x /= length;
        this.y /= length;
        this.z /= length;
        this.w /= length;

        return this;
    }

    // Method to multiply the vector by a scalar
    public Vector multiplyByScalar(double s) {
        this.x *= s;
        this.y *= s;
        this.z *= s;
        this.w *= s;

        return this;
    }

    // Default constructor
    public Vector() {
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
        this.w = 0.0;
    }

    // All arguments constructor
    public Vector(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    // Parameterized constructor
    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = 1.0;
    }

    // Getters
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double getW() {
        return w;
    }

    // Setters
    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setW(double w) {
        this.w = w;
    }
}
