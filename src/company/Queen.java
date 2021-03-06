package company;

import java.util.ArrayList;
import java.util.Arrays;

public class Queen {
    public static long[] attacksTo = new long[64];

    public static void loadTable(Board board) {
        for (int i=0; i<64; i++) {
            attacksTo[i] = (HandVMoves(i, board,false,true) | DAndAntiDMoves(i, board,false,true));
        }
    }

    public static ArrayList getMoves(Board board, boolean inBitboard, boolean isWhite) {
        long bitboard = 0L;
        long queenPosition;
        ArrayList<Move> moves = new ArrayList<>();
        if (isWhite) {
            queenPosition = board.WQ;
        }
        else {
            queenPosition = board.BQ;
        }
        for (int i = Long.numberOfTrailingZeros(queenPosition); i < 64-Long.numberOfLeadingZeros(queenPosition); i++) {
            if (((queenPosition>>i)&1) == 1) {
                if (inBitboard) {
                    bitboard |= HandVMoves(i, board, true, isWhite);
                    bitboard |= DAndAntiDMoves(i, board, true, isWhite);
                }
                else {
                    long enemyKing = isWhite ? board.BK:board.WK;
                    long queenHandVMovesBoard = HandVMoves(i, board, false, isWhite)&~enemyKing;
                    for (int j = Long.numberOfTrailingZeros(queenHandVMovesBoard);j < 64-Long.numberOfLeadingZeros(queenHandVMovesBoard); j++) {
                        if (((queenHandVMovesBoard>>j)&1) == 1) {
                            moves.add(new Move(i, j));
                        }
                    }
                    long queenDandAntiDMovesBoard = DAndAntiDMoves(i, board, false, isWhite)&~enemyKing;
                    for (int j = Long.numberOfTrailingZeros(queenDandAntiDMovesBoard);j < 64-Long.numberOfLeadingZeros(queenDandAntiDMovesBoard); j++) {
                        if (((queenDandAntiDMovesBoard>>j)&1) == 1) {
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
