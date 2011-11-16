package net.codjo.test.common.excel.matchers;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class BoldSheetStyleMatcher extends AbstractSheetMatcher {
    public static final String NAME = "bold";


    public void match(String sheetName, HSSFWorkbook expected, HSSFWorkbook actual) {
        assertCells("Style Gras des cellules de la feuille", sheetName, expected, actual,
                    new BoldCellStyleStringifier(expected),
                    new BoldCellStyleStringifier(actual));
    }

 
}
