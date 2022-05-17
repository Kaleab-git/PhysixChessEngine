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
    }

    public void makeMove(Board board) {
        ArrayList<Move> legalMoves = new ArrayList<>();

//        legalMoves.addAll(Pawn.getMoves(board, false, isWhite));
//        legalMoves.addAll(Rook.getMoves(board, false, isWhite));
//        legalMoves.addAll(King.getMoves(board, false, isWhite));
//        legalMoves.addAll(Queen.getMoves(board, false, isWhite));
//        legalMoves.addAll(Knight.getMoves(board, false, isWhite));
//        legalMoves.addAll(Bishop.getMoves(board, false, isWhite));
        King.getMoves(board, true, isWhite, false);
            for (Move move:legalMoves){
            System.out.println(move.moveNotation);
        }
    }
}
