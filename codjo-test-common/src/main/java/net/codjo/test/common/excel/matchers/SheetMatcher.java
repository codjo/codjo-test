package net.codjo.test.common.excel.matchers;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
/**
 *
 */
public interface SheetMatcher {
    public void match(String sheetName, HSSFWorkbook expected, HSSFWorkbook actual);
}
