package company;

import java.util.LinkedList;
import java.util.Arrays;

public class Board {
    public LinkedList<Move> history = new LinkedList<Move>();
    public long WP=0L, WN=0L, WB=0L, WR=0L, WQ=0L, WK=0L, BP=0L, BN=0L, BB=0L, BR=0L, BQ=0L, BK=0L;
    private long PrevWP=0L, PrevWN=0L, PrevWB=0L, PrevWR=0L, PrevWQ=0L, PrevWK=0L, PrevBP=0L, PrevBN=0L, PrevBB=0L, PrevBR=0L, PrevBQ=0L, PrevBK=0L;
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
    static long KING_SPAN=460039L;
    static long KNIGHT_SPAN=43234889994L;
    long WHITE_PIECES = 0L;
    long BLACK_PIECES = 0L;
    long OCCUPIED = 0L;
    long EMPTY =~ OCCUPIED;

    static long[] RankMasks8 =/*from rank1 to rank8*/
            {
                    0xFFL, 0xFF00L, 0xFF0000L, 0xFF000000L, 0xFF00000000L, 0xFF0000000000L, 0xFF000000000000L, 0xFF00000000000000L
            };
    static long[] FileMasks8 =/*from fileA to FileH*/
            {
                    0x101010101010101L, 0x202020202020202L, 0x404040404040404L, 0x808080808080808L,
                    0x1010101010101010L, 0x2020202020202020L, 0x4040404040404040L, 0x8080808080808080L
            };
    static long[] DiagonalMasks8 =/*from top left to bottom right*/
            {
                    0x1L, 0x102L, 0x10204L, 0x1020408L, 0x102040810L, 0x10204081020L, 0x1020408102040L,
                    0x102040810204080L, 0x204081020408000L, 0x408102040800000L, 0x810204080000000L,
                    0x1020408000000000L, 0x2040800000000000L, 0x4080000000000000L, 0x8000000000000000L
            };
    static long[] AntiDiagonalMasks8 =/*from top right to bottom left*/
            {
                    0x80L, 0x8040L, 0x804020L, 0x80402010L, 0x8040201008L, 0x804020100804L, 0x80402010080402L,
                    0x8040201008040201L, 0x4020100804020100L, 0x2010080402010000L, 0x1008040201000000L,
                    0x804020100000000L, 0x402010000000000L, 0x201000000000000L, 0x100000000000000L
            };
//    private static String[][] mailbox = {
//            {"r","n","b","q","k","b","n","r"},
//            {"p","p","p","p","p","p","p","p"},
//            {" "," "," "," "," "," "," "," "},
//            {" "," "," "," "," "," "," "," "},
//            {" "," "," "," "," "," "," "," "},
//            {" "," "," "," "," "," "," "," "},
//            {"P","P","P","P","P","P","P","P"},
//            {"R","N","B","Q","K","B","N","R"}};

