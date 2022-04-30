package company;

import java.util.ArrayList;
import java.util.Arrays;

public class Knight extends Piece {
    private boolean isWhite;
    private String pieceType;

    public Knight(boolean isWhite) {
        this.isWhite = isWhite;
        if (isWhite == true) {
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
        long knightMoves;

        return null;
    }
}
