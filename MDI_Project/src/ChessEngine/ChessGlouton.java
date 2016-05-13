package ChessEngine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import jchess.core.Chessboard;
import jchess.core.Colors;
import jchess.core.Square;
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
		
		short maxGain = 0;
		Piece maxPiece = null;
		Square maxSquare = null;
		
		ArrayList<Piece> pieceList = this.m_chessboard.getAllPieces(this.m_color);
		
		for(int i=0; i<pieceList.size(); i++)
		{
			Set<Square> movesList = pieceList.get(i).getAllMoves();
			
			if(!movesList.isEmpty())
			{
				// If any pieces can be eat
				if(maxPiece == null)
				{
					maxPiece = pieceList.get(i);
					maxSquare = movesList.iterator().next();
				}
				
				Iterator<Square> itMove = movesList.iterator();
				while(itMove.hasNext())
				{
					Square currentSq = itMove.next();
					if(currentSq.getPiece() != null)
					{						
						if(currentSq.getPiece().getValue() >= maxGain)
						{
							maxGain = currentSq.getPiece().getValue();
							maxPiece = pieceList.get(i);
							maxSquare = currentSq;
						}
					}
				}
			}
		}
		
		m_chessboard.move(maxPiece.getSquare(), maxSquare, true);
		
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
