package ru.seriousdron;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

class MaxPath {

    private Field field;

    private int pathChecked = 0;

    private Path bestPath = null;

    public static void main(String[] args) throws FileNotFoundException {

        MaxPath solver = new MaxPath();

        solver.loadInput();

        solver.field.print(System.out);

        solver.solve();
    }

    private void solve() {
        List<PointInterface> startPoints = findStartPoints();
        Collections.sort(startPoints, Collections.reverseOrder());

        for (PointInterface startPoint: startPoints) {
            if (bestPath != null && startPoint.getHeight() < bestPath.getSlope()) {
                continue;
            }
            Path path = new Path();
            findPath(path, startPoint);
        }

        System.out.println(String.format("Checked %d paths", pathChecked));
        System.out.println(bestPath);
    }

    private void findPath(Path path, PointInterface point) {
        path.addWayPoint(point);

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

    private void loadInput() throws FileNotFoundException {
        File input = new File("map.txt");
        try (Scanner scanner = new Scanner(input)) {
            field = new Field(scanner);
        }
    }

}
