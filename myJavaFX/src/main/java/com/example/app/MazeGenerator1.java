package com.example.app;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class MazeGenerator1 extends Application {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private static final int CELL_SIZE = 20;
    private static final int COLS = WIDTH / CELL_SIZE;
    private static final int ROWS = HEIGHT / CELL_SIZE;
    private Cell[][] grid = new Cell[COLS][ROWS];
    private List<Cell> path = new ArrayList<>();
    private Timeline timeline;
    private int pathIndex = 0;

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

        // 寻路算法
        Cell start = grid[0][0];
        Cell end = grid[COLS - 1][ROWS - 1];
        path = aStarSearch(start, end);

        // 使用 Timeline 实现路径动画
        timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            if (pathIndex < path.size()) {
                Cell current = path.get(pathIndex);
                current.highlight(gc, Color.BLUE);
                pathIndex++;
            }
        }));
        timeline.setCycleCount(path.size());
        timeline.play();

        StackPane root = new StackPane();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        primaryStage.setTitle("Maze Generator with Pathfinding");
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

    private List<Cell> aStarSearch(Cell start, Cell end) {
        PriorityQueue<Cell> openSet = new PriorityQueue<>((a, b) -> Double.compare(a.f, b.f));
        List<Cell> closedSet = new ArrayList<>();
        start.g = 0;
        start.f = heuristic(start, end);
        openSet.add(start);

        while (!openSet.isEmpty()) {
            Cell current = openSet.poll();

            if (current == end) {
                return reconstructPath(current);
            }

            closedSet.add(current);

            for (Cell neighbor : getNeighbors(current)) {
                if (closedSet.contains(neighbor)) {
                    continue;
                }

                double tentativeG = current.g + 1;

                if (!openSet.contains(neighbor) || tentativeG < neighbor.g) {
                    neighbor.g = tentativeG;
                    neighbor.f = neighbor.g + heuristic(neighbor, end);
                    neighbor.previous = current;

                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }
            }
        }

        return new ArrayList<>();
    }

    private List<Cell> getNeighbors(Cell cell) {
        List<Cell> neighbors = new ArrayList<>();

        if (!cell.walls[0] && cell.y > 0) neighbors.add(grid[cell.x][cell.y - 1]); // top
        if (!cell.walls[1] && cell.x < COLS - 1) neighbors.add(grid[cell.x + 1][cell.y]); // right
        if (!cell.walls[2] && cell.y < ROWS - 1) neighbors.add(grid[cell.x][cell.y + 1]); // bottom
        if (!cell.walls[3] && cell.x > 0) neighbors.add(grid[cell.x - 1][cell.y]); // left

        return neighbors;
    }

    private double heuristic(Cell a, Cell b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    private List<Cell> reconstructPath(Cell current) {
        List<Cell> path = new ArrayList<>();
        Cell temp = current;
        while (temp != null) {
            path.add(temp);
            temp = temp.previous;
        }
        Collections.reverse(path);
        return path;
    }

    class Cell {
        int x, y;
        boolean[] walls = {true, true, true, true}; // top, right, bottom, left
        boolean visited = false;
        double g = Double.MAX_VALUE;
        double f = Double.MAX_VALUE;
        Cell previous = null;

        Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }

        void draw(GraphicsContext gc) {
            double xPos = x * CELL_SIZE;
            double yPos = y * CELL_SIZE;

            gc.setStroke(Color.BLACK);
            if (walls[0]) gc.strokeLine(xPos, yPos, xPos + CELL_SIZE, yPos); // top
            if (walls[1]) gc.strokeLine(xPos + CELL_SIZE, yPos, xPos + CELL_SIZE, yPos + CELL_SIZE); // right
            if (walls[2]) gc.strokeLine(xPos + CELL_SIZE, yPos + CELL_SIZE, xPos, yPos + CELL_SIZE); // bottom
            if (walls[3]) gc.strokeLine(xPos, yPos + CELL_SIZE, xPos, yPos); // left

            if (visited) {
                gc.setFill(Color.LIGHTGRAY);
                gc.fillRect(xPos, yPos, CELL_SIZE, CELL_SIZE);
            }
        }

        void highlight(GraphicsContext gc, Color color) {
            double xPos = x * CELL_SIZE;
            double yPos = y * CELL_SIZE;
            gc.setFill(color);
            gc.fillRect(xPos, yPos, CELL_SIZE, CELL_SIZE);
        }
    }
}

