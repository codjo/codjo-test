package net.codjo.test.common.excel.matchers;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
/**
 *
 */
public class BorderCellStyleStringifier implements CellStringifier {

    public String toString(Cell cell) {
        HSSFCellStyle style = cell.getPoiCell().getCellStyle();

        return new StringBuffer()
              .append("T=").append(borderToString(style.getBorderTop()))
              .append("/")
              .append("B=").append(borderToString(style.getBorderBottom()))
              .append("/")
              .append("L=").append(borderToString(style.getBorderLeft()))
              .append("/")
              .append("R=").append(borderToString(style.getBorderRight()))
              .toString();
    }


    @SuppressWarnings({"OverlyComplexMethod"})
    private String borderToString(short borderType) {
        switch (borderType) {
            case HSSFCellStyle.BORDER_DASH_DOT:
                return "BORDER_DASH_DOT";
            case HSSFCellStyle.BORDER_DASH_DOT_DOT:
                return "BORDER_DASH_DOT_DOT";
            case HSSFCellStyle.BORDER_DASHED:
                return "BORDER_DASHED";
            case HSSFCellStyle.BORDER_DOTTED:
                return "BORDER_DOTTED";
            case HSSFCellStyle.BORDER_DOUBLE:
                return "BORDER_DOUBLE";
            case HSSFCellStyle.BORDER_HAIR:
                return "BORDER_HAIR";
            case HSSFCellStyle.BORDER_MEDIUM:
                return "BORDER_MEDIUM";
            case HSSFCellStyle.BORDER_MEDIUM_DASH_DOT:
                return "BORDER_MEDIUM_DASH_DOT";
            case HSSFCellStyle.BORDER_MEDIUM_DASH_DOT_DOT:
                return "BORDER_MEDIUM_DASH_DOT_DOT";
            case HSSFCellStyle.BORDER_MEDIUM_DASHED:
                return "BORDER_MEDIUM_DASHED";
            case HSSFCellStyle.BORDER_NONE:
                return "BORDER_NONE";
            case HSSFCellStyle.BORDER_SLANTED_DASH_DOT:
                return "BORDER_SLANTED_DASH_DOT";
            case HSSFCellStyle.BORDER_THICK:
                return "BORDER_THICK";
            case HSSFCellStyle.BORDER_THIN:
                return "BORDER_THIN";
            default:
                return "";
        }
    }
}
