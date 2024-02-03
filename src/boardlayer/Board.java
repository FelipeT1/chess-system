package boardlayer;

public class Board {
    private Integer rows;
    private Integer columns;

//    public boolean positionExists(Position position){
//
//    }

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
