package Factory;

import jchess.core.Chessboard;
import jchess.core.Player;
import jchess.core.pieces.*;
import jchess.core.pieces.implementation.*;

public class ConcreteFactory {

	
	
	private Chessboard chessboard;
	
	public ConcreteFactory(Chessboard chessboard) {
		this.chessboard = chessboard ;
	}
	
	
	public Piece createPiece(PieceType pieceType, Player player) {
		
		switch(pieceType) {
		
			case Pawn :  
				return new Pawn(chessboard,player);
			case Bishop :  
				return new Bishop(chessboard,player);
			case Rook :  
				return new Rook(chessboard,player);
			case Knight :  
				return new Knight(chessboard,player);
			case King :  
				return new King(chessboard,player);
			case Queen :  
				return new Queen(chessboard,player);
			default:
				return null;	
		}
	}
	
	
}
