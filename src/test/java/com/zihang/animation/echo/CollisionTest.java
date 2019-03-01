package com.zihang.animation.echo;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class CollisionTest {

    @Test
    public void verticalBounce() {
        Wall wall = new Wall(0, 0, 100, 0);

        Particle verticalDown = new Particle(50, 1, 0, -1, 1);
        assertEquals(new Particle(50, 1, 0, 1, 1), wall.collide(verticalDown));

        Particle verticalUp = new Particle(50, -1, 0, 1, 1);
        assertEquals(new Particle(50, -1, 0, -1, 1), wall.collide(verticalUp));

        Particle rightDown = new Particle(0, 1, 1, -1, 1);
        assertEquals(new Particle(0, 1, 1, 1, 1), wall.collide(rightDown));

        Particle rightUp = new Particle(100, -1, 1, Math.sqrt(3), 1);
        assertEquals(new Particle(100, -1, 1, -Math.sqrt(3), 1), wall.collide(rightUp));

        Particle leftDown = new Particle(0, 1, -1, -1, 1);
        assertEquals(new Particle(0, 1, -1, 1, 1), wall.collide(leftDown));

        Particle leftUp = new Particle(100, -1, -1, Math.sqrt(3), 1);
        assertEquals(new Particle(100, -1, -1, -Math.sqrt(3), 1), wall.collide(leftUp));
    }

    @Test
    public void horizontalBounce() {
        Wall wall = new Wall(0, 0, 0, 100);

        Particle horizontalLeft = new Particle(1, 50, -1, 0, 1);
        assertEquals(new Particle(1, 50, 1, 0, 1), wall.collide(horizontalLeft));

        Particle horizontalRight = new Particle(-1, 50, 1, 0, 1);
        assertEquals(new Particle(-1, 50, -1, 0, 1), wall.collide(horizontalRight));

        Particle rightDown = new Particle(-1, 1, 1, -1, 1);
        assertEquals(new Particle(-1, 1, -1, -1, 1), wall.collide(rightDown));

        Particle rightUp = new Particle(-1, 100, 1, Math.sqrt(3), 1);
        assertEquals(new Particle(-1, 100, -1, Math.sqrt(3), 1), wall.collide(rightUp));

        Particle leftDown = new Particle(1, 1, -1, -1, 1);
        assertEquals(new Particle(1, 1, 1, -1, 1), wall.collide(leftDown));

        Particle leftUp = new Particle(1, 100, -1, Math.sqrt(3), 1);
        assertEquals(new Particle(1, 100, 1, Math.sqrt(3), 1), wall.collide(leftUp));
    }

    @Test
    public void distanceTest() {
        Wall wall = new Wall(-100, 0, 100, 200);

        Particle horizontalLeft = new Particle(0, 50, -1, 0, 1);
        assertEquals(50 - Math.sqrt(2) * Particle.RADIUS, wall.distanceToCollide(horizontalLeft), Particle.DELTA);

        Particle horizontalRight = new Particle(0, 50, 1, 0, 1);
        assertEquals(Double.MAX_VALUE, wall.distanceToCollide(horizontalRight), Particle.DELTA);

        Particle rightDown = new Particle(0, 0, 1, -1, 1);
        assertEquals(Double.MAX_VALUE, wall.distanceToCollide(rightDown), Particle.DELTA);

        Particle rightUp = new Particle(100 - 200 / Math.sqrt(3), 0, 1, Math.sqrt(3), 1);
        assertEquals(400 / Math.sqrt(3) - 2 * Math.sqrt(2) / (Math.sqrt(3) - 1) * Particle.RADIUS,
                wall.distanceToCollide(rightUp), Particle.DELTA);

        Particle leftDown = new Particle(1, 1, -1, -1, 1);
        assertEquals(Double.MAX_VALUE, wall.distanceToCollide(leftDown), Particle.DELTA);

        Particle leftUp = new Particle(0, 0, -1, Math.sqrt(3), 1);
        assertEquals((200 - 2 * Math.sqrt(2) * Particle.RADIUS) / (1 + Math.sqrt(3)), wall.distanceToCollide(leftUp),
                Particle.DELTA);

        Particle onLine = new Particle(-100, 0, 1, 0, 1);
        assertEquals(0, wall.distanceToCollide(onLine));
    }
}