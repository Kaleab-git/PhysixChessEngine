package company;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.stream.Stream;


/**
 * Perft Integration Test Suite using Parameterized Tests.
 * Positions and number of moves are taken from https://www.chessprogramming.org/Perft_Results
 */
public class PerftTest {
    private static final String P1_START_FEN     = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    private static final String P2_KIWIPETE_FEN  = "r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 1";
    private static final String P3_ENDGAME_FEN   = "8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - - 0 1";
    private static final String P4_PROMOTION_FEN = "r3k2r/Pppp1ppp/1b3nbN/nP6/BBP1P3/q4N2/Pp1P2PP/R2Q1RK1 w kq - 0 1";
    private static final String P5_COMPLEX_FEN   = "rnbq1k1r/pp1Pbppp/2p5/8/2B5/8/PPP1NnPP/RNBQK2R w KQ - 1 8";
    private static final String P6_MATE_FEN      = "r4rk1/1pp1qppp/p1np1n2/2b1p1B1/2B1P3/2NP1Q2/PPP2PPP/R4RK1 w - - 0 10";

    private static Stream<Arguments> p1StartCases() {
        return Stream.of(
                Arguments.of(P1_START_FEN, 1, 20L),
                Arguments.of(P1_START_FEN, 2, 400L),
                Arguments.of(P1_START_FEN, 3, 8902L),
                Arguments.of(P1_START_FEN, 4, 197281L),
                Arguments.of(P1_START_FEN, 5, 4865609L)
        );
    }

    private static Stream<Arguments> p2KiwipeteCases() {
        return Stream.of(
                Arguments.of(P2_KIWIPETE_FEN, 1, 48L),
                Arguments.of(P2_KIWIPETE_FEN, 2, 2039L),
                Arguments.of(P2_KIWIPETE_FEN, 3, 97862L),
                Arguments.of(P2_KIWIPETE_FEN, 4, 4085603L),
                Arguments.of(P2_KIWIPETE_FEN, 5, 193690690L)
        );
    }

//      P3: Endgame Aspiration Cases
    private static Stream<Arguments> p3EndgameCases() {
        return Stream.of(
                Arguments.of(P3_ENDGAME_FEN, 1, 14L),
                Arguments.of(P3_ENDGAME_FEN, 2, 191L),
                Arguments.of(P3_ENDGAME_FEN, 3, 2812L),
                Arguments.of(P3_ENDGAME_FEN, 4, 43238L),
                Arguments.of(P3_ENDGAME_FEN, 5, 674624L)
        );
    }

//      P4: Promotion/Castling Cases
    private static Stream<Arguments> p4PromotionCases() {
        return Stream.of(
                Arguments.of(P4_PROMOTION_FEN, 1, 6L),
                Arguments.of(P4_PROMOTION_FEN, 2, 264L),
                Arguments.of(P4_PROMOTION_FEN, 3, 9467L),
                Arguments.of(P4_PROMOTION_FEN, 4, 422333L),
                Arguments.of(P4_PROMOTION_FEN, 5, 15833292L)
        );
    }

//      P5: Complex Check/Capture Cases.
    private static Stream<Arguments> p5ComplexCases() {
        return Stream.of(
                Arguments.of(P5_COMPLEX_FEN, 1, 44L),
                Arguments.of(P5_COMPLEX_FEN, 2, 1486L),
                Arguments.of(P5_COMPLEX_FEN, 3, 62379L),
                Arguments.of(P5_COMPLEX_FEN, 4, 2103487L),
                Arguments.of(P5_COMPLEX_FEN, 5, 89941194L)
        );
    }

//     P6: Mate Threat Cases. */
    private static Stream<Arguments> p6MateCases() {
        return Stream.of(
                Arguments.of(P6_MATE_FEN, 1, 46L),
                Arguments.of(P6_MATE_FEN, 2, 2079L),
                Arguments.of(P6_MATE_FEN, 3, 89890L),
                Arguments.of(P6_MATE_FEN, 4, 3894594L),
                Arguments.of(P6_MATE_FEN, 5, 164075551L)
        );
    }

    private static Stream<Arguments> allPerftCases() {
        return Stream.of(
                p1StartCases(),
                p2KiwipeteCases(),
                p3EndgameCases(),
                p4PromotionCases(),
                p5ComplexCases(),
                p6MateCases()
        ).flatMap(s -> s);
    }


    /**
     * The Perft integration test.
     * @param depth The maximum search depth.
     * @param expectedNodes The pre-calculated, verified node count (long).
     */
    @ParameterizedTest(name = "P{index}: D{1}, FEN: {0}")
    @MethodSource("allPerftCases")
    void testPerftResults(String fen, int depth, long expectedNodes) {

        System.out.printf("Running Perft: D%d, FEN: %s%n", depth, fen);
        Board board = new Board(fen);

        long actualNodes = Perft.divide(depth, board, true, new Move("_0,_0"));

        assertEquals(expectedNodes, actualNodes,
                String.format("Perft failed for FEN %s at depth %d.\nExpected: %d, Actual: %d",
                        fen, depth, expectedNodes, actualNodes));
    }
}