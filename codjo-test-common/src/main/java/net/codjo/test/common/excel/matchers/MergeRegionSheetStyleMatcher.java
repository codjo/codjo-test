package net.codjo.test.common.excel.matchers;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import static org.junit.Assert.assertEquals;
/**
 *
 */
public class MergeRegionSheetStyleMatcher extends AbstractSheetMatcher {
    public static final String NAME = "merge-region";


    public void match(String sheetName, HSSFWorkbook expected, HSSFWorkbook actual) {
           assertEquals("liste des regions mergées",
                     toRegionString(expected.getSheet(sheetName)),
                     toRegionString(actual.getSheet(sheetName)));
    }



    private static String toRegionString(HSSFSheet sheet) {
        StringBuilder buffer = new StringBuilder();
        int regionNumber = sheet.getNumMergedRegions();

        for (int i = 0; i < regionNumber; i++) {
            CellRangeAddress region = sheet.getMergedRegion(i);
            if (buffer.length() != 0) {
                buffer.append("\n");
            }
            buffer.append("Region(")
                  .append("from[")
                  .append(region.getFirstRow()).append(",").append(region.getFirstColumn())
                  .append("] ")
                  .append("to[")
                  .append(region.getLastRow()).append(",").append(region.getLastColumn())
                  .append("])");
        }
        return buffer.toString();
    }
}
