package com.zihang.animation.echo;

import javafx.geometry.Point2D;

public class Particle extends Point2D {
    public static final int RADIUS = 1;
    public static final double DELTA = 1e-10; // precision

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
     * Reduce the lifespan of the particle
     * @return a new particle with shorter lifespan or null if the particle dies
     */
    public Particle decreaseLifespan() {
        if (lifespan == 1)
            return null;
        else
            return new Particle(this.getX(), this.getY(), this.direction, this.lifespan - 1);
    }

    /**
     * Move the particle along it's direction. The particle will disappear if it
     * reaches it's lifespan.
     * 
     * @param distance the distance to move
     * @return a new particle at the new position
     */
    public Particle move(double distance) {
            return new Particle(getX() + distance * direction.getX(), getY() + distance * direction.getY(), direction,
                    lifespan);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Particle))
            return false;
        Particle other = (Particle) obj;
        return Math.abs(this.getX() - other.getX()) < DELTA && Math.abs(this.getY() - other.getY()) < DELTA
                && Math.abs(this.direction.getX() - ((Particle) obj).direction.getX()) <= DELTA
                && Math.abs(this.direction.getY() - ((Particle) obj).direction.getY()) <= DELTA;
    }

    @Override
    public String toString() {
        return "Particle [x = " + getX() + ", y = " + getY() + ", direction = (" + direction.getX() + ", "
                + direction.getY() + ")]";
    }
}