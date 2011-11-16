package net.codjo.test.common.excel.matchers;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
/**
 *
 */
public class BackgroundColorStyleStringifierTest {

    @Test
    public void test_toStringSolid() throws Exception {
        HSSFWorkbook wb = new HSSFWorkbook();

        HSSFCellStyle style = wb.createCellStyle();

        // attention, cf doc apache, si le style est solid
        // pour le fond, la couleur est a setter en foreground (!)
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.RED.index);

        HSSFCell cellModel = wb.createSheet().createRow(0).createCell(0);
        cellModel.setCellStyle(style);
        Cell cell = new Cell(0, 0, cellModel);

        BackgroundColorStyleStringifier csas = new BackgroundColorStyleStringifier();
        assertEquals("RED", csas.toString(cell));
    }


    @Test
    public void test_toStringNoFillBackgroundColor() throws Exception {
        HSSFWorkbook wb = new HSSFWorkbook();

        HSSFCellStyle style = wb.createCellStyle();

        HSSFCell cellModel = wb.createSheet().createRow(0).createCell(0);
        cellModel.setCellStyle(style);
        Cell cell = new Cell(0, 0, cellModel);

        BackgroundColorStyleStringifier csas = new BackgroundColorStyleStringifier();
        assertEquals("TRANSPARENT", csas.toString(cell));
    }


    @Test
    public void test_toStringDotted() throws Exception {
        HSSFWorkbook wb = new HSSFWorkbook();

        HSSFCellStyle style = wb.createCellStyle();

        style.setFillPattern(HSSFCellStyle.FINE_DOTS);
        style.setFillForegroundColor(HSSFColor.RED.index);
        style.setFillBackgroundColor(HSSFColor.RED.index);

        HSSFCell cellModel = wb.createSheet().createRow(0).createCell(0);
        cellModel.setCellStyle(style);
        Cell cell = new Cell(0, 0, cellModel);

        BackgroundColorStyleStringifier csas = new BackgroundColorStyleStringifier();
        assertEquals("RED", csas.toString(cell));
    }
}
