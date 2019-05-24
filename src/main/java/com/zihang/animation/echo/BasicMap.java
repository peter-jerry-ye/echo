package com.zihang.animation.echo;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.Pane;

public class BasicMap implements Map {
    private List<Wall> walls = new ArrayList<>();
    private final int N = 1;

    @Override
    public void addTo(Pane pane) {
        for (Wall w : walls)
            pane.getChildren().add(w);
    }

    @Override
    public Particle collisionDetection(Particle p) {
        Particle newParticle = p;
        for (Wall w : walls)
            newParticle = w.collide(newParticle);
        return newParticle;
    }

    @Override
    public double distanceToCollide(Particle p, boolean reflected) {
        double distance = Double.MAX_VALUE;
        for (Wall w : walls) {
            double d = w.distanceToCollide(p);
            if (reflected && d == 0)
                continue;
            distance = Math.min(d, distance);
        }
        return distance;
    }

    @Override
    public void insert(Wall wall) {
        walls.add(wall);
    }

}