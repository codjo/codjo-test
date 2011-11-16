package net.codjo.test.common.excel.matchers;
import junit.framework.TestCase;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;

public class BorderCellStyleStringifierTest extends TestCase {

    @Test
    public void test_toString() throws Exception {

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFCell cellModel = wb.createSheet().createRow(0).createCell(0);

        HSSFCellStyle style = wb.createCellStyle();
        cellModel.setCellStyle(style);

        style.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
        style.setBorderRight(HSSFCellStyle.BORDER_DOUBLE);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setBorderBottom(HSSFCellStyle.BORDER_DASHED);

        Cell cell = new Cell(0, 0, cellModel);

        BorderCellStyleStringifier csas = new BorderCellStyleStringifier();
        assertEquals("T=BORDER_THIN/B=BORDER_DASHED/L=BORDER_MEDIUM/R=BORDER_DOUBLE", csas.toString(cell));
    }
}
