package com.t3man.minesweeper.Bomb_Gen;

import java.util.Random;

public class Generator {

    //generating
    public static int[][] generate(int bombnumber, final int width, final int height) {
        Random r = new Random();

        int[][] grid = new int[width][height];
        for (int x = 0; x < width; x++) {
            grid[x] = new int[height];
        }

        while (bombnumber > 0) {
            int x = r.nextInt(width);
            int y = r.nextInt(height);

            if (grid[x][y] != -1) {
                grid[x][y] = -1;
                bombnumber--;
            }
        }
        grid = calculateNeigbours(grid, width, height);

        return grid;
    }

    private static int[][] calculateNeigbours(int[][] grid, final int width, final int height) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                grid[x][y] = getNeighbourNumber(grid, x, y, width, height);
            }
        }

        return grid;
    }

    private static int getNeighbourNumber(final int grid[][], final int x, final int y, final int width, final int height) {
        if (grid[x][y] == -1) {
            return -1;
        }

        int counter = 0;

        if (isMineAt(grid, x - 1, y + 1, width, height)) counter++;    //1             1   2   3
        if (isMineAt(grid, x, y + 1, width, height)) counter++;        //2             4   -   5
        if (isMineAt(grid, x + 1, y + 1, width, height)) counter++;    //3             6   7   8
        if (isMineAt(grid, x - 1, y, width, height)) counter++;        //4
        if (isMineAt(grid, x + 1, y, width, height)) counter++;        //5
        if (isMineAt(grid, x - 1, y - 1, width, height)) counter++;    //6
        if (isMineAt(grid, x, y - 1, width, height)) counter++;        //7
        if (isMineAt(grid, x + 1, y - 1, width, height)) counter++;    //8

        return counter;
    }

    private static boolean isMineAt(final int[][] grid, final int x, final int y, final int width, final int height) {
        if (x >= 0 && y >= 0 && x < width && y < height) {
            if (grid[x][y] == -1) {
                return true;
            }
        }
        return false;
    }

}
