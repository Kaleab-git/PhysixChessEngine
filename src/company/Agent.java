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
        legalMoves.addAll(Bishop.getMoves(board, false, isWhite));
        long whiteAttacks = 0L;

        whiteAttacks |= (long) Pawn.getMoves(board, true, isWhite).get(0);
//        whiteAttacks |= (long) king.getMoves(board, true).get(0);
//        whiteAttacks |= (long) rook.getMoves(board, true).get(0);
//        whiteAttacks |= (long) bishop.getMoves(board, true).get(0);
//        whiteAttacks |= (long) knight.getMoves(board, true).get(0);
//        whiteAttacks |= (long) queen.getMoves(board, true).get(0);
        Board.drawFromBitboard(whiteAttacks);


        for (Move move:legalMoves){
            System.out.println(move.moveNotation);
        }
    }
}
