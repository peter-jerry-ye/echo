package com.zihang.animation.echo;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class CollisionTest {

    @Test
    public void verticalBounce() {
        Wall wall = new Wall(0, 0, 100, 0);

        Particle verticalDown = new Particle(50, 1, - Math.PI / 2, 1);
        assertEquals(new Particle(50, 1, Math.PI / 2, 1), wall.collide(verticalDown));

        Particle verticalUp = new Particle(50, -1, Math.PI / 2, 1);
        assertEquals(new Particle(50, -1, - Math.PI / 2, 1), wall.collide(verticalUp));

        Particle rightDown = new Particle(0, 1, - Math.PI / 4, 1);
        assertEquals(new Particle(0, 1, Math.PI / 4, 1), wall.collide(rightDown));

        Particle rightUp = new Particle(100, -1, Math.PI / 6, 1);
        assertEquals(new Particle(100, -1, - Math.PI / 6, 1), wall.collide(rightUp));

        Particle leftDown = new Particle(0, 1, - Math.PI * 3/ 4, 1);
        assertEquals(new Particle(0, 1, Math.PI * 3 / 4, 1), wall.collide(leftDown));

        Particle leftUp = new Particle(100, -1, Math.PI * 5 / 6, 1);
        assertEquals(new Particle(100, -1, - Math.PI * 5 / 6, 1), wall.collide(leftUp));
    }

    @Test
    public void horizontalBounce() {
        Wall wall = new Wall(0, 0, 0, 100);

        Particle horizontalLeft = new Particle(1, 50, -Math.PI, 1);
        assertEquals(new Particle(1, 50, 0, 1), wall.collide(horizontalLeft));

        Particle horizontalRight = new Particle(-1, 50, 0, 1);
        assertEquals(new Particle(-1, 50, -Math.PI, 1), wall.collide(horizontalRight));

        Particle rightDown = new Particle(-1, 1, - Math.PI / 4, 1);
        assertEquals(new Particle(-1, 1, - Math.PI * 3 / 4, 1), wall.collide(rightDown));

        Particle rightUp = new Particle(-1, 100, Math.PI / 6, 1);
        assertEquals(new Particle(-1, 100, Math.PI * 5 / 6, 1), wall.collide(rightUp));

        Particle leftDown = new Particle(1, 1, - Math.PI * 3 / 4, 1);
        assertEquals(new Particle(1, 1, - Math.PI / 4, 1), wall.collide(leftDown));

        Particle leftUp = new Particle(1, 100, Math.PI * 5 / 6, 1);
        assertEquals(new Particle(1, 100, Math.PI / 6, 1), wall.collide(leftUp));
    }
}