package ru.seriousdron;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class MaxPath {

    private static Field field;

    private int bestPathLen = 0;
    private int bestPathSlope = 0;

    public static void main(String[] args) throws FileNotFoundException {

        MaxPath solver = new MaxPath();

        solver.loadInput();

        field.print(System.out);

        List<PointInterface> endPoints = solver.findEndPoints();

        System.out.println(endPoints);
        Collections.sort(endPoints);
        System.out.println(endPoints);

        List<PointInterface> startPoints = solver.findStartPoints();
        Collections.sort(startPoints, Collections.reverseOrder());
        System.out.println(startPoints);


    }



    private void processBranch(Point endpoint, int pathLen) {
     /*   pathLen++;
        ArrayList<Point> neighbors = getNeighbors(endpoint);
        if (neighbors.size() == 0) {
            
        }*/
    }

    private List<PointInterface> findStartPoints() {
        return field.stream().filter(PointInterface::isPeak).collect(Collectors.toList());
    }

    private List<PointInterface> findEndPoints() {
        return field.stream().filter(point -> point.getSlopes().size() == 0).collect(Collectors.toList());
    }

    private void loadInput() throws FileNotFoundException {
        File input = new File("map.txt");
        try (Scanner scanner = new Scanner(input)) {
            field = new Field(scanner);
        }
    }

}
