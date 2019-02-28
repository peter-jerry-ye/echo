package com.zihang.animation.echo;

import javafx.geometry.Point2D;

public class Particle extends Point2D {
    public static final int RADIUS = 1;

    private final Point2D direction;
    private final long lifespan;

    public Particle(Point2D point, Point2D direction, long lifespan) {
        this(point.getX(), point.getY(), direction, lifespan);
    }

    public Particle(double x, double y, double directionX, double directionY, long lifespan) {
        this(x, y, new Point2D(directionX, directionY), lifespan);
    }

    public Particle(double x, double y, Point2D direction, long lifespan) {
        super(x, y);
        if (direction.equals(Point2D.ZERO))
            throw new IllegalArgumentException("A particle has to have a direction");
        this.direction = direction.normalize();
        if (lifespan <= 0)
            throw new IllegalArgumentException("The lifespan of the particle has to be positive");
        this.lifespan = lifespan;
    }

    public Point2D getDirection() {
        return direction;
    }

    public long getLifespan() {
        return lifespan;
    }

    /**
     * Move the particle along it's direction. The particle will disappear if it
     * reaches it's lifespan.
     * 
     * @param distance the distance to move
     * @return a new particle at the new position or null if the particle dies.
     */
    public Particle move(double distance) {
        if (lifespan > 1)
            return new Particle(getX() + distance * direction.getX(), getY() + distance * direction.getY(), direction,
                    lifespan - 1);
        else
            return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Particle))
            return false;
        Particle other = (Particle) obj;
        // To avoid inaccuracy due to double
        final double error = 1e-8;
        return Math.abs(this.getX() - other.getX()) < error && Math.abs(this.getY() - other.getY()) < error
                && Math.abs(this.direction.getX() - ((Particle) obj).direction.getX()) <= error
                && Math.abs(this.direction.getY() - ((Particle) obj).direction.getY()) <= error;
    }

    @Override
    public String toString() {
        return "Particle [x = " + getX() + ", y = " + getY() + ", direction = (" + direction.getX() + ", "
                + direction.getY() + ")]";
    }
}