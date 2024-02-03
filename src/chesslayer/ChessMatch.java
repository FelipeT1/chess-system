package chesslayer;

import boardlayer.Board;
import chesslayer.enums.Color;

public class ChessMatch {
    final private int SIZE = 8;
    private Integer turn;
    private Color currentPlayer;
    private boolean isCheck;
    private boolean isCheckMate;
    private ChessPiece enPassantVulnerable;
    private ChessPiece promoted;
    private Board board;

    public ChessMatch() {
        this.board = new Board(SIZE, SIZE);
    }
    
    // Feito para que o programa conheça apenas a chesslayer, por isso
    // O downcast para ChessPiece
    // Como tudo é null, o downcast não causará uma excessão de castClassException
    public ChessPiece[][] getPieces(){
        ChessPiece[][] matrix = new ChessPiece[board.getRows()][board.getColumns()];
        for(int i = 0; i < board.getRows(); i++){
            for(int j = 0; j< board.getColumns(); j++){
                matrix[i][j] = (ChessPiece) board.piece(i,j);
            }
        }
        return matrix;
    }
}
