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
 * Authors:
 * Mateusz SÅ‚awomir Lach ( matlak, msl )
 * Damian Marciniak
 */
package jchess.core.pieces.implementation;

import java.util.Random;

import jchess.core.pieces.Piece;
import jchess.core.Chessboard;
import jchess.core.Player;
import jchess.core.pieces.traits.behaviors.implementation.BishopBehavior;
import jchess.core.pieces.traits.behaviors.implementation.TouretteBehavior;

/**
 * Class to represent a chess pawn bishop
 * Tourette can move across the chessboard
 *
 *	Tourette is very .. verbal.
|_|_|_|X|_|_|_| |7
| |_|_|_|_|_| |_|6
|_|_|_|X|_|_|_|_|5
|_|_|_|_|_|_|_|_|4
|_|X|_|T|_|X|_|X|3
|_|_|_|_|_|_|_|_|2
|_|_|_|X|_|_|_|_|1
| |_|_|_|_|_|_|_|0
0 1 2 3 4 5 6 7
 */
public class Tourette extends Piece
{
    protected static final short value = 3;
	static String insultes[] = {
		"tocard",
		"tata",
		"fdpdp",
		"pauvre gland",
		"rip in peace connard",
		"coquin",
		"brigand",
		"vil félon",
		"batard",
		"socialiste",
		"raclure de bidet",
		"prof de mdi",
		"sac à vin",
		"benne à jouir",
		"shagasse",
		"t'es tellement mauvais que même le jeu t'insulte",
		"get cancer ebola sida diabete noob",
		"you're salty"
	};
    public Tourette(Chessboard chessboard, Player player)
    {
        super(chessboard, player); //call initializer of super type: Piece
        this.symbol = "T";
        this.addBehavior(new TouretteBehavior(this));
    }
    
    public static String getInsulte(){
    	return insultes[(int)(Math.random()*18)];
    }
}
