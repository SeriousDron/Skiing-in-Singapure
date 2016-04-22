package ru.seriousdron;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class MaxPath {

    private static Field field;

    private static int pathChecked = 0;

    private static Path bestPath = null;
    private static Set<PointInterface> endPoints;

    public static void main(String[] args) throws FileNotFoundException {

        MaxPath solver = new MaxPath();

        solver.loadInput();

        field.print(System.out);

        endPoints = solver.findEndPoints();
        System.out.println(endPoints);


        List<PointInterface> startPoints = solver.findStartPoints();
        Collections.sort(startPoints, Collections.reverseOrder());

        for (PointInterface startPoint: startPoints) {
            if (solver.bestPath != null && startPoint.getHeight() < bestPath.getSlope()) {
                continue;
            }
            Path path = new Path();
            solver.findPath(path, startPoint);
        }

        System.out.println(String.format("Checked %d paths", pathChecked));
        System.out.println(bestPath);
    }

    private void findPath(Path path, PointInterface point) {
        path.addWaypoint(point);

        if (point.getSlopes().size() == 0) { //No ways
            //System.out.println(path);
            pathChecked++;
            if (bestPath == null || path.compareTo(bestPath) > 0) {
                bestPath = path;
                return ;
            }
        }

        for(PointInterface nextPoint: point.getSlopes()) {
            findPath(new Path(path), nextPoint);
        }
    }

    private List<PointInterface> findStartPoints() {
        return field.stream().filter(PointInterface::isPeak).collect(Collectors.toList());
    }

    private Set<PointInterface> findEndPoints() {
        return field.stream().filter(point -> point.getSlopes().size() == 0).collect(Collectors.toSet());
    }

    private void loadInput() throws FileNotFoundException {
        File input = new File("map.txt");
        try (Scanner scanner = new Scanner(input)) {
            field = new Field(scanner);
        }
    }

}
