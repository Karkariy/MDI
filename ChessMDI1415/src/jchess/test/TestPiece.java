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
import jchess.core.pieces.implementation.Knight;
import jchess.core.pieces.implementation.Pawn;
import jchess.core.pieces.implementation.Queen;
import jchess.core.pieces.implementation.Rook;
import jchess.utils.Settings;

import org.jdesktop.application.SingleFrameApplication;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by macher1 on 12/04/2015.
 */
public class TestPiece {

    private  Settings settings;

    private  Chessboard board;

    @Before
    public void setUp() {
        //SingleFrameApplication.launch(JChessApp.class, new String[] {});

        settings = new Settings();
        board = new Game().getChessboard(); // new Chessboard(settings, new Moves(new Game()));


        // Game g = new Game();
        // #1 bad API design
        // g.newGame(); // fails because coupled to GUI concerns and tabs stuff
        // anyway
        board.setPieces("", settings.getPlayerWhite(), settings.getPlayerBlack());


        // #2 bad API design
        //  Moves moves = new Moves(g);
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


        Square sq = board.getSquare(5, 1); // 1st rown (black relative)
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
        board.move(4, 6, 4, 4);

        // #4 bad API design
        //assertEquals(1, board.getMoves().size());

        assertNull(board.getSquare(4, 6).getPiece()); // now the pawn is not present in e2
        Piece p4 = board.getSquare(4, 4).getPiece(); // and there is a pawn in e4
        assertTrue(p4 instanceof Pawn);
        assertEquals(Colors.WHITE, p4.getPlayer().getColor());
    }
    
    @Test
    //test de la notation algébrique
    public void testBasicMovement2() throws Exception {


        Square sq = board.getSquare(5, 1); // 1st rown (black relative)
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
        //board.move("e2", "e4");
        // #4 bad API design
        //assertEquals(1, board.getMoves().size());

        assertNull(board.getSquare(4, 6).getPiece()); // now the pawn is not present in e2
        Piece p4 = board.getSquare(4, 4).getPiece(); // and there is a pawn in e4
        assertTrue(p4 instanceof Pawn);
        assertEquals(Colors.WHITE, p4.getPlayer().getColor());
    }
    
    // test de l'enchainement de méthodes
    public void testBasicMovement3() throws Exception {


        Square sq = board.getSquare(5, 1); // 1st rown (black relative)
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
        //board.xFrom(4).yFrom(6).xTo(4).yTo(4);
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
        board.move(4, 6, 4, 4);

        // e7 (4, 1) e5 (4, 3)
        board.move(4, 1, 4, 3);


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
        board.move(3, 6, 3, 4);

        // e7 (4, 1) e5 (4, 3)
        board.move(4, 1, 4, 3);

        // bishop in c1
        Piece b1 = board.getSquare(2, 7).getPiece();
        assertTrue(b1 instanceof Bishop);
        assertEquals(Colors.WHITE, b1.getPlayer().getColor());

        assertEquals(5, b1.getAllMoves().size());


    }
    
    @Test
    public void testKing1() throws Exception {

        // e2 (4, 6) e4 (5, 4)
        board.move(4, 6, 4, 4);

        // e7 (4, 1) e5 (4, 3)
        board.move(4, 1, 4, 3);


        assertNull(board.getSquare(4, 1).getPiece()); // now the pawn is not present in e7
        Piece p1 = board.getSquare(4, 3).getPiece(); // and there is a pawn in e5
        assertTrue(p1 instanceof Pawn);
        assertEquals(Colors.BLACK, p1.getPlayer().getColor());

        // king in e1
        Piece k1 = board.getSquare(4, 7).getPiece();
        assertTrue(k1 instanceof King);
        assertEquals(Colors.WHITE, k1.getPlayer().getColor());

        assertEquals(1, k1.getAllMoves().size());



    }

    @Test
    public void testKing2() throws Exception {

        // d2 (3, 6) d4 (3, 4)
        board.move(3, 6, 3, 4);

        // e7 (4, 1) e5 (4, 3)
        board.move(4, 1, 4, 3);
        
        //board.move("e2", "e4");
        //board.move("d7", "d6");

        // king en e1
        Piece k1 = board.getSquare(4, 7).getPiece();
        assertTrue(k1 instanceof King);
        assertEquals(Colors.WHITE, k1.getPlayer().getColor());

        assertEquals(2, k1.getAllMoves().size());

        //board.move("e1", "e2");
        //board.move("f7", "f6");
        
        Piece k2 = board.getSquare(4, 6).getPiece();
        assertTrue(k2 instanceof King);
        assertEquals(Colors.WHITE, k2.getPlayer().getColor());

        assertEquals(5, k2.getAllMoves().size());

    }
    
    @Test
    public void testKnight() throws Exception {

        
        Piece k1 = board.getSquare(1, 7).getPiece();
        assertTrue(k1 instanceof Knight);
        assertEquals(Colors.WHITE, k1.getPlayer().getColor());

        assertEquals(2, k1.getAllMoves().size());
        
        //board.move("b1", "a3");

        board.move(4, 1, 4, 3);
        
        //board.move("a3", "c4");
        //board.move("d7", "d6");

        // chevalier en c1
        Piece k2 = board.getSquare(2, 4).getPiece();
        assertTrue(k2 instanceof Knight);
        assertEquals(Colors.WHITE, k2.getPlayer().getColor());

        assertEquals(6, k2.getAllMoves().size());

    }
    
    public void testQueen() throws Exception {
    	/*board.move("d2", "d4");
    	board.move("d7", "d6");
    	
        board.move("d4", "d5");
        board.move("a7", "a6");
        
        board.move("e4", "e5");
        board.move("b7", "b6");*/
        
        Piece q1 = board.getSquare(3, 7).getPiece();
        assertTrue(q1 instanceof Queen);
        assertEquals(Colors.WHITE, q1.getPlayer().getColor());

        assertEquals(7, q1.getAllMoves().size());
        
        
        //board.move("d1", "d3");
        //board.move("h7", "h6");

        Piece q2 = board.getSquare(3, 5).getPiece();
        assertTrue(q2 instanceof Queen);
        assertEquals(Colors.WHITE, q2.getPlayer().getColor());

        assertEquals(13, q2.getAllMoves().size());

    }
    public void testRook() throws Exception {
    	//board.move("b2", "b4");
    	//board.move("a7", "a6");
    	
        Piece r1 = board.getSquare(0, 7).getPiece();
        assertTrue(r1 instanceof Rook);
        assertEquals(Colors.WHITE, r1.getPlayer().getColor());

        assertEquals(2, r1.getAllMoves().size());
        
        //board.move("b7", "b6");

        Piece r2 = board.getSquare(0, 5).getPiece();
        assertTrue(r2 instanceof Rook);
        assertEquals(Colors.WHITE, r2.getPlayer().getColor());

        assertEquals(9, r2.getAllMoves().size());

    }
    
    @Test
    public void testChat() throws Exception {

        
        Piece c1 = board.getSquare(1, 7).getPiece();
        //assertTrue(c1 instanceof Chat);
        assertEquals(Colors.WHITE, c1.getPlayer().getColor());

        assertEquals(2, c1.getAllMoves().size());
        
        //board.move("b1", "a3");

        board.move(4, 1, 4, 3);
        
        //board.move("a3", "c4");
        //board.move("d7", "d6");

        Piece c2 = board.getSquare(2, 4).getPiece();
        //assertTrue(c2 instanceof Chat);
        assertEquals(Colors.WHITE, c2.getPlayer().getColor());

        assertEquals(6, c2.getAllMoves().size());

    }
}
