package ChessEngine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import jchess.core.Chessboard;
import jchess.core.Colors;
import jchess.core.Square;
import jchess.core.pieces.Piece;
import jchess.core.pieces.implementation.King;

public class ChessRandom extends ChessEngine {

	public ChessRandom(Chessboard chessboard) {
		super(chessboard);
	}

	@Override
	public boolean play() {
		if(!canPlay())
		{
			return false;
		}
		
		ArrayList<Piece> pieceList = this.m_chessboard.getAllPieces(this.m_color);
		
		Random randomGenerator = new Random();
		int randomValue = 0;
		
		Piece randomPiece;
		Set<Square> movesList;
		
		do{
			randomValue = randomGenerator.nextInt(pieceList.size());
					
			randomPiece = pieceList.get(randomValue);
			
			movesList = randomPiece.getAllMoves();
		}while(movesList.size() == 0);
		
		randomValue = randomGenerator.nextInt(movesList.size());

		Square nextSq = new ArrayList<Square>(movesList).get(randomValue);
		
		m_chessboard.move(randomPiece.getSquare(), nextSq, true);
		
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
