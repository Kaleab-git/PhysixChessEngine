package company;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Arrays;

public class Board {
    public LinkedList<Move> history = new LinkedList<Move>();
//    Casteling Rights
//    If castled or king moves, set both rights for that side to false. If rook moves, set corresponding right to false.
    public boolean WKC=true, WQC=true, BKC=false, BQC=false;
//    To reset these rights when unmaking a castling move
    public boolean PrevWKC, PrevWQC, PrevBKC, PrevBQC;
    private ArrayList<Boolean> PrevWKCs = new ArrayList();
    private ArrayList<Boolean> PrevWQCs = new ArrayList();
    private ArrayList<Boolean> PrevBKCs = new ArrayList();
    private ArrayList<Boolean> PrevBQCs = new ArrayList();

    //    Bitboards
    public long WP=0L, WN=0L, WB=0L, WR=0L, WQ=0L, WK=0L, BP=0L, BN=0L, BB=0L, BR=0L, BQ=0L, BK=0L;
    private long PrevWP=0L, PrevWN=0L, PrevWB=0L, PrevWR=0L, PrevWQ=0L, PrevWK=0L, PrevBP=0L, PrevBN=0L, PrevBB=0L, PrevBR=0L, PrevBQ=0L, PrevBK=0L;
    private ArrayList<Long> PrevWPs = new ArrayList();
    private ArrayList<Long> PrevWNs = new ArrayList();
    private ArrayList<Long> PrevWBs = new ArrayList();
    private ArrayList<Long> PrevWRs = new ArrayList();
    private ArrayList<Long> PrevWQs = new ArrayList();
    private ArrayList<Long> PrevWKs = new ArrayList();
    private ArrayList<Long> PrevBPs = new ArrayList();
    private ArrayList<Long> PrevBNs = new ArrayList();
    private ArrayList<Long> PrevBBs = new ArrayList();
    private ArrayList<Long> PrevBRs = new ArrayList();
    private ArrayList<Long> PrevBQs = new ArrayList();
    private ArrayList<Long> PrevBKs = new ArrayList();

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
    private static String[][] mailbox = {
            {"r","n"," ","q"," ","k"," ","r"},
            {"p","p"," "," ","b","p","p","p"},
            {" "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "},
            {" "," "," ","n"," "," "," "," "},
            {"P","P","P","B","N"," ","P","P"},
            {"R","P"," ","Q","K"," "," ","R"}};

//    private static String[][] mailbox = {
//            {" "," "," "," "," "," "," "," "},
//            {"p"," "," "," "," "," "," "," "},
//            {" "," "," "," "," "," "," "," "},
//            {" "," "," "," "," "," "," "," "},
//            {" "," "," "," "," "," "," "," "},
//            {" "," "," "," "," "," "," "," "},
//            {"P"," "," "," "," "," "," "," "},
//            {" "," "," "," "," "," "," "," "},};


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

    public Board(String fenString) {
//        Does not support En passant yet
        WP=0; WN=0; WB=0;
        WR=0; WQ=0; WK=0;
        BP=0; BN=0; BB=0;
        BR=0; BQ=0; BK=0;
        WKC=false; WQC=false;
        BKC=false; BQC=false;
        int charIndex = 0;
        int boardIndex = 0;
        while (fenString.charAt(charIndex) != ' ')
        {
            switch (fenString.charAt(charIndex++))
            {
                case 'P': WP |= (1L << boardIndex++);
                    break;
                case 'p': BP |= (1L << boardIndex++);
                    break;
                case 'N': WN |= (1L << boardIndex++);
                    break;
                case 'n': BN |= (1L << boardIndex++);
                    break;
                case 'B': WB |= (1L << boardIndex++);
                    break;
                case 'b': BB |= (1L << boardIndex++);
                    break;
                case 'R': WR |= (1L << boardIndex++);
                    break;
                case 'r': BR |= (1L << boardIndex++);
                    break;
                case 'Q': WQ |= (1L << boardIndex++);
                    break;
                case 'q': BQ |= (1L << boardIndex++);
                    break;
                case 'K': WK |= (1L << boardIndex++);
                    break;
                case 'k': BK |= (1L << boardIndex++);
                    break;
                case '/':
                    break;
                case '1': boardIndex++;
                    break;
                case '2': boardIndex += 2;
                    break;
                case '3': boardIndex += 3;
                    break;
                case '4': boardIndex += 4;
                    break;
                case '5': boardIndex += 5;
                    break;
                case '6': boardIndex += 6;
                    break;
                case '7': boardIndex += 7;
                    break;
                case '8': boardIndex += 8;
                    break;
                default:
                    break;
            }
            this.updateMailbox();
            this.updateBoard();
        }
//        This variable is not going to be used. turn is a private variable of the game class and board does not have acess to it
        Main.whiteTurn = (fenString.charAt(++charIndex) == 'w');
        charIndex += 2;
        while (fenString.charAt(charIndex) != ' ')
        {
            switch (fenString.charAt(charIndex++))
            {
                case '-':
                    break;
                case 'K': WKC = true;
                    break;
                case 'Q': WQC = true;
                    break;
                case 'k': BKC = true;
                    break;
                case 'q': BQC = true;
                    break;
                default:
                    break;
            }
        }
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
    public void makeMove(Move move) {
        PrevWPs.add(WP);
        PrevWNs.add(WN);
        PrevWBs.add(WB);
        PrevWRs.add(WR);
        PrevWQs.add(WQ);
        PrevWKs.add(WK);
        PrevBPs.add(BP);
        PrevBNs.add(BN);
        PrevBBs.add(BB);
        PrevBRs.add(BR);
        PrevBQs.add(BQ);
        PrevBKs.add(BK);
        PrevWKCs.add(WKC);
        PrevWQCs.add(WQC);
        PrevBKCs.add(BKC);
        PrevBQCs.add(BQC);
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

        if (move.type.equals("En Passant")) {
//            because we've moved the piece from start -> destination, whichever piece is at the destination index, is the piece that made the move
            if (((WP>>>move.destinationIndex)&1)==1) {
                BP &= ~((long) Math.pow(2, move.enPassantCaptureSquare));
            }
            else {
                WP &= ~((long) Math.pow(2, move.enPassantCaptureSquare));
            }
        }
        else if (move.type.equals("Castling")) {
            switch (move.moveNotation) {
//                King side castle for white so we move rook at h1 -> f1. Although this function is slightly recursive, there shouldn't be a problem
//                since the next call is guarenteed to make a regular move
                case "e1,g1":
                    WR &= ~(-9223372036854775808L);
                    WR |= (long) Math.pow(2, 61);
                    this.WKC = false;
                    break;
                case "e1,c1":
                    WR &= ~((long) Math.pow(2, 56));
                    WR |= (long) Math.pow(2, 59);
                    this.WQC = false;
                    break;
                case "e8,g8":
                    BR &= ~((long) Math.pow(2, 7));
                    BR |= (long) Math.pow(2, 5);
                    this.BQC = false;
                    this.BKC = false;
                    break;
                case "e8,c8":
                    BR &= ~((long) Math.pow(2, 0));
                    BR |= (long) Math.pow(2, 3);
                    this.BQC = false;
                    this.BKC = false;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + move.moveNotation);
            }
        }
        else if (move.type.equals("Promotion")) {
            if (((WP>>>move.destinationIndex)&1)==1) {
                WP &= ~((long) Math.pow(2, move.destinationIndex));
            }
            else {
                BP &= ~((long) Math.pow(2, move.destinationIndex));
            }
            switch (move.promoteToPiece) {
                case 'Q':
                    if (move.destinationIndex < 8) {
                        WQ |= ((long) Math.pow(2, move.destinationIndex));
                    }
                    else {
                        BQ |= ((long) Math.pow(2, move.destinationIndex));
                    }
                    break;
                case 'R':
                    if (move.destinationIndex < 8) {
                        WR |= ((long) Math.pow(2, move.destinationIndex));
                    }
                    else {
                        BR |= ((long) Math.pow(2, move.destinationIndex));
                    }
                    break;
                case 'B':
                    if (move.destinationIndex < 8) {
                        WB |= ((long) Math.pow(2, move.destinationIndex));
                    }
                    else {
                        BB |= ((long) Math.pow(2, move.destinationIndex));
                    }
                    break;
                case 'N':
                    if (move.destinationIndex < 8) {
                        WN |= ((long) Math.pow(2, move.destinationIndex));
                    }
                    else {
                        BN |= ((long) Math.pow(2, move.destinationIndex));
                    }
                    break;
            }
        }

// If king has moved, turn off both castling rights for that side
        if (PrevWKs.get(PrevWKs.size()-1) != WK) {
            WKC = false;
            WQC = false;
        }
        if (PrevBKs.get(PrevBKs.size()-1) != BK) {
            BKC = false;
            BQC = false;
        }
// If rook has moved or been captured, turn off cooresponding castling rights. Also we're doing "&=" because once the values become false, we want that to be permanent
        WKC &= ((WR>>63)&1)==1;
        WQC &= ((WR>>56)&1)==1;
        BKC &= ((BR>>7)&1)==1;
        BQC &= ((BR>>0)&1)==1;

        if (history.size() > 50) {
            history.removeFirst();
        }
        history.add(move);
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
        WP = PrevWPs.remove(PrevWPs.size()-1);
        WN = PrevWNs.remove(PrevWNs.size()-1);
        WB = PrevWBs.remove(PrevWBs.size()-1);
        WR = PrevWRs.remove(PrevWRs.size()-1);
        WQ = PrevWQs.remove(PrevWQs.size()-1);
        WK = PrevWKs.remove(PrevWKs.size()-1);
        BP = PrevBPs.remove(PrevBPs.size()-1);
        BN = PrevBNs.remove(PrevBNs.size()-1);
        BB = PrevBBs.remove(PrevBBs.size()-1);
        BR = PrevBRs.remove(PrevBRs.size()-1);
        BQ = PrevBQs.remove(PrevBQs.size()-1);
        BK = PrevBKs.remove(PrevBKs.size()-1);
        WKC = PrevWKCs.remove(PrevWKCs.size()-1);
        WQC = PrevWQCs.remove(PrevWQCs.size()-1);
        BKC = PrevBKCs.remove(PrevBKCs.size()-1);
        BQC = PrevBQCs.remove(PrevBQCs.size()-1);
        this.updateBoard();
        history.remove(history.size()-1);
    }
}
