package net.codjo.test.common.excel;
/**
 *
 */
public class ExcelMatchingException extends RuntimeException {
    public ExcelMatchingException(String message, Exception e) {
        super(message,e);
    }


    public ExcelMatchingException(String message) {
        super(message);
    }
}
