package company;

import java.util.ArrayList;

public class Agent {
    public boolean isWhite;
    public Pawn pawn;
    public Rook rook;
    public Queen queen;
    public Bishop bishop;

    public Agent(boolean computerWhite) {
        this.isWhite = computerWhite;
        this.pawn = new Pawn(this.isWhite);
        this.rook = new Rook(this.isWhite);
        this.bishop = new Bishop(this.isWhite);
        this.queen = new Queen(this.isWhite);
    }

    public void makeMove(Board board) {
        ArrayList<Move> legalMoves = new ArrayList();

        legalMoves.addAll(pawn.getMoves(board));
        legalMoves.addAll(rook.getMoves(board));
        legalMoves.addAll(bishop.getMoves(board));
        legalMoves.addAll(queen.getMoves(board));
        for (Move move:legalMoves){
            System.out.println(move.moveNotation);
        }
    }
}
