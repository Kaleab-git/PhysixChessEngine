package company;

import java.util.ArrayList;
import java.util.Arrays;

public class Bishop extends Piece{
    private Boolean isWhite;
    private String pieceType;

    public Bishop(Boolean isWhite) {
        this.isWhite = isWhite;
        if (isWhite) {
            this.pieceType = "B";
        }
        else {
            this.pieceType = "b";
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

        long bishopPosition;
        if (this.isWhite) {
            bishopPosition = board.WB;
        }
        else {
            bishopPosition = board.BB;
        }
        for (int i = Long.numberOfTrailingZeros(bishopPosition);i < 64-Long.numberOfLeadingZeros(bishopPosition); i++) {
            if (((bishopPosition>>i)&1) == 1){
                long bishopMovesBoard = DAndAntiDMoves(i, board);
                for (int j = Long.numberOfTrailingZeros(bishopMovesBoard);j < 64-Long.numberOfLeadingZeros(bishopMovesBoard); j++) {
                    if (((bishopMovesBoard>>j)&1) == 1) {
                        moves.add(new Move(i, j));
                    }
                }
            }
        }
        return moves;
    }

    long DAndAntiDMoves(int s, Board board) {
        long binaryS=1L<<s;
        long possibilitiesDiagonal = ((board.OCCUPIED&Board.DiagonalMasks8[(s / 8) + (s % 8)]) - (2 * binaryS)) ^ Long.reverse(Long.reverse(board.OCCUPIED&Board.DiagonalMasks8[(s / 8) + (s % 8)]) - (2 * Long.reverse(binaryS)));
        long possibilitiesAntiDiagonal = ((board.OCCUPIED&Board.AntiDiagonalMasks8[(s / 8) + 7 - (s % 8)]) - (2 * binaryS)) ^ Long.reverse(Long.reverse(board.OCCUPIED&Board.AntiDiagonalMasks8[(s / 8) + 7 - (s % 8)]) - (2 * Long.reverse(binaryS)));
        long possibilities = (possibilitiesDiagonal&Board.DiagonalMasks8[(s / 8) + (s % 8)]) | (possibilitiesAntiDiagonal&Board.AntiDiagonalMasks8[(s / 8) + 7 - (s % 8)]);
        if (this.isWhite) {
            possibilities &= board.NOT_WHITE_PIECES;
        }
        else {
            possibilities &= ~board.BLACK_PIECES;
        }
        return possibilities;
    }
}
