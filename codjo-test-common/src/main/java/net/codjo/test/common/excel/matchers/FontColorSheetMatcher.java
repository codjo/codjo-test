package net.codjo.test.common.excel.matchers;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
/**
 *
 */
public class FontColorSheetMatcher extends AbstractSheetMatcher {
    public static final String NAME = "font-color";


    public void match(String sheetName, HSSFWorkbook expected, HSSFWorkbook actual) {
        assertCells("Couleur de la font des cellules de la feuille", sheetName,
                    expected,
                    actual,
                    new FontColorCellStyleStringifier(expected),
                    new FontColorCellStyleStringifier(actual));
    }
}
