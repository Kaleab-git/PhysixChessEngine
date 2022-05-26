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
        ArrayList<Move> moves = new ArrayList<>();

        moves.addAll(Pawn.getMoves(board, false, isWhite));
        moves.addAll(Rook.getMoves(board, false, isWhite));
        moves.addAll(Queen.getMoves(board, false, isWhite));
        moves.addAll(Knight.getMoves(board, false, isWhite));
        moves.addAll(Bishop.getMoves(board, false, isWhite));
//        reconnaissanceCall is false here. But when this king wants to know possible moves for opponent's king, it would call getMoves with reconnaissanceCall set to true
        moves.addAll(King.getMoves(board, false, isWhite , false));

        ArrayList<Move> legalMoves = new ArrayList<>();

        for (Move move:moves) {
            board.makeMove(move);
//            If this move doesn't result in King being in check it's a legal move. Otherwise, it's a pseudolegal move
            if (!Game.inCheck(isWhite, board)) {
                legalMoves.add(move);
            }
            board.unmakeMove();
        }

        for (Move move:moves) {
            if (!legalMoves.contains(move)) {
                System.out.println(move.moveNotation + " REJECTED!");
            }
        }
        for (Move legalMoveL:legalMoves) {
            System.out.println(legalMoveL.moveNotation);
        }

    }
}
