package company;

import java.util.ArrayList;
import java.util.Arrays;

public class Knight extends Piece {
    private boolean isWhite;
    private String pieceType;

    public Knight(boolean isWhite) {
        this.isWhite = isWhite;
        if (isWhite) {
            this.pieceType = "N";
        }
        else {
            this.pieceType = "n";
        }
    }

    @Override
    public void drawBitBoard(Board board) {
        String[][] bitboardArray =  new String[8][8];
        long piece;
        if (this.isWhite) {
            piece = board.WN;
        }
        else {
            piece = board.BN;
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

        long knightPositions;
        if (this.isWhite) {
            knightPositions = board.WN;
        }
        else {
            knightPositions = board.BN;
        }
        for (int i = Long.numberOfTrailingZeros(knightPositions);i < 64-Long.numberOfLeadingZeros(knightPositions); i++) {
            if (((knightPositions>>i)&1) == 1){
                long knightMovesBoard;
//                avoid capturing your own piece
                long notMyPieces;
                if (this.isWhite) {
                    notMyPieces = ~board.WHITE_PIECES;
                }
                else {
                    notMyPieces = ~board.BLACK_PIECES;
                }
//                We're not shifting in a single direction.
                if (i > 18) {
                    knightMovesBoard = Board.KNIGHT_SPAN<<(i-18);
                }
                else {
                    knightMovesBoard = Board.KNIGHT_SPAN>>(18-i);
                }
//                Because we're not always shifting to in the same direction, the unreachable files can't be predicted
                if (i%8 < 4) {
                    knightMovesBoard &= ~Board.FILE_GH&notMyPieces;
                }
                else {
                    knightMovesBoard &= ~Board.FILE_AB&notMyPieces;
                }
                Board.drawFromBitboard(knightMovesBoard);
            }
        }
        return moves;
    }
}
