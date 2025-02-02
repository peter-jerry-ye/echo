package com.zihang.animation.echo;

import javafx.scene.layout.Pane;

public interface Map {
    /**
     * Add all the walls to the pane where also situates the canvas.
     * @param group the pane for the walls.
     */
    void addTo(Pane pane);
    /**
     * Detect if there's collision.
     * If there is, return the bounced particle.
     * Otherwise, return the origin particle.
     * @param p the Particle to be examined.
     * @return the origin particle if there's no collision, the new particle if there is collision.
     */
    Particle collisionDetection(Particle p);
    /**
     * Calculate the time before collision.
     * 0 if already collided, Double.MAX_VALUE if no collision.
     * If the particle is reflected, then the wall that collides with it
     * isn't taken into account.
     * @param p the Particle to be examined
     * @param reflected the Particle has already reflected.
     * @return the distance before next collision, 0 if already collided, Double.MAX_VALUE if no collision.
     */
    double distanceToCollide(Particle p, boolean reflected);
    /**
     * Insert wall to the map.
     * @param wall the wall to be inserted
     */
    void insert(Wall wall);
}