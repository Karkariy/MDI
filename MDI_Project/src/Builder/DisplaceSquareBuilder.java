package Builder;

import jchess.core.Chessboard;
import jchess.core.Square;

public class DisplaceSquareBuilder{
	
	private Chessboard m_chessboard;
	
	int m_xFrom, m_yFrom, m_xTo, m_yTo = 0;
	Square m_fromSQ, m_toSQ = null;
	
	public DisplaceSquareBuilder(Chessboard chessboard)
	{
		m_chessboard = chessboard;
	}
	
	public DisplaceSquareBuilder from(String square)
	{
		m_xFrom = square.charAt(0)-97;
		m_yFrom = 8 - square.charAt(1)+48;
		
		m_fromSQ = m_chessboard.getSquares()[m_xFrom][m_yFrom];
		
		return this;
	}
	
	public DisplaceSquareBuilder to(String square)
	{
		m_xTo = square.charAt(0)-97;
		m_yTo = 8 - square.charAt(1)+48;
		
		m_toSQ = m_chessboard.getSquares()[m_xTo][m_yTo];
		
		m_chessboard.move(m_fromSQ, m_toSQ, true);
		
		return this;
	}
}