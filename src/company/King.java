package company;

import java.util.ArrayList;
import java.util.Arrays;

public class King {
    public static ArrayList getMoves(Board board, boolean inBitboard, boolean isWhite, boolean reconnaissanceCall) {
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
                if (i > 9) {
                    kingMovesBoard = Board.KING_SPAN<<(i-9);
                }
                else {
                    kingMovesBoard = Board.KING_SPAN>>>(9-i);
                }
//                Because we're not always shifting in the same direction, what files are unreachable can't be predicted
//                Also(unrelated) we want to set our attacks bitboard before removing friendly pieces. This way we also consider squares we're defending as sqaures we're attacking.
                if (i%8 < 4) {
                    bitboard |= kingMovesBoard&~Board.FILE_GH;
                    kingMovesBoard &= ~Board.FILE_GH&notMyPieces;
                }
                else {
                    bitboard |= kingMovesBoard&~Board.FILE_AB;
                    kingMovesBoard &= ~Board.FILE_AB&notMyPieces;
                }

//                A king called on for reconnaissance should avoid calling reconnaissance on his opponents.
//                1. Inorder to avoid infinitely calling on eachother
//                2. Just because enemy king can't move to a square because original piece is attacking that square, it does not mean that enemy king is not attacking that square
//                   Original king can't move to an attacked cell thinking that it has got its own piece to defend it
                if (!reconnaissanceCall) {
                    long enemyAttacks = 0L;
                    enemyAttacks |= (long) Pawn.getMoves(board, true, !isWhite).get(0);
                    enemyAttacks |= (long) Rook.getMoves(board, true, !isWhite).get(0);
                    enemyAttacks |= (long) Bishop.getMoves(board, true, !isWhite).get(0);
                    enemyAttacks |= (long) Knight.getMoves(board, true, !isWhite).get(0);
                    enemyAttacks |= (long) Queen.getMoves(board, true, !isWhite).get(0);
                    enemyAttacks |= (long) King.getMoves(board, true, !isWhite, true).get(0);
                    kingMovesBoard &= (~enemyAttacks);
                }
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
