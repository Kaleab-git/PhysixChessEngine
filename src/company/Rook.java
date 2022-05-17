package company;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Rook {
    public static long[] attacksTo = new long[64];

    public static void loadTable(Board board) {
        for (int i=0; i<64; i++) {
            attacksTo[i] = HandVMoves(i, board, true);
        }
    }

    public static ArrayList getMoves(Board board, boolean inBitboard, boolean isWhite) {
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
            if (((rookPosition>>i)&1) == 1){
                long rookMovesBoard = HandVMoves(i, board, isWhite);
                bitboard |= rookMovesBoard;
                for (int j = Long.numberOfTrailingZeros(rookMovesBoard);j < 64-Long.numberOfLeadingZeros(rookMovesBoard); j++) {
                    if (((rookMovesBoard>>j)&1) == 1) {
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

    static long HandVMoves(int s, Board board, boolean isWhite) {
        long binaryS=1L<<s;
        long possibilitiesHorizontal = (board.OCCUPIED - 2 * binaryS) ^ Long.reverse(Long.reverse(board.OCCUPIED) - 2 * Long.reverse(binaryS));
        long possibilitiesVertical = ((board.OCCUPIED&Board.FileMasks8[s % 8]) - (2 * binaryS)) ^ Long.reverse(Long.reverse(board.OCCUPIED&Board.FileMasks8[s % 8]) - (2 * Long.reverse(binaryS)));
        long possibilities = (possibilitiesHorizontal&Board.RankMasks8[s / 8]) | (possibilitiesVertical&Board.FileMasks8[s % 8]);
        if (isWhite == true) {
            possibilities &= ~board.WHITE_PIECES;
        }
        else {
            possibilities &= ~board.BLACK_PIECES;
        }
        return possibilities;
    }
}
