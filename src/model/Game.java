package model;

public class Game {
	private Piece[][] board; // Bàn cờ 9x7

	public Game() {
		// Khởi tạo bàn cờ 9x7
		this.board = new Piece[9][7];  // 9 hàng và 7 cột

		// Khởi tạo các quân cờ vào bàn cờ (giả sử bạn có phương thức initializePieces)
		initializePieces();
	}

	// Phương thức khởi tạo quân cờ
	private void initializePieces() {
		// PLAYER_ONE's pieces
		board[0][0] = new Piece(PieceType.TIGER, Player.PLAYER_ONE, new Position(0, 0));
		board[0][6] = new Piece(PieceType.LION, Player.PLAYER_ONE, new Position(0, 6));
		board[1][1] = new Piece(PieceType.CAT, Player.PLAYER_ONE, new Position(1, 1));
		board[1][5] = new Piece(PieceType.DOG, Player.PLAYER_ONE, new Position(1, 5));
		board[2][0] = new Piece(PieceType.ELEPHANT, Player.PLAYER_ONE, new Position(2, 0));
		board[2][2] = new Piece(PieceType.WOLF, Player.PLAYER_ONE, new Position(2, 2));
		board[2][4] = new Piece(PieceType.LEOPARD, Player.PLAYER_ONE, new Position(2, 4));
		board[2][6] = new Piece(PieceType.MOUSE, Player.PLAYER_ONE, new Position(2, 6));

		// PLAYER_TWO's pieces
		board[8][0] = new Piece(PieceType.LION, Player.PLAYER_TWO, new Position(8, 0));
		board[8][6] = new Piece(PieceType.TIGER, Player.PLAYER_TWO, new Position(8, 6));
		board[7][1] = new Piece(PieceType.DOG, Player.PLAYER_TWO, new Position(7, 1));
		board[7][5] = new Piece(PieceType.CAT, Player.PLAYER_TWO, new Position(7, 5));
		board[6][0] = new Piece(PieceType.MOUSE, Player.PLAYER_TWO, new Position(6, 0));
		board[6][2] = new Piece(PieceType.LEOPARD, Player.PLAYER_TWO, new Position(6, 2));
		board[6][4] = new Piece(PieceType.WOLF, Player.PLAYER_TWO, new Position(6, 4));
		board[6][6] = new Piece(PieceType.ELEPHANT, Player.PLAYER_TWO, new Position(6, 6));
	}

	// Kiểm tra di chuyển bình thường cho tất cả các quân cờ
	private boolean isMoveDirectionValid(Piece piece, Position from, Position to) {
		PieceType pieceType = piece.getType();
		if (Math.abs(from.getColumn() - to.getColumn()) > 1 || Math.abs(from.getRow() - to.getRow()) > 1) {
			return false;
		}

		switch (pieceType) {
		case MOUSE:
			// Chuột có thể di chuyển vào vùng Sông
			if (isInRiver(to)) {
				return true;  // Chuột có thể di chuyển vào Sông
			}
			break;

		case LION:
		case TIGER:
			// Sư Tử và Hổ có thể nhảy qua Sông
			if (isRiverJumpValid(from, to, piece)) {
				return true;  // Nếu Sư Tử hoặc Hổ nhảy qua Sông hợp lệ
			}
			break;

		case ELEPHANT:
		case LEOPARD:
		case CAT:
		case DOG:
		case WOLF:
			// Các quân khác có thể di chuyển theo 1 ô (không chéo)
			break;

		default:
			return false;
		}

		// Nếu quân không vào vùng Sông hoặc không nhảy qua Sông, kiểm tra di chuyển một ô bình thường
		return Math.abs(from.getColumn() - to.getColumn()) <= 1 && Math.abs(from.getRow() - to.getRow()) <= 1;
	}


