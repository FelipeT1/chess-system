package chesslayer;

import boardlayer.Board;
import boardlayer.Piece;
import boardlayer.Position;
import chesslayer.enums.Color;
import chesslayer.exceptions.ChessException;
import chesslayer.pieces.King;
import chesslayer.pieces.Rook;

import java.util.ArrayList;
import java.util.List;

public class ChessMatch {
    final private int SIZE = 8;
    private int turn;
    private Color currentPlayer;
    private boolean isCheck;
    private boolean isCheckMate;
    private ChessPiece enPassantVulnerable;
    private ChessPiece promoted;
    protected Board board;
    public List<Piece> piecesOnTheBoard = new ArrayList<>();
    public List<Piece> capturedPieces = new ArrayList<>();

    public ChessMatch() {
        this.board = new Board(SIZE, SIZE);
        this.turn = 1;
        // No xadrez é regra que as peças brancas comecem
        // Entenda Yellow como branco aqui
        this.currentPlayer = Color.YELLOW;
        initialSetup();
    }
    public int getTurn(){
        return this.turn;
    }
    private void setTurn(int turn){
        this.turn = turn;
    }
    private void nextTurn(){
        setTurn(getTurn()+1);
        currentPlayer = getTurn() % 2 == 0 ? Color.GREEN : Color.YELLOW;
    }
    public Color getCurrentPlayer(){
        return this.currentPlayer;
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
    public boolean[][] possibleMoves(ChessPosition sourcePosition){
        validateSourcePosition(sourcePosition.toPosition());
        ChessPiece piece = (ChessPiece) board.piece(sourcePosition.toPosition());
        return piece.possibleMoves();
    }
    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition){
        validateSourcePosition(sourcePosition.toPosition());
        validateTargetPosition(sourcePosition.toPosition(),targetPosition.toPosition());
        nextTurn();
        return (ChessPiece) makeMove(sourcePosition.toPosition(), targetPosition.toPosition());
    }
    public Piece makeMove(Position sourcePosition, Position targetPosition){
        Piece pieceToBeMoved = board.removePiece(sourcePosition);
        Piece capturedPiece = board.removePiece(targetPosition);
        if(capturedPiece!=null){
            capturedPieces.add(capturedPiece);
            piecesOnTheBoard.remove(capturedPiece);
        }
        board.placePiece(pieceToBeMoved, targetPosition);
        return capturedPiece;
    }
    public void validateSourcePosition(Position position){
        if(!board.thereIsAPiece(position)){
            throw new ChessException("Error moving chess piece: there is no piece in start position");
        }
        if(!((ChessPiece) board.piece(position)).getColor().equals(this.currentPlayer)){
            throw new ChessException("Error moving chess piece: this is not "+ ((ChessPiece) board.piece(position)).getColor()+"'s turn");
        }
        if(!board.piece(position).isMovingPossible()){
            throw new ChessException("Error moving chess piece: this piece cannot move");
        }
    }
    public void validateTargetPosition(Position sourcePosition, Position targetPosition){
        ChessPiece p = (ChessPiece) board.piece(sourcePosition);
        // utiliza o hook method da classe abstrata Piece
        // que já utiliza o método especializado na subclasse
        if(!p.possibleMove(targetPosition)){
            throw new ChessException("Error moving chess piece: target position is not a valid move");
        }

    }
    // Coloca a peça na posição equivalente de ChessPosition
    public void placeNewPieceAsChessPosition(char col, int row, ChessPiece piece){
        board.placePiece(piece, new ChessPosition(col, row).toPosition());
        piecesOnTheBoard.add(piece);
    }
    // Inicia a partida colocando as peças
    private void initialSetup(){
        placeNewPieceAsChessPosition('c', 2, new Rook(board, Color.YELLOW));
        placeNewPieceAsChessPosition('c', 1, new Rook(board, Color.YELLOW));
        placeNewPieceAsChessPosition('d', 2, new Rook(board, Color.YELLOW));
        placeNewPieceAsChessPosition('e', 2, new Rook(board, Color.YELLOW));
        placeNewPieceAsChessPosition('e', 1, new Rook(board, Color.YELLOW));
        placeNewPieceAsChessPosition('d', 1, new King(board, Color.YELLOW));

        placeNewPieceAsChessPosition('c', 7, new Rook(board, Color.GREEN));
        placeNewPieceAsChessPosition('c', 8, new Rook(board, Color.GREEN));
        placeNewPieceAsChessPosition('d', 7, new Rook(board, Color.GREEN));
        placeNewPieceAsChessPosition('e', 7, new Rook(board, Color.GREEN));
        placeNewPieceAsChessPosition('e', 8, new Rook(board, Color.GREEN));
        placeNewPieceAsChessPosition('d', 8, new King(board, Color.GREEN));
    }
}
