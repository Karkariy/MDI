package ChessEngine;

import jchess.core.Chessboard;
import jchess.core.Colors;
import jchess.core.Square;

public abstract class ChessEngine {

	protected Chessboard m_chessboard;
	protected Colors m_color;
	
	public ChessEngine(Chessboard chessboard)
	{
		m_chessboard = chessboard;
	}
	
	public void setColor(Colors color)
	{
		m_color = color;
	}
	
	public abstract boolean play();
}
