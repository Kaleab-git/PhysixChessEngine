package company;

import java.util.ArrayList;
import java.util.Arrays;

public class King {
    public static ArrayList getMoves(Board board, boolean inBitboard, boolean isWhite) {
        long bitboard = 0L;
        ArrayList moves = new ArrayList();

        long kingPositions;
        if (isWhite) {
            kingPositions = board.WK;
        }
        else {
            kingPositions = board.BK;
        }
        for (int i = Long.numberOfTrailingZeros(kingPositions);i < 64-Long.numberOfLeadingZeros(kingPositions); i++) {
            if (((kingPositions>>i)&1) == 1){
                long kingMovesBoard;
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
                    kingMovesBoard = Board.KING_SPAN<<(i-9);
                }
                else {
                    kingMovesBoard = Board.KING_SPAN>>(9-i);
                }
//                Because we're not always shifting to in the same direction, the unreachable files can't be predicted
                if (i%8 < 4) {
                    kingMovesBoard &= ~Board.FILE_GH&notMyPieces;
                }
                else {
                    kingMovesBoard &= ~Board.FILE_AB&notMyPieces;
                }

                long enemyAttacks = 0L;
//                enemyAttacks |= (long) King.getMoves(board, true).get(0);
//                enemyAttacks |= (long) Rook.getMoves(board, true).get(0);
//                enemyAttacks |= (long) Bishop.getMoves(board, true).get(0);
//                enemyAttacks |= (long) Knight.getMoves(board, true).get(0);
//                enemyAttacks |= (long) Queen.getMoves(board, true).get(0);
                enemyAttacks |= (long) Pawn.getMoves(board, true, true).get(0);
                bitboard |= kingMovesBoard;
                for (int j = Long.numberOfTrailingZeros(kingMovesBoard);j < 64-Long.numberOfLeadingZeros(kingMovesBoard); j++) {
                    if (((kingMovesBoard>>j)&1) == 1) {
                        moves.add(new Move(i, j));
                    }
                }
            }
        }
        if (inBitboard){
            return new ArrayList(Arrays.asList(bitboard));
        }
        return moves;
    }
}
