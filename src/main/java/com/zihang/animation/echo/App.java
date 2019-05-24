package com.zihang.animation.echo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

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

    private final List<Particle> particles = new ArrayList<>();
    private ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    private final Map map = new QuadTreeMap(0, 0, 500, 500);
    // private final Map map = new BasicMap();

    {
        int N = 100;
        for (int i = 0; i < N; i++) {
            map.insert(new Wall(100 + i * 300 / N, 100, 100 + i * 300 / N + 300 / N, 100));
            map.insert(new Wall(100 + i * 300 / N, 400, 100 + i * 300 / N + 300 / N, 400));
            map.insert(new Wall(100, 100 + i * 300 / N, 100, 100 + i * 300 / N + 300 / N));
            map.insert(new Wall(400, 100 + i * 300 / N, 400, 100 + i * 300 / N + 300 / N));
        }
        map.insert(new Wall(200, 300, 300, 200));
    }

    private final BiFunction<Map, Particle, Particle> BI_FUNCTION = (Map m, Particle p) -> {
        double toGo = SPEED;
        double distance = m.distanceToCollide(p, false);
        p = p.decreaseLifespan();
        if (p == null)
            return null;
        while (true) {
            if (distance > toGo) {
                return p.move(toGo);
            }
            p = p.move(distance);
            p = m.collisionDetection(p);
            toGo -= distance;
            distance = m.distanceToCollide(p, true);
        }
    };

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
                        particles.add(
                                new Particle(event.getX(), event.getY(), Math.cos(angle), Math.sin(angle), LIFESPAN));
                    }
                }
            }
        });

        // Use FixedDelay so that the fps depends on the speed of calculation.
        service.scheduleWithFixedDelay(() -> {
            List<Particle> ps = new ArrayList<>();
            synchronized (particles) {
                List<Particle> tmp = particles.parallelStream().map((p) -> BI_FUNCTION.apply(map, p))
                        .filter(p -> p != null).sequential().collect(Collectors.toList());
                particles.clear();
                particles.addAll(tmp);

                // Repaint
                ps.addAll(tmp);
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
