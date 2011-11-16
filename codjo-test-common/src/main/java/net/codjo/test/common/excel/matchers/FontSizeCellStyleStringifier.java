package net.codjo.test.common.excel.matchers;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
/**
 *
 */
public class FontSizeCellStyleStringifier implements CellStringifier {
    private final HSSFWorkbook workbook;


    public FontSizeCellStyleStringifier(HSSFWorkbook workbook) {
        this.workbook = workbook;
    }


    public String toString(Cell cell) {
        HSSFCellStyle style = cell.getPoiCell().getCellStyle();

        return String.valueOf(workbook.getFontAt(style.getFontIndex()).getFontHeightInPoints());
    }
}
