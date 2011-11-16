package net.codjo.test.common.excel.matchers;
import junit.framework.TestCase;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;
/**
 *
 */
public class BoldCellStyleStringifierTest extends TestCase {
    @Test
    public void test_toString() throws Exception {

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFCell cellModel = wb.createSheet().createRow(0).createCell(0);

        HSSFFont font = wb.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        HSSFCellStyle style = wb.createCellStyle();
        style.setFont(font);
        cellModel.setCellStyle(style);

        Cell cell = new Cell(0, 0, cellModel);

        BoldCellStyleStringifier csas = new BoldCellStyleStringifier(wb);
        assertEquals("BOLD", csas.toString(cell));
    }
}
