package chesslayer;

import boardlayer.Board;
import chesslayer.enums.Color;
import chesslayer.pieces.King;
import chesslayer.pieces.Rook;

public class ChessMatch {
    final private int SIZE = 8;
    private Integer turn;
    private Color currentPlayer;
    private boolean isCheck;
    private boolean isCheckMate;
    private ChessPiece enPassantVulnerable;
    private ChessPiece promoted;
    protected Board board;

    public ChessMatch() {
        this.board = new Board(SIZE, SIZE);
        initialSetup();
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
    // Coloca a peça na posição equivalente de ChessPosition
    public void placeNewPieceAsChessPosition(char col, int row, ChessPiece piece){
        board.placePiece(piece, new ChessPosition(col, row).toPosition());
    }

    // Inicia a partida colocando as peças no tabuleiro padrão (0-7)
    private void initialSetup(){
        placeNewPieceAsChessPosition('b',6, new Rook(board, Color.WHITE));
        placeNewPieceAsChessPosition('e',8, new King(board, Color.BLACK));
        placeNewPieceAsChessPosition('e',1, new King(board, Color.WHITE));
    }
}
