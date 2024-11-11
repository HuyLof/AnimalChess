package model;

public class Position {
    private int row;
    private int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    // Phương thức so sánh hai vị trí
    public boolean equals(Position other) {
        return this.row == other.row && this.column == other.column;
    }
}
