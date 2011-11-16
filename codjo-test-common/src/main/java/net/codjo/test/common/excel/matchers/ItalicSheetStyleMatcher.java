package net.codjo.test.common.excel.matchers;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ItalicSheetStyleMatcher extends AbstractSheetMatcher {
    public static final String NAME = "italic";


    public void match(String sheetName, HSSFWorkbook expected, HSSFWorkbook actual) {
        assertCells("Style Italic des cellules de la feuille", sheetName, expected, actual,
                    new ItalicCellStyleStringifier(expected),
                    new ItalicCellStyleStringifier(actual));
    }
 
}
