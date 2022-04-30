package company;

import java.util.Arrays;

public class Board {
    public long WP=0L, WN=0L, WB=0L, WR=0L, WQ=0L, WK=0L, BP=0L, BN=0L, BB=0L, BR=0L, BQ=0L, BK=0L;
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
    long NOT_WHITE_PIECES;
    long WHITE_PIECES;
    long BLACK_PIECES;
    long OCCUPIED;
    long EMPTY;
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
    private static String[][] mailbox = {
            {"r","n","b","q","k","b","n","r"},
            {"p","p","p","p","p","p","p","p"},
            {" "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "},
            {"P","P","P","P","P","P","P","P"},
            {"R","N","B","Q","K","B","N","R"}};

//    private static String mailbox[][] = {
//            {"r","n","b","q","k"," ","n","r"},
//            {" "," "," "," "," "," "," "," "},
//            {"B"," "," "," ","N"," "," "," "},
//            {" "," "," ","r"," ","K","b"," "},
//            {"q"," ","B","K","R"," ","N"," "},
//            {" ","R","N"," "," "," "," "," "},
//            {" ","p"," ","p","b","P"," "," "},
//            {"k"," ","B","B","B"," "," "," "}};


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
        this.updateBitboards();
        this.updateBoard();
    }

    public Board() {
        this.updateBitboards();
        this.updateBoard();
    }

    public void updateBitboards() {
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

//        Remove the piece from start position and place it at destination. Remove any piece at the destination
        if (((WP>>move.startIndex)&1)==1) {
            WP &= (~(long) Math.pow(2, move.startIndex));
            WP |= (long) Math.pow(2, move.destinationIndex);
        }
        else {WP &= (~(long) Math.pow(2, move.destinationIndex));}

        if (((WN>>move.startIndex)&1)==1) {
            WN &= (~(long) Math.pow(2, move.startIndex));
            WN |= (long) Math.pow(2, move.destinationIndex);
        }
        else {WN &= (~(long) Math.pow(2, move.destinationIndex));}

        if (((WB>>move.startIndex)&1)==1) {
            WB &= (~(long) Math.pow(2, move.startIndex));
            WB |= (long) Math.pow(2, move.destinationIndex);
        }
        else {WB &= (~(long) Math.pow(2, move.destinationIndex));}

        if (((WR>>move.startIndex)&1)==1) {
            WR &= (~(long) Math.pow(2, move.startIndex));
            WR |= (long) Math.pow(2, move.destinationIndex);
        }
        else {WR &= (~(long) Math.pow(2, move.destinationIndex));}

        if (((WQ>>move.startIndex)&1)==1) {
            WQ &= (~(long) Math.pow(2, move.startIndex));
            WQ |= (long) Math.pow(2, move.destinationIndex);
        }
        else {WQ &= (~(long) Math.pow(2, move.destinationIndex));}

        if (((WK>>move.startIndex)&1)==1) {
            WK &= (~(long) Math.pow(2, move.startIndex));
            WK |= (long) Math.pow(2, move.destinationIndex);
        }
        else {WK &= (~(long) Math.pow(2, move.destinationIndex));}

        if (((BP>>move.startIndex)&1)==1) {
            BP &= (~(long) Math.pow(2, move.startIndex));
            BP |= (long) Math.pow(2, move.destinationIndex);
        }
        else {BP &= (~(long) Math.pow(2, move.destinationIndex));}

        if (((BN>>move.startIndex)&1)==1) {
            BN &= (~(long) Math.pow(2, move.startIndex));
            BN |= (long) Math.pow(2, move.destinationIndex);
        }
        else {BN &= (~(long) Math.pow(2, move.destinationIndex));}

        if (((BB>>move.startIndex)&1)==1) {
            BB &= (~(long) Math.pow(2, move.startIndex));
            BB |= (long) Math.pow(2, move.destinationIndex);
        }
        else {BB &= (~(long) Math.pow(2, move.destinationIndex));}

        if (((BR>>move.startIndex)&1)==1) {
            BR &= (~(long) Math.pow(2, move.startIndex));
            BR |= (long) Math.pow(2, move.destinationIndex);
        }
        else {BR &= (~(long) Math.pow(2, move.destinationIndex));}

        if (((BQ>>move.startIndex)&1)==1) {
            BQ &= (~(long) Math.pow(2, move.startIndex));
            BQ |= (long) Math.pow(2, move.destinationIndex);
        }
        else {BQ &= (~(long) Math.pow(2, move.destinationIndex));}

        if (((BK>>move.startIndex)&1)==1) {
            BK &= (~(long) Math.pow(2, move.startIndex));
            BK |= (long) Math.pow(2, move.destinationIndex);
        }
        else {BK &= (~(long) Math.pow(2, move.destinationIndex));}

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
        //added BK to avoid illegal capture
        NOT_WHITE_PIECES =~ (WP|WN|WB|WR|WQ|WK|BK);
        //omitted BK to avoid illegal capture
        BLACK_PIECES = (BP|BN|BB|BR|BQ|WK|BK);
        OCCUPIED = (WP|WN|WB|WR|WQ|WK|BP|BN|BB|BR|BQ|BK);
        EMPTY =~ OCCUPIED;
        WHITE_PIECES = (WP|WN|WB|WR|WQ|WK|BK);
    }
}
