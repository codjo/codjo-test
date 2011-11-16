package net.codjo.test.common.excel.matchers;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
/**
 *
 */
public class BackgroundColorSheetMatcher extends AbstractSheetMatcher {
    public static final String NAME = "background-color";


    public void match(String sheetName, HSSFWorkbook expected, HSSFWorkbook actual) {
        assertCells("Couleur de fond des cellules de la feuille", sheetName,
                    expected,
                    actual,
                    new BackgroundColorStyleStringifier(),
                    new BackgroundColorStyleStringifier());
    }
}
