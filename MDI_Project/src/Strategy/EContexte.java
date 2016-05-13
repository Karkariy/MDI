package Strategy;

import jchess.core.Chessboard;
import jchess.core.Player;

public class EContexte {

	public enum ChessBoardType {
		Default,
		Random,
		Custom,
		OnlyOnePiece
	}

	private Strategy chessBoardStrategy ;
	
	private Player plWhite , plBlack;
	
    private int chessBordSizeH ;

	private int chessBordSizeW;
	
	
	public EContexte (Strategy chessBoardStrategy, Player plWhite, Player plBlack) {
		this.chessBoardStrategy = chessBoardStrategy;
		this.plWhite=plWhite;
		this.plBlack=plBlack;
	}
	
	public void updateStrategy(Strategy chessboardStrategy)
	{
		chessBoardStrategy = chessboardStrategy;
	}
	 public void executeStrategy(Chessboard chessboard){
		chessBoardStrategy.cheessboardExecutor(chessboard,plWhite,plBlack);
	   }

	public int getChessBordSizeH() {
		return chessBordSizeH;
	}

	public void setChessBordSizeH(int chessBordSizeH) {
		this.chessBordSizeH = chessBordSizeH;
	}

	public int getChessBordSizeW() {
		return chessBordSizeW;
	}

	public void setChessBordSizeW(int chessBordSizeW) {
		this.chessBordSizeW = chessBordSizeW;
	}
	
}
