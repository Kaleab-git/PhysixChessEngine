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
//        initializing bitboard to enemy kings position so as to consider even enemy kings position as a possible attack square
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
                long enemyKing = isWhite ? board.BK:board.WK;
                long notMyPieces = isWhite ? ~board.WHITE_PIECES:~board.BLACK_PIECES;

//                We're not shifting in a single direction.
                if (i > 18) {
                    knightMovesBoard = Board.KNIGHT_SPAN<<(i-18);
                }
                else {
                    knightMovesBoard = Board.KNIGHT_SPAN>>(18-i);
                }
//                Because we're not always shifting to in the same direction, the unreachable files can't be predicted
//                Also(unrelated) we want to set our attacks bitboard before removing friendly pieces. This way we also consider squares we're defending as sqaures we're attacking.
                if (i%8 < 4) {
                    bitboard |= knightMovesBoard&~Board.FILE_GH;
                    knightMovesBoard &= ~Board.FILE_GH&notMyPieces&~enemyKing;
                }
                else {
                    bitboard |= knightMovesBoard&~Board.FILE_AB;
                    knightMovesBoard &= ~Board.FILE_AB&notMyPieces&~enemyKing;
                }

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
