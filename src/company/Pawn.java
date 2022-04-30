package company;

import java.util.ArrayList;
import java.util.Arrays;

public class Pawn extends Piece{
    private Boolean isWhite;
    private String pieceType;

    public Pawn(Boolean isWhite) {
        this.isWhite = isWhite;
        if (isWhite == true) {
            this.pieceType = "P";
        }
        else {
            this.pieceType = "p";
        }
    }

    @Override
    public ArrayList getMoves(Board board) {
        ArrayList<Move> pawnMoves = new ArrayList();

//      Forward Move
        long forwardMove = this.isWhite ? ((board.WP>>>8)&board.EMPTY&~Board.RANK_8) : ((board.BP<<8)&board.EMPTY&~Board.RANK_1);
        for (int i = Long.numberOfTrailingZeros(forwardMove);i < 64-Long.numberOfLeadingZeros(forwardMove); i++){
            if (((forwardMove>>i) & 1) == 1) {
                Move move = this.isWhite ? new Move(i+8, i) : new Move(i-8, i);
                pawnMoves.add(move);
            }
        }
//      Right Capture
        long rightCapture = this.isWhite ? ((board.WP>>>7)&board.BLACK_PIECES&~Board.RANK_8&~Board.FILE_A) : ((board.BP<<9)&board.WHITE_PIECES&~Board.RANK_1&~Board.FILE_A);
        for (int i = Long.numberOfTrailingZeros(rightCapture);i < 64-Long.numberOfLeadingZeros(rightCapture); i++){
            if (((rightCapture>>i) & 1) == 1) {
                Move move = this.isWhite ? new Move(i+7, i) : new Move(i-9, i);
                pawnMoves.add(move);
            }
        }
//      Left Capture
        long leftCapture = this.isWhite ? ((board.WP>>>9)&board.BLACK_PIECES&~Board.FILE_H&~Board.RANK_8) : ((board.BP<<7)&board.WHITE_PIECES&~Board.RANK_1&~Board.FILE_H);
        for (int i = Long.numberOfTrailingZeros(leftCapture);i < 64-Long.numberOfLeadingZeros(leftCapture); i++){
            if (((leftCapture>>i) & 1) == 1) {
                Move move = this.isWhite ? new Move(i+9, i) : new Move(i-7, i);
                pawnMoves.add(move);
            }
        }
//      Double Forward Move
        long doubleForwardMove = this.isWhite ? ((board.WP>>>16)&board.EMPTY&(board.EMPTY>>8)&Board.RANK_4) : ((board.BP<<16)&board.EMPTY&(board.EMPTY<<8)&Board.RANK_5);
        for (int i = Long.numberOfTrailingZeros(doubleForwardMove);i < 64-Long.numberOfLeadingZeros(doubleForwardMove); i++){
            if (((doubleForwardMove>>i) & 1) == 1) {
                Move move = this.isWhite ? new Move(i+16, i) : new Move(i-16, i);
                pawnMoves.add(move);
            }
        }
//        Promotion forward
        long forwardPromotion = this.isWhite ? ((board.WP>>>8)&board.EMPTY&Board.RANK_8) : ((board.BP<<8)&board.EMPTY&Board.RANK_1);
        for (int i = Long.numberOfTrailingZeros(forwardPromotion);i < 64-Long.numberOfLeadingZeros(forwardPromotion); i++){
            if (((forwardPromotion>>i) & 1) == 1) {
                Move move = this.isWhite ? new Move(i+8, i) : new Move(i-8, i);
                pawnMoves.add(move);
            }
        }
//        Promotion by capturing to the right
        long rightPromotion = this.isWhite ? ((board.WP>>>7)&board.BLACK_PIECES&Board.RANK_8&~Board.FILE_A) : (((board.BP<<9)&board.WHITE_PIECES&Board.RANK_1&~Board.FILE_A));
        for (int i = Long.numberOfTrailingZeros(rightPromotion);i < 64-Long.numberOfLeadingZeros(rightPromotion); i++){
            if (((rightPromotion>>i) & 1) == 1) {
                Move move = this.isWhite ? new Move(i+7, i) : new Move(i-9, i);
                pawnMoves.add(move);
            }
        }
//        Promotion by capturing to the left
        long leftPromotion = this.isWhite ? ((board.WP>>>9)&board.BLACK_PIECES&Board.RANK_8&~Board.FILE_H) : ((board.BP<<7)&board.WHITE_PIECES&Board.RANK_1&~Board.FILE_H);
        for (int i = Long.numberOfTrailingZeros(leftPromotion);i < 64-Long.numberOfLeadingZeros(leftPromotion); i++){
            if (((leftPromotion>>i) & 1) == 1) {
                Move move = this.isWhite ? new Move(i+9, i) : new Move(i-7, i);
                pawnMoves.add(move);
            }
        }

        return pawnMoves;
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
}
