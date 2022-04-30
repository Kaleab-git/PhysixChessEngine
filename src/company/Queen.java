package company;

import java.util.ArrayList;
import java.util.Arrays;

public class Queen extends Piece{
    private boolean isWhite;
    private String pieceType;

    public Queen(Boolean isWhite) {
        this.isWhite = isWhite;
        if (isWhite == true) {
            this.pieceType = "Q";
        }
        else {
            this.pieceType = "q";
        }
    }

    @Override
    public void drawBitBoard(Board board) {
        String[][] bitboardArray =  new String[8][8];
        for (int i=0; i<64; i++){
            if (((board.WP>>i)&1) == 1){
                bitboardArray[i/8][i%8] = pieceType;
            }
            else{
                bitboardArray[i/8][i%8] = " ";
            }
        }
        for (int i=0; i<8; i++) {
            System.out.println(Arrays.toString(bitboardArray[i]));
        }
    }

    @Override
    public ArrayList getMoves(Board board) {
        ArrayList<Move> moves = new ArrayList<>();

        long queenPosition;
        if (this.isWhite) {
            queenPosition = board.WQ;
        }
        else {
            queenPosition = board.BQ;
        }
        for (int i = Long.numberOfTrailingZeros(queenPosition);i < 64-Long.numberOfLeadingZeros(queenPosition); i++) {
            if (((queenPosition>>i)&1) == 1){
                long queenHandVMovesBoard = HandVMoves(i, board);
                for (int j = Long.numberOfTrailingZeros(queenHandVMovesBoard);j < 64-Long.numberOfLeadingZeros(queenHandVMovesBoard); j++) {
                    if (((queenHandVMovesBoard>>j)&1) == 1) {
                        moves.add(new Move(i, j));
                    }
                }
                long queenDandAntiDMovesBoard = DAndAntiDMoves(i, board);
                for (int j = Long.numberOfTrailingZeros(queenDandAntiDMovesBoard);j < 64-Long.numberOfLeadingZeros(queenDandAntiDMovesBoard); j++) {
                    if (((queenDandAntiDMovesBoard>>j)&1) == 1) {
                        moves.add(new Move(i, j));
                    }
                }
            }
        }
        return moves;
    }

    long HandVMoves(int s, Board board) {
        long binaryS=1L<<s;
        long possibilitiesHorizontal = (board.OCCUPIED - 2 * binaryS) ^ Long.reverse(Long.reverse(board.OCCUPIED) - 2 * Long.reverse(binaryS));
        long possibilitiesVertical = ((board.OCCUPIED&Board.FileMasks8[s % 8]) - (2 * binaryS)) ^ Long.reverse(Long.reverse(board.OCCUPIED&Board.FileMasks8[s % 8]) - (2 * Long.reverse(binaryS)));
        long possibilities = (possibilitiesHorizontal&Board.RankMasks8[s / 8]) | (possibilitiesVertical&Board.FileMasks8[s % 8]);
        if (this.isWhite == true) {
            possibilities &= board.NOT_WHITE_PIECES;
        }
        else {
            possibilities &= ~board.BLACK_PIECES;
        }
        return possibilities;
    }

    long DAndAntiDMoves(int s, Board board) {
        long binaryS=1L<<s;
        long possibilitiesDiagonal = ((board.OCCUPIED&Board.DiagonalMasks8[(s / 8) + (s % 8)]) - (2 * binaryS)) ^ Long.reverse(Long.reverse(board.OCCUPIED&Board.DiagonalMasks8[(s / 8) + (s % 8)]) - (2 * Long.reverse(binaryS)));
        long possibilitiesAntiDiagonal = ((board.OCCUPIED&Board.AntiDiagonalMasks8[(s / 8) + 7 - (s % 8)]) - (2 * binaryS)) ^ Long.reverse(Long.reverse(board.OCCUPIED&Board.AntiDiagonalMasks8[(s / 8) + 7 - (s % 8)]) - (2 * Long.reverse(binaryS)));
        long possibilities = (possibilitiesDiagonal&Board.DiagonalMasks8[(s / 8) + (s % 8)]) | (possibilitiesAntiDiagonal&Board.AntiDiagonalMasks8[(s / 8) + 7 - (s % 8)]);
        if (this.isWhite == true) {
            possibilities &= board.NOT_WHITE_PIECES;
        }
        else {
            possibilities &= ~board.BLACK_PIECES;
        }
        return possibilities;
    }
}
