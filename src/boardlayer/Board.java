package boardlayer;

public class Board {
    private Integer rows;
    private Integer columns;
    private Piece[][] piece;

    public Board(Integer rows, Integer columns) {
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
}
