package jchess.core.moves;

import jchess.core.Square;
import jchess.core.pieces.Piece;

public abstract class DecorateurDeMovement extends Move{

    DecorateurDeMovement(Square from, Square to, Piece movedPiece, Piece takenPiece, Castling castlingMove, boolean wasEnPassant, Piece promotedPiece, String Commentaire, float temps){

		super(from, to, movedPiece, takenPiece, castlingMove, wasEnPassant,
				promotedPiece, Commentaire, temps);
		// TODO Auto-generated constructor stub
	}

	

}
