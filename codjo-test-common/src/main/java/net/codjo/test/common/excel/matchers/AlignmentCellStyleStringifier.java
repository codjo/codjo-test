package net.codjo.test.common.excel.matchers;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
/**
 *
 */
public class AlignmentCellStyleStringifier implements CellStringifier {

    public AlignmentCellStyleStringifier() {
    }


    public String toString(Cell cell) {
        return alignementToString(cell.getPoiCell().getCellStyle());
    }


    private static String alignementToString(HSSFCellStyle style) {
        switch (style.getAlignment()) {
            case HSSFCellStyle.ALIGN_GENERAL:
                return "ALIGN_GENERAL";
            case HSSFCellStyle.ALIGN_LEFT:
                return "ALIGN_LEFT";
            case HSSFCellStyle.ALIGN_CENTER:
                return "ALIGN_CENTER";
            case HSSFCellStyle.ALIGN_JUSTIFY:
                return "ALIGN_JUSTIFY";
            case HSSFCellStyle.ALIGN_RIGHT:
                return "ALIGN_RIGHT";
            default:
                return "";
        }
    }
}
