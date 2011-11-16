package net.codjo.test.common.excel.matchers;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class FontSizeSheetStyleMatcher extends AbstractSheetMatcher {
    public static final String NAME = "font-size";


    public void match(String sheetName, HSSFWorkbook expected, HSSFWorkbook actual) {
        assertCells("Style Taille de la police des cellules de la feuille", sheetName, expected,
                    actual,
                    new FontSizeCellStyleStringifier(expected),
                    new FontSizeCellStyleStringifier(actual));
    }

 
}
