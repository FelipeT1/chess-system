package chesslayer.pieces;

import boardlayer.Board;
import chesslayer.ChessPiece;
import chesslayer.enums.Color;

// Rook == Torre
public class Rook extends ChessPiece {
    public Rook(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] moves = new boolean[getBoard().getRows()][getBoard().getColumns()];
        return moves;
    }

    @Override
    public String toString() {
        return "R";
    }
}
