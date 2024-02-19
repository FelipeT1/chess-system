package chesslayer.pieces;

import boardlayer.Board;
import boardlayer.Position;
import chesslayer.ChessPiece;
import chesslayer.enums.Color;

public class Bishop extends ChessPiece {
    public Bishop(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] moves = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position p = new Position(0,0); // só para inicializar
        p.setValues(position.getRow()-1, position.getCol()+1);

        // verifica diagonal cima
        while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
            moves[p.getRow()][p.getCol()] = true;
            p.setValues(p.getRow()-1, p.getCol()+1);
        }
        if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
            moves[p.getRow()][p.getCol()] = true;
        }
        p.setValues(position.getRow()+1, position.getCol()+1);
        // verifica diagonal baixo
        while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
            moves[p.getRow()][p.getCol()] = true;
            p.setValues(p.getRow()+1, p.getCol()+1);
        }
        if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
            moves[p.getRow()][p.getCol()] = true;
        }
        p.setValues(position.getRow()-1, position.getCol()-1);
        // verifica diagonal cima
        while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
            moves[p.getRow()][p.getCol()] = true;
            p.setValues(p.getRow()-1, p.getCol()-1);
        }
        if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
            moves[p.getRow()][p.getCol()] = true;
        }
        p.setValues(position.getRow()+1, position.getCol()-1);
        // verifica horizontal direita
        while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
            moves[p.getRow()][p.getCol()] = true;
            p.setValues(p.getRow()+1, p.getCol()-1);
        }
        if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
            moves[p.getRow()][p.getCol()] = true;
        }
        return moves;
    }

    @Override
    public String toString() {
        return "B";
    }
}
