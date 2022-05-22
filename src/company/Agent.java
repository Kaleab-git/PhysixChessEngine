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
//        legalMoves.addAll(Queen.getMoves(board, false, isWhite));
        legalMoves.addAll(Knight.getMoves(board, false, isWhite));
//        legalMoves.addAll(Bishop.getMoves(board, false, isWhite));
//        reconnaissanceCall is false here. But when this king wants to know possible moves for opponent's king, it would call getMoves with reconnaissanceCall set to true
//        legalMoves.addAll(King.getMoves(board, false, false , false));

            for (Move move:legalMoves) {
            System.out.println(move.moveNotation);
        }
    }
}
