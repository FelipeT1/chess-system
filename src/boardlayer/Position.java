package boardlayer;

public class Position {

    /*
    Essa classe representa a posição da peça dentro da tabela. É representada por linha, coluna. Ambos inteiros.

    OOP: Encapsulamento (os campos private), Construtor e Sobrecarga (Override no ToString()).
     */

    private Integer row;
    private Integer col;

    public Position(int row, int col){
        setRow(row);
        setCol(col);
    }
    public Integer getRow() {
        return row;
    }
    public Integer getCol() {
        return col;
    }
    private void setRow(Integer row) {
        this.row = row;
    }
    private void setCol(Integer col) {
        this.col = col;
    }
    public void setValues(int row, int col){
        setCol(col);
        setRow(row);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Position: ").append(getRow()).append(", ").append(getCol()).append(".\n");
        return sb.toString();
    }
}
