package company;

import java.util.ArrayList;
import java.util.Arrays;

public class King {
    public static ArrayList getMoves(Board board, boolean inBitboard, boolean isWhite, boolean reconnaissanceCall) {
        long bitboard = 0L;
        ArrayList moves = new ArrayList();

        long kingPosition;
        if (isWhite) {
            kingPosition = board.WK;
        }
        else {
            kingPosition = board.BK;
        }
        for (int i = Long.numberOfTrailingZeros(kingPosition);i < 64-Long.numberOfLeadingZeros(kingPosition); i++) {
            if (((kingPosition>>i)&1) == 1){
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
                long enemyAttacks = 0L;
                if (!reconnaissanceCall) {
                    enemyAttacks |= (long) Pawn.getMoves(board, true, !isWhite).get(0);
                    enemyAttacks |= (long) Rook.getMoves(board, true, !isWhite).get(0);
                    enemyAttacks |= (long) Bishop.getMoves(board, true, !isWhite).get(0);
                    enemyAttacks |= (long) Knight.getMoves(board, true, !isWhite).get(0);
                    enemyAttacks |= (long) Queen.getMoves(board, true, !isWhite).get(0);
                    enemyAttacks |= (long) King.getMoves(board, true, !isWhite, true).get(0);
                    kingMovesBoard &= (~enemyAttacks);
                }
//              Casteling
                boolean kingSide = isWhite ? board.WKC:board.BKC;
                boolean queenSide = isWhite ? board.WQC:board.WKC;
//                Squares inbetween King and rook depending on color and side
                long kingSideSquares = isWhite ? 6917529027641081856L:96;
                long queenSideSqueares = isWhite ? 1008806316530991104L:14;
/*                    Make sure that: the current player has Kingside castling right, the king's position and none of the squares in between are under attack,
                      that the squares in between are empty */

                if (kingSide && ((kingPosition | kingSideSquares) & enemyAttacks) == 0 && (kingSideSquares & board.EMPTY) == kingSideSquares) {
                    String moveNotation = isWhite ? "e1,g1":"e8,g8";
                    Move move = new Move(moveNotation);
                    move.type = "Castling";
                    moves.add(move);
                }
                if (queenSide && ((kingPosition | queenSideSqueares) & enemyAttacks) == 0 && (queenSideSqueares & board.EMPTY) == queenSideSqueares) {
                    String moveNotation = isWhite ? "e1,c1":"e8,c8";
                    Move move = new Move(moveNotation);
                    move.type = "Castling";
                    moves.add(move);
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
