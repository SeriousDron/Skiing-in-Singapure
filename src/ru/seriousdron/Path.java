package ru.seriousdron;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrey Petrenko
 */
class Path implements Comparable<Path> {

    private final List<PointInterface> wayPoints;

    public Path() {
        wayPoints = new ArrayList<>();
    }

    public Path(PointInterface point) {
        wayPoints = new ArrayList<>(1);
        wayPoints.add(point);
    }

    public Path(Path other) {
        wayPoints = new ArrayList<>(other.wayPoints);
    }

    public Path(PointInterface from, Path bestSubPath) {
        wayPoints = new ArrayList<>(bestSubPath.getLength()+1);
        wayPoints.add(from);
        wayPoints.addAll(bestSubPath.wayPoints);
    }

    public void addWayPoint(PointInterface point) {

        wayPoints.add(point);
    }

    public int getLength() {
        return  wayPoints.size();
    }

    public int getSlope() {
        if (wayPoints.size() == 0) {
            return 0;
        }
        //noinspection ConstantConditions
        return wayPoints.get(0).getHeight() - getLastPoint().getHeight();
    }

    @Override
    public int compareTo(Path o) {
        if (this.getLength() != o.getLength()) {
            return this.getLength() - o.getLength();
        }
        return this.getSlope() - o.getSlope();
    }

    @Override
    public String toString() {
        return "Path{" +
                "wayPoints=" + wayPoints +
                '}';
    }

    public boolean isComplete() {
        if (wayPoints.size() == 0) {
            return false;
        }
        //noinspection ConstantConditions
        return getLastPoint().isEnd();
    }

    private PointInterface getLastPoint() {
        if (wayPoints.size() == 0) {
            return null;
        }
        return wayPoints.get(wayPoints.size() - 1);
    }

}
