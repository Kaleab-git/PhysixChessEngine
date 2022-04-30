package company;

import java.util.*;

public class BoardInitialization {
    public static void initializeBoard(){
        long WhitePawn=0L, WhiteKnight=0L, WhiteBishop=0L, WhiteRook=0L, WhiteQueen=0L, WhiteKing=0L;
        long BlackPawn=0L, BlackKnight=0L, BlackBishop=0L, BlackRook=0L, BlackQueen=0L, BlackKing=0L;

        String mailbox[][] = {
                {"r","n","b","q","k","b","n","r"},
                {"p","p","p","p","p","p","p","p"},
                {" "," "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "," "},
                {"P","P","P","P","P","P","P","P"},
                {"R","N","B","Q","K","B","N","R"}};
        mailboxToBitboards(mailbox, WhitePawn, WhiteKnight, WhiteBishop, WhiteRook, WhiteQueen, WhiteKing, BlackPawn, BlackKnight, BlackBishop, BlackRook, BlackQueen, BlackKing);
    }

    public static void mailboxToBitboards(String[][] mailbox, long WP, long WN, long WB, long WR, long WQ, long WK, long BP, long BN, long BB, long BR, long BQ, long BK) {
        String binary;
        for (int i=0; i<64; i++){
            binary = "0000000000000000000000000000000000000000000000000000000000000000";
//          Flip the ith bit starting from right to left so as to have the LSB represent the top left corner
            binary = binary.substring(i+1) + "1" + binary.substring(0, i);
            switch (mailbox[i/8][i%8]){
                case "P": WP+=convertStringToBitboard(binary);
                    break;
                case "N": WN+=convertStringToBitboard(binary);
                    break;
                case "B": WB+=convertStringToBitboard(binary);
                    break;
                case "R": WR+=convertStringToBitboard(binary);
                    break;
                case "Q": WQ+=convertStringToBitboard(binary);
                    break;
                case "K": WK+=convertStringToBitboard(binary);
                    break;
                case "p": BP+=convertStringToBitboard(binary);
                    break;
                case "n": BN+=convertStringToBitboard(binary);
                    break;
                case "b": BB+=convertStringToBitboard(binary);
                    break;
                case "r": BR+=convertStringToBitboard(binary);
                    break;
                case "q": BQ+=convertStringToBitboard(binary);
                    break;
                case "k": BK+=convertStringToBitboard(binary);
                    break;
            }
        }
        drawArray(WP,WN,WB,WR,WQ,WK,BP,BN,BB,BR,BQ,BK);
    }

    public static long convertStringToBitboard(String Binary) {
        if (Binary.charAt(0)=='0') {
            return Long.parseLong(Binary, 2);
        } else {
            return Long.parseLong("1"+Binary.substring(2), 2)*2;
        }
    }

    public static void drawArray(long WP,long WN,long WB,long WR,long WQ,long WK,long BP,long BN,long BB,long BR,long BQ,long BK) {
        String chessBoard[][]=new String[8][8];
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
            System.out.println(Arrays.toString(chessBoard[i]));
        }
    }

}
