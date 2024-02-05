package chesslayer;

import boardlayer.Board;
import boardlayer.Piece;
import boardlayer.Position;
import chesslayer.enums.Color;

// Classe genérica demais
// Os movimentos, lógicas de cada peça
// Serão implementados em suas respectivas classes
// King, queen, rook, pawn, ...
// Por isso essa classe também é abstrata
public abstract class ChessPiece extends Piece {

    private Color color;

    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public abstract boolean[][] possibleMoves();
    protected boolean isThereOpponentPiece(Position position){
        ChessPiece chessPiece = (ChessPiece) getBoard().piece(position);
        return chessPiece != null && !chessPiece.getColor().equals(this.color);
    }

}
