package chesslayer.pieces;

import boardlayer.Board;
import boardlayer.Position;
import chesslayer.ChessPiece;
import chesslayer.enums.Color;

// Rook == Torre
public class Rook extends ChessPiece {
    public Rook(Board board, Color color) {
        super(board, color);
    }


    // Uma torre se move na vertical e horizontal
    @Override
    public boolean[][] possibleMoves() {
        boolean[][] moves = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position p = new Position(0,0); // só para inicializar

        p.setValues(position.getRow()-1, position.getCol());

        // verifica vertical cima
        while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
            moves[p.getRow()][p.getCol()] = true;
            p.setValues(p.getRow()-1, p.getCol());
        }
        if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
            moves[p.getRow()][p.getCol()] = true;
        }
        p.setValues(position.getRow()+1, position.getCol());
        // verifica vertical baixo
        while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
            moves[p.getRow()][p.getCol()] = true;
            p.setValues(p.getRow()+1, p.getCol());
        }
        if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
            moves[p.getRow()][p.getCol()] = true;
        }
        p.setValues(position.getRow(), position.getCol()-1);
        // verifica horizontal esquerda
        while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
            moves[p.getRow()][p.getCol()] = true;
            p.setValues(p.getRow(), p.getCol()-1);
        }
        if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
            moves[p.getRow()][p.getCol()] = true;
        }
        p.setValues(position.getRow(), position.getCol()+1);
        // verifica horizontal direita
        while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
            moves[p.getRow()][p.getCol()] = true;
            p.setValues(p.getRow(), p.getCol()+1);
        }
        if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
            moves[p.getRow()][p.getCol()] = true;
        }

        return moves;
    }

    @Override
    public String toString() {
        return "R";
    }
}
