package exo5;

import java.awt.Graphics2D;
import java.awt.Image;

import jchess.core.Game;
import jchess.core.Square;
import jchess.core.pieces.Piece;
import jchess.display.views.chessboard.implementation.graphic2D.Pieces2D;

public class Survivants extends Exo5 {
	
	 public Survivants(Game game) {
		super(game);
	}

	public void countPieces(Square[][] piecess) {		
		for (int i = 0 ; i < 6 ; i++) {
			Exo5.listePiecesBlanc[i] = 0;
			Exo5.listePiecesNoir[i] = 0;
		}
		 		 
	        for (int i = 0; i < this.game.getLargeur() ; i++)
	        {
	            for (int y = 0; y < this.game.getHauteur() ; y++)
	            {
	            	if (piecess[i][y].getPiece() != null) {
	            		if (piecess[i][y].getPiece().getPlayer().getColor().getColorName().equals("white")) {	
			                if (piecess[i][y].getPiece().getName().equals("Pawn")) Exo5.listePiecesBlanc[0]++;    
			                if (piecess[i][y].getPiece().getName().equals("Bishop")) Exo5.listePiecesBlanc[1]++;                 
			                if (piecess[i][y].getPiece().getName().equals("Knight")) Exo5.listePiecesBlanc[2]++;                
			                if (piecess[i][y].getPiece().getName().equals("Rook")) Exo5.listePiecesBlanc[3]++;                 
			                if (piecess[i][y].getPiece().getName().equals("Queen")) Exo5.listePiecesBlanc[4]++;                 
			                if (piecess[i][y].getPiece().getName().equals("King")) Exo5.listePiecesBlanc[5]++;
	            		}
	            		else {	
			                if (piecess[i][y].getPiece().getName().equals("Pawn")) Exo5.listePiecesNoir[0]++;    
			                if (piecess[i][y].getPiece().getName().equals("Bishop")) Exo5.listePiecesNoir[1]++;                 
			                if (piecess[i][y].getPiece().getName().equals("Knight")) Exo5.listePiecesNoir[2]++;                
			                if (piecess[i][y].getPiece().getName().equals("Rook")) Exo5.listePiecesNoir[3]++;                 
			                if (piecess[i][y].getPiece().getName().equals("Queen")) Exo5.listePiecesNoir[4]++;                 
			                if (piecess[i][y].getPiece().getName().equals("King")) Exo5.listePiecesNoir[5]++;
	            		}
	            	}
	            }
	        }
/*
	        System.out.println("Blanc");
	        System.out.println("\t Pion : " + Exo5.listePiecesBlanc[0]);
			System.out.println("\t Fou : " + Exo5.listePiecesBlanc[1]);
			System.out.println("\t Cavalier : " + Exo5.listePiecesBlanc[2]);
			System.out.println("\t Tour : " + Exo5.listePiecesBlanc[3]);
			System.out.println("\t Reine : " + Exo5.listePiecesBlanc[4]);
			System.out.println("\t Roi : " + Exo5.listePiecesBlanc[5]);	        
			
			System.out.println("Noir");
	        System.out.println("\t Pion : " + Exo5.listePiecesNoir[0]);
			System.out.println("\t Fou : " + Exo5.listePiecesNoir[1]);
			System.out.println("\t Cavalier : " + Exo5.listePiecesNoir[2]);
			System.out.println("\t Tour : " + Exo5.listePiecesNoir[3]);
			System.out.println("\t Reine : " + Exo5.listePiecesNoir[4]);
			System.out.println("\t Roi : " + Exo5.listePiecesNoir[5]);	
*/
	   }


}
