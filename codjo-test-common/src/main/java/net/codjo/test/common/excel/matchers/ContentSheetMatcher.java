package net.codjo.test.common.excel.matchers;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
/**
 *
 */
public class ContentSheetMatcher extends AbstractSheetMatcher {

    public static final String NAME = "content";

    public void match(String sheetName, HSSFWorkbook expected, HSSFWorkbook actual) {
       assertCells("Contenu de la feuille",
                   sheetName, expected, actual,
                    new CellValueStringifier(),
                    new CellValueStringifier());
    }
}
