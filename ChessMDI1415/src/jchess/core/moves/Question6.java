package jchess.core.moves;

import jchess.core.Chessboard;
import jchess.core.Square;
import jchess.core.pieces.Piece;

public class Question6 extends DecorateurDeMovement{

	Question6(Square from, Square to, Piece movedPiece, Piece takenPiece,
			Castling castlingMove, boolean wasEnPassant, Piece promotedPiece,
			String Commentaire, float time) {
		super(from, to, movedPiece, takenPiece, castlingMove, wasEnPassant,
				promotedPiece, Commentaire, time);
		this.commentaire = Commentaire;
		this.time = (float) 0.54;
	}
}
