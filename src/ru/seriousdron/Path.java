package ru.seriousdron;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrey Petrenko
 */
public class Path implements Comparable<Path> {

    List<PointInterface> waypoints;

    public Path() {
        waypoints = new ArrayList<>();
    }

    public Path(Path other) {
        waypoints = new ArrayList<>(other.waypoints);
    }

    public void addWaypoint(PointInterface point) {

        waypoints.add(point);
    }

    public int getLength() {
        return  waypoints.size();
    }

    public int getSlope() {
        if (waypoints.size() == 0) {
            return 0;
        }
        return waypoints.get(0).getHeight() - waypoints.get(waypoints.size() - 1).getHeight();
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
                "waypoints=" + waypoints +
                '}';
    }
}
