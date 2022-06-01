package company;

import java.util.ArrayList;

public class Perft {
    public static int divide(int depth, Board board, boolean whiteTurn, Move parentMove) {
        if (depth == 0) {
            if (parentMove.moveNotation.equals("e8,f8")) {
//                System.out.println(parentMove.moveNotation);
            }
            return 1;
        }
//        Collect all moves
        ArrayList<Move> moves = new ArrayList<>();
        ArrayList<Move> legalMoves = new ArrayList<>();
        moves.addAll(Pawn.getMoves(board, false, whiteTurn));
        moves.addAll(Rook.getMoves(board, false, whiteTurn));
        moves.addAll(Queen.getMoves(board, false, whiteTurn));
        moves.addAll(Knight.getMoves(board, false, whiteTurn));
        moves.addAll(Bishop.getMoves(board, false, whiteTurn));
        moves.addAll(King.getMoves(board, false, whiteTurn , false));
//        Filter out pseudolegal moves
        for (Move move:moves) {
            board.makeMove(move);
            if (!Game.inCheck(whiteTurn, board)) {
                legalMoves.add(move);
            }
            board.unmakeMove();
        }
//        Recursively explore every move. Switch turn cause next move is by opposing side
        int numberOfPositions = 0;
        for (Move legalMove:legalMoves) {
            board.makeMove(legalMove);
            int positions = divide(depth-1, board, !whiteTurn, legalMove);
            if (depth == 1
                    & parentMove.moveNotation.equals("e0,e6")) System.out.println(legalMove.moveNotation + " " + positions + " " + legalMove.type + " " + legalMove.promoteToPiece);
            numberOfPositions += positions;
            board.unmakeMove();
        }

        return numberOfPositions;
    }

}
