/*
#    This program is free software: you can redistribute it and/or modify
#    it under the terms of the GNU General Public License as published by
#    the Free Software Foundation, either version 3 of the License, or
#    (at your option) any later version.
#
#    This program is distributed in the hope that it will be useful,
#    but WITHOUT ANY WARRANTY; without even the implied warranty of
#    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#    GNU General Public License for more details.
#
#    You should have received a copy of the GNU General Public License
#    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*

 */
package jchess.core;

import Factory.*;
import Strategy.ChessboardDefaultStrategy;
import Strategy.EContexte;
//import Builder.*;
import jchess.core.pieces.Piece;
import jchess.core.pieces.PieceType;
import jchess.core.pieces.implementation.King;
import jchess.core.pieces.implementation.Pawn;
import jchess.core.pieces.implementation.Rook;

import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Set;
import jchess.JChessApp;
import jchess.core.moves.Castling;
import jchess.core.moves.Move;
import jchess.core.moves.Moves;
import jchess.display.views.chessboard.implementation.graphic2D.Chessboard2D;
import jchess.display.views.chessboard.ChessboardView;
import jchess.utils.Settings;
import org.apache.log4j.*;

/** 
 * @author: Mateusz Sławomir Lach ( matlak, msl )
 * @author: Damian Marciniak
 * Class to represent chessboard. Chessboard is made from squares.
 * It is setting the squers of chessboard and sets the pieces(pawns)
 * witch the owner is current player on it.
 */
public class Chessboard //implements Builder
{
    private static final Logger LOG = Logger.getLogger(Chessboard.class);
    
    protected static final int TOP = 0;
    
    protected static final int BOTTOM = 7;
    
    /*
     * squares of chessboard
     */
    private Square squares[][];

    private Set<Square> moves;
    
    private Settings settings;
    
    private ConcreteFactory pieceFactory;
    
    protected King kingWhite;
    
    protected King kingBlack;
    

    
    //For En passant:
    //|-> Pawn whose in last turn moved two square
    protected Pawn twoSquareMovedPawn = null;
    
    private Moves Moves;
    
    protected Square activeSquare;
    
    protected int activeSquareX;
    
    protected int activeSquareY;   
    
    protected EContexte contexte;
    
    /**
     * chessboard view data object
     */  
    private ChessboardView chessboardView;

    /** 
     * Chessboard class constructor
     * @param settings reference to Settings class object for this chessboard
     * @param moves_history reference to Moves class object for this chessboard 
     */
    public Chessboard(Settings settings,EContexte contexte, Moves Moves)
    {
    	
        this.settings = settings;
       

        this.activeSquareX = 0;
        this.activeSquareY = 0;
//--endOf--create object for each square
        this.Moves = Moves;
        
        this.setPieceFactory(new ConcreteFactory(this));
        
        this.contexte = contexte ;
    	this.contexte.executeStrategy(this);
    	this.chessboardView = new Chessboard2D(this);
       
    }/*--endOf-Chessboard--*/

    /**
     * @return the top
     */
    public static int getTop() 
    {
        return TOP;
    }

    /**
     * @return the bottom
     */
    public static int getBottom() 
    {
        return BOTTOM;
    }
    /** Method setPieces on begin of new game or loaded game
     * @param places string with pieces to set on chessboard
     * @param plWhite reference to white player
     * @param plBlack reference to black player
     */
    public void setPieces(String places, Player plWhite, Player plBlack)
    {

        if (places.equals("")) //if newGame
        {
        	
        } 
        else //if loadedGame
        {
            return;
        }
    }/*--endOf-setPieces--*/



    /**  method set Pawns in row
     *  @param i row where to set pawns
     *  @param player player which is owner of pawns
     * */

    /** Method selecting piece in chessboard
     * @param  sq square to select (when clicked))
     */
    public void select(Square sq)
    {
        this.setActiveSquare(sq);
        this.setActiveSquareX(sq.getPozX() + 1);
        this.setActiveSquareY(sq.getPozY() + 1);

        LOG.debug("active_x: " + this.getActiveSquareX() + " active_y: " + this.getActiveSquareY());//4tests
        this.getChessboardView().repaint();
    }/*--endOf-select--*/

    public void unselect()
    {
        this.setActiveSquareX(0);
        this.setActiveSquareY(0);
        this.setActiveSquare(null);

        this.getChessboardView().unselect();
    }/*--endOf-unselect--*/
        
    public void resetActiveSquare() 
    {
        this.setActiveSquare(null);
    }
 
    public void move(Square begin, Square end)
    {
        move(begin, end, true);
    }

