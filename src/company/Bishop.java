package company;

import java.util.ArrayList;
import java.util.Arrays;

public class Bishop {
    public static long[] attacksTo = new long[64];

    public static void loadTable(Board board) {
        for (int i=0; i<64; i++) {
            attacksTo[i] = DAndAntiDMoves(i, board, true);
        }
    }

    public static ArrayList getMoves(Board board, boolean inBitboard, boolean isWhite) {
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
            if (((bishopPosition>>i)&1) == 1){
                long bishopMovesBoard = DAndAntiDMoves(i, board, isWhite);
                bitboard |= bishopMovesBoard;
                for (int j = Long.numberOfTrailingZeros(bishopMovesBoard);j < 64-Long.numberOfLeadingZeros(bishopMovesBoard); j++) {
                    if (((bishopMovesBoard>>j)&1) == 1) {
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

    static long DAndAntiDMoves(int s, Board board, boolean isWhite) {
        long binaryS=1L<<s;
        long possibilitiesDiagonal = ((board.OCCUPIED&Board.DiagonalMasks8[(s / 8) + (s % 8)]) - (2 * binaryS)) ^ Long.reverse(Long.reverse(board.OCCUPIED&Board.DiagonalMasks8[(s / 8) + (s % 8)]) - (2 * Long.reverse(binaryS)));
        long possibilitiesAntiDiagonal = ((board.OCCUPIED&Board.AntiDiagonalMasks8[(s / 8) + 7 - (s % 8)]) - (2 * binaryS)) ^ Long.reverse(Long.reverse(board.OCCUPIED&Board.AntiDiagonalMasks8[(s / 8) + 7 - (s % 8)]) - (2 * Long.reverse(binaryS)));
        long possibilities = (possibilitiesDiagonal&Board.DiagonalMasks8[(s / 8) + (s % 8)]) | (possibilitiesAntiDiagonal&Board.AntiDiagonalMasks8[(s / 8) + 7 - (s % 8)]);
        if (isWhite) {
            possibilities &= ~board.WHITE_PIECES;
        }
        else {
            possibilities &= ~board.BLACK_PIECES;
        }
        return possibilities;
    }
}
