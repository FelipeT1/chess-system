package chesslayer;

import boardlayer.Position;
import chesslayer.exceptions.ChessException;

public class ChessPosition {
    final private static int SIZE = 8;
    private char column;
    private int row;

    // Converte ChessPosition (letra-numero) para position (numero-numero)
    protected Position toPosition(){
        // 8 - 8 == 0 -> Começo da matriz
        int matrixRow = SIZE - this.row;
        // Unicode difference. 'a' - 'a' == 0, 'b' - 'a' == 1, ...
        int matrixCol = this.column - 'a';

        return new Position(matrixRow,matrixCol);
    }

    // Converte position (numero-numero) para ChessPosition (letra-numero)
    protected static ChessPosition fromPosition(Position position){
        // Pois se posRow == 0 então chessRow == 8 (ambos no começo)
        int chessRow = SIZE - position.getRow();
        // 'a' -> U+0061 até 'h' -> U+0068
        char chessCol = (char) (position.getCol() + 'a');
        return new ChessPosition(chessCol, chessRow);
    }
    public ChessPosition(char column, int row) {
        // Programação defensiva, para evitar erros óbvios
        if(column < 'a' || column > 'h' || row < 1 || row > SIZE){
            throw new ChessException("Error in ChessPosition: Valores válidos são de a1 até h8");
        }
        this.column = column;
        this.row = row;
    }

    public char getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    @Override
    public String toString() {
        return column + "" + row;
    }
}
