package exo5;

import jchess.core.Game;
import jchess.core.Square;

public class Score extends Exo5 {

	public Score(Game game) {
		super(game);
	}
	
	public int getScoreNoir() {
		int[] pieceBlancheMange = new int[6];
		pieceBlancheMange[0] = 8 - Exo5.listePiecesBlanc[0];
		pieceBlancheMange[1] = 2 - Exo5.listePiecesBlanc[1];
		pieceBlancheMange[2] = 2 - Exo5.listePiecesBlanc[2];
		pieceBlancheMange[3] = 2 - Exo5.listePiecesBlanc[3];
		pieceBlancheMange[4] = 1 - Exo5.listePiecesBlanc[4];
		pieceBlancheMange[5] = 1 - Exo5.listePiecesBlanc[5];
		
		return pieceBlancheMange[0] + (pieceBlancheMange[1]+pieceBlancheMange[2])*3 + pieceBlancheMange[3]*5 + pieceBlancheMange[4]*10 + pieceBlancheMange[5]*1000;
	}
	
	public int getScoreBlanc(){
		int[] pieceNoirMange = new int[6];
		pieceNoirMange[0] = 8 - Exo5.listePiecesNoir[0];
		pieceNoirMange[1] = 2 - Exo5.listePiecesNoir[1];
		pieceNoirMange[2] = 2 - Exo5.listePiecesNoir[2];
		pieceNoirMange[3] = 2 - Exo5.listePiecesNoir[3];
		pieceNoirMange[4] = 1 - Exo5.listePiecesNoir[4];
		pieceNoirMange[5] = 1 - Exo5.listePiecesNoir[5];
		
		return pieceNoirMange[0] + (pieceNoirMange[1]+pieceNoirMange[2])*3 + pieceNoirMange[3]*5 + pieceNoirMange[4]*10 + pieceNoirMange[5]*1000;
	}

}
