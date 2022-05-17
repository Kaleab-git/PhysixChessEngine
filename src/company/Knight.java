package company;

import java.util.ArrayList;
import java.util.Arrays;

public class Knight {
    public static long[] attacksTo = new long[64];

    public static void loadTable(Board board) {
        for (int i=0; i<64; i++) {
            long knightMovesBoard;
            if (i > 18) {
                knightMovesBoard = Board.KNIGHT_SPAN<<(i-18);
            }
            else {
                knightMovesBoard = Board.KNIGHT_SPAN>>(18-i);
            }
            if (i%8 < 4) {
                knightMovesBoard &= ~Board.FILE_GH;
            }
            else {
                knightMovesBoard &= ~Board.FILE_AB;
            }
            attacksTo[i] = knightMovesBoard;
        }
    }
    public static ArrayList getMoves(Board board, boolean inBitboard, boolean isWhite) {
        long bitboard = 0L;
        ArrayList<Move> moves = new ArrayList<>();

        long knightPositions;
        if (isWhite) {
            knightPositions = board.WN;
        }
        else {
            knightPositions = board.BN;
        }

        for (int i = Long.numberOfTrailingZeros(knightPositions);i < 64-Long.numberOfLeadingZeros(knightPositions); i++) {
            if (((knightPositions>>i)&1) == 1){
                long knightMovesBoard;
//                avoid capturing your own piece
                long notMyPieces;
                if (isWhite) {
                    notMyPieces = ~board.WHITE_PIECES;
                }
                else {
                    notMyPieces = ~board.BLACK_PIECES;
                }
//                We're not shifting in a single direction.
                if (i > 18) {
                    knightMovesBoard = Board.KNIGHT_SPAN<<(i-18);
                }
                else {
                    knightMovesBoard = Board.KNIGHT_SPAN>>(18-i);
                }
//                Because we're not always shifting to in the same direction, the unreachable files can't be predicted
                if (i%8 < 4) {
                    knightMovesBoard &= ~Board.FILE_GH&notMyPieces;
                }
                else {
                    knightMovesBoard &= ~Board.FILE_AB&notMyPieces;
                }

                bitboard |= knightMovesBoard;
                for (int j = Long.numberOfTrailingZeros(knightMovesBoard);j < 64-Long.numberOfLeadingZeros(knightMovesBoard); j++) {
                    if (((knightMovesBoard>>j)&1) == 1) {
                        moves.add(new Move(i, j));
                    }
                }
            }
        }
        if (inBitboard) {
            return new ArrayList(Arrays.asList(bitboard));
        }
        return moves;
    }
}
