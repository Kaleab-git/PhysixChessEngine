package company;

        import javax.swing.plaf.synth.SynthTextAreaUI;
        import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
//        TODO: Change how you iterate through bits from trailing -> leading to just jumping to where we have 1s.
//        TODO: Create an iterator class for iterating through bits in the most optimal way
//        TODO: Measure the time difference between normal iteration and optimal iteration
//        FIXME: The way I chose to implement unsafe cells for the king can use a little improvement
/*        FIXME: change bitboards name to something like attacksBitboard. Also change the parameter inBitboard.
                 It's very misleading. You're not gettings the moves in bitboard. You're gettings attacks         */
        Game game = new Game();
        game.play();
    }
}