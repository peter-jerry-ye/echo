package com.zihang.animation.echo;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class ParticleTest {
    @Test
    public void particleTest() {
        // Constructor test
        assertEquals(new Particle(0, 0, -Math.PI), new Particle(0, 0, -Math.PI * 3));
        assertEquals(new Particle(0, 0, Math.PI), new Particle(0, 0, Math.PI * 3));
        // Move test
        assertEquals(new Particle(1, 1, Math.PI / 4), new Particle(0, 0, Math.PI / 4).move(Math.sqrt(2)));
        assertEquals(new Particle(0, 1, Math.PI / 2), new Particle(0, 0, Math.PI / 2).move(1));
        assertEquals(new Particle(1, 0, 0), new Particle(0, 0, 0).move(1));
    }
}