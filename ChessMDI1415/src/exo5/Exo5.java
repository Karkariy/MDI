package exo5;

import jchess.core.Game;
import jchess.core.Square;

public abstract class Exo5 {
	
	protected Game game;
	protected Square [] [] squares;
	protected static int [] listePiecesBlanc; 
	protected static int [] listePiecesNoir; 

	
	Exo5(Game game) {
		this.game = game;
		this.squares = this.game.getChessboard().getSquares();
		this.listePiecesBlanc = new int [6];
		this.listePiecesNoir = new int [6];
		for (int i = 0 ; i < 6 ; i++) {
			Exo5.listePiecesBlanc[i] = 0;
			Exo5.listePiecesNoir[i] = 0;
		}
	}
	
	public void getListePieces() {

	}
}
