package company;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Rook {
    public static long[] attacksTo = new long[64];

    public static void loadTable(Board board) {
        for (int i=0; i<64; i++) {
            attacksTo[i] = HandVMoves(i, board, false,true);
        }
    }

    public static ArrayList getMoves(Board board, boolean inBitboard, boolean isWhite) {
//        initializing bitboard to enemy kings position so as to consider even enemy kings position as a possible attack square
        long bitboard = 0L;
        ArrayList<Move> moves = new ArrayList<>();

        long rookPosition;
        if (isWhite) {
            rookPosition = board.WR;
        }
        else {
            rookPosition = board.BR;
        }
        for (int i = Long.numberOfTrailingZeros(rookPosition);i < 64-Long.numberOfLeadingZeros(rookPosition); i++) {
//            if we're considering attacks only, 1) we just want the bitboard. We dont want to iterate through it. 2) we also dont want to remove friendly pieces form the board.
            if (((rookPosition>>i)&1) == 1) {
                if (inBitboard) {
                    bitboard |= HandVMoves(i, board, true, isWhite);
                }
                else {

                    long enemyKing = isWhite ? board.BK:board.WK;
                    long rookMovesBoard = HandVMoves(i, board, false, isWhite)&~enemyKing;
                    for (int j = Long.numberOfTrailingZeros(rookMovesBoard);j < 64-Long.numberOfLeadingZeros(rookMovesBoard); j++) {
                        if (((rookMovesBoard>>j)&1) == 1) {
                            moves.add(new Move(i, j));
                        }
                    }
                }
            }
        }
        if (inBitboard) {
            return new ArrayList(Arrays.asList(bitboard));
        }
        return moves;
    }

    static long HandVMoves(int s, Board board, boolean inBitboard, boolean isWhite) {
        long binaryS=1L<<s;
        long possibilitiesHorizontal = (board.OCCUPIED - 2 * binaryS) ^ Long.reverse(Long.reverse(board.OCCUPIED) - 2 * Long.reverse(binaryS));
        long possibilitiesVertical = ((board.OCCUPIED&Board.FileMasks8[s % 8]) - (2 * binaryS)) ^ Long.reverse(Long.reverse(board.OCCUPIED&Board.FileMasks8[s % 8]) - (2 * Long.reverse(binaryS)));
        long possibilities = (possibilitiesHorizontal&Board.RankMasks8[s / 8]) | (possibilitiesVertical&Board.FileMasks8[s % 8]);
        if (inBitboard) {
            return possibilities;
        }
        if (isWhite == true) {
            possibilities &= ~board.WHITE_PIECES;
        }
        else {
            possibilities &= ~board.BLACK_PIECES;
        }
        return possibilities;
    }
}
