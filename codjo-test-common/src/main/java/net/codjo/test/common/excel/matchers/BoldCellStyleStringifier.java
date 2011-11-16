package net.codjo.test.common.excel.matchers;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
/**
 *
 */
public class BoldCellStyleStringifier implements CellStringifier {
    private final HSSFWorkbook workbook;
    private static final short BOLDWEIGHT = HSSFFont.BOLDWEIGHT_BOLD;

    private static final String BOLD = "BOLD";


    public BoldCellStyleStringifier(HSSFWorkbook workbook) {
        this.workbook = workbook;
    }


    public String toString(Cell cell) {
        HSSFCellStyle style = cell.getPoiCell().getCellStyle();

        return (BOLDWEIGHT == workbook.getFontAt(style.getFontIndex()).getBoldweight()) ?
               BOLD : "";
    }
}