    /** Method to move piece over chessboard
     * @param xFrom from which x move piece
     * @param yFrom from which y move piece
     * @param xTo to which x move piece
     * @param yTo to which y move piece
     */
    public void move(int xFrom, int yFrom, int xTo, int yTo)
    {
        Square fromSQ = null;
        Square toSQ = null;
        try
        {
            fromSQ = this.getSquares()[xFrom][yFrom];
            toSQ = this.getSquares()[xTo][yTo];
        }
        catch (java.lang.IndexOutOfBoundsException exc)
        {
            LOG.error("error moving piece: " + exc.getMessage());
            return;
        }
        this.move(fromSQ, toSQ, true);
    }

    public void move(Square begin, Square end, boolean refresh)
    {
        this.move(begin, end, refresh, true);
    }

    /** Method move piece from square to square
     * @param begin square from which move piece
     * @param end square where we want to move piece
     * @param refresh chessboard, default: true
     * */
    public void move(Square begin, Square end, boolean refresh, boolean clearForwardHistory)
    {
        Castling wasCastling = Castling.NONE;
        Piece promotedPiece = null;
        boolean wasEnPassant = false;
        if (end.piece != null)
        {
            end.getPiece().setSquare(null);
        }

        Square tempBegin = new Square(begin);//4 moves history
        Square tempEnd = new Square(end);  //4 moves history

        begin.getPiece().setSquare(end);//set square of piece to ending
        end.piece = begin.piece;//for ending square set piece from beginin square
        begin.piece = null;//make null piece for begining square

        if (end.getPiece().getName().equals("King"))
        {
            if (!((King) end.piece).getWasMotioned())
            {
                ((King) end.piece).setWasMotioned(true);
            }

            //Castling
            if (begin.getPozX() + 2 == end.getPozX())
            {
                move(getSquare(7, begin.getPozY()), getSquare(end.getPozX() - 1, begin.getPozY()), false, false);
                wasCastling = Castling.SHORT_CASTLING;
            }
            else if (begin.getPozX() - 2 == end.getPozX())
            {
                move(getSquare(0, begin.getPozY()), getSquare(end.getPozX() + 1, begin.getPozY()), false, false);
                wasCastling = Castling.LONG_CASTLING;
            }
            //endOf Castling
        }
        else if (end.getPiece().getName().equals("Rook"))
        {
            if (!((Rook) end.piece).getWasMotioned())
            {
                ((Rook) end.piece).setWasMotioned(true);
            }
        }
        else if (end.getPiece().getName().equals("Pawn"))
        {
            if (getTwoSquareMovedPawn() != null && getSquares()[end.getPozX()][begin.getPozY()] == getTwoSquareMovedPawn().getSquare()) //en passant
            {
                tempEnd.piece = getSquares()[end.getPozX()][begin.getPozY()].piece; //ugly hack - put taken pawn in en passant plasty do end square

                getSquares()[end.pozX][begin.pozY].piece = null;
                wasEnPassant = true;
            }

            if (begin.getPozY() - end.getPozY() == 2 || end.getPozY() - begin.getPozY() == 2) //moved two square
            {
                twoSquareMovedPawn = (Pawn) end.piece;
            }
            else
            {
                twoSquareMovedPawn = null; //erase last saved move (for En passant)
            }

            if (end.getPiece().getSquare().getPozY() == 0 || end.getPiece().getSquare().getPozY() == 7) //promote Pawn
            {
                if (clearForwardHistory)
                {
                    String color = String.valueOf(end.getPiece().getPlayer().getColor().getSymbolAsString().toUpperCase());
                    String newPiece = JChessApp.getJavaChessView().showPawnPromotionBox(color); //return name of new piece
                    
                    Piece piece;
                    switch (newPiece)
                    {
                        case "Queen":
                            piece = getPieceFactory().createPiece(PieceType.Queen, end.getPiece().getPlayer());
                            break;
                        case "Rook":
                            piece = getPieceFactory().createPiece(PieceType.Rook, end.getPiece().getPlayer());
                            break;
                        case "Bishop":
                            piece = getPieceFactory().createPiece(PieceType.Bishop, end.getPiece().getPlayer());
                            break;
                        default:
                            piece = getPieceFactory().createPiece(PieceType.Knight, end.getPiece().getPlayer());
                            break;
                    }
                    piece.setChessboard(end.getPiece().getChessboard());
                    piece.setPlayer(end.getPiece().getPlayer());
                    piece.setSquare(end.getPiece().getSquare());
                    end.piece = piece;                    
                    promotedPiece = end.piece;
                }
            }
        }
        else if (!end.getPiece().getName().equals("Pawn"))
        {
            twoSquareMovedPawn = null; //erase last saved move (for En passant)
        }
        if (refresh)
        {
            this.unselect();//unselect square
            repaint();
        }
        if (clearForwardHistory)
        {
            this.Moves.clearMoveForwardStack();
            this.Moves.addMove(tempBegin, tempEnd, true, wasCastling, wasEnPassant, promotedPiece);
        }
        else
        {
            this.Moves.addMove(tempBegin, tempEnd, false, wasCastling, wasEnPassant, promotedPiece);
        }
    }/*endOf-move()-*/


    public boolean redo()
    {
        return redo(true);
    }

    public boolean redo(boolean refresh)
    {
        if ( this.getSettings().getGameType() == Settings.gameTypes.local ) //redo only for local game
        {
            Move first = this.Moves.redo();

            Square from = null;
            Square to = null;

            if (first != null)
            {
                from = first.getFrom();
                to = first.getTo();

                this.move(this.getSquares()[from.getPozX()][from.getPozY()], this.getSquares()[to.getPozX()][to.getPozY()], true, false);
                if (first.getPromotedPiece() != null)
                {
                    Pawn pawn = (Pawn) this.getSquares()[to.getPozX()][to.getPozY()].piece;
                    pawn.setSquare(null);

                    this.getSquares()[to.pozX][to.pozY].piece = first.getPromotedPiece();
                    Piece promoted = this.getSquares()[to.getPozX()][to.getPozY()].piece;
                    promoted.setSquare(this.getSquares()[to.getPozX()][to.getPozY()]);
                }
                return true;
            }
            
        }
        return false;
    }

    public boolean undo()
    {
        return undo(true);
    }

    public synchronized boolean undo(boolean refresh) //undo last move
    {
        Move last = this.Moves.undo();

        if (last != null && last.getFrom() != null)
        {
            Square begin = last.getFrom();
            Square end = last.getTo();
            try
            {
                Piece moved = last.getMovedPiece();
                this.getSquares()[begin.pozX][begin.pozY].piece = moved;

                moved.setSquare(this.getSquares()[begin.getPozX()][begin.getPozY()]);

                Piece taken = last.getTakenPiece();
                if (last.getCastlingMove() != Castling.NONE)
                {
                    Piece rook = null;
                    if (last.getCastlingMove() == Castling.SHORT_CASTLING)
                    {
                        rook = this.getSquares()[end.getPozX() - 1][end.getPozY()].piece;
                        this.getSquares()[7][begin.pozY].piece = rook;
                        rook.setSquare(this.getSquares()[7][begin.getPozY()]);
                        this.getSquares()[end.pozX - 1][end.pozY].piece = null;
                    }
                    else
                    {
                        rook = this.getSquares()[end.getPozX() + 1][end.getPozY()].piece;
                        this.getSquares()[0][begin.pozY].piece = rook;
                        rook.setSquare(this.getSquares()[0][begin.getPozY()]);
                        this.getSquares()[end.pozX + 1][end.pozY].piece = null;
                    }
                    ((King) moved).setWasMotioned(false);
                    ((Rook) rook).setWasMotioned(false);
                }
                else if (moved.getName().equals("Rook"))
                {
                    ((Rook) moved).setWasMotioned(false);
                }
                else if (moved.getName().equals("Pawn") && last.wasEnPassant())
                {
                    Pawn pawn = (Pawn) last.getTakenPiece();
                    this.getSquares()[end.pozX][begin.pozY].piece = pawn;
                    pawn.setSquare(this.getSquares()[end.getPozX()][begin.getPozY()]);

                }
                else if (moved.getName().equals("Pawn") && last.getPromotedPiece() != null)
                {
                    Piece promoted = this.getSquares()[end.getPozX()][end.getPozY()].piece;
                    promoted.setSquare(null);
                    this.getSquares()[end.pozX][end.pozY].piece = null;
                }

                //check one more move back for en passant
                Move oneMoveEarlier = this.Moves.getLastMoveFromHistory();
                if (oneMoveEarlier != null && oneMoveEarlier.wasPawnTwoFieldsMove())
                {
                    Piece canBeTakenEnPassant = this.getSquare(oneMoveEarlier.getTo().getPozX(), oneMoveEarlier.getTo().getPozY()).getPiece();
                    if (canBeTakenEnPassant.getName().equals("Pawn"))
                    {
                        this.twoSquareMovedPawn = (Pawn) canBeTakenEnPassant;
                    }
                }

                if (taken != null && !last.wasEnPassant())
                {
                    this.getSquares()[end.pozX][end.pozY].piece = taken;
                    taken.setSquare(this.getSquares()[end.getPozX()][end.getPozY()]);
                }
                else
                {
                    this.getSquares()[end.pozX][end.pozY].piece = null;
                }

                if (refresh)
                {
                    this.unselect();//unselect square
                    repaint();
                }

            }
            catch (ArrayIndexOutOfBoundsException | NullPointerException exc)
            {
                LOG.error("error: " + exc.getClass()+ " exc object: " + exc);
                return false;
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    public void componentMoved(ComponentEvent e)
    {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void componentShown(ComponentEvent e)
    {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void componentHidden(ComponentEvent e)
    {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @return the squares
     */
    public Square[][] getSquares() 
    {
        return squares;
    }
    
    public Square getSquare(int x, int y) 
    {
        try 
        {
            return getSquares()[x][y];
        } 
        catch(ArrayIndexOutOfBoundsException exc) 
        {
            return null;
        }
    }

    /**
     * @return the activeSquare
     */
    public Square getActiveSquare() 
    {
        return activeSquare;
    }

    public ArrayList<Piece> getAllPieces(Colors color)
    {
        ArrayList<Piece> result = new ArrayList<>();
        for(int i=0; i<getSquares().length; i++)
        {
            for(int j=0; j<getSquares()[i].length; j++)
            {
                Square sq = getSquares()[i][j];
                if(null != sq.getPiece() && ( sq.getPiece().getPlayer().color == color || color == null) )
                {
                    result.add(sq.getPiece());
                }
            }
        }       
        return result;
    }
    
    public static boolean wasEnPassant(Square sq)
    {
        return sq.getPiece() != null
                    && sq.getPiece().getChessboard().getTwoSquareMovedPawn() != null
                    && sq == sq.getPiece().getChessboard().getTwoSquareMovedPawn().getSquare();
    }    

    /**
     * @return the kingWhite
     */
    public King getKingWhite()
    {
        return kingWhite;
    }

    /**
     * @return the kingBlack
     */
    public King getKingBlack()
    {
        return kingBlack;
    }

    /**
     * @return the twoSquareMovedPawn
     */
    public Pawn getTwoSquareMovedPawn()
    {
        return twoSquareMovedPawn;
    }

    /**
     * @return the chessboardView
     */
    public ChessboardView getChessboardView()
    {
        return chessboardView;
    }

    /**
     * @param chessboardView the chessboardView to set
     */
    public void setChessboardView(ChessboardView chessboardView)
    {
        this.chessboardView = chessboardView;
    }
    
    public void repaint()
    {
        getChessboardView().repaint();
    }

    /**
     * @return the settings
     */
    public Settings getSettings()
    {
        return settings;
    }

    /**
     * @param settings the settings to set
     */
    public void setSettings(Settings settings)
    {
        this.settings = settings;
    }

    /**
     * @return the moves
     */
    public Set<Square> getMoves()
    {
        return moves;
    }

    /**
     * @param moves the moves to set
     */
    public void setMoves(Set<Square> moves)
    {
        this.moves = moves;
    }

    /**
     * @param activeSquare the activeSquare to set
     */
    public void setActiveSquare(Square activeSquare)
    {
        this.activeSquare = activeSquare;
    }

    /**
     * @return the activeSquareX
     */
    public int getActiveSquareX()
    {
        return activeSquareX;
    }

    /**
     * @param activeSquareX the activeSquareX to set
     */
    public void setActiveSquareX(int activeSquareX)
    {
        this.activeSquareX = activeSquareX;
    }

    /**
     * @return the activeSquareY
     */
    public int getActiveSquareY()
    {
        return activeSquareY;
    }

    /**
     * @param activeSquareY the activeSquareY to set
     */
    public void setActiveSquareY(int activeSquareY)
    {
        this.activeSquareY = activeSquareY;
    }

	public ConcreteFactory getPieceFactory() {
		return pieceFactory;
	}

	public void setPieceFactory(ConcreteFactory pieceFactory) {
		this.pieceFactory = pieceFactory;
	}

	public int getChessBordSizeH() {
		return contexte.getChessBordSizeH();
	}

	public void setChessBordSizeW(int chessBordSizeW) {
		contexte.setChessBordSizeW(chessBordSizeW);
	}

	public int getChessBordSizeW() {
		return contexte.getChessBordSizeH();
	}
	public void setChessBordSizeH(int chessBordSizeH) {
		contexte.setChessBordSizeH(chessBordSizeH);
	}

	public Chessboard xFrom(int x) {
		
		return this;
	}

	public Chessboard yFrom(int y) {
		
		return this;
	}

	public Chessboard xTo(int x) {
		
		return this;
	}

	public Chessboard yTo(int y) {
		
		return this;
	}

	public void setKingWhite(King createPiece) {
		kingWhite = createPiece;
	}
	public void setKingBlack(King createPiece) {
		kingBlack = createPiece;
	}

	public void setSquares(Square squares[][]) {
		this.squares = squares;
	}

}
