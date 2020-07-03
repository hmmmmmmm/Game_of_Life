package life;

import java.io.IOException;

public class Grid {
    private Cell[][] cells;

    public Grid(Cell[][] cells) {
        this.cells = cells;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    //    public void print(int i) {
//        System.out.printf("Generation #%d%nAlive: %d%n", i+1, countTotal(cells));
//        for (Cell[] row : cells) {
//            for (Cell cell: row) {
//                System.out.print(cell);
//            }
//            System.out.println();
//        }
//    }

    public Cell[][] next() {
        Cell[][] nextCells = new Cell[cells.length][];
        for (int row = 0; row < cells.length; row++) {
            nextCells[row] = new Cell[cells[row].length];
            for (int col = 0; col < cells[row].length; col++) {
                Cell cell = cells[row][col];
                State newState;
                State state = cell.state;
                if (state == State.ALIVE && isSurvivor(cell) || state == State.DEAD && isReborn(cell)) {
                    newState = State.ALIVE;
                } else {
                    newState = State.DEAD;
                }
                Cell nextCell = new Cell(row, col, newState);
                nextCells[row][col] = nextCell;
            }
        }
        return nextCells;
    }

    private boolean isReborn(Cell cell) {
        int count = countAlive(cell);
        return count == 3;
    }

    private boolean isSurvivor(Cell cell) {
        int count = countAlive(cell);
        return count == 2 || count == 3;
    }

    private int countAlive(Cell cell) {
        int count = 0;

        int row = cell.row;
        int rowLength = cells.length;
        int upRow = back(row, rowLength);
        int downRow = forth(row, rowLength);

        int col = cell.col;
        int colLength = cells[row].length;
        int leftCol = back(col, colLength);
        int rightCol = forth(col, colLength);

        State state = State.ALIVE;

        Cell northwest = cells[upRow][leftCol];
        if (northwest.state == state) count++;
        Cell north = cells[upRow][col];
        if (north.state == state) count++;
        Cell northeast = cells[upRow][rightCol];
        if (northeast.state == state) count++;
        Cell west = cells[row][leftCol];
        if (west.state == state) count++;
        Cell east = cells[row][rightCol];
        if (east.state == state) count++;
        Cell southwest = cells[downRow][leftCol];
        if (southwest.state == state) count++;
        Cell south = cells[downRow][col];
        if (south.state == state) count++;
        Cell southeast = cells[downRow][rightCol];
        if (southeast.state == state) count++;
        return count;
    }

    public int countTotal(Cell[][] toCount) {
        int counter = 0;
        for (int i = 0; i < toCount.length; i++) {
            for (int j = 0; j < toCount[i].length; j++) {
                if (toCount[i][j].state == State.ALIVE) {
                    counter++;
                }
            }
        }
        return counter;
    }

    private int back(int index, int length) {
        int back = index - 1;
        if (back >= 0) {
            return back;
        } else {
            return length - 1;
        }
    }

    private int forth(int index, int length) {
        int forth = index + 1;
        if (forth < length) {
            return forth;
        } else {
            return 0;
        }
    }
}
