package com.zihang.animation.echo;

import javafx.geometry.Point2D;

public class Particle extends Point2D {
    public static final int RADIUS = 1;

    private final double direction;
    private final long lifespan;

    public Particle(Point2D point, double direction, long lifespan) {
        this(point.getX(), point.getY(), direction, lifespan);
    }

    public Particle(double x, double y, double direction, long lifespan) {
        super(x, y);
        while (direction >= Math.PI) {
            direction -= 2 * Math.PI;
        }
        while (direction < -Math.PI) {
            direction += 2 * Math.PI;
        }
        this.direction = direction;
        if (lifespan <= 0)
            throw new IllegalArgumentException("The lifespan of the particle has to be positive");
        this.lifespan = lifespan;
    }

    public double getDirection() {
        return direction;
    }

    public long getLifespan() {
        return lifespan;
    }

    /**
     * Move the particle along it's direction.
     * The particle will disappear if it reaches it's lifespan.
     * @param distance the distance to move
     * @return a new particle at the new position or null if the particle dies.
     */
    public Particle move(double distance) {
        if (lifespan > 1)
            return new Particle(getX() + distance * Math.cos(direction), getY() + distance * Math.sin(direction),
                    direction, lifespan - 1);
        else
            return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Particle))
            return false;
        Particle other = (Particle) obj;
        // To avoid inaccuracy due to double
        return Math.abs(this.getX() - other.getX()) < 1e-8 && Math.abs(this.getY() - other.getY()) < 1e-8
                && Math.abs(this.direction - ((Particle) obj).direction) <= 1e-8;
    }

    @Override
    public String toString() {
        return "Particle [x = " + getX() + ", y = " + getY() + ", direction = " + getDirection();
    }
}