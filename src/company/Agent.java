package company;

import java.util.ArrayList;

public class Agent {
    private boolean isWhite;
    private Pawn pawn;
    private Rook rook;
    private King king;
    private Queen queen;
    private Knight knight;
    private Bishop bishop;

    public Agent(boolean computerWhite) {
        this.isWhite = computerWhite;
        this.pawn = new Pawn(this.isWhite);
        this.rook = new Rook(this.isWhite);
        this.king = new King(this.isWhite);
        this.queen = new Queen(this.isWhite);
        this.knight = new Knight(this.isWhite);
        this.bishop = new Bishop(this.isWhite);
    }

    public void makeMove(Board board) {
        ArrayList<Move> legalMoves = new ArrayList<>();

        legalMoves.addAll(pawn.getMoves(board));
        legalMoves.addAll(rook.getMoves(board));
        legalMoves.addAll(king.getMoves(board));
        legalMoves.addAll(queen.getMoves(board));
        legalMoves.addAll(knight.getMoves(board));
        legalMoves.addAll(bishop.getMoves(board));
        for (Move move:legalMoves){
            System.out.println(move.moveNotation);
        }
    }
}
