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
import java.util.stream.Collectors;

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

    public boolean isCheck() {
        return isCheck;
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
    private Color opponent(Color color){
        return color.equals(Color.YELLOW) ? Color.GREEN : Color.YELLOW;
    }
    // Para localizar o rei
    private ChessPiece kingColor(Color color){
        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor().equals(color)).collect(Collectors.toList());
        for(Piece p : list){
            if(p instanceof King){
                return (ChessPiece) p;
            }
        }
        throw new IllegalStateException("THERE IS NO KING " + color + " IN THIS BOARD. IS CHESS A REPLUBIC NOW?");
    }
    // Verifica se o rei dessa cor está em check
    private boolean testCheck(Color color){
        Position kingPosition = kingColor(color).getChessPosition().toPosition();
        List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x->((ChessPiece)x).getColor().equals(opponent(color))).collect(Collectors.toList());
        for(Piece opponent : opponentPieces){
            if(opponent.possibleMove(kingPosition)){
                return true;
            }
        }
        return false;
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
        Piece capturedPiece = makeMove(sourcePosition.toPosition(), targetPosition.toPosition());
        // checa se o seu movimento te põe em cheque
        if(testCheck(getCurrentPlayer())){
            undoMove(sourcePosition.toPosition(), targetPosition.toPosition(), capturedPiece);
            throw new ChessException("Error moving chess piece: Your " + getCurrentPlayer() + " king would be in check. Illegal movement.");
        }
        // checa se o oponente ficou em xeque
        // se for true, então a partida está em xeque
        isCheck = testCheck(opponent(getCurrentPlayer()));

        nextTurn();
        return (ChessPiece) capturedPiece;
    }
    public Piece makeMove(Position sourcePosition, Position targetPosition){
        Piece pieceToBeMoved = board.removePiece(sourcePosition);
        Piece capturedPiece = board.removePiece(targetPosition);
        if(capturedPiece != null){
            capturedPieces.add(capturedPiece);
            piecesOnTheBoard.remove(capturedPiece);
        }
        board.placePiece(pieceToBeMoved, targetPosition);
        return capturedPiece;
    }
    // Se uma jogada colocar seu Rei em Xeque esta deve ser desfeita
    // Se possível
    public void undoMove(Position sourcePosition, Position targetPosition, Piece capturedPiece){
        Piece p = board.removePiece(targetPosition);
        board.placePiece(p, sourcePosition);
        if(capturedPiece != null){
            board.placePiece(capturedPiece, targetPosition);
            piecesOnTheBoard.add(capturedPiece);
            capturedPieces.remove(capturedPiece);
        }
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
