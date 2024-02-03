package boardlayer;

public class Position {
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
        return "Position " +
                "row= " + row +
                ", col= " + col;
    }
}
