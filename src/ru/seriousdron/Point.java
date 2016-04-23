package ru.seriousdron;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Andrey Petrenko
 */
public class Point implements PointInterface {

    private final int x;
    private final int y;
    private final int height;

    private Path longestPath = null;

    private final Field field;

    private List<PointInterface> neighbours;
    private List<PointInterface> slopes;

    public Point(int x, int y, int height, Field field) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.field = field;
    }

    private List<PointInterface> getNeighbours() {

        if (neighbours == null) {
            neighbours = new ArrayList<>(4);
            if (x > 0) {
                neighbours.add(field.getPoint(x-1, y));
            }
            if (x < field.getWidth() - 1 ) {
                neighbours.add(field.getPoint(x+1, y));
            }
            if (y > 0) {
                neighbours.add(field.getPoint(x, y-1));
            }
            if (y < field.getHeight() -1) {
                neighbours.add(field.getPoint(x, y+1));
            }
        }

        return neighbours;
    }

    public List<PointInterface> getSlopes() {

        if (slopes == null) {
            slopes = Collections.unmodifiableList(getNeighbours().stream().filter(point -> this.compareTo(point) > 0).collect(Collectors.toList()));
        }

        return slopes;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                ", height=" + height +
                '}';
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public boolean isPeak() {
        return getNeighbours().stream().allMatch(point -> this.compareTo(point) > 0);
    }

    @Override
    public boolean isEnd() {
        return getSlopes().size() == 0;
    }

    @Override
    public int compareTo(PointInterface o) {
        return getHeight() - o.getHeight();
    }

    public Path getLongestPath() {
        return longestPath;
    }

    public void setLongestPath(Path longestPath) {
        this.longestPath = longestPath;
    }
}
