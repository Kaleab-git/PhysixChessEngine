package company;

import javax.sound.midi.Soundbank;
import java.sql.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;

public class Pawn {
//    First 64 lenth array is attacksTo by white pawns. Second array is attacksTo by black pawns
    public static long[][] attacksTo = new long[2][64];

    public static void loadTable(Board board) {
        long pawnPosition;
//        White pawn attack table
        for (int i=0; i<64; i++) {
            pawnPosition = (long) Math.pow(2, i);
            if (i == 63) {
                pawnPosition = (-9223372036854775808L);
            }
            attacksTo[0][i] = ((pawnPosition>>>7&~Board.FILE_A) | (pawnPosition>>>9&~Board.FILE_H));
        }
//        Black pawns attack table
        for (int i=0; i<64; i++) {
            pawnPosition = (long) Math.pow(2, i);
            if (i == 63) {
                pawnPosition = -9223372036854775808L;
            }
            attacksTo[1][i] = ((pawnPosition<<9&~Board.FILE_A) | (pawnPosition<<7&~Board.FILE_H));
        }
    }

    public static ArrayList getMoves(Board board, boolean inBitboard, boolean isWhite) {
//        TODO: Initialize generic variables early on like enemyPiece, enemyKing, myPiece, myKing, promotionRank, to avoid using '?' operator every 5 lines
/*        TODO: Instead of initializing different variables like forwardMove, rightCapture, leftCapture, doubleForwardMove that you assign values to, use it rigth after,
                but never look back to. Initialize one generic variables (pawnMoves for ex) and then reuse that variable for all the possible moves Because getMoves function
                is going to be called a lot of times. And I'm not sure about how Garbage collection works in Java, or how much of an overhead it is.
        long pawnMovesBitboard = 0L;
        long myPieces = isWhite ? board.WHITE_PIECES:board.BLACK_PIECES;
        long enemyPieces = isWhite ? board.BLACK_PIECES:board.WHITE_PIECES;
        long myKing = isWhite ? board.WK:board.BK;
        long promotionRank = isWhite ? Board.RANK_8:Board.RANK_1;

* */

//        initializing bitboard to enemy kings position so as to consider even enemy kings position as a possible attack square
        long bitboard = 0L;
        ArrayList<Move> pawnMoves = new ArrayList();

//      Forward Move
        long forwardMove = isWhite ? ((board.WP>>>8)&board.EMPTY&~Board.RANK_8) : ((board.BP<<8)&board.EMPTY&~Board.RANK_1);
        for (int i = Long.numberOfTrailingZeros(forwardMove);i < 64-Long.numberOfLeadingZeros(forwardMove); i++){
            if (((forwardMove>>i) & 1) == 1) {
                Move move = isWhite ? new Move(i+8, i) : new Move(i-8, i);
                pawnMoves.add(move);
            }
        }
//      Right Capture
        long rightCapture = isWhite ? ((board.WP>>>7)&board.BLACK_PIECES&~Board.RANK_8&~Board.FILE_A) : ((board.BP<<9)&board.WHITE_PIECES&~Board.RANK_1&~Board.FILE_A);
        bitboard |= isWhite ? ((board.WP>>>7)&~Board.RANK_8&~Board.FILE_A) : ((board.BP<<9)&~Board.RANK_1&~Board.FILE_A);
//      Remove enemy king to avoid king capture. But after we've included all positions (even king's to bitboard)
        rightCapture = isWhite ? rightCapture&~board.BK : rightCapture&~board.WK;
        for (int i = Long.numberOfTrailingZeros(rightCapture);i < 64-Long.numberOfLeadingZeros(rightCapture); i++){
            if (((rightCapture>>i) & 1) == 1) {
                Move move = isWhite ? new Move(i+7, i) : new Move(i-9, i);
                pawnMoves.add(move);
            }
        }
//      Left Capture
        long leftCapture = isWhite ? ((board.WP>>>9)&board.BLACK_PIECES&~Board.FILE_H&~Board.RANK_8) : ((board.BP<<7)&board.WHITE_PIECES&~Board.RANK_1&~Board.FILE_H);
        bitboard |= isWhite ? ((board.WP>>>9)&~Board.FILE_H&~Board.RANK_8) : ((board.BP<<7)&~Board.RANK_1&~Board.FILE_H);
//      Remove enemy king to avoid king capture. But after we've included all positions (even king's to bitboard)
        leftCapture = isWhite ? leftCapture&~board.BK : leftCapture&~board.WK;
        for (int i = Long.numberOfTrailingZeros(leftCapture);i < 64-Long.numberOfLeadingZeros(leftCapture); i++){
            if (((leftCapture>>i) & 1) == 1) {
                Move move = isWhite ? new Move(i+9, i) : new Move(i-7, i);
                pawnMoves.add(move);
            }
        }
//      Double Forward Move
        long doubleForwardMove = isWhite ? ((board.WP>>>16)&board.EMPTY&(board.EMPTY>>8)&Board.RANK_4) : ((board.BP<<16)&board.EMPTY&(board.EMPTY<<8)&Board.RANK_5);
        for (int i = Long.numberOfTrailingZeros(doubleForwardMove);i < 64-Long.numberOfLeadingZeros(doubleForwardMove); i++){
            if (((doubleForwardMove>>i) & 1) == 1) {
                Move move = isWhite ? new Move(i+16, i) : new Move(i-16, i);
                pawnMoves.add(move);
            }
        }
//        Promotion forward
        long forwardPromotion = isWhite ? ((board.WP>>>8)&board.EMPTY&Board.RANK_8) : ((board.BP<<8)&board.EMPTY&Board.RANK_1);
        for (int i = Long.numberOfTrailingZeros(forwardPromotion);i < 64-Long.numberOfLeadingZeros(forwardPromotion); i++){
            if (((forwardPromotion>>i) & 1) == 1) {
                Move move = isWhite ? new Move(i+8, i) : new Move(i-8, i);
                move.type = "Promotion";
                move.promoteToPiece = 'Q';
                pawnMoves.add(move);
            }
        }
//        Promotion by capturing to the right
        long rightPromotion = isWhite ? ((board.WP>>>7)&board.BLACK_PIECES&Board.RANK_8&~Board.FILE_A) : (((board.BP<<9)&board.WHITE_PIECES&Board.RANK_1&~Board.FILE_A));
        bitboard |= isWhite ? ((board.WP>>>7)&Board.RANK_8&~Board.FILE_A) : (((board.BP<<9)&Board.RANK_1&~Board.FILE_A));
        rightPromotion = isWhite ? rightPromotion&~board.BK : rightPromotion&~board.WK;
        for (int i = Long.numberOfTrailingZeros(rightPromotion);i < 64-Long.numberOfLeadingZeros(rightPromotion); i++){
            if (((rightPromotion>>i) & 1) == 1) {
                Move move = isWhite ? new Move(i+7, i) : new Move(i-9, i);
                move.type = "Promotion";
                move.promoteToPiece = 'N';
                pawnMoves.add(move);
            }
        }
//        Promotion by capturing to the left
        long leftPromotion = isWhite ? ((board.WP>>>9)&board.BLACK_PIECES&Board.RANK_8&~Board.FILE_H) : ((board.BP<<7)&board.WHITE_PIECES&Board.RANK_1&~Board.FILE_H);
        bitboard |= isWhite ? ((board.WP>>>9)&Board.RANK_8&~Board.FILE_H) : ((board.BP<<7)&Board.RANK_1&~Board.FILE_H);
        leftPromotion = isWhite ? leftPromotion&~board.BK : leftPromotion&~board.WK;
        for (int i = Long.numberOfTrailingZeros(leftPromotion);i < 64-Long.numberOfLeadingZeros(leftPromotion); i++){
            if (((leftPromotion>>i) & 1) == 1) {
                Move move = isWhite ? new Move(i+9, i) : new Move(i-7, i);
                move.type = "Promotion";
                move.promoteToPiece = 'Q';
                pawnMoves.add(move);
            }
        }
//        En Passant
        if (board.history.size() > 0) {
            Move lastMove = board.history.get(board.history.size()-1);
            if (isWhite) {
//           Since I'm white last move must've been black's. Check if last move was 2 square step and if it was made by a pawn
//           &ing board.BP with rank5 isn't necessary. If it was a 2 square move and if it was made by a pawn, we know that piece couldn't be anywhere but rank5
                if (lastMove.destinationIndex - lastMove.startIndex == 16 & (((board.BP&Board.RANK_5)>>lastMove.destinationIndex)&1) == 1) {
                    if (lastMove.moveNotation.charAt(3)!='a' & ((board.WP>>lastMove.destinationIndex-1)&1) == 1) {
                        Move move = new Move(lastMove.destinationIndex-1, lastMove.destinationIndex-8);
                        move.type = "En Passant";
                        move.enPassantCaptureSquare = lastMove.destinationIndex;
                        pawnMoves.add(move);
                    }
                    if (lastMove.moveNotation.charAt(3)!='h' & ((board.WP>>lastMove.destinationIndex+1)&1) == 1) {
                        Move move = new Move(lastMove.destinationIndex+1, lastMove.destinationIndex-8);
                        move.type = "En Passant";
                        move.enPassantCaptureSquare = lastMove.destinationIndex;
                        pawnMoves.add(move);
                    }
                }
            }
            else {
                if (lastMove.startIndex - lastMove.destinationIndex == 16 & (((board.WP&Board.RANK_4)>>lastMove.destinationIndex)&1) == 1) {
                    if (lastMove.moveNotation.charAt(3)!='a' & ((board.BP>>(lastMove.destinationIndex-1))&1) == 1) {
                        Move move = new Move(lastMove.destinationIndex-1, lastMove.destinationIndex+8);
                        move.type = "En Passant";
                        move.enPassantCaptureSquare = lastMove.destinationIndex;
                        pawnMoves.add(move);
                    }
                    if (lastMove.moveNotation.charAt(3)!='h' & ((board.BP>>(lastMove.destinationIndex+1))&1) == 1) {
                        Move move = new Move(lastMove.destinationIndex+1, lastMove.destinationIndex+8);
                        move.type = "En Passant";
                        move.enPassantCaptureSquare = lastMove.destinationIndex;
                        pawnMoves.add(move);
                    }
                }
            }
        }
        if (inBitboard) {
            return new ArrayList(Arrays.asList(bitboard));
        }
        return pawnMoves;
    }
}
