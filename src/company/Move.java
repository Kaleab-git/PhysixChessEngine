package company;

import java.util.Objects;

public class Move {
    public String type = "Regular";
//    relevant if its a promotion move
    public char promoteToPiece;
//    relevant if its an en passant move
    public long enPassantCaptureSquare;
    public String moveNotation;
    public int[] startCell;
    public int[] destinationCell;
    public int startIndex;
    public int destinationIndex;

    public Move(String moveNotation) {
        this.moveNotation = moveNotation;
        String[] tokens = moveNotation.split(",");
        this.startCell = notationToCoordinate(tokens[0]);
        this.destinationCell = notationToCoordinate(tokens[1]);
        this.startIndex = coordinateToIndex(startCell);
        this.destinationIndex = coordinateToIndex(destinationCell);
    }

    public Move(int startIndex, int destinationIndex) {
        this.moveNotation = indexToString(startIndex, destinationIndex);
        this.startIndex = startIndex;
        this.destinationIndex = destinationIndex;
    }

    public static int[] notationToCoordinate(String notation) {
        int row = 8 - Integer.parseInt(String.valueOf(notation.charAt(1)));
        int col = (char)notation.charAt(0) - 'a';
        return new int[] {row, col};
    }
    public static int coordinateToIndex(int[] coordinate) {
        int row = coordinate[0];
        int col = coordinate[1];
        return (row*8) + (col%8);
    }
    public static int[] indexToCoordinate(int index) {
        return new int[] {index/8,index%8};
    }

    public static String indexToString(int start, int end){
        String RANKS = "abcdefgh";
        return "" + RANKS.charAt((start%8)) + (8-start/8) + "," + RANKS.charAt((end%8)) + (8-end/8);
    }

    @Override
    public boolean equals(Object o) {
        // Check if it's a self reference (same object in memory)
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Move otherMove = (Move) o;
        return Objects.equals(type, otherMove.type) &&
                startIndex == otherMove.startIndex &&
                destinationIndex == otherMove.destinationIndex;
    }

    @Override
    public int hashCode() {
        // Make sure two objects equal by equals(), have the same hash code.
        // Prolly important for any caching or move tables
        return Objects.hash(type, startIndex, destinationIndex);
    }

    @Override
    public String toString() {
        return moveNotation;
    }
}
