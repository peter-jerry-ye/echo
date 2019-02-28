package com.zihang.animation.echo;

import javafx.geometry.Point2D;
import javafx.scene.shape.Line;

public class Wall extends Line {
    private final Point2D start;
    private final Point2D end;
    public Wall(double startX, double startY, double endX, double endY) {
        super(startX, startY, endX, endY);
        start = new Point2D(startX, startY);
        end = new Point2D(endX, endY);
    }
    public Wall(Point2D start, Point2D end) {
        this(start.getX(), start.getY(), end.getX(), end.getY());
    }

    /**
     * Detects if the particle collides with this wall.
     * If they collide, return the bounced particle.
     * Otherwise return null;
     * @param p the particle to be examined
     * @return origin particle if they do not collide, the bounced particle otherwise
     */
    public Particle collide(Particle p) {
        // Detect if they are close enough
        // Find a vector vertical to this wall
        Point2D vertical = new Point2D(getStartY() - getEndY(), getEndX() - getStartX()).normalize();
        // Find out the distance between the p and the line where this wall situates.
        Point2D vector = p.subtract(start);
        double distance = vector.dotProduct(vertical);
        
        // The p also needs to hit this wall, i.e. between start and end
        Point2D line = end.subtract(start).normalize();
        double projection = vector.dotProduct(line);
        
        boolean collide = Math.abs(distance) <= Particle.RADIUS
                && projection >= -Particle.RADIUS
                && projection <= start.distance(end) + Particle.RADIUS;
        // If they collide
        if (collide) {
            // pv = p - (p .* line) * line
            Point2D verticalSpeed = p.getDirection().subtract(line.multiply(p.getDirection().dotProduct(line)));
            Point2D newDirection = p.getDirection().subtract(verticalSpeed.multiply(2));
            return new Particle(p.getX(), p.getY(), newDirection, p.getLifespan());
        }
        // Otherwise
        else
            return p;
    }
}