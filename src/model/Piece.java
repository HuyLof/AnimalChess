package model;

public class Piece {
    private PieceType type;
    private Player player;  // Xác định người chơi sở hữu quân cờ này
    private Position position;  // Vị trí của quân cờ trên bàn cờ

    public Piece(PieceType type, Player player, Position position) {
        this.type = type;
        this.player = player;
        this.position = position;
    }

    public PieceType getType() {
        return type;
    }

    public Player getPlayer() {
        return player;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
