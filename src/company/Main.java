package company;

public class Main {
    public static boolean whiteTurn = true;

    public static void main(String[] args) {
//        -------------------- 2025 Look back ------------------------------
//        FIXME: (2025): A good amount of refactoring. Especially Board class.
/*        FIXME: TESTS ARE CRITICAL!!! Too many parts of the project are non-trivial. Most of the code is not obvious if
                 it should work or not. Its also tricky to write the code, plenty of edge cases..... no amount of
                 caution would suffice. Need to setup tests that verify.


*/
//        TODO: Change how you iterate through bits from trailing -> leading to just jumping to where we have 1s.
//        TODO: Create an iterator class for iterating through bits in the most optimal way
//        TODO: Measure the time difference between normal iteration and optimal iteration
//        FIXME: The way I chose to implement unsafe cells for the king can use a little improvement
/*        FIXME: change bitboards name to something like attacksBitboard. Also change the parameter inBitboard.
                 It's very misleading. You're not gettings the moves in bitboard. You're gettings attacks         */
/*        TODO: Board class is becoming messy. Maybe split it into 2. 1) Would contain static methods(drawing, converting)
                2) Would contain Board instance specific logic                                                    */
//        TODO: En passant is not recognized if it's made by the user?
//        TODO: Code in Game.inCheck() is literally the same as a certain part of King's getMoves logic
//        FIXME: Actually it works. unmakeMove returns all bitboards to their previous state. Even though casteling is
//               two moves, its made by different pieces so its still 1 move per piece.
//               [board.history is not correct. When castling we add 2 moves. When unmaking a castle move, we only remove the last one.]
//        Game game = new Game();
//        game.play();

/*     Perft Test Positions from Chess Programming Wiki.
       Initialize Board with a position from below. Compare results with https://www.chessprogramming.org/Perft_Results
       Position 1: rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1
       Position 3: 8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - - 0 1
       Position 4: r3k2r/Pppp1ppp/1b3nbN/nP6/BBP1P3/q4N2/Pp1P2PP/R2Q1RK1 w kq - 0 1
       Position 5: rnbq1k1r/pp1Pbppp/2p5/8/2B5/8/PPP1NnPP/RNBQK2R w KQ - 1 8
       Position 6: r4rk1/1pp1qppp/p1np1n2/2b1p1B1/2B1P1b1/P1NP1N2/1PP1QPPP/R4RK1 w - - 0 10
*/
        String position_1 = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        String position_2 = "r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - ";
        String position_3 = "8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - - 0 1 ";
        String position_4 = "r3k2r/Pppp1ppp/1b3nbN/nP6/BBP1P3/q4N2/Pp1P2PP/R2Q1RK1 w kq - 0 1";
        String position_5 = "rnbq1k1r/pp1Pbppp/2p5/8/2B5/8/PPP1NnPP/RNBQK2R w KQ - 1 8  ";
        String position_6 = "r4rk1/1pp1qppp/p1np1n2/2b1p1B1/2B1P1b1/P1NP1N2/1PP1QPPP/R4RK1 w - - 0 10 ";

        Board board = new Board(position_6);

        board.drawBitboard();
        int depth = 9;
        for (int i=1; i<=depth; i++) {
            long startTime = System.currentTimeMillis();
            int perftCount = Perft.divide(i, board, whiteTurn, new Move("_0,_0"));
            long endTime = System.currentTimeMillis();
            long duration = (endTime - startTime);
            System.out.println("Depth: " + i + " ply    Result: " + perftCount + " positions    Time: " + duration + " milliseconds");
        }
    }
}