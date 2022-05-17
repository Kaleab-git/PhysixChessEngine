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
        Move move1 = new Move(0, 63);
        System.out.println(move1.moveNotation);
        Agent agent = new Agent(computerWhite);
        Scanner input = new Scanner(System.in);
//        Setup bitboards to starting position described by mailbox
        Board mainBoard = new Board();
        Board emptyBoard = new Board(0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L);

//        Precompute Attack Table for each piece using an empty board
        Rook.loadTable(emptyBoard);
        Pawn.loadTable(emptyBoard);
        Queen.loadTable(emptyBoard);
        Knight.loadTable(emptyBoard);
        Bishop.loadTable(emptyBoard);

        mainBoard.updateBitboards();
        mainBoard.drawBitboard();
        while (true) {
            if (computerWhite == whiteTurn) {
//            computer makes a random legal move
                System.out.println("Computer made super smart move in 0.0001 microseconds");
                agent.makeMove(mainBoard);
            }
            else {
                System.out.println("User's turn: ");
                String playerMove = input.nextLine();
                Move move = new Move(playerMove);
                mainBoard.movePiece(move);
            }
            mainBoard.drawBitboard();
            whiteTurn = !whiteTurn;
        }
    }

//     Returns true if player who's turn it is is in check
    private boolean inCheck(boolean whiteTurn, Board board) {
        long kingPosition = whiteTurn ? board.WK : board.BK;
        boolean pawnAttacksKing;
        return true;
    }
}
