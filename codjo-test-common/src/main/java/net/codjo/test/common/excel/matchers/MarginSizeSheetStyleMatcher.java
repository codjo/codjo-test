package net.codjo.test.common.excel.matchers;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import static org.junit.Assert.assertEquals;
/**
 *
 */
public class MarginSizeSheetStyleMatcher extends AbstractSheetMatcher {
    public static final String NAME = "margin-size";


    public void match(String sheetName, HSSFWorkbook expected, HSSFWorkbook actual) {
       assertEquals("liste tailles des marges",
                     toDocumentMarginString(expected.getSheet(sheetName)),
                     toDocumentMarginString(actual.getSheet(sheetName)));
    }


    private String toDocumentMarginString(HSSFSheet sheet) {
        StringBuilder buffer = new StringBuilder("margin:");
        buffer.append(sheet.getMargin(HSSFSheet.BottomMargin))
              .append("/")
              .append(sheet.getMargin(HSSFSheet.LeftMargin))
              .append("/")
              .append(sheet.getMargin(HSSFSheet.TopMargin))
              .append("/")
              .append(sheet.getMargin(HSSFSheet.RightMargin));

        return buffer.toString();
    }
}
