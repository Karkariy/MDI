package Strategy;

import java.util.ArrayList;
import java.util.Random;

import jchess.core.Chessboard;
import jchess.core.Colors;
import jchess.core.Player;
import jchess.core.pieces.PieceType;
import jchess.core.pieces.implementation.King;

public class ChessboardRandStrategy extends ChessboardDefaultStrategy {

	public ChessboardRandStrategy() {
		super();
	}

	@Override
	public void cheessboardExecutor(Chessboard chessboard, Player plWhite, Player plBlack) {
		super.cheessboardExecutor(chessboard, plWhite, plBlack);
	}

	protected void setFigures4NewGame(Chessboard chessboard,int i, Player player) {
		King king;
		do {

			int posPawn =0;
			if (i == 0)
			{
				player.goDown = true;
			}

			Random randomGenerator = new Random();
			ArrayList<Integer> rand = new ArrayList<>();
			int tmpX, tmpY;
			/*New : Creation of the Pieces with Factory Pattern */
			for(int k = 2 ; k < 8 ;++k) {
				do{
					tmpX = randomGenerator.nextInt(7);
					tmpY = randomGenerator.nextInt(7);
				}while(contain(rand,tmpX,tmpY));
				rand.add(tmpX);
				rand.add(tmpY);
				chessboard.getSquare(tmpX, tmpY).setPiece(chessboard.getPieceFactory().createPiece(intToEnumType(k/2), player));
			}

			for(int k = 0 ; k < 9 ;++k) {
				do{
					tmpX = randomGenerator.nextInt(7);
					tmpY = randomGenerator.nextInt(7);
				}while(contain(rand,tmpX,tmpY));
				rand.add(tmpX);
				rand.add(tmpY);
				chessboard.getSquare(tmpX, tmpY).setPiece(chessboard.getPieceFactory().createPiece(intToEnumType(0), player));
			}

			do{
				tmpX = randomGenerator.nextInt(7);
				tmpY = randomGenerator.nextInt(7);
			}while(contain(rand,tmpX,tmpY));
			rand.add(tmpX);
			rand.add(tmpY);
			chessboard.getSquare(tmpX, tmpY).setPiece(chessboard.getPieceFactory().createPiece(PieceType.Queen, player));


			if (player.getColor() == Colors.WHITE)
			{
				king = (King)chessboard.getPieceFactory().createPiece(PieceType.King, player);
				chessboard.setKingWhite(king);
				chessboard.getSquare(4, i).setPiece(chessboard.getKingWhite());
			}
			else
			{
				king = (King)chessboard.getPieceFactory().createPiece(PieceType.King, player);
				chessboard.setKingBlack(king);
				chessboard.getSquare(4, i).setPiece(chessboard.getKingBlack());
			}
		}while(!king.isSafe());
	}

	private boolean contain(ArrayList<Integer> rand, int x ,int y) {


		if(!rand.contains(x) || !rand.contains(y))
			return false;
		else
			for(int it = 0 ; it < rand.size() -1 ; it+=2)
				if(rand.get(it) == x && rand.get(it+1)==y)
					if(x !=4 && (y != 0 || y != 7))
						return true;
		return false;
	}

	private PieceType intToEnumType(int type) {
		switch (type) {
		case 0 :
			return PieceType.Pawn;
		case 1 : 
			return PieceType.Rook;
		case 2 :
			return PieceType.Bishop;
		case 3 : 
			return PieceType.Knight;
		case 4 :
			return PieceType.King;
		case 5 :	
			return PieceType.Queen;
		default : 
			return PieceType.Pawn;
		}
	}
}
