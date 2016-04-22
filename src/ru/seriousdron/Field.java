package ru.seriousdron;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Andrey Petrenko
 */
public class Field {

    private int width;
    private int height;

    private int[][] field;

    private int peak = 0;

    public Field(Scanner scanner) {
        width = scanner.nextInt();
        height = scanner.nextInt();

        field = new int[height][width];

        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                field[y][x] = scanner.nextInt();
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Stream<PointInterface> stream()
    {
        return IntStream.range(0, width * height).mapToObj(i -> getPoint(i % width, i / width));
    }

    public PointInterface getPoint(int x, int y)
    {
        return new Point(x, y, field[y][x], this);
    }

    public void print(PrintStream out) {

        if (peak == 0) {
            peak = stream().max(PointInterface::compareTo).get().getHeight();
        }

        int maxlength = String.valueOf(peak).length();

        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                out.print(String.format("%"+maxlength+"d", field[y][x]));
                out.print(' ');
            }
            out.println();
        }
    }
}
