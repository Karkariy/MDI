package Decorator;

import jchess.core.Game;
import jchess.core.Square;
import jchess.core.moves.Castling;
import jchess.core.moves.Moves;
import jchess.core.pieces.Piece;

public abstract class AbstractMovesDecorator extends Moves
{
	public AbstractMovesDecorator(Game game) {
		super(game);
	}
	
	protected Moves moves;
	public abstract void addMove(Square begin, Square end, boolean registerInHistory, Castling castlingMove, boolean wasEnPassant, Piece promotedPiece); 
}