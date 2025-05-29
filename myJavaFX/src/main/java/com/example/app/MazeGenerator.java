package com.example.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MazeGenerator extends Application {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private static final int CELL_SIZE = 20;
    private static final int COLS = WIDTH / CELL_SIZE;
    private static final int ROWS = HEIGHT / CELL_SIZE;
    private Cell[][] grid = new Cell[COLS][ROWS];

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        initializeGrid();
        generateMaze(0, 0);

        drawMaze(gc);

        StackPane root = new StackPane();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        primaryStage.setTitle("Maze Generator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initializeGrid() {
        for (int x = 0; x < COLS; x++) {
            for (int y = 0; y < ROWS; y++) {
                grid[x][y] = new Cell(x, y);
            }
        }
    }

    private void generateMaze(int x, int y) {
        grid[x][y].visited = true;
        List<Cell> neighbors = getUnvisitedNeighbors(x, y);

        while (!neighbors.isEmpty()) {
            Cell next = neighbors.remove(0);
            removeWalls(grid[x][y], next);
            generateMaze(next.x, next.y);
            neighbors = getUnvisitedNeighbors(x, y);
        }
    }

    private List<Cell> getUnvisitedNeighbors(int x, int y) {
        List<Cell> neighbors = new ArrayList<>();

        if (x > 0 && !grid[x - 1][y].visited) {
            neighbors.add(grid[x - 1][y]);
        }
        if (x < COLS - 1 && !grid[x + 1][y].visited) {
            neighbors.add(grid[x + 1][y]);
        }
        if (y > 0 && !grid[x][y - 1].visited) {
            neighbors.add(grid[x][y - 1]);
        }
        if (y < ROWS - 1 && !grid[x][y + 1].visited) {
            neighbors.add(grid[x][y + 1]);
        }

        Collections.shuffle(neighbors);
        return neighbors;
    }

    private void removeWalls(Cell current, Cell next) {
        int dx = current.x - next.x;
        int dy = current.y - next.y;

        if (dx == 1) {
            current.walls[3] = false;
            next.walls[1] = false;
        } else if (dx == -1) {
            current.walls[1] = false;
            next.walls[3] = false;
        }

        if (dy == 1) {
            current.walls[0] = false;
            next.walls[2] = false;
        } else if (dy == -1) {
            current.walls[2] = false;
            next.walls[0] = false;
        }
    }

    private void drawMaze(GraphicsContext gc) {
        for (int x = 0; x < COLS; x++) {
            for (int y = 0; y < ROWS; y++) {
                grid[x][y].draw(gc);
            }
        }
    }

    class Cell {
        int x, y;
        boolean[] walls = {true, true, true, true}; // top, right, bottom, left
        boolean visited = false;

        Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }

        void draw(GraphicsContext gc) {
            double xPos = x * CELL_SIZE;
            double yPos = y * CELL_SIZE;

            gc.setStroke(javafx.scene.paint.Color.BLACK);
            if (walls[0]) gc.strokeLine(xPos, yPos, xPos + CELL_SIZE, yPos); // top
            if (walls[1]) gc.strokeLine(xPos + CELL_SIZE, yPos, xPos + CELL_SIZE, yPos + CELL_SIZE); // right
            if (walls[2]) gc.strokeLine(xPos + CELL_SIZE, yPos + CELL_SIZE, xPos, yPos + CELL_SIZE); // bottom
            if (walls[3]) gc.strokeLine(xPos, yPos + CELL_SIZE, xPos, yPos); // left

            if (visited) {
                gc.setFill(javafx.scene.paint.Color.LIGHTGRAY);
                gc.fillRect(xPos, yPos, CELL_SIZE, CELL_SIZE);
            }
        }
    }
}

