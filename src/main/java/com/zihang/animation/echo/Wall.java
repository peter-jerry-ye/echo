package com.zihang.animation.echo;

import javafx.geometry.Point2D;
import javafx.scene.shape.Line;

public class Wall extends Line {
    private final Point2D start;
    private final Point2D end;
    // private final Point2D normal;
    private final Point2D vector;

    public Wall(double startX, double startY, double endX, double endY) {
        super(startX, startY, endX, endY);
        start = new Point2D(startX, startY);
        end = new Point2D(endX, endY);
        vector = end.subtract(start).normalize();
        // normal = new Point2D(getStartY() - getEndY(), getEndX() - getStartX()).normalize();
    }

    public Wall(Point2D start, Point2D end) {
        this(start.getX(), start.getY(), end.getX(), end.getY());
    }

    /**
     * Calculate the distance for the particle to move before the collision.
     * 
     * @param p the particle to be examined
     * @return the distance before collision, or Double.MAX_VALUE if no collision
     *         will happen
     */
    public double distanceToCollide(Particle p) {
        // line function: v(x-x0)=u(y-y0) with (u, v) vector
        // which gives vx-uy=vx0-uy0
        // function for the particle:
        double ap = p.getDirection().getY(), bp = -p.getDirection().getX(),
                cp = p.getDirection().getY() * p.getX() - p.getDirection().getX() * p.getY();
        double al = vector.getY(), bl = -vector.getX(), cl = vector.getY() * start.getX() - vector.getX() * start.getY();
        // determine if there's a solution
        // if there's no solution, return Double.MAX_VALUE
        if (ap * bl == al * bp) {
            return Double.MAX_VALUE;
        }
        // calculate the solution
        double x = (cp * bl - cl * bp) / (ap * bl - al * bp);
        double y = (ap * cl - al * cp) / (ap * bl - al * bp);
        Point2D intersection = new Point2D(x, y);
        Point2D direction = intersection.subtract(p).normalize();
        // determine if they collide already
        if (direction.equals(Point2D.ZERO))
            return 0;
        // determine if the point is in the right direction
        if (Math.abs(direction.getX() - p.getDirection().getX()) >= Particle.DELTA
            || Math.abs(direction.getY() - p.getDirection().getY()) >= Particle.DELTA) {
            return Double.MAX_VALUE;
        }
        // determine if the point is on the segment
        double angle = vector.angle(p.getDirection()) / 180 * Math.PI;
        double projection = intersection.subtract(start).dotProduct(vector);
        if (projection < -1 / Math.sin(angle) || projection > end.distance(start) + 1 / Math.sin(angle)) {
            return Double.MAX_VALUE;
        }
        // find out the distance
        // substract the distance as the circle reaches the line before the center
        double distance = intersection.distance(p) - 1 / Math.sin(angle) * Particle.RADIUS;
        // if they are close enough, return 0
        return distance > 0 ? distance : 0;
    }

    /**
     * Detects if the particle collides with this wall. If they collide, return the
     * bounced particle. Otherwise return null;
     * 
     * @param p the particle to be examined
     * @return origin particle if they do not collide, the bounced particle
     *         otherwise
     */
    public Particle collide(Particle p) {
        if (distanceToCollide(p) < Particle.DELTA) {
            // pv = p - (p .* line) * line
            Point2D verticalSpeed = p.getDirection()
                    .subtract(this.vector.multiply(p.getDirection().dotProduct(this.vector)));
            Point2D newDirection = p.getDirection().subtract(verticalSpeed.multiply(2));
            return new Particle(p.getX(), p.getY(), newDirection, p.getLifespan());
        }
        // Otherwise
        else
            return p;
    }
}