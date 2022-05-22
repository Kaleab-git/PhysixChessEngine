package company;

import java.util.ArrayList;
import java.util.Arrays;

public class Bishop {
    public static long[] attacksTo = new long[64];

    public static void loadTable(Board board) {
        for (int i=0; i<64; i++) {
            attacksTo[i] = DAndAntiDMoves(i, board, false,true);
        }
    }

    public static ArrayList getMoves(Board board, boolean inBitboard, boolean isWhite) {
//        initializing bitboard to enemy kings position so as to consider even enemy kings position as a possible attack square
        long bitboard = 0L;
        ArrayList<Move> moves = new ArrayList<>();

        long bishopPosition;
        if (isWhite) {
            bishopPosition = board.WB;
        }
        else {
            bishopPosition = board.BB;
        }
        for (int i = Long.numberOfTrailingZeros(bishopPosition);i < 64-Long.numberOfLeadingZeros(bishopPosition); i++) {
            if (inBitboard) {
                bitboard |= DAndAntiDMoves(i, board, true, isWhite);
            }
            else {
                if (((bishopPosition>>i)&1) == 1){
                    long enemyKing = isWhite ? board.BK:board.WK;
                    long bishopMovesBoard = DAndAntiDMoves(i, board, false, isWhite)&~enemyKing;
                    for (int j = Long.numberOfTrailingZeros(bishopMovesBoard);j < 64-Long.numberOfLeadingZeros(bishopMovesBoard); j++) {
                        if (((bishopMovesBoard>>j)&1) == 1) {
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

    static long DAndAntiDMoves(int s, Board board, boolean inBitboard, boolean isWhite) {
        long binaryS=1L<<s;
        long possibilitiesDiagonal = ((board.OCCUPIED&Board.DiagonalMasks8[(s / 8) + (s % 8)]) - (2 * binaryS)) ^ Long.reverse(Long.reverse(board.OCCUPIED&Board.DiagonalMasks8[(s / 8) + (s % 8)]) - (2 * Long.reverse(binaryS)));
        long possibilitiesAntiDiagonal = ((board.OCCUPIED&Board.AntiDiagonalMasks8[(s / 8) + 7 - (s % 8)]) - (2 * binaryS)) ^ Long.reverse(Long.reverse(board.OCCUPIED&Board.AntiDiagonalMasks8[(s / 8) + 7 - (s % 8)]) - (2 * Long.reverse(binaryS)));
        long possibilities = (possibilitiesDiagonal&Board.DiagonalMasks8[(s / 8) + (s % 8)]) | (possibilitiesAntiDiagonal&Board.AntiDiagonalMasks8[(s / 8) + 7 - (s % 8)]);
        if (inBitboard) {
            return possibilities;
        }
        if (isWhite) {
            possibilities &= ~board.WHITE_PIECES;
        }
        else {
            possibilities &= ~board.BLACK_PIECES;
        }
        return possibilities;
    }
}
