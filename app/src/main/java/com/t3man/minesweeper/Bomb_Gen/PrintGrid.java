package com.t3man.minesweeper.Bomb_Gen;

import android.util.Log;

public class PrintGrid {

    //void for getting the grid into Android Monitor
    public static void print(final int[][] grid, final int width, final int height) {
        for (int x = 0; x < width; x++) {
            String printedText = "| ";
            for (int y = 0; y < height; y++) {
                printedText += String.valueOf(grid[x][y]).replace("-1", "B") + " | ";
            }
            Log.e("", printedText);
        }
    }
}
