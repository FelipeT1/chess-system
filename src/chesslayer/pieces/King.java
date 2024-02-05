package chesslayer.pieces;

import boardlayer.Board;
import boardlayer.Position;
import chesslayer.ChessPiece;
import chesslayer.enums.Color;

public class King extends ChessPiece {
    public King(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] moves = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position p = new Position(0,0);
        // cima
        p.setValues(position.getRow()-1, position.getCol());
        if(getBoard().positionExists(p) && canMove(p)){
            moves[p.getRow()][p.getCol()] = true;
        }
        // baixo
        p.setValues(position.getRow()+1, position.getCol());
        if(getBoard().positionExists(p) && canMove(p)){
            moves[p.getRow()][p.getCol()] = true;
        }
        // esquerda
        p.setValues(position.getRow(), position.getCol()-1);
        if(getBoard().positionExists(p) && canMove(p)){
            moves[p.getRow()][p.getCol()] = true;
        }
        // direita
        p.setValues(position.getRow(), position.getCol()+1);
        if(getBoard().positionExists(p) && canMove(p)){
            moves[p.getRow()][p.getCol()] = true;
        }
        // diagonal esquerda cima
        p.setValues(position.getRow()-1, position.getCol()-1);
        if(getBoard().positionExists(p) && canMove(p)){
            moves[p.getRow()][p.getCol()] = true;
        }
        // diagonal esquerda baixo
        p.setValues(position.getRow()+1, position.getCol()-1);
        if(getBoard().positionExists(p) && canMove(p)){
            moves[p.getRow()][p.getCol()] = true;
        }
        // diagonal direita cima
        p.setValues(position.getRow()-1, position.getCol()+1);
        if(getBoard().positionExists(p) && canMove(p)){
            moves[p.getRow()][p.getCol()] = true;
        }
        // diagonal direita baixo
        p.setValues(position.getRow()+1, position.getCol()+1);
        if(getBoard().positionExists(p) && canMove(p)){
            moves[p.getRow()][p.getCol()] = true;
        }
        return moves;
    }

    private boolean canMove(Position p) {
        return !getBoard().thereIsAPiece(p) || isThereOpponentPiece(p);
    }

    @Override
    public String toString() {
        return "K";
    }
}
