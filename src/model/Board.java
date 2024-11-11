package model;

public class Board {
    private Tile[][] tiles;
    
    public Board() {
        this.tiles = new Tile[9][7]; // 9 rows, 7 columns
        initializeBoard();
    }
    
    private void initializeBoard() {
        // Initialize the board with pieces, traps, water areas, etc.
    }
    
    public Piece getPieceAt(Position position) {
        return tiles[position.getRow()][position.getColumn()].getPiece();
    }
    
    public void movePiece(Position from, Position to) {
        Piece piece = getPieceAt(from);
        tiles[to.getRow()][to.getColumn()].setPiece(piece);
        tiles[from.getRow()][from.getColumn()].setPiece(null);
    }
    
    public Tile getTileAt(Position position) {
        return tiles[position.getRow()][position.getColumn()];
    }
}

