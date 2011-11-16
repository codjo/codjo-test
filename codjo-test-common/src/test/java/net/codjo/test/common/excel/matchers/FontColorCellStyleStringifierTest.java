package net.codjo.test.common.excel.matchers;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
/**
 *
 */
public class FontColorCellStyleStringifierTest {

    @Test
    public void test_toStringBlack() throws Exception {
        HSSFWorkbook wb = new HSSFWorkbook();

        HSSFFont font = wb.createFont();
        font.setColor(HSSFColor.BLACK.index);

        HSSFCellStyle style = wb.createCellStyle();
        style.setFont(font);

        HSSFCell cellModel = wb.createSheet().createRow(0).createCell(0);
        cellModel.setCellStyle(style);
        Cell cell = new Cell(0, 0, cellModel);

        FontColorCellStyleStringifier csas = new FontColorCellStyleStringifier(wb);
        assertEquals("AUTOMATIC", csas.toString(cell));
    }


    @Test
    public void test_toStringRed() throws Exception {
        HSSFWorkbook wb = new HSSFWorkbook();

        HSSFFont font = wb.createFont();
        font.setColor(HSSFColor.RED.index);

        HSSFCellStyle style = wb.createCellStyle();
        style.setFont(font);

        HSSFCell cellModel = wb.createSheet().createRow(0).createCell(0);
        cellModel.setCellStyle(style);
        Cell cell = new Cell(0, 0, cellModel);

        FontColorCellStyleStringifier csas = new FontColorCellStyleStringifier(wb);
        assertEquals("RED", csas.toString(cell));
    }


    @Test
    public void test_toStringGreen() throws Exception {
        HSSFWorkbook wb = new HSSFWorkbook();

        HSSFFont font = wb.createFont();
        font.setColor(HSSFColor.GREEN.index);

        HSSFCellStyle style = wb.createCellStyle();
        style.setFont(font);

        HSSFCell cellModel = wb.createSheet().createRow(0).createCell(0);
        cellModel.setCellStyle(style);
        Cell cell = new Cell(0, 0, cellModel);

        FontColorCellStyleStringifier csas = new FontColorCellStyleStringifier(wb);
        assertEquals("GREEN", csas.toString(cell));
    }
}