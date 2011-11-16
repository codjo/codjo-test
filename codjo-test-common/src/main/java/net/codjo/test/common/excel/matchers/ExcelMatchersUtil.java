package net.codjo.test.common.excel.matchers;
import java.awt.Color;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.poi.hssf.util.HSSFColor;
/**
 *
 */
public class ExcelMatchersUtil {

    private static final Hashtable INDEX_TO_COLOR = HSSFColor.getIndexHash();

    private static final Map<String, HSSFColor> COLOR_MAP = buildNameToColorMap();


    public static String colorIndexToString(short colorIndex, boolean bbackgroundColorContext) {

        HSSFColor hssfColor = (HSSFColor)INDEX_TO_COLOR.get(new Integer(colorIndex));
// un test a montré qu'un text noir pouvait renvoyer un index > 32000 et donc introuvable dans la palette
        if (hssfColor == null) {
            if (!bbackgroundColorContext) {
                hssfColor = new HSSFColor.AUTOMATIC();
            }
            else {
                return "TRANSPARENT";
            }
        }

        return getColor(hssfColor);
    }


    private static String getColor(HSSFColor hssfColor) {
        for (Entry<String, HSSFColor> stringColorEntry : COLOR_MAP.entrySet()) {
            if (isEqual(hssfColor, stringColorEntry.getValue())) {
                return stringColorEntry.getKey();
            }
        }
//        final HSSFColor color = new HSSFColor(rgb[0], rgb[1], rgb[2]);
//        String bestColor = color.toString();
//        double bestHsbDistance = Double.MAX_VALUE;
//        for (Entry<String, HSSFColor> stringColorEntry : COLOR_MAP.entrySet()) {
//
//            double hsbDistance = computeHSBDistance(color, stringColorEntry.getValue());
//            if (bestHsbDistance > hsbDistance) {
//                bestColor = stringColorEntry.getKey();
//                bestHsbDistance = hsbDistance;
//            }
//        }

        return null;
    }


    private static boolean isEqual(HSSFColor hssfColor, HSSFColor value) {
        final short[] rgb1 = hssfColor.getTriplet();
        final short[] rgb2 = value.getTriplet();

        return rgb1[0] == rgb2[0] && rgb1[1] == rgb2[1] && rgb1[2] == rgb2[2];
    }


    private static double computeHSBDistance(HSSFColor expected, HSSFColor actual) {
        float[] expectedHSB = toHSB(expected);
        float[] actualHSB = toHSB(actual);

        return Math.sqrt(Math.pow((expectedHSB[0] - actualHSB[0]) * 20, 2)
                         + Math.pow(expectedHSB[1] - actualHSB[1], 2)
                         + Math.pow((expectedHSB[2] - actualHSB[2]) * 2, 2));
    }


    private static float[] toHSB(HSSFColor aColor) {
        final short[] rgb = aColor.getTriplet();
        return Color.RGBtoHSB(rgb[0], rgb[1], rgb[3], null);
    }


    private static Map<String, HSSFColor> buildNameToColorMap() {
        Map<String, HSSFColor> colorMap = new HashMap<String, HSSFColor>();
        Class[] classes = HSSFColor.class.getClasses();
        for (Class clazz : classes) {
            if (isConstantColorField(clazz)) {
                try {
                    HSSFColor color = (HSSFColor)clazz.getConstructor().newInstance();
                    colorMap.put(clazz.getSimpleName().toUpperCase(), color);
                }
                catch (Exception e) {
//                    Black hole ah ah ah
                }
            }
        }
        return colorMap;
    }


    private static boolean isConstantColorField(Class field) {
        return Modifier.isPublic(field.getModifiers()) && Modifier.isStatic(field.getModifiers())
               && field.getSuperclass() == HSSFColor.class;
    }
}
