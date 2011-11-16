package net.codjo.test.common.excel.matchers;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
/**
 *
 */
public class FontColorCellStyleStringifier extends BackgroundColorStyleStringifier {
    private final HSSFWorkbook workbook;


    public FontColorCellStyleStringifier(HSSFWorkbook workbook) {
        this.workbook = workbook;
    }


    public String toString(Cell cell) {
        HSSFCellStyle style = cell.getPoiCell().getCellStyle();

        short colorCode = workbook.getFontAt(style.getFontIndex()).getColor();

        return ExcelMatchersUtil.colorIndexToString(colorCode, false);
    }
}
