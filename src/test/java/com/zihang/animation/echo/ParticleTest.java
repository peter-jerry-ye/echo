package com.zihang.animation.echo;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class ParticleTest {
    @Test
    public void angleTest() {
        assertEquals(new Particle(0, 0, 0, -1, 1), new Particle(0, 0, 0, -100, 1));
        assertEquals(new Particle(0, 0, 1, 0, 1), new Particle(0, 0, 100, 0, 1));        
    }

    @Test
    public void moveTest() {
        assertEquals(new Particle(1, 1, 1, 1, 1), new Particle(0, 0, 1, 1, 2).move(Math.sqrt(2)));
        assertEquals(new Particle(0, 1, 0, 1, 1), new Particle(0, 0, 0, 1, 2).move(1));
        assertEquals(new Particle(1, 0, 1, 0, 1), new Particle(0, 0, 1, 0, 2).move(1));
    }

    @Test
    public void lifespanTest() {
        assertThrows(IllegalArgumentException.class, () -> {new Particle(0, 0, 0, 1, -1);});
        assertThrows(IllegalArgumentException.class, () -> {new Particle(0, 0, 0, 0, 1);});
        assertNull(new Particle(0, 0, 1, 0, 1).move(1));
    }
}