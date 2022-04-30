package company;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Rook extends Piece{
    private boolean isWhite;
    private String pieceType;

    public Rook(Boolean isWhite) {
        this.isWhite = isWhite;
        if (isWhite == true) {
            this.pieceType = "R";
        }
        else {
            this.pieceType = "r";
        }
    }

    @Override
    public void drawBitBoard(Board board) {
        String[][] bitboardArray =  new String[8][8];
        long piece;
        if (this.isWhite) {
            piece = board.WR;
        }
        else {
            piece = board.BR;
        }
        for (int i=0; i<64; i++){
            if (((piece>>i)&1) == 1){
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

        long rookPosition;
        if (this.isWhite) {
            rookPosition = board.WR;
        }
        else {
            rookPosition = board.BR;
        }
        for (int i = Long.numberOfTrailingZeros(rookPosition);i < 64-Long.numberOfLeadingZeros(rookPosition); i++) {
            if (((rookPosition>>i)&1) == 1){
                long rookMovesBoard = HandVMoves(i, board);
                for (int j = Long.numberOfTrailingZeros(rookMovesBoard);j < 64-Long.numberOfLeadingZeros(rookMovesBoard); j++) {
                    if (((rookMovesBoard>>j)&1) == 1) {
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
}
