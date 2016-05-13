package Decorator;

import java.awt.Rectangle;

import jchess.core.Chessboard;
import jchess.core.Square;
import jchess.core.moves.Castling;
import jchess.core.moves.Move;
import jchess.core.moves.Moves;
import jchess.core.pieces.Piece;

public class MovesWithTimeDecorator extends AbstractMovesDecorator{

	public MovesWithTimeDecorator(Moves moves) {
		super(moves.getGame());
		this.moves = moves;
	}

	public void addMove(Square begin, Square end, boolean registerInHistory, Castling castlingMove, boolean wasEnPassant, Piece promotedPiece) 
	{	
		boolean wasCastling = castlingMove != Castling.NONE;
        String locMove = begin.getPiece().getSymbol();
        
        if(game.getSettings().isUpsideDown() )
        {
            locMove += Character.toString((char) ( ( Chessboard.getBottom() - begin.getPozX()) + 97));//add letter of Square from which move was made
            locMove += Integer.toString( begin.getPozY() + 1 );//add number of Square from which move was made
        }
        else
        {
            locMove += Character.toString((char) (begin.getPozX() + 97));//add letter of Square from which move was made
            locMove += Integer.toString(8 - begin.getPozY());//add number of Square from which move was made
        }
        
        if (end.piece != null)
        {
            locMove += " Kill ";//take down opponent piece
        }
        else
        {
            locMove += "-";//normal move
        }
        
        if ( game.getSettings().isUpsideDown() )
        {
            locMove += Character.toString((char) (( Chessboard.getBottom() - end.getPozX()) +  97));//add letter of Square to which move was made
            locMove += Integer.toString( end.getPozY() + 1 );//add number of Square to which move was made
        }
        else
        {
            locMove += Character.toString((char) (end.getPozX() + 97));//add letter of Square to which move was made
            locMove += Integer.toString(8 - end.getPozY());//add number of Square to which move was made
        }
        
        if (begin.getPiece().getSymbol().equals("") && begin.getPozX() - end.getPozX() != 0 && end.piece == null)
        {
            locMove += "(e.p)";//pawn take down opponent en passant
            wasEnPassant = true;
        }
        if ((!this.enterBlack && this.game.getChessboard().getKingBlack().isChecked())
                || (this.enterBlack && this.game.getChessboard().getKingWhite().isChecked()))
        {//if checked

            if ((!this.enterBlack && this.game.getChessboard().getKingBlack().isCheckmatedOrStalemated() == 1)
                    || (this.enterBlack && this.game.getChessboard().getKingWhite().isCheckmatedOrStalemated() == 1))
            {//check if checkmated
                locMove += "#";//check mate
            }
            else
            {
                locMove += "+";//check
            }
        }
        if (castlingMove != Castling.NONE)
        {
            this.addCastling(castlingMove.getSymbol());
        }
        else
        {
    		int strokeTime;
        	if (this.game.getActivePlayer() == this.game.getGameClock().clock1.getPlayer())
        		strokeTime = this.game.getGameClock().beginStrokeTimeClock1 - this.game.getGameClock().clock1.getLeftTime();
            else
        		strokeTime = this.game.getGameClock().beginStrokeTimeClock2 - this.game.getGameClock().clock2.getLeftTime();     
    		
        	// On aurait du modifier le contenu des deux m�thodes sauf que la construction du coup
        	// (locMove) est fait pendant la m�thode est n'est donc pas disponible en dehors.
        	// On a donc du reprogrammer la m�thode addMove(...) en copiant enti�rement ce qui avait
        	// �t� fait dans la classe Moves. 
        	// Design Pattern pas super adapt� dans ce cas l�.
            this.move.add(locMove + " - " + strokeTime + " secondes");
            this.addMove2Table(locMove + " - " + strokeTime + " secondes");
        }
        this.scrollPane.scrollRectToVisible(new Rectangle(0, this.scrollPane.getHeight() - 2, 1, 1));

        if (registerInHistory)
        {
            Move moveToAdd = new Move(new Square(begin), new Square(end), begin.piece, end.piece, castlingMove, wasEnPassant, promotedPiece);
            this.moveBackStack.add(moveToAdd);
        }
	}
}
