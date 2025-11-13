package company.agent;

import company.*;

import java.util.ArrayList;

public class Agent {
    final boolean isWhite;
    private boolean printReasoning;


    public Agent(boolean isWhite, boolean printReasoning) {
        this.isWhite = isWhite;
        this.printReasoning = printReasoning;
    }

    public Move makeMove(Board board) {
        ArrayList<Move> legalMoves = Game.getLegalMoves(board, isWhite);

//       Test algorithm: First time's the charm. Always make the first legal move
        return legalMoves.get(0);
    }
}
