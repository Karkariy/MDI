package Builder;

import jchess.core.Chessboard;
import jchess.core.Square;

public class DisplacementBuilder{
	
	private Chessboard m_chessboard;
	
	int m_xFrom, m_yFrom, m_xTo, m_yTo = 0;
	Square m_fromSQ, m_toSQ = null;
	
	public DisplacementBuilder(Chessboard chessboard)
	{
		m_chessboard = chessboard;
	}
	
	public DisplacementBuilder xFrom(int xFrom)
	{        
		m_xFrom = xFrom;
		
		return this;
	}
	
	public DisplacementBuilder yFrom(int yFrom)
	{        
		m_yFrom = yFrom;
		m_fromSQ = m_chessboard.getSquares()[m_xFrom][m_yFrom];
        
        return this;
	}
	
	public DisplacementBuilder xTo(int xTo)
	{        
		m_xTo = xTo;
		
		return this;
	}
	
	public DisplacementBuilder yTo(int yTo)
	{
		m_yTo = yTo;
		m_toSQ = m_chessboard.getSquares()[m_xTo][m_yTo];
        
		m_chessboard.move(m_fromSQ, m_toSQ, true);
		
		return this;
	}
}
