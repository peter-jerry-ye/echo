package com.zihang.animation.echo;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.Pane;

public class BasicMap implements Map {
    private List<Wall> walls = new ArrayList<>();
    private final int N = 1;
    public BasicMap() {
        for (int i = 0; i < N; i++) {
            walls.add(new Wall(100 + i * 300 / N, 100, 100 + i * 300 / N + 300 / N, 100));
            walls.add(new Wall(100 + i * 300 / N, 400, 100 + i * 300 / N + 300 / N, 400));
            walls.add(new Wall(100, 100 + i * 300 / N, 100, 100 + i * 300 / N + 300 / N));
            walls.add(new Wall(400, 100 + i * 300 / N, 400, 100 + i * 300 / N + 300 / N));
        }
        walls.add(new Wall(200, 300, 300, 200));
    }

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
    public double distanceToCollide(Particle p) {
        double distance = Double.MAX_VALUE;
        for (Wall w : walls)
            distance = Math.min(distance, w.distanceToCollide(p));
        return distance;
    }

}