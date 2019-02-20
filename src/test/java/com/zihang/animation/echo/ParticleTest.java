package com.zihang.animation.echo;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class ParticleTest {
    @Test
    public void angleTest() {
        assertEquals(new Particle(0, 0, -Math.PI, 1), new Particle(0, 0, -Math.PI * 3, 1));
        assertEquals(new Particle(0, 0, Math.PI, 1), new Particle(0, 0, Math.PI * 3, 1));        
    }

    @Test
    public void moveTest() {
        assertEquals(new Particle(1, 1, Math.PI / 4, 1), new Particle(0, 0, Math.PI / 4, 2).move(Math.sqrt(2)));
        assertEquals(new Particle(0, 1, Math.PI / 2, 1), new Particle(0, 0, Math.PI / 2, 2).move(1));
        assertEquals(new Particle(1, 0, 0, 1), new Particle(0, 0, 0, 2).move(1));
    }

    @Test
    public void lifespanTest() {
        assertThrows(IllegalArgumentException.class, () -> {new Particle(0, 0, 0, -1);});
        assertNull(new Particle(0, 0, 0, 1).move(1));
    }
}