	// Kiểm tra xem vị trí có nằm trong vùng sông trái hay phải không
	private boolean isInRiver(Position position) {
		int row = position.getRow();
		int col = position.getColumn();

		// Vùng sông trái
		if ((row == 3 && (col == 1 || col == 2)) || 
				(row == 4 && (col == 1 || col == 2)) || 
				(row == 5 && (col == 1 || col == 2))) {
			return true;
		}

		// Vùng sông phải
		if ((row == 3 && (col == 4 || col == 5)) || 
				(row == 4 && (col == 4 || col == 5)) || 
				(row == 5 && (col == 4 || col == 5))) {
			return true;
		}

		return false;
	}


	// Kiểm tra nếu vị trí hợp lệ trong bàn cờ
	private Piece getPieceAt(Position position) {
		if (position.getRow() >= 0 && position.getRow() < board.length &&
				position.getColumn() >= 0 && position.getColumn() < board[position.getRow()].length) {
			return board[position.getRow()][position.getColumn()];
		}
		return null;  // Không có quân cờ tại vị trí này
	}


	// Kiểm tra xem vị trí (row, column) có hợp lệ không trong bàn cờ
	public void placePiece(Piece piece, Position position) {
		if (position.getRow() >= 0 && position.getRow() < board.length &&
				position.getColumn() >= 0 && position.getColumn() < board[position.getRow()].length) {

			// Đặt quân cờ vào ô trên bàn cờ tại vị trí (row, column)
			board[position.getRow()][position.getColumn()] = piece;  
		}
	}


	// Phương thức kiểm tra cho sư tử và hổ nhảy qua sông
	private boolean isRiverJumpValid(Position from, Position to, Piece piece) {
		// Kiểm tra nếu quân cờ là Sư Tử hoặc Hổ
		if (piece.getType() == PieceType.LION || piece.getType() == PieceType.TIGER) {
			// Kiểm tra nếu vị trí đi qua là Sông (giả sử ở giữa có sông)
			if (from.getRow() == to.getRow()) {
				// Kiểm tra nếu có quân Chuột trên đường nhảy qua sông
				int minColumn = Math.min(from.getColumn(), to.getColumn());
				int maxColumn = Math.max(from.getColumn(), to.getColumn());

				// Lặp qua tất cả các ô giữa từ vị trí "from" và "to"
				for (int col = minColumn + 1; col < maxColumn; col++) {
					Position checkPosition = new Position(from.getRow(), col);

					// Kiểm tra nếu ô đó nằm trong vùng sông
					if (isInRiver(checkPosition)) {
						// Kiểm tra nếu có quân Chuột trên đường đi qua sông, không hợp lệ
						if (getPieceAt(checkPosition) != null && 
								getPieceAt(checkPosition).getType() == PieceType.MOUSE) {
							return false; // Không hợp lệ nếu có quân Chuột
						}
					}
				}
			}
		}
		return true;
	}




	// Các vị trí bẫy của PLAYER_ONE và PLAYER_TWO
	private boolean isInTrap(Position position) {
		Position[] trapsPlayerOne = {
				new Position(0, 2),
				new Position(0, 4),
				new Position(1, 3)
		};

		Position[] trapsPlayerTwo = {
				new Position(8, 2),
				new Position(8, 4),
				new Position(7, 3)
		};

		// Kiểm tra nếu vị trí hiện tại nằm trong các ô bẫy của PLAYER_ONE và PLAYER_TWO
		for (Position trap : trapsPlayerOne) {
			if (position.equals(trap)) {
				return true;
			}
		}

		for (Position trap : trapsPlayerTwo) {
			if (position.equals(trap)) {
				return true;
			}
		}

		return false;
	}


	// Xác định vị trí "Hang" của đối phương cho mỗi người chơi
	private boolean isInOpponentDen(Position position, Player player) {
		if (player == Player.PLAYER_ONE) {
			// "Hang" của đối phương là của PLAYER_TWO
			return position.getRow() == 8 && position.getColumn() == 3;
		} else {
			// "Hang" của đối phương là của PLAYER_ONE
			return position.getRow() == 0 && position.getColumn() == 3;
		}
	}


