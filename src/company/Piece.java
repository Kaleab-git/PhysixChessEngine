package company;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Piece {
    public String pieceType;

    public abstract void drawBitBoard(Board board);

    public abstract ArrayList getMoves(Board board);

}
