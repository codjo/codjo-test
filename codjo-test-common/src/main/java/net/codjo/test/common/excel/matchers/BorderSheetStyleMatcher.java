package net.codjo.test.common.excel.matchers;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class BorderSheetStyleMatcher extends AbstractSheetMatcher {
    public static final String NAME = "border";;


    public void match(String sheetName, HSSFWorkbook expected, HSSFWorkbook actual) {
        assertCells("Style bordure des cellules de la feuille", sheetName, expected, actual,
                    new BorderCellStyleStringifier(),
                    new BorderCellStyleStringifier());
    }

}
