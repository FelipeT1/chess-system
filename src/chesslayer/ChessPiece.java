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

    private int moveCount;

    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }
    protected void increaseMoveCount(){
        moveCount++;
    }
    protected void decreaseMoveCount(){
        moveCount--;
    }
    public int getMoveCount(){
        return moveCount;
    }
    public Color getColor() {
        return color;
    }
    // Isso é feito pois o atributo position de Piece não deve ser diretamente acessado por essa camada chesslayer
    public ChessPosition getChessPosition(){
        return ChessPosition.fromPosition(position);
    }
    public abstract boolean[][] possibleMoves();
    protected boolean isThereOpponentPiece(Position position){
        ChessPiece chessPiece = (ChessPiece) getBoard().piece(position);
        return chessPiece != null && !chessPiece.getColor().equals(this.color);
    }

}
