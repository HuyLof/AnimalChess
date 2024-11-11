package model;

public class Tile {
    private Piece piece; // Piece occupying the tile
    
    public Tile() {
        this.piece = null; // No piece initially
    }
    
    public Piece getPiece() {
        return piece;
    }
    
    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}

