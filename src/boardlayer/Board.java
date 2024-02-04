package boardlayer;

import boardlayer.exceptions.BoardException;

public class Board {
    private Integer rows;
    private Integer columns;
    private Piece[][] piece;

    public Board(Integer rows, Integer columns) {
        // Programação defensiva, evitando erros óbvios
        if (rows < 1 || columns < 1) {
            throw new BoardException("Error creating board: Número de linhas ou colunas devem ser pelo menos 1");
        }
        setRows(rows);
        setColumns(columns);
        this.piece = new Piece[getRows()][getColumns()];
    }

    public Integer getColumns() {
        return columns;
    }

    public Integer getRows() {
        return rows;
    }

    private void setRows(Integer rows) {
        this.rows = rows;
    }

    private void setColumns(Integer columns) {
        this.columns = columns;
    }

    public Piece[][] getPiece() {
        return piece;
    }

    public Piece piece(int row, int col) {
        if (!positionExists(row, col)) {
            throw new BoardException("Error accessing piece: position does not exist");
        }
        return this.piece[row][col];
    }

    public Piece piece(Position position) {
        if (!positionExists(position)) {
            throw new BoardException("Error accessing piece: position does not exist");
        }
        return this.piece[position.getRow()][position.getCol()];
    }

    public void placePiece(Piece piece, Position position) {
        if (thereIsAPiece(position)) {
            throw new BoardException("Error placing piece: a piece already exists in this position " + position);
        }
        this.piece[position.getRow()][position.getCol()] = piece;
        // Atributo acessível por ser protected.
        // O atributo position desse objeto peça não será mais nulo
        piece.position = position;
    }

    // Verifica se a posição EXISTE no tabuleiro
    // Não se ela é ocupada.
    // Sobrecarga para auxiliar em outros métodos.
    public boolean positionExists(int row, int col) {
        return row >= 0
                && row < this.rows
                && col >= 0
                && col < this.columns;
    }

    public boolean positionExists(Position position) {
        // Verifica se há uma peça na posição.
        // Se houver uma peça a matriz não terá um null alocado
        // Se a expressão for diferente de null é pq é True, há uma peça
        // Utiliza a sobrecarga de piece para verificar esse objeto se é nulo ou não
        return positionExists(position.getRow(), position.getCol());
    }
    public boolean thereIsAPiece(Position position){
        // Para testar duas coisas em um método só
        // Para uma peça existir a posição deve existir
        if(!positionExists(position)){
            throw new BoardException("Error accessing piece: position does not exist");
        }
        return piece(position) != null;
    }
}
