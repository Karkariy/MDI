package ChessEngine;

import java.util.ArrayList;

import jchess.core.Chessboard;
import jchess.core.Colors;
import jchess.core.pieces.Piece;
import jchess.core.pieces.implementation.King;

public class ChessGlouton extends ChessEngine {

	public ChessGlouton(Chessboard chessboard) {
		super(chessboard);
	}

	@Override
	public boolean play() {
		if(!canPlay())
		{
			return false;
		}
		
		ArrayList<Piece> pieceList = this.m_chessboard.getAllPieces(this.m_color);
		
		
		return true;
	}

	protected boolean canPlay()
	{
		King king;
		if(m_color == Colors.WHITE)
		{
			king = m_chessboard.getKingWhite();
		} else {
			king = m_chessboard.getKingBlack();
		}
		
		if(king.isCheckmatedOrStalemated() == 1){
			return false;
		}
		else{
			return true;
		}
	}
}
