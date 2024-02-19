package chesslayer.pieces;

import boardlayer.Board;
import boardlayer.Position;
import chesslayer.ChessPiece;
import chesslayer.enums.Color;

public class Knight extends ChessPiece {
    public Knight(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "N";
    }

    private boolean canMove(Position position) {
        ChessPiece p = (ChessPiece)getBoard().piece(position);
        return !getBoard().thereIsAPiece(position) || isThereOpponentPiece(position);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] moves = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(0, 0);

        p.setValues(position.getRow() - 1, position.getCol() - 2);
        if (getBoard().positionExists(p) && canMove(p)) {
            moves[p.getRow()][p.getCol()] = true;
        }

        p.setValues(position.getRow() - 2, position.getCol() - 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            moves[p.getRow()][p.getCol()] = true;
        }

        p.setValues(position.getRow() - 2, position.getCol() + 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            moves[p.getRow()][p.getCol()] = true;
        }

        p.setValues(position.getRow() - 1, position.getCol() + 2);
        if (getBoard().positionExists(p) && canMove(p)) {
            moves[p.getRow()][p.getCol()] = true;
        }

        p.setValues(position.getRow() + 1, position.getCol() + 2);
        if (getBoard().positionExists(p) && canMove(p)) {
            moves[p.getRow()][p.getCol()] = true;
        }

        p.setValues(position.getRow() + 2, position.getCol() + 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            moves[p.getRow()][p.getCol()] = true;
        }

        p.setValues(position.getRow() + 2, position.getCol() - 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            moves[p.getRow()][p.getCol()] = true;
        }

        p.setValues(position.getRow() + 1, position.getCol() - 2);
        if (getBoard().positionExists(p) && canMove(p)) {
            moves[p.getRow()][p.getCol()] = true;
        }

        return moves;
    }
}
