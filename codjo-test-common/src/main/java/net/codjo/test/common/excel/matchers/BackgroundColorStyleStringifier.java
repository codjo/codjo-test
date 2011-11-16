package net.codjo.test.common.excel.matchers;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
/**
 *
 */
public class BackgroundColorStyleStringifier implements CellStringifier {

    public String toString(Cell cell) {
        HSSFCellStyle style = cell.getPoiCell().getCellStyle();
        short colorCode;

        if (HSSFCellStyle.SOLID_FOREGROUND == style.getFillPattern()) {
            colorCode = style.getFillForegroundColor();
        }
        else {
            colorCode = style.getFillBackgroundColor();
        }

        return ExcelMatchersUtil.colorIndexToString(colorCode, true);
    }
}
