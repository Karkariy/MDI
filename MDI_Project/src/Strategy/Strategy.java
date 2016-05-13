package Strategy;

import jchess.core.Chessboard;
import jchess.core.Player;

abstract class Strategy {

	//private Chessboard cheesboard;
	
	
	abstract void cheessboardExecutor(Chessboard chessboard,Player plWhite, Player plBlack);

		
	Strategy(/*Chessboard chessboard*/){
	//	this.cheesboard = chessboard;
	}
	
	
	//public Chessboard getCheesboard() {
		//return cheesboard;
	//}


	public void setCheesboard(Chessboard cheesboard) {
		//this.cheesboard = cheesboard;
	}

	
}
