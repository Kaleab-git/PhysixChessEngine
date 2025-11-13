package company;

public class Main {
    public static boolean whiteTurn = true;

    public static void main(String[] args) {
//        -------------------- 2025 Look back ------------------------------
//        FIXME: (2025): A good amount of refactoring. Especially Board class.
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
//
        Game game = new Game();
        game.play();
    }
}