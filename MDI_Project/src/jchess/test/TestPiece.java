package jchess.test;

import jchess.JChessApp;
import jchess.core.Chessboard;
import jchess.core.Colors;
import jchess.core.Game;
import jchess.core.Square;
import jchess.core.moves.Moves;
import jchess.core.pieces.Piece;
import jchess.core.pieces.implementation.Bishop;
import jchess.core.pieces.implementation.King;
import jchess.core.pieces.implementation.Pawn;
import jchess.utils.Settings;
import org.jdesktop.application.SingleFrameApplication;
import org.junit.Before;
import org.junit.Test;

import Builder.DisplacementBuilder;
import Builder.DisplaceSquareBuilder;

import java.awt.*;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by macher1 on 12/04/2015.
 */
public class TestPiece {

    private Settings settings;

    private Chessboard board;
    private DisplacementBuilder displacement;
    private DisplaceSquareBuilder displaceSquare;
    
    @Before
    public void setUp() {
        //SingleFrameApplication.launch(JChessApp.class, new String[] {});

        settings = new Settings();
        board = new Game().getChessboard(); // new Chessboard(settings, new Moves(new Game()));
        displacement = new DisplacementBuilder(board);
        displaceSquare = new DisplaceSquareBuilder(board);

        // Game g = new Game();
        // #1 bad API design
        // g.newGame(); // fails because coupled to GUI concerns and tabs stuff
        // anyway
        board.setPieces("", settings.getPlayerWhite(), settings.getPlayerBlack());


        // #2 bad API design
        // Moves moves = new Moves(g);
        // Chessboard board = new Chessboard(settings, moves);
        // g.getChessboard() != board :(
        // board.getMoves() != moves :(
    }

    @Test
    public void testInitBoard() throws Exception {
        assertEquals(16, board.getAllPieces(Colors.WHITE).size());
        assertEquals(16, board.getAllPieces(Colors.BLACK).size());
        // #3 bad API design
        // assertNotNull(board.getMoves());
    }

    @Test
    public void testBasicMovement() throws Exception {
        Square sq = board.getSquare(5, 1); // 1st row (black relative)
        Piece p = sq.getPiece();
        assertTrue(p instanceof Pawn);
        assertEquals(Colors.BLACK, p.getPlayer().getColor());

        Piece p2 = board.getSquare(5, 6).getPiece(); // 6th row (black relative)
        assertNotNull(p2);
        assertTrue(p2 instanceof Pawn);
        assertEquals(Colors.WHITE, p2.getPlayer().getColor());

        assertEquals(2, p2.getAllMoves().size()); // e2e3 or e2e4

        Piece p3 = board.getSquare(4, 7).getPiece(); // 7th row (black relative)
        assertNotNull(p3);
        assertTrue(p3 instanceof King);
        assertEquals(Colors.WHITE, p3.getPlayer().getColor());

        assertEquals(0, p3.getAllMoves().size()); // no legal move


        assertNull(board.getSquare(4, 4).getPiece()); // nothing there
        
        // e2 (4, 6) e4 (4, 4)
        // board.move(4, 6, 4, 4);
        // displacement.xFrom(4).yFrom(6).xTo(4).yTo(4);
        displaceSquare.from("e2").to("e4");
        
        // #4 bad API design
        //assertEquals(1, board.getMoves().size());

        assertNull(board.getSquare(4, 6).getPiece()); // now the pawn is not present in e2
        Piece p4 = board.getSquare(4, 4).getPiece(); // and there is a pawn in e4
        assertTrue(p4 instanceof Pawn);
        assertEquals(Colors.WHITE, p4.getPlayer().getColor());
    }

    @Test
    public void testBishop1() throws Exception {
        // e2 (4, 6) e4 (5, 4)
        // board.move(4, 6, 4, 4);
        
    	// displacement.xFrom(4).yFrom(6).xTo(4).yTo(4);
        displaceSquare.from("e2").to("e4");
        
        // e7 (4, 1) e5 (4, 3)
        // board.move(4, 1, 4, 3);
        
        // displacement.xFrom(4).yFrom(1).xTo(4).yTo(3);
        displaceSquare.from("e7").to("e5");
        

        assertNull(board.getSquare(4, 1).getPiece()); // now the pawn is not present in e7
        Piece p1 = board.getSquare(4, 3).getPiece(); // and there is a pawn in e5
        assertTrue(p1 instanceof Pawn);
        assertEquals(Colors.BLACK, p1.getPlayer().getColor());

        // bishop in f1
        Piece b1 = board.getSquare(5, 7).getPiece();
        assertTrue(b1 instanceof Bishop);
        assertEquals(Colors.WHITE, b1.getPlayer().getColor());

        assertEquals(5, b1.getAllMoves().size());
    }

    @Test
    public void testBishop2() throws Exception {
        // d2 (3, 6) d4 (3, 4)
        // board.move(3, 6, 3, 4);
    	
    	// displacement.xFrom(3).yFrom(6).xTo(3).yTo(4);
    	displaceSquare.from("d2").to("d4");
        
        // e7 (4, 1) e5 (4, 3)
        // board.move(4, 1, 4, 3);
    	
    	// displacement.xFrom(4).yFrom(1).xTo(4).yTo(3);
    	displaceSquare.from("e7").to("e5");
        
        // bishop in c1
        Piece b1 = board.getSquare(2, 7).getPiece();
        assertTrue(b1 instanceof Bishop);
        assertEquals(Colors.WHITE, b1.getPlayer().getColor());

        assertEquals(5, b1.getAllMoves().size());
    }
    
    @Test
    public void testKing() throws Exception {
    	// d2 (3, 6) d4 (3, 4)
    	// board.move(3, 6, 3, 4);

    	// displacement.xFrom(3).yFrom(6).xTo(3).yTo(4);
        displaceSquare.from("d2").to("d4");
        
        // c7 (2, 1) c5 (4, 3)
        // board.move(2, 1, 2, 3);

    	// displacement.xFrom(2).yFrom(1).xTo(2).yTo(3);
        displaceSquare.from("c7").to("c5");
        
        // e1 (4, 8) d2 (3, 7)
        // board.move(4, 7, 3, 6);

    	// displacement.xFrom(4).yFrom(7).xTo(3).yTo(6);
        displaceSquare.from("e1").to("d2");
        
        // d8 (3, 0) a5 (0, 3)
        // board.move(3, 0, 0, 3);

    	// displacement.xFrom(3).yFrom(0).xTo(0).yTo(3);
        displaceSquare.from("d8").to("a5");
        
        // king in d2
        Piece k1 = board.getSquare(3, 6).getPiece();
        assertTrue(k1 instanceof King);
        assertEquals(Colors.WHITE, k1.getPlayer().getColor());

        assertEquals(2, k1.getAllMoves().size());
    }
}
