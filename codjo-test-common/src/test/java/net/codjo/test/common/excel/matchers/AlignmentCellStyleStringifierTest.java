package net.codjo.test.common.excel.matchers;
import junit.framework.TestCase;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;

public class AlignmentCellStyleStringifierTest extends TestCase {

    @Test
    public void test_toString() throws Exception {

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFCell cellModel = wb.createSheet().createRow(0).createCell(0);

        HSSFCellStyle style = wb.createCellStyle();
        cellModel.setCellStyle(style);

        Cell cell = new Cell(0, 0, cellModel);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        AlignmentCellStyleStringifier csas = new AlignmentCellStyleStringifier();
        assertEquals("ALIGN_CENTER", csas.toString(cell));
    }
}
