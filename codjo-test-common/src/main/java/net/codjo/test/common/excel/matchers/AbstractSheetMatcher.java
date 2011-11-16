package net.codjo.test.common.excel.matchers;
import net.codjo.test.common.AssertUtil;
import net.codjo.test.common.excel.ExcelMatchingException;
import net.codjo.test.common.excel.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
/**
 *
 */
public abstract class AbstractSheetMatcher implements SheetMatcher {

    protected void assertCells(String message, String sheetName,
                               HSSFWorkbook expectedWorkbook,
                               HSSFWorkbook actualWorkbook,
                               CellStringifier expectedStringifier,
                               CellStringifier actualStringifier) {
        String expected = ExcelUtil.toString(expectedWorkbook.getSheet(sheetName), expectedStringifier);
        String actual = ExcelUtil.toString(actualWorkbook.getSheet(sheetName), actualStringifier);

        String[] actualLines = actual.split("\n");
        String[] expectedLines = expected.split("\n");
        final int minLength = Math.min(expectedLines.length, actualLines.length);

        for (int i = 0; i < minLength; i++) {

            String expectedLine = expectedLines[i];
            String actualLine = actualLines[i];

            int pos = AssertUtil.equalsRow(expectedLine, actualLine, '*');

            if (pos != -1) {
                StringBuilder messageBuilder = new StringBuilder(
                      message + " '" + sheetName + "' en erreur.\n");

                messageBuilder.append("Excel line ").append(i + 1).append("\n");
                messageBuilder.append("\tExpected > ").append(expectedLine).append("\n");
                messageBuilder.append("\tActual   > ").append(actualLine).append("\n");
                messageBuilder.append("\tGap      > ");
                for (int j = 0; j < pos; j++) {
                    messageBuilder.append("_");
                }
                messageBuilder.append("*\n");

                throw new ExcelMatchingException(messageBuilder.toString());
            }
        }

        if (expectedLines.length > actualLines.length) {
            throw new ExcelMatchingException(
                  "Expected sheet contains more lines after line " + (minLength + 1));
        }
        if (expectedLines.length < actualLines.length) {
            throw new ExcelMatchingException(
                  "Actual sheet contains more lines after line " + (minLength + 1));
        }
    }
}