    private static String[][] mailbox = {
            {" "," "," "," "," "," "," ","q"},
            {" "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "},
            {" ","N"," "," "," "," "," "," "},
            {"K"," "," "," "," "," "," "," "}};


    public Board(long WP, long WN, long WB, long WR, long WQ, long WK, long BP, long BN, long BB, long BR, long BQ, long BK) {
        this.WP = WP;
        this.WN = WN;
        this.WB = WB;
        this.WR = WR;
        this.WQ = WQ;
        this.WK = WK;
        this.BP = BP;
        this.BN = BN;
        this.BB = BB;
        this.BR = BR;
        this.BQ = BQ;
        this.BK = BK;

        this.updateMailbox();
        this.updateBoard();
    }

    public Board() {
        this.updateBitboards();
        this.updateBoard();
    }

    public void
    updateBitboards() {
        String binary;
        WP=0L; WN=0L; WB=0L; WR=0L; WQ=0L; WK=0L; BP=0L; BN=0L; BB=0L; BR=0L; BQ=0L; BK=0L;
        for (int i=0; i<64; i++){
            binary = "0000000000000000000000000000000000000000000000000000000000000000";
//          Flip the ith bit starting from right to left so as to have the LSB represent the top left corner
            binary = binary.substring(i+1) + "1" + binary.substring(0, i);
            switch (mailbox[i / 8][i % 8]) {
                case "P" -> WP += convertStringToBitboard(binary);
                case "N" -> WN += convertStringToBitboard(binary);
                case "B" -> WB += convertStringToBitboard(binary);
                case "R" -> WR += convertStringToBitboard(binary);
                case "Q" -> WQ += convertStringToBitboard(binary);
                case "K" -> WK += convertStringToBitboard(binary);
                case "p" -> BP += convertStringToBitboard(binary);
                case "n" -> BN += convertStringToBitboard(binary);
                case "b" -> BB += convertStringToBitboard(binary);
                case "r" -> BR += convertStringToBitboard(binary);
                case "q" -> BQ += convertStringToBitboard(binary);
                case "k" -> BK += convertStringToBitboard(binary);
            }
        }
    }
    private static long convertStringToBitboard(String Binary) {
        if (Binary.charAt(0)=='0') {
            return Long.parseLong(Binary, 2);
        } else {
            return Long.parseLong("1"+Binary.substring(2), 2)*2;
        }
    }
    public static void drawMailbox() {
        for (int i=0;i<8;i++) {
            System.out.println(""+ (8-i) + "  " + Arrays.toString(mailbox[i]));
        }
        System.out.println("    a  b  c  d  e  f  g  h \n");
    }
    public void updateMailbox() {
        for (int i=0;i<64;i++) {
            if (((WP>>i)&1)==1) {mailbox[i/8][i%8]="P";}
            else if (((WN>>i)&1)==1) {mailbox[i/8][i%8]="N";}
            else if (((WB>>i)&1)==1) {mailbox[i/8][i%8]="B";}
            else if (((WR>>i)&1)==1) {mailbox[i/8][i%8]="R";}
            else if (((WQ>>i)&1)==1) {mailbox[i/8][i%8]="Q";}
            else if (((WK>>i)&1)==1) {mailbox[i/8][i%8]="K";}
            else if (((BP>>i)&1)==1) {mailbox[i/8][i%8]="p";}
            else if (((BN>>i)&1)==1) {mailbox[i/8][i%8]="n";}
            else if (((BB>>i)&1)==1) {mailbox[i/8][i%8]="b";}
            else if (((BR>>i)&1)==1) {mailbox[i/8][i%8]="r";}
            else if (((BQ>>i)&1)==1) {mailbox[i/8][i%8]="q";}
            else if (((BK>>i)&1)==1) {mailbox[i/8][i%8]="k";}
            else {mailbox[i/8][i%8]=" ";}
        }
    }
    public void movePiece(Move move) {
        PrevWP = WP;
        PrevWN = WN;
        PrevWB = WB;
        PrevWR = WR;
        PrevWQ = WQ;
        PrevWK = WK;
        PrevBP = BP;
        PrevBN = BN;
        PrevBB = BB;
        PrevBR = BR;
        PrevBQ = BQ;
        PrevBK = BK;

        if (history.size() > 50) {
            history.removeFirst();
        }
        history.add(move);

//        Remove the piece from start position and place it at destination. Remove any piece at the destination
        long startNumber;
        long destinationNumber;

        if (move.startIndex < 63) {
            startNumber = (~(long) Math.pow(2, move.startIndex));
        }
        else {
            startNumber = (9223372036854775807L);
        }
        if (move.destinationIndex < 63) {
            destinationNumber = (long) Math.pow(2, move.destinationIndex);
        }
        else {
            destinationNumber = -9223372036854775808L;
        }

        if (((WP>>>move.startIndex)&1)==1) {
            WP &= startNumber;
            WP |= destinationNumber;
        }
        else {WP &= (~destinationNumber);}

        if (((WN>>>move.startIndex)&1)==1) {
            WN &= startNumber;
            WN |= destinationNumber;
        }
        else {WN &= (~destinationNumber);}

        if (((WB>>>move.startIndex)&1)==1) {
            WB &= startNumber;
            WB |= destinationNumber;
        }
        else {WB &= (~destinationNumber);}

        if (((WR>>>move.startIndex)&1)==1) {
            WR &= startNumber;
            WR |= destinationNumber;
        }
        else {WR &= (~destinationNumber);}

        if (((WQ>>>move.startIndex)&1)==1) {
            WQ &= startNumber;
            WQ |= destinationNumber;
        }
        else {WQ &= (~destinationNumber);}

        if (((WK>>>move.startIndex)&1)==1) {
            WK &= startNumber;
            WK |= destinationNumber;
        }
        else {WK &= (~destinationNumber);}

        if (((BP>>>move.startIndex)&1)==1) {
            BP &= startNumber;
            BP |= destinationNumber;
        }
        else {BP &= (~destinationNumber);}

        if (((BN>>>move.startIndex)&1)==1) {
            BN &= startNumber;
            BN |= destinationNumber;
        }
        else {BN &= (~destinationNumber);}

        if (((BB>>>move.startIndex)&1)==1) {
            BB &= startNumber;
            BB |= destinationNumber;
        }
        else {BB &= (~destinationNumber);}

        if (((BR>>>move.startIndex)&1)==1) {
            BR &= startNumber;
            BR |= destinationNumber;
        }
        else {BR &= (~destinationNumber);}

        if (((BQ>>>move.startIndex)&1)==1) {
            BQ &= startNumber;
            BQ |= destinationNumber;
        }
        else {BQ &= (~destinationNumber);}

        if (((BK>>>move.startIndex)&1)==1) {
            BK &= startNumber;
            BK |= destinationNumber;
        }
        else {BK &= (~destinationNumber);}

        updateBoard();
    }
    public void drawBitboard() {
        String[][] chessBoard = new String[8][8];
        for (int i=0;i<64;i++) {
            chessBoard[i/8][i%8]=" ";
        }
        for (int i=0;i<64;i++) {
            if (((WP>>i)&1)==1) {chessBoard[i/8][i%8]="P";}
            if (((WN>>i)&1)==1) {chessBoard[i/8][i%8]="N";}
            if (((WB>>i)&1)==1) {chessBoard[i/8][i%8]="B";}
            if (((WR>>i)&1)==1) {chessBoard[i/8][i%8]="R";}
            if (((WQ>>i)&1)==1) {chessBoard[i/8][i%8]="Q";}
            if (((WK>>i)&1)==1) {chessBoard[i/8][i%8]="K";}
            if (((BP>>i)&1)==1) {chessBoard[i/8][i%8]="p";}
            if (((BN>>i)&1)==1) {chessBoard[i/8][i%8]="n";}
            if (((BB>>i)&1)==1) {chessBoard[i/8][i%8]="b";}
            if (((BR>>i)&1)==1) {chessBoard[i/8][i%8]="r";}
            if (((BQ>>i)&1)==1) {chessBoard[i/8][i%8]="q";}
            if (((BK>>i)&1)==1) {chessBoard[i/8][i%8]="k";}
        }
        for (int i=0;i<8;i++) {
            System.out.println(""+ (8-i) + "  " + Arrays.toString(chessBoard[i]));
        }
        System.out.println("    a  b  c  d  e  f  g  h \n");
    }
    public static void drawFromBitboard(long board){
        String[][] bitboardArray =  new String[8][8];
        for (int i=0; i<64; i++){
            if (((board>>i)&1) == 1){
                bitboardArray[i/8][i%8] = "X";
            }
            else{
                bitboardArray[i/8][i%8] = " ";
            }
        }
        for (int i=0; i<8; i++) {
            System.out.println(""+ (8-i) + "  " + Arrays.toString(bitboardArray[i]));
        }
        System.out.println("    a  b  c  d  e  f  g  h \n");
    }

    private void updateBoard() {
        //added enemy king to avoid illegal capture
        WHITE_PIECES = (WP|WN|WB|WR|WQ|WK);
        BLACK_PIECES = (BP|BN|BB|BR|BQ|BK);
        OCCUPIED = (WP|WN|WB|WR|WQ|WK|BP|BN|BB|BR|BQ|BK);
        EMPTY =~ OCCUPIED;
    }

    public void unmakeMove() {
        WP = PrevWP;
        WN = PrevWN;
        WB = PrevWB;
        WR = PrevWR;
        WQ = PrevWQ;
        WK = PrevWK;
        BP = PrevBP;
        BN = PrevBN;
        BB = PrevBB;
        BR = PrevBR;
        BQ = PrevBQ;
        BK = PrevBK;
        this.updateBoard();
        history.remove(history.size()-1);
    }
}
