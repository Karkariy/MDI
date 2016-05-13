package Factory;

import jchess.core.Player;
import jchess.core.pieces.Piece;
import jchess.core.pieces.PieceType;

public interface FactoryPiece {
	
	
	public Piece createPiece(PieceType pieceType, Player player);
}
