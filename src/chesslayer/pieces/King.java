package chesslayer.pieces;

import boardlayer.Board;
import boardlayer.Position;
import chesslayer.ChessMatch;
import chesslayer.ChessPiece;
import chesslayer.enums.Color;

public class King extends ChessPiece {
    private ChessMatch chessMatch;
    public King(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }
    public boolean testIfRookIsCastling(Position position){
        ChessPiece p = (ChessPiece) getBoard().piece(position);
        return (p instanceof Rook) && (p.getColor().equals(getColor())) && (p.getMoveCount() == 0);
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

        // #Special move - Roque = Castling
        // Small castling
        p.setValues(position.getRow(), position.getCol() + 3);
        if(testIfRookIsCastling(p)){
            Position p1 = new Position(position.getRow(), position.getCol() + 1);
            Position p2 = new Position(position.getRow(), position.getCol() + 2);
            if(!getBoard().thereIsAPiece(p1) && !getBoard().thereIsAPiece(p2)){
                moves[position.getRow()][position.getCol() + 2] = true;
            }

        }

        // Big castling
        p.setValues(position.getRow(), position.getCol() - 4);
        if(testIfRookIsCastling(p)){
            Position p1 = new Position(position.getRow(), position.getCol() - 1);
            Position p2 = new Position(position.getRow(), position.getCol() - 2);
            Position p3 = new Position(position.getRow(), position.getCol() - 3);
            if(!getBoard().thereIsAPiece(p1) && !getBoard().thereIsAPiece(p2) && !getBoard().thereIsAPiece(p3)){
                moves[position.getRow()][position.getCol() - 2] = true;
            }

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
