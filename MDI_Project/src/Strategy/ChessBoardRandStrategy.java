package Strategy;

import jchess.core.Chessboard;
import jchess.core.Colors;
import jchess.core.Player;
import jchess.core.Square;
import jchess.core.pieces.PieceType;
import jchess.core.pieces.implementation.King;

public class ChessBoardRandStrategy extends Strategy {

	public ChessBoardRandStrategy() {
		super();
	}

	@Override
	void cheessboardExecutor(Chessboard chessboard, Player plWhite, Player plBlack) {
		chessboard.setChessBordSizeH(6);
		chessboard.setChessBordSizeW(6);

		chessboard.setSquares(new Square[chessboard.getChessBordSizeH()][chessboard.getChessBordSizeW()]);//initalization of 8x8 chessboard

        for (int i = 0; i < chessboard.getChessBordSizeH(); i++) //create object for each square
        {
            for (int y = 0; y < chessboard.getChessBordSizeW(); y++)
            {
            	chessboard.getSquares()[i][y] = new Square(i, y, null);
            }
        }
        
        this.setFigures4NewGame(chessboard,0, plBlack);
		this.setFigures4NewGame(chessboard,chessboard.getChessBordSizeH()-1, plWhite);
	}

	private void setFigures4NewGame(Chessboard chessboard,int i, Player player) {
		int posPawn =0;
		if (i == 0)
		{
			player.goDown = true;
		}

	
		chessboard.getSquare(3, i).setPiece(chessboard.getPieceFactory().createPiece(PieceType.Knight, player));       
		chessboard.getSquare(2, i).setPiece(chessboard.getPieceFactory().createPiece(PieceType.Bishop, player));
		chessboard.getSquare(1, i).setPiece(chessboard.getPieceFactory().createPiece(PieceType.Bishop, player));


		if (player.getColor() == Colors.WHITE)
		{
			chessboard.setKingWhite((King)chessboard.getPieceFactory().createPiece(PieceType.King, player));
			chessboard.getSquare(4, i).setPiece(chessboard.getKingWhite());
			posPawn = i-1;
		}
		else
		{
			chessboard.setKingBlack((King)chessboard.getPieceFactory().createPiece(PieceType.King, player));
			chessboard.getSquare(4, i).setPiece(chessboard.getKingBlack());
			posPawn = i+1;
		}

		for (int x = 0; x < chessboard.getChessBordSizeW(); x++)
		{
			
			chessboard.getSquare(x, posPawn).setPiece(chessboard.getPieceFactory().createPiece(PieceType.Pawn, player));
		}
	}
}
