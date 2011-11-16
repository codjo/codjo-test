package net.codjo.test.common.excel.matchers;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
/**
 *
 */
public class ItalicCellStyleStringifier implements CellStringifier {
    private final HSSFWorkbook workbook;
    private static final String ITALIC = "ITALIC";


    public ItalicCellStyleStringifier(HSSFWorkbook workbook) {
        this.workbook = workbook;
    }


    public String toString(Cell cell) {
        HSSFCellStyle style = cell.getPoiCell().getCellStyle();

        return workbook.getFontAt(style.getFontIndex()).getItalic() ? ITALIC : "";
    }
}

