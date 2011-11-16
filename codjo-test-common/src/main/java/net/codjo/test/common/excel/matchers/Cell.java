package net.codjo.test.common.excel.matchers;
import org.apache.poi.hssf.usermodel.HSSFCell;

public class Cell implements Comparable<Cell> {
    private final int rowIndex;
    private final int columnIndex;
    private HSSFCell poiCell;


    public Cell(int rowIndex, int columnIndex, HSSFCell model) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.poiCell = model;
    }


    public HSSFCell getPoiCell() {
        return poiCell;
    }


    public int getRowIndex() {
        return rowIndex;
    }


    public int getColumnIndex() {
        return columnIndex;
    }


    public int compareTo(Cell otherCell) {
        if (rowIndex != otherCell.rowIndex) {
            return compare(rowIndex, otherCell.rowIndex);
        }
        else {
            return compare(columnIndex, otherCell.columnIndex);
        }
    }


    private int compare(int thisVal, int anotherVal) {
        return thisVal < anotherVal ? -1 : (thisVal == anotherVal ? 0 : 1);
    }
}
