package ru.seriousdron;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

class MaxPath {

    private Field field;

    private int pathChecked = 0;
    private int pointsChecked = 0;
    private int reusedPath = 0;

    private Path bestPath = null;

    public static void main(String[] args) throws FileNotFoundException {

        MaxPath solver = new MaxPath();

        solver.loadInput();

        //solver.field.print(System.out);

        solver.solve();
    }

    private void solve() {
        List<PointInterface> startPoints = findStartPoints();
        Collections.sort(startPoints, Collections.reverseOrder());

        long startTime = System.nanoTime();

        for (PointInterface startPoint: startPoints) {
            if (bestPath != null && startPoint.getHeight() < bestPath.getSlope()) {
                continue;
            }
            Path path = new Path();
            findPath(path, startPoint);
        }

        long endTime = System.nanoTime();

        System.out.println(String.format("Checked %d points", pointsChecked));
        System.out.println(String.format("Checked %d paths", pathChecked));
        System.out.println(String.format("Elapsed time %d", endTime-startTime));
        System.out.println(bestPath);

        pointsChecked = 0;
        pathChecked = 0;
        bestPath = null;

        startTime = System.nanoTime();
        bestPath = startPoints.stream().map(this::findBestPath).max(Path::compareTo).get();
        endTime = System.nanoTime();

        System.out.println(String.format("Checked %d points", pointsChecked));
        System.out.println(String.format("Checked %d paths", pathChecked));
        System.out.println(String.format("Reused %d paths", pathChecked));
        System.out.println(String.format("Elapsed time %d", endTime-startTime));
        System.out.println(bestPath);
    }

    private Path findBestPath(PointInterface from) {
        if (from.getLongestPath() != null) {
            reusedPath++;
            return from.getLongestPath();
        }
        pointsChecked++;

        Path bestPath;
        if (from.isEnd()) {
            pathChecked++;
            bestPath = new Path(from);
        } else {
            Path bestSubPath = from.getSlopes().parallelStream().map(this::findBestPath).max(Path::compareTo).get();
            bestPath = new Path(from, bestSubPath);
        }
        from.setLongestPath(bestPath);
        return bestPath;
    }

    private void findPath(Path path, PointInterface point) {
        pointsChecked++;
        path.addWayPoint(point);

        if (path.isComplete()) { //No ways
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
        File input = new File("map1.txt");
        try (Scanner scanner = new Scanner(input)) {
            field = new Field(scanner);
        }
    }

}
