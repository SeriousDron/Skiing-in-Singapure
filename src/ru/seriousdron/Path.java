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

    public Path(Path other) {
        wayPoints = new ArrayList<>(other.wayPoints);
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
        return wayPoints.get(0).getHeight() - wayPoints.get(wayPoints.size() - 1).getHeight();
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
}
