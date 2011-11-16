package net.codjo.test.common.excel.matchers;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
/**
 *
 */
public class AlignmentSheetStyleMatcher extends AbstractSheetMatcher {
    public static final String NAME =  "alignement";


    public void match(String sheetName, HSSFWorkbook expected, HSSFWorkbook actual) {
           assertCells("Style Alignement des cellules de la feuille", sheetName, expected, actual,
                    new AlignmentCellStyleStringifier(),
                    new AlignmentCellStyleStringifier());
    }

 
}
