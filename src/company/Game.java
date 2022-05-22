package company;

import java.util.Scanner;

public class Game {
    public String[] history;
    private boolean whiteTurn = true;
    private boolean computerWhite = true;

    public Game() {}

    public boolean isDraw() {return false;}

    public boolean isCheck() {return false;}

    public boolean checkMate() {return false;}

    public void play() {
        Agent agent = new Agent(computerWhite);
        Scanner input = new Scanner(System.in);

//        Initialize a bitboard to starting position described by mailbox
        Board mainBoard = new Board();
        mainBoard.updateBitboards();

//        Initialize bitboard to an empty board
        Board emptyBoard = new Board(0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L);

//        Precompute Attack Table for each piece using the empty board
        Rook.loadTable(emptyBoard);
        Pawn.loadTable(emptyBoard);
        Queen.loadTable(emptyBoard);
        Knight.loadTable(emptyBoard);
        Bishop.loadTable(emptyBoard);

        while (true) {
            mainBoard.drawBitboard();
            if (computerWhite == whiteTurn) {
                System.out.println("Computer made super smart move in 0.0001 microseconds");
                agent.makeMove(mainBoard);
            }
            else {
                System.out.println("User's turn: ");
                String playerMove = input.nextLine();
                Move move = new Move(playerMove);
                mainBoard.movePiece(move);
            }
            whiteTurn = !whiteTurn;
        }
    }

//     Returns true if player who's turn it is is in check
//     Fair warning: since kingPositionIndex is initialized to 0. Even if a king doesn't exist on the board, this routine might report the king is under check.
    private boolean inCheck(boolean whiteTurn, Board board) {
        long attacksToKing = 0L;
        attacksToKing |= (long) Pawn.getMoves(board, true, !whiteTurn).get(0);
        attacksToKing |= (long) Rook.getMoves(board, true, !whiteTurn).get(0);
        attacksToKing |= (long) Queen.getMoves(board, true, !whiteTurn).get(0);
        attacksToKing |= (long) Knight.getMoves(board, true, !whiteTurn).get(0);
        attacksToKing |= (long) Bishop.getMoves(board, true, !whiteTurn).get(0);
//        Set reconnaissanceCall to false because a King can be attacking a square even if that square is also under attack by enemy and the King can technically never occupt that square
        attacksToKing |= (long) King.getMoves(board, true, !whiteTurn, false).get(0);
        long kingPosition = whiteTurn ? board.WK:board.BK;
        return ((attacksToKing&kingPosition) > 0);

    }
}
