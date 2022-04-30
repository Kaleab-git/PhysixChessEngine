package company;

import java.util.ArrayList;
import java.util.Arrays;

public class King extends Piece{
    private boolean isWhite;
    private String pieceType;

    public King(boolean isWhite) {
        this.isWhite = isWhite;
        if (isWhite) {
            this.pieceType = "K";
        }
        else {
            this.pieceType = "k";
        }
    }

    @Override
    public void drawBitBoard(Board board) {
        String[][] bitboardArray =  new String[8][8];
        long piece;
        if (this.isWhite) {
            piece = board.WK;
        }
        else {
            piece = board.BK;
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

        long kingPositions;
        if (this.isWhite) {
            kingPositions = board.WK;
        }
        else {
            kingPositions = board.BK;
        }
        for (int i = Long.numberOfTrailingZeros(kingPositions);i < 64-Long.numberOfLeadingZeros(kingPositions); i++) {
            if (((kingPositions>>i)&1) == 1){
                long kingMovesBoard;
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
                    kingMovesBoard = Board.KING_SPAN<<(i-9);
                }
                else {
                    kingMovesBoard = Board.KING_SPAN>>(9-i);
                }
//                Because we're not always shifting to in the same direction, the unreachable files can't be predicted
                if (i%8 < 4) {
                    kingMovesBoard &= ~Board.FILE_GH&notMyPieces;
                }
                else {
                    kingMovesBoard &= ~Board.FILE_AB&notMyPieces;
                }
                for (int j = Long.numberOfTrailingZeros(kingMovesBoard);j < 64-Long.numberOfLeadingZeros(kingMovesBoard); j++) {
                    if (((kingMovesBoard>>j)&1) == 1) {
                        moves.add(new Move(i, j));
                    }
                }
            }
        }
        return moves;
    }
}
