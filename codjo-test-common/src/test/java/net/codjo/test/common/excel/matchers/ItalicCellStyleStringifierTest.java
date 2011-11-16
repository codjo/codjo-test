package net.codjo.test.common.excel.matchers;
import junit.framework.TestCase;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;

public class ItalicCellStyleStringifierTest extends TestCase {

    @Test
    public void test_toString() throws Exception {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFCell cellModel = wb.createSheet().createRow(0).createCell(0);

        HSSFFont font = wb.createFont();
        font.setItalic(true);

        HSSFCellStyle style = wb.createCellStyle();
        style.setFont(font);
        cellModel.setCellStyle(style);

        Cell cell = new Cell(0, 0, cellModel);

        ItalicCellStyleStringifier csas = new ItalicCellStyleStringifier(wb);
        assertEquals("ITALIC", csas.toString(cell));
    }
}
