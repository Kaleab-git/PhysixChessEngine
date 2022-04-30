package company;

public class Moves {
    static long FILE_A=72340172838076673L;
    static long FILE_H=-9187201950435737472L;
    static long FILE_AB=217020518514230019L;
    static long FILE_GH=-4557430888798830400L;
    static long RANK_1=-72057594037927936L;
    static long RANK_4=1095216660480L;
    static long RANK_5=4278190080L;
    static long RANK_8=255L;
    static long CENTRE=103481868288L;
    static long EXTENDED_CENTRE=66229406269440L;
    static long KING_SIDE=-1085102592571150096L;
    static long QUEEN_SIDE=1085102592571150095L;
    static long KING_B7=460039L;
    static long KNIGHT_C6=43234889994L;
    static long NOT_WHITE_PIECES;
    static long BLACK_PIECES;
    static long EMPTY;

    public static String possibleWhiteMoves(String history,long WP,long WN,long WB,long WR,long WQ,long WK,long BP,long BN,long BB,long BR,long BQ,long BK){
        //added BK to avoid illegal capture
        NOT_WHITE_PIECES=~(WP|WN|WB|WR|WQ|WK|BK);
        //omitted BK to avoid illegal capture
        BLACK_PIECES=BP|BN|BB|BR|BQ;
        EMPTY=~(WP|WN|WB|WR|WQ|WK|BP|BN|BB|BR|BQ|BK);
        StringBuilder allMoves = new StringBuilder();
        allMoves.append(possiblePW(WP));
//        allMoves.append(possibleBW);
//        allMoves.append(possibleRW);
//        allMoves.append(possibleQW);
//        allMoves.append(possibleKW);
//        allMoves.append(possibleNW);
        return allMoves.toString();
    }

    public static String possiblePW(long WP) {
        StringBuilder PawnMoves = new StringBuilder();

//      Right Capture
        long RightCapture = (WP>>7)&BLACK_PIECES&~RANK_8&~FILE_A;
        for (int i = Long.numberOfTrailingZeros(RightCapture);i < 64-Long.numberOfLeadingZeros(RightCapture); i++){
            if (((RightCapture>>i) & 1) == 1) {
                PawnMoves.append(convertMoveToString(63-i-7, 63-i));
            }
        }
//      Left Capture
        long leftCapture = (WP>>9)&BLACK_PIECES&~FILE_H&~RANK_8;
        for (int i = Long.numberOfTrailingZeros(leftCapture);i < 64-Long.numberOfLeadingZeros(leftCapture); i++){
            if (((leftCapture>>i) & 1) == 1) {
                PawnMoves.append(convertMoveToString(63-i-9, 63-i));
            }
        }
//      Forward Move
        long forwardMove = (WP>>8)&EMPTY&~RANK_8;
        for (int i = Long.numberOfTrailingZeros(forwardMove);i < 64-Long.numberOfLeadingZeros(forwardMove); i++){
            if (((forwardMove>>i) & 1) == 1) {
                PawnMoves.append(convertMoveToString(63-i-8, 63-i));
            }
        }
//      Double Forward Move
        long doubleForwardMove = (WP>>16)&EMPTY&(EMPTY>>8)&RANK_4;
        for (int i = Long.numberOfTrailingZeros(doubleForwardMove);i < 64-Long.numberOfLeadingZeros(doubleForwardMove); i++){
            if (((doubleForwardMove>>i) & 1) == 1) {
                PawnMoves.append(convertMoveToString(63-i-16, 63-i));
            }
        }
//        Promotion by capturing to the right
        long rightPromotion = (WP>>7)&BLACK_PIECES&RANK_8&~FILE_A;
        System.out.println("" + (WP>>7) + " " + BLACK_PIECES + " " + RANK_8 + " " + ~FILE_A);
        System.out.println(BLACK_PIECES&RANK_8);
        for (int i = Long.numberOfTrailingZeros(rightPromotion);i < 64-Long.numberOfLeadingZeros(rightPromotion); i++){
            if (((rightPromotion>>i) & 1) == 1) {
                PawnMoves.append(convertMoveToString(63-i-7, 63-i));
            }
        }
//        Promotion by capturing to the left
        long leftPromotion = (WP>>9)&BLACK_PIECES&RANK_8&~FILE_H;
        for (int i = Long.numberOfTrailingZeros(leftPromotion);i < 64-Long.numberOfLeadingZeros(leftPromotion); i++){
            if (((leftPromotion>>i) & 1) == 1) {
                PawnMoves.append(convertMoveToString(63-i-9, 63-i));
            }
        }
//        Promotion forward
        long forwardPromotion = (WP>>8)&EMPTY&RANK_8;
        for (int i = Long.numberOfTrailingZeros(forwardPromotion);i < 64-Long.numberOfLeadingZeros(forwardPromotion); i++){
            if (((forwardPromotion>>i) & 1) == 1) {
                PawnMoves.append(convertMoveToString(63-i-8, 63-i));
            }
        }
        return PawnMoves.toString();
    }


    public static String convertMoveToString(int start, int end){
        String RANKS = "hgfedcba";
        return "" + RANKS.charAt((start%8)) + (start/8+1) + "->" + RANKS.charAt((end%8)) + (end/8+1) + "\n";
    }
}
