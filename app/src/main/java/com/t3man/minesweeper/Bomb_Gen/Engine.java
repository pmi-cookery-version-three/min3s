package com.t3man.minesweeper.Bomb_Gen;

import android.content.Context;

import com.t3man.minesweeper.Grid.Cell;
import com.t3man.minesweeper.MainActivity;

public class Engine {
    private static Engine instance;

    public static final int width = 10;
    public static final int height = 10;
    public static final int bomb_num = width * height / 9;
    public static final int bomb_counter = bomb_num;


    private static Context context;

    private static Cell[][] MinesweeperGrid = new Cell[width][height];

    public static Engine getInstance() {
        if (instance == null) {
            instance = new Engine();
        }
        return instance;
    }

    private Engine() {
    }

    //refreshing the grid
    public void refreshGrid() {
        createGrid(this.context);

        //refreshing flag points
        Cell.refreshFlags();
    }

    //creating the grid
    public void createGrid(Context context) {
        this.context = context;

        int[][] GeneratedGrid = Generator.generate(bomb_num, width, height);
        PrintGrid.print(GeneratedGrid, width, height);
        setGrid(context, GeneratedGrid);

        //enabling grid cells
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                getCellAt(x, y).setEnabled(true);
            }
        }
    }

    private void setGrid(final Context context, final int[][] grid) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (MinesweeperGrid[x][y] == null) {
                    MinesweeperGrid[x][y] = new Cell(context, x, y);
                }
                MinesweeperGrid[x][y].setValue(grid[x][y]);
                MinesweeperGrid[x][y].invalidate();
            }
        }
    }

    public Cell getCellAt(int position) {
        int x = position % width;
        int y = position / width;

        return MinesweeperGrid[x][y];
    }

    public static Cell getCellAt(int x, int y) {
        return MinesweeperGrid[x][y];
    }

    public void click(int x, int y) {
        if (x >= 0 && y >= 0 && x < width && y < height && !getCellAt(x, y).isClicked()) {
            getCellAt(x, y).setClicked();

            if (getCellAt(x, y).getValue() == 0) {
                for (int xt = -1; xt <= 1; xt++) {
                    for (int yt = -1; yt <= 1; yt++) {
                        if (xt * xt + yt * yt != 0) {
                            click(x + xt, y + yt);
                        }
                    }
                }
            }

            if (getCellAt(x, y).isBomb()) {
                onGameLost();
            }
        }

        checkEnd();
    }

    public static boolean checkEnd() {
        int bombNotFound = bomb_num;
        int notRevealed = width * height;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (getCellAt(x, y).isRevealed() || getCellAt(x, y).isFlagged()) {
                    notRevealed--;
                }

                if (getCellAt(x, y).isFlagged() && getCellAt(x, y).isBomb()) {
                    bombNotFound--;
                }
            }
        }

        //Log.e("CHECKING COUNTERS", "" + bombNotFound + " && " + bomb_num + " == " + Cell.flagged);
        if (bombNotFound == 0) {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    getCellAt(x, y).setEnabled(false);
                }
            }
            MainActivity.timer_stop();

            //win
            MainActivity.WActivity.show();
        }


        return false;
    }

    public void flag(int x, int y) {
        boolean isFlagged = getCellAt(x, y).isFlagged();
        getCellAt(x, y).setFlagged(!isFlagged);
        getCellAt(x, y).invalidate();
    }

    private void onGameLost() {

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                getCellAt(x, y).setRevealed();
            }
        }

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                getCellAt(x, y).setEnabled(false);
            }
        }
        MainActivity.timer_stop();

        //loss
        MainActivity.LActivity.show();
    }
}
