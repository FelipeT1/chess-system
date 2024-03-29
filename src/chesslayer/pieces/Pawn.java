package chesslayer.pieces;

import boardlayer.Board;
import boardlayer.Position;
import chesslayer.ChessMatch;
import chesslayer.ChessPiece;
import chesslayer.enums.Color;

public class Pawn extends ChessPiece {
    private ChessMatch chessMatch;
    public Pawn(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] moves = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(0, 0);

        if (getColor() == Color.YELLOW) {
            p.setValues(position.getRow() - 1, position.getCol());
            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
                moves[p.getRow()][p.getCol()] = true;
            }
            p.setValues(position.getRow() - 2, position.getCol());
            Position p2 = new Position(position.getRow() - 1, position.getCol());
            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
                moves[p.getRow()][p.getCol()] = true;
            }
            p.setValues(position.getRow() - 1, position.getCol() - 1);
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                moves[p.getRow()][p.getCol()] = true;
            }
            p.setValues(position.getRow() - 1, position.getCol() + 1);
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                moves[p.getRow()][p.getCol()] = true;
            }

            // #Special Move EnPassantVulnerable

            if(position.getRow() == 3){
                Position left = new Position(position.getRow(), position.getCol() - 1);
                Position right = new Position(position.getRow(), position.getCol() + 1);

                if(getBoard().positionExists(left) && isThereOpponentPiece(left) && chessMatch.getEnPassantVulnerable() == getBoard().piece(left)){
                    moves[left.getRow()-1][left.getCol()] = true;
                }
                if(getBoard().positionExists(right) && isThereOpponentPiece(right) && chessMatch.getEnPassantVulnerable() == getBoard().piece(right)){
                    moves[right.getRow()-1][right.getCol()] = true;
                }
            }

        }
        // BLACK
        else {
            p.setValues(position.getRow() + 1, position.getCol());
            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
                moves[p.getRow()][p.getCol()] = true;
            }
            p.setValues(position.getRow() + 2, position.getCol());
            Position p2 = new Position(position.getRow() + 1, position.getCol());
            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
                moves[p.getRow()][p.getCol()] = true;
            }
            p.setValues(position.getRow() + 1, position.getCol() - 1);
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                moves[p.getRow()][p.getCol()] = true;
            }
            p.setValues(position.getRow() + 1, position.getCol() + 1);
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                moves[p.getRow()][p.getCol()] = true;
            }

            // #Special Move EnPassantVulnerable

            if(position.getRow() == 4){
                Position left = new Position(position.getRow(), position.getCol() - 1);
                Position right = new Position(position.getRow(), position.getCol() + 1);

                if(getBoard().positionExists(left) && isThereOpponentPiece(left) && chessMatch.getEnPassantVulnerable()==getBoard().piece(left)){
                    moves[left.getRow()+1][left.getCol()] = true;
                }
                if(getBoard().positionExists(right) && isThereOpponentPiece(right) && chessMatch.getEnPassantVulnerable()==getBoard().piece(right)){
                    moves[right.getRow()+1][right.getCol()] = true;
                }
            }

        }
        return moves;
    }

    @Override
    public String toString() {
        return "P";
    }
}
