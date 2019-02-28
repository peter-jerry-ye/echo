package com.zihang.animation.echo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class App extends Application {

    private final int NPARTICLES = 64;
    private final int LIFESPAN = 500;
    private final int SPEED = 1;

    private LinkedList<Particle> particles = new LinkedList<>();
    private ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    private Map map = new BasicMap();

    @Override
    public void start(Stage primaryStage) {

        Canvas canvas = new Canvas(500, 500);

        // Add mouse listener
        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                synchronized (particles) {
                    for (int i = 0; i < NPARTICLES; i++) {
                        double angle = 2 * Math.PI * i / NPARTICLES;
                        particles.add(new Particle(event.getX(), event.getY(), Math.cos(angle), Math.sin(angle), LIFESPAN));
                    }
                }
            }
        });

        // Use FixedDelay so that the fps depends on the speed of calculation.
        service.scheduleWithFixedDelay(() -> {
            List<Particle> ps = new ArrayList<>();
            synchronized (particles) {
                int size = particles.size();
                // Collision detection for each particle.
                for (int i = 0; i < size; i++) {
                    Particle p = particles.remove(0);
                    p = map.collisionDetection(p);
                    p = p.move(SPEED);
                    if (p != null)
                        particles.add(p);
                }

                // Repaint.
                ps.addAll(particles);
            }
            Platform.runLater(() -> {
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.setFill(Color.WHITE);
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                for (Particle p : ps) {
                    gc.setFill(Color.BLACK);
                    gc.fillOval(p.getX(), p.getY(), 2 * Particle.RADIUS, 2 * Particle.RADIUS);
                }
            });
        }, 100, 10, TimeUnit.MILLISECONDS);

        Pane root = new Pane();
        root.getChildren().add(canvas);
        map.addTo(root);

        Scene scene = new Scene(root, 500, 500);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Echo");
        primaryStage.show();
    }

    @Override
    public void stop() {
        service.shutdown();
    }

    public static void main(String[] args) {
        launch();
    }
}
