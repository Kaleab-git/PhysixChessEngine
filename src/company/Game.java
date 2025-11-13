package company;

import company.agent.Agent;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    public String[] history;

    public Game() {}

    public boolean isDraw() {return false;}

    public boolean isCheck() {return false;}

    public boolean checkMate() {return false;}

    public void play() {
        boolean agentWhite = false;
        Agent agent = new Agent(agentWhite, false);
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

        boolean whiteTurn = true;
        while (true) {
            mainBoard.drawBitboard();
            if (agentWhite == whiteTurn) {
                Move agentMove = agent.makeMove(mainBoard);
                mainBoard.makeMove(agentMove);
                System.out.println("Computer made super smart move in 0.0001 microseconds");
            }
            else {
                boolean legalMoveMade = false;
                while (!legalMoveMade) {
                    System.out.println("User's turn: ");
                    Move playerMove = new Move(input.nextLine());

                    if (!isLegal(playerMove, mainBoard, whiteTurn)) {
                        mainBoard.unmakeMove();
                        System.out.println("Illegal move: " + playerMove.moveNotation + ". Make a different move.");
                    } else{
                        mainBoard.makeMove(playerMove);
                        legalMoveMade = true;
                    }
                }
            }
            whiteTurn = !whiteTurn;
        }
    }

//     Returns true if player whose turn it is in check
//     Fair warning: since kingPositionIndex is initialized to 0. Even if a king doesn't exist on the board, this routine might report the king is under check.
    public static boolean inCheck(boolean whiteTurn, Board board) {
        long attacksToKing = 0L;
        attacksToKing |= (long) Pawn.getMoves(board, true, !whiteTurn).get(0);
        attacksToKing |= (long) Rook.getMoves(board, true, !whiteTurn).get(0);
        attacksToKing |= (long) Queen.getMoves(board, true, !whiteTurn).get(0);
        attacksToKing |= (long) Knight.getMoves(board, true, !whiteTurn).get(0);
        attacksToKing |= (long) Bishop.getMoves(board, true, !whiteTurn).get(0);
//        Set reconnaissanceCall to false because a King can be attacking a square even if that square is also under
//        attack by enemy and the King can technically never occupy that square
        attacksToKing |= (long) King.getMoves(board, true, !whiteTurn, false).get(0);
        long kingPosition = whiteTurn ? board.WK:board.BK;
        return ((attacksToKing&kingPosition) != 0);

    }

    public static boolean isLegal(Move move, Board board, boolean whiteTurn) {
        return getLegalMoves(board, whiteTurn).contains(move);
    }

    public static ArrayList<Move> getLegalMoves(Board board, boolean whiteTurn) {
        ArrayList<Move> moves = new ArrayList<>();

        moves.addAll(Pawn.getMoves(board, false, whiteTurn));
        moves.addAll(Rook.getMoves(board, false, whiteTurn));
        moves.addAll(Queen.getMoves(board, false, whiteTurn));
        moves.addAll(Knight.getMoves(board, false, whiteTurn));
        moves.addAll(Bishop.getMoves(board, false, whiteTurn));
//        reconnaissanceCall is false here. But when this king wants to know possible moves for opponent's king, it would call getMoves with reconnaissanceCall set to true
        moves.addAll(King.getMoves(board, false, whiteTurn , false));

        ArrayList<Move> legalMoves = new ArrayList<>();

        for (Move mv:moves) {
            board.makeMove(mv);
            if (!Game.inCheck(whiteTurn, board)) {
                legalMoves.add(mv);
            }
            board.unmakeMove();
        }
        return legalMoves;
    }
}
