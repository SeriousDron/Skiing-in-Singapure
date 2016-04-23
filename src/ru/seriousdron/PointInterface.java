package ru.seriousdron;

import java.util.List;

/**
 * @author Andrey Petrenko
 */
public interface PointInterface extends Comparable<PointInterface> {

    List<PointInterface> getSlopes();

    int getHeight();

    boolean isPeak();
    boolean isEnd();

    Path getLongestPath();
    void setLongestPath(Path length);
}
