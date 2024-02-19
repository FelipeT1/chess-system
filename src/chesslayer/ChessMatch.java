package chesslayer;

import boardlayer.Board;
import boardlayer.Piece;
import boardlayer.Position;
import chesslayer.enums.Color;
import chesslayer.exceptions.ChessException;
import chesslayer.pieces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChessMatch {
    /*
    Essa classe contém a partida de xadrez, a lógica de funcionamento se encontra aqui.
     */

    // A partida que deve saber o tamanho do tabuleiro.
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
    public boolean isCheckMate(){
        return isCheckMate;
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
        throw new IllegalStateException("THERE IS NO KING " + color + " IN THIS BOARD. IS CHESS A REPUBLIC NOW?");
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
    public boolean testCheckMate(Color color){
        List<Piece> list = piecesOnTheBoard.stream().filter(x->((ChessPiece) x).getColor().equals(color)).collect(Collectors.toList());
        if(!testCheck(color)){
            return false;
        }
        for(Piece p : list){
            boolean[][] allyMovements = p.possibleMoves();
            for(int i = 0; i < SIZE; i++){
                for(int c = 0; c < SIZE; c++){
                    if(allyMovements[i][c]){
                        Position source = ((ChessPiece) p).getChessPosition().toPosition();
                        Position target = new Position(i,c);
                        Piece capturedPiece = makeMove(source,target);
                        boolean testCheck = testCheck(color);
                        undoMove(source, target,capturedPiece);
                        // o movimento tira o rei do xeque
                        if(!testCheck){
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
    // Feito para que o programa conheça apenas a chesslayer, por isso
    // O downcast para ChessPiece
    // Como tudo é null, o downcast não causará uma excessão de castClassException
    // Isso é em prol do desenvolvimento em camadas. A partida deve conhecer apenas coisas da camada de xadre (chesslayer)
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
        // checa se o movimento feito pelo jogador se põe em xeque
        if(testCheck(getCurrentPlayer())){
            undoMove(sourcePosition.toPosition(), targetPosition.toPosition(), capturedPiece);
            throw new ChessException("Error moving chess piece: Your " + getCurrentPlayer() + " king would be in check. Illegal movement.");
        }
        // checa se o oponente ficou em xeque
        // se for true, então a partida está em xeque
        isCheck = testCheck(opponent(getCurrentPlayer()));

        if(testCheckMate(opponent(currentPlayer))){
            isCheckMate = true;
        }
        else{
            nextTurn();
        }
        return (ChessPiece) capturedPiece;
    }
    public Piece makeMove(Position sourcePosition, Position targetPosition){
        Piece pieceToBeMoved = board.removePiece(sourcePosition);
        ((ChessPiece) pieceToBeMoved).increaseMoveCount();
        Piece capturedPiece = board.removePiece(targetPosition);
        if(capturedPiece != null){
            capturedPieces.add(capturedPiece);
            piecesOnTheBoard.remove(capturedPiece);
        }
        board.placePiece(pieceToBeMoved, targetPosition);

        // #Special Move - Small Castling - Roque Menor -
        if(pieceToBeMoved instanceof King && targetPosition.getCol() == sourcePosition.getCol() + 2){
            Position sourceRook = new Position(sourcePosition.getRow(), sourcePosition.getCol() + 3);
            Position targetRook = new Position(sourcePosition.getRow(), sourcePosition.getCol() + 1);
            ChessPiece rook = (ChessPiece) board.removePiece(sourceRook);
            board.placePiece(rook, targetRook);
            rook.increaseMoveCount();

        }
        // #Special Move - Big Castling - Roque Maior -
        else if(pieceToBeMoved instanceof King && targetPosition.getCol() == sourcePosition.getCol() - 2){
            Position sourceRook = new Position(sourcePosition.getRow(), sourcePosition.getCol() - 4);
            Position targetRook = new Position(sourcePosition.getRow(), sourcePosition.getCol() - 1);
            ChessPiece rook = (ChessPiece) board.removePiece(sourceRook);
            board.placePiece(rook, targetRook);
            rook.increaseMoveCount();

        }
        return capturedPiece;
    }
    // Se uma jogada colocar seu Rei em Xeque esta deve ser desfeita
    // Se possível
    public void undoMove(Position sourcePosition, Position targetPosition, Piece capturedPiece){
        Piece p = board.removePiece(targetPosition);
        ((ChessPiece) p).decreaseMoveCount();
        board.placePiece(p, sourcePosition);
        if(capturedPiece != null){
            board.placePiece(capturedPiece, targetPosition);
            piecesOnTheBoard.add(capturedPiece);
            capturedPieces.remove(capturedPiece);
        }

        // #Special Move - Small Castling - Roque Menor -
        if(p instanceof King && targetPosition.getCol() == sourcePosition.getCol() + 2){
            Position sourceRook = new Position(sourcePosition.getRow(), sourcePosition.getCol() + 3);
            Position targetRook = new Position(sourcePosition.getRow(), sourcePosition.getCol() + 1);
            ChessPiece rook = (ChessPiece) board.removePiece(targetRook);
            rook.decreaseMoveCount();
            board.placePiece(rook, sourceRook);
        }
        // #Special Move - Big Castling - Roque Maior -
        else if(p instanceof King && targetPosition.getCol() == sourcePosition.getCol() - 2){
            Position sourceRook = new Position(sourcePosition.getRow(), sourcePosition.getCol() - 4);
            Position targetRook = new Position(sourcePosition.getRow(), sourcePosition.getCol() - 1);
            ChessPiece rook = (ChessPiece) board.removePiece(targetRook);
            rook.decreaseMoveCount();
            board.placePiece(rook, sourceRook);
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
        placeNewPieceAsChessPosition('a', 1, new Rook(board, Color.YELLOW));
        placeNewPieceAsChessPosition('e', 1, new King(board, Color.YELLOW,this));
        placeNewPieceAsChessPosition('h', 1, new Rook(board, Color.YELLOW));
        placeNewPieceAsChessPosition('a', 2, new Pawn(board, Color.YELLOW));
        placeNewPieceAsChessPosition('b', 2, new Pawn(board, Color.YELLOW));
        placeNewPieceAsChessPosition('c', 2, new Pawn(board, Color.YELLOW));
        placeNewPieceAsChessPosition('d', 2, new Pawn(board, Color.YELLOW));
        placeNewPieceAsChessPosition('e', 2, new Pawn(board, Color.YELLOW));
        placeNewPieceAsChessPosition('f', 2, new Pawn(board, Color.YELLOW));
        placeNewPieceAsChessPosition('g', 2, new Pawn(board, Color.YELLOW));
        placeNewPieceAsChessPosition('h', 2, new Pawn(board, Color.YELLOW));

        placeNewPieceAsChessPosition('a', 8, new Rook(board, Color.GREEN));
        placeNewPieceAsChessPosition('e', 8, new King(board, Color.GREEN, this));
        placeNewPieceAsChessPosition('h', 8, new Rook(board, Color.GREEN));
        placeNewPieceAsChessPosition('a', 7, new Pawn(board, Color.GREEN));
        placeNewPieceAsChessPosition('b', 7, new Pawn(board, Color.GREEN));
    }
}
