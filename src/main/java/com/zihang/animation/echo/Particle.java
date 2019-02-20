package com.zihang.animation.echo;

import javafx.geometry.Point2D;

public class Particle extends Point2D {
    private final double direction;

    public Particle (Point2D point, double direction) {
        this(point.getX(), point.getY(), direction);
    }
    public Particle (double x, double y, double direction) {
        super(x, y);
        while (direction >= Math.PI) {
            direction -= 2 * Math.PI;
        }
        while (direction < -Math.PI) {
            direction += 2 * Math.PI;
        }
        this.direction = direction;
    }

    public double getDirection() {
        return direction;
    }

    public Particle move(double distance) {
        return new Particle(getX() + distance * Math.cos(direction), getY() + distance * Math.sin(direction), direction);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Particle))
            return false;
        Particle other = (Particle)obj;
        // To avoid inaccuracy due to double
        return Math.abs(this.getX() - other.getX()) < 1e-8
                && Math.abs(this.getY() - other.getY()) < 1e-8
                && Math.abs(this.direction - ((Particle)obj).direction) <= 1e-8;
    }

    @Override
    public String toString() {
        return "Particle [x = " + getX() + ", y = " + getY() + ", direction = " + getDirection();
    }
}