package net.codjo.test.common.excel.matchers;
import net.codjo.test.common.excel.ExcelMatchingException;
import net.codjo.test.common.excel.ExcelUtil;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;

public class CellValueStringifier implements CellStringifier {
    private NumberFormat numberFormat = NumberFormat.getInstance(Locale.FRENCH);
    private static final String RELATIVE_DATE_REGEXP
          = "(.*)(\\$\\{TODAY\\})(([+-])([1-9][0-9]*)([DMY]))?(.*)";
    //    private static final String RELATIVE_DATE_REGEXP = "(.*)(\\$)(.*)";
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final int BEFORE_TODAY_GROUP_REGEXP = 1;
    private static final int AFTER_TODAY_GROUP_REGEXP = 7;
    private static final int AMOUNT_GROUP_REGEXP = 5;
    private static final int SIGN_GROUP_REGEXP = 4;
    private static final int DATE_FIELD_GROUP_REGEXP = 6;


    public String toString(Cell cell) {
        return toString(cell.getPoiCell());
    }


    private String getDateCellStringValue(HSSFCell cell) {
        String dateString;
        SimpleDateFormat sdf;

        if (cell.getCellStyle().getDataFormat() == 14) {
            sdf = new SimpleDateFormat("dd/MM/yyyy");
            dateString = sdf.format(cell.getDateCellValue());
        }
        else if (cell.getCellStyle().getDataFormat() == 181) {
            sdf = new SimpleDateFormat("dd/MM/yy");
            dateString = sdf.format(cell.getDateCellValue());
        }
        else {
            dateString = cell.getDateCellValue().toString();
        }

        return dateString;
    }


    public String toString(HSSFCell cell) {
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_NUMERIC:
                // if cell format is "date"
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    return getDateCellStringValue(cell);
                }
                // if cell format is "standard"
                else if (0 == cell.getCellStyle().getDataFormat()) {
                    return numberFormat.format(cell.getNumericCellValue());
                }
                // if cell format is "number"
                else {
                    NumberFormat myFormat = new DecimalFormat(cell.getCellStyle().getDataFormatString());
                    return myFormat.format(cell.getNumericCellValue());
                }
            case HSSFCell.CELL_TYPE_STRING:
                return computeDynamicVariable(cell.getStringCellValue().trim());
            case HSSFCell.CELL_TYPE_BLANK:
                return "";
            case HSSFCell.CELL_TYPE_FORMULA:
                return computeFormula(cell);
            default:
                return null;
        }
    }


    private String computeFormula(HSSFCell cell) {
        try {
            if (!ExcelUtil.isNumericCellValue(cell)) {
                return cell.getStringCellValue().trim();
            }

            // if cell format is "standard"
            if (cell.getCellStyle().getDataFormat() == 0) {
                return numberFormat.format(cell.getNumericCellValue());
            }
            // if cell format is "number"
            else {
                NumberFormat myFormat = new DecimalFormat(
                      cell.getCellStyle().getDataFormatString());
                return myFormat.format(cell.getNumericCellValue());
            }
        }
        catch (NumberFormatException nfe) {
            return cell.getStringCellValue().trim();
        }
        catch (Exception e) {
            throw new ExcelMatchingException("Erreur interne lors de la conversion d"
                                             + "'une cellule Excel en String", e);
        }
    }


    private String computeDynamicVariable(String stringValue) {
        if (isDateRelative(stringValue)) {
            return computeRelativeDate(stringValue);
        }
        return stringValue;
    }


    public boolean isDateRelative(String stringValue) {
        Matcher relativeDateMatcher = initRelativeDateMatcher(stringValue);
        return relativeDateMatcher.matches();
    }


    private Matcher initRelativeDateMatcher(String stringValue) {
        Pattern relativeDatePattern = Pattern.compile(RELATIVE_DATE_REGEXP);
        return relativeDatePattern.matcher(stringValue);
    }


    public String computeRelativeDate(String stringValue) {

        Matcher relativeDateMatcher = initRelativeDateMatcher(stringValue);
        relativeDateMatcher.matches();

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());

        calendar.add(getDateField(relativeDateMatcher), getAmount(relativeDateMatcher));

        String relativeDateString = new SimpleDateFormat(DATE_FORMAT).format(calendar.getTime());
        String beforeDateGroup = relativeDateMatcher.group(BEFORE_TODAY_GROUP_REGEXP);
        String afterDateGroup = relativeDateMatcher.group(AFTER_TODAY_GROUP_REGEXP);

        return beforeDateGroup + relativeDateString + afterDateGroup;
    }


    private int getAmount(Matcher relativeDateMatcher) {
        String amountGroup = relativeDateMatcher.group(AMOUNT_GROUP_REGEXP);
        String sign = relativeDateMatcher.group(SIGN_GROUP_REGEXP);

        if (amountGroup != null && sign != null) {
            int amount = Integer.parseInt(amountGroup);
            if ("-".equalsIgnoreCase(sign)) {
                return -amount;
            }
            else {
                return amount;
            }
        }
        return 0;
    }


    private int getDateField(Matcher matcher) {
        String dateField = matcher.group(DATE_FIELD_GROUP_REGEXP);

        if ("D".equals(dateField)) {
            return Calendar.DATE;
        }
        else if ("M".equalsIgnoreCase(dateField)) {
            return Calendar.MONTH;
        }
        else {
            return Calendar.YEAR;
        }
    }
}
