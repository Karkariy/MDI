package Visitor;

import java.util.ArrayList;

import jchess.core.Player;
import jchess.core.Chessboard;
import jchess.core.Square;
import jchess.core.pieces.Piece;
import jchess.core.pieces.PieceType;

public class VisitorM2 implements Visitor {
	
	protected Chessboard m_chessboard;
	protected ArrayList<Piece> pieceList;
	
	public VisitorM2(Chessboard chessboard) {
		this.m_chessboard = chessboard;
	}
	
	@Override
	public void visit(Player player) {
		this.pieceList =  this.m_chessboard.getAllPieces(player.getColor());
	}

	@Override
	@SuppressWarnings("static-access")
	public void print() {
		System.out.print(this.pieceList.get(0).getPlayer().getName());
		System.out.print(" score : ");
		
		short score = 0;
		
		for(int i=0; i<this.pieceList.size(); i++)
		{ 
			score += this.pieceList.get(i).getValue();
		}
		
		System.out.print(score);
		System.out.println("\n");
	}

}
