package company;

import java.util.Scanner;

public class Game {
    public String[] history;
    private boolean whiteTurn = true;
    private boolean computerWhite = false;

    public Game() {}

    public boolean isDraw() {return false;}

    public boolean isCheck() {return false;}

    public boolean checkMate() {return false;}

    public void play() {
        Agent agent = new Agent(computerWhite);
        Scanner input = new Scanner(System.in);
//        Setup bitboards to starting position described by mailbox
        Board mainBoard = new Board();
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
                mainBoard.movePiece(new Move(playerMove));
            }
            mainBoard.drawBitboard();
            whiteTurn = !whiteTurn;
        }
    }
}
