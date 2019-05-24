package com.zihang.animation.echo;

import javafx.geometry.Rectangle2D;
import javafx.scene.layout.Pane;

public class QuadTreeMap extends Rectangle2D implements Map {

    private QuadTreeMap[] submaps;
    private Wall[] walls;
    private boolean isLeaf;
    private int pointer;

    public QuadTreeMap(double minX, double minY, double width, double height) {
        super(minX, minY, width, height);
        walls = new Wall[4];
        submaps = new QuadTreeMap[4];
        isLeaf = true;
        pointer = 0;
    }
    /**
     * Determine if a wall intersect with this shape
     * @param wall the wall to be examined
     * @return true if they intersect, false otherwise
     */
    public boolean intersect(Wall wall) {
        if (wall == null)
            throw new IllegalArgumentException();
        return wall.intersects(this.getMinX(), this.getMinY(), this.getWidth(), this.getHeight());
    }

    @Override
    public void insert(Wall wall) {
        if (wall == null) {
            throw new IllegalArgumentException();
        }
        if (isLeaf && pointer <= 3) {
            walls[pointer++] = wall;
        }
        else if (isLeaf && pointer == 4) {
            submaps[0] = new QuadTreeMap(getMinX(), getMinY(), getWidth() / 2, getHeight() / 2);
            submaps[1] = new QuadTreeMap(getMinX() + getWidth() / 2, getMinY(), getWidth() / 2, getHeight() / 2);
            submaps[2] = new QuadTreeMap(getMinX(), getMinY() + getHeight() / 2, getWidth() / 2, getHeight() / 2);
            submaps[3] = new QuadTreeMap(getMinX() + getWidth() / 2, getMinY() + getHeight() / 2, getWidth() / 2,
                    getHeight() / 2);
            for (Wall w : walls) {
                for (QuadTreeMap m : submaps) {
                    if (m.intersect(w))
                        m.insert(w);
                }
            }
            isLeaf = false;
        }
        if (!isLeaf) {
            for (QuadTreeMap m : submaps) {
                if (m.intersect(wall))
                    m.insert(wall);
            }
        }
    }

    @Override
    public void addTo(Pane pane) {
        if (isLeaf) {
            for (Wall w : walls) {
                if (w != null && !pane.getChildren().contains(w))
                    pane.getChildren().add(w);
            }
        } else {
            for (QuadTreeMap m : submaps) {
                if (m != null)
                    m.addTo(pane);
            }
        }
    }

    @Override
    public Particle collisionDetection(Particle p) {
        if (isLeaf) {
            Particle newParticle = p;
            for (Wall w : walls)
                if (w != null)
                    newParticle = w.collide(newParticle);
            return newParticle;
        } else {
            Particle newParticle = p;
            for (QuadTreeMap m : submaps)
                if (m != null && m.contains(newParticle))
                    newParticle = m.collisionDetection(newParticle);
            return newParticle;
        }
    }

    @Override
    public double distanceToCollide(Particle p, boolean reflected) {
        if (isLeaf) {
            double distance = Double.MAX_VALUE;
            for (Wall w : walls) {
                double d = w != null ? w.distanceToCollide(p) : Double.MAX_VALUE;
                distance = reflected && d == 0 ? distance : Math.min(distance, d);
            }
            return distance;
        } else {
            double distance = Double.MAX_VALUE;
            for (QuadTreeMap m : submaps)
                if (m != null && m.contains(p))
                    distance = Math.min(distance, m.distanceToCollide(p, reflected));
            return distance;
        }
    }

}