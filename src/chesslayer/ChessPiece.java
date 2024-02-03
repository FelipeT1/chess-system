package chesslayer;

import boardlayer.Board;
import boardlayer.Piece;
import chesslayer.enums.Color;


public class ChessPiece extends Piece {

    private Color color;

    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