	// Hàm trả về cấp bậc của quân cờ
	private int getRank(PieceType type) {
		switch (type) {
		case ELEPHANT:
			return 8;
		case LION:
			return 7;
		case TIGER:
			return 6;
		case LEOPARD:
			return 5;
		case WOLF:
			return 4;
		case DOG:
			return 3;
		case CAT:
			return 2;
		case MOUSE:
			return 1;
		default:
			return 0;
		}
	}




	// Hàm kiểm tra ăn quân hợp lệ
	private boolean isEatingValid(Piece piece, Position from, Position to) {
		// Lấy quân cờ tại vị trí 'to' trên bàn cờ
		Piece targetPiece = board[to.getRow()][to.getColumn()];

		if (targetPiece != null && targetPiece.getPlayer() != piece.getPlayer()) {
			// Kiểm tra cấp bậc của quân cờ và quy tắc ăn của Chuột
			PieceType pieceType = piece.getType();
			PieceType targetType = targetPiece.getType();

			// 1. Chuột ăn Voi
			if (pieceType == PieceType.MOUSE) {
				if (targetType == PieceType.ELEPHANT) {
					return true; // Chuột ăn Voi
				}
				return false; // Chuột không ăn được các quân khác
			}

			// 2. Kiểm tra nếu quân đang ở dưới nước
			if (isInRiver(from) && pieceType == PieceType.MOUSE) {
				return targetPiece == null || targetPiece.getType() == PieceType.MOUSE; // Chuột dưới nước chỉ có thể ăn chuột
			}

			// 3. Kiểm tra bẫy
			if (isInTrap(to)) {
				// Nếu quân bị mắc bẫy, cấp bậc bị giảm xuống 0 và có thể bị bất kỳ quân cờ nào ăn
				return true;
			}

			// 4. Kiểm tra cấp bậc của quân cờ
			return getRank(pieceType) > getRank(targetType) && 
					Math.abs(from.getColumn() - to.getColumn()) <= 1 && 
					Math.abs(from.getRow() - to.getRow()) <= 1;
		}
		return false;
	}


	// Hàm kiểm tra di chuyển hợp lệ
	public boolean isMoveValid(Piece piece, Position from, Position to) {
		// Kiểm tra nếu từ vị trí hiện tại đến vị trí mới có cùng một ô (không di chuyển)
		if (from.equals(to)) {
			return false;
		}

		// Kiểm tra xem nước đi có hợp lệ theo hướng di chuyển của quân cờ hay không
		if (!isMoveDirectionValid(piece, from, to)) {
			return false;
		}

		// Kiểm tra nếu quân cờ đang ở trong vùng Sông (đối với Chuột và Sư Tử, Hổ)
		if (isInRiver(from) && piece.getType() != PieceType.MOUSE) {
			return false;  // Nếu quân cờ không phải Chuột mà lại ở Sông, không thể di chuyển.
		}

		// Kiểm tra điều kiện đặc biệt cho Sư Tử và Hổ (nhảy qua Sông)
		if ((piece.getType() == PieceType.LION || piece.getType() == PieceType.TIGER) &&
				isRiverJumpValid(from, to, piece)) {
			return true;  // Nếu Sư Tử hoặc Hổ nhảy qua Sông hợp lệ, cho phép.
		}

		// Kiểm tra nếu quân cờ đang vào vùng Bẫy của đối thủ
		if (isInTrap(to)) {
			// Nếu quân cờ đối thủ vào Bẫy của mình, quân sẽ bị mắc bẫy
			return false;
		}

		// Kiểm tra nếu quân cờ đi vào Hang của đối thủ
		if (isInOpponentDen(to, piece.getPlayer())) {
			// Nếu quân đi vào Hang đối thủ, cho phép và sẽ chiến thắng
			return true;
		}

		// Kiểm tra quy tắc bắt quân (Ăn quân)
		if (isEatingValid(piece, from, to)) {
			return true;  // Nếu quân có thể bắt quân đối thủ hợp lệ
		}

		return false;
	}
}
