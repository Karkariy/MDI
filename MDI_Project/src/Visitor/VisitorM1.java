package Visitor;

import java.util.ArrayList;

import jchess.core.Player;
import jchess.core.Chessboard;
import jchess.core.Square;
import jchess.core.pieces.Piece;

public class VisitorM1 implements Visitor {
	
	protected Chessboard m_chessboard;
	protected ArrayList<Piece> peiceList;
	
	public VisitorM1(Chessboard chessboard) {
		this.m_chessboard = chessboard;
	}
	
	@Override
	public void visit(Player player) {
		this.peiceList =  this.m_chessboard.getAllPieces(player.getColor());
	}

	@Override
	public void print() {
		System.out.print(this.peiceList.get(0).getPlayer().getName());
		System.out.println(" alive pieces :");
		
		for(int i=0; i<this.peiceList.size(); i++)
		{
			System.out.print(this.peiceList.get(i).getName());
			System.out.print(" | ");
		}
		
		System.out.println("\n");
	}

}
