package com.example.trw.calculator.xlythe;

import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by TRW on 24/7/2560.
 */

public class NumberCalculateHelper {

    public static final char PLUS = '+';
    public static final char MINUS = '-';
    public static final char MULTIPLY = '*';
    public static final char DIVIDE = '/';
    public static final char DOT = '.';

    public static DecimalFormatSymbols DECIMAL_FORMAT = new DecimalFormatSymbols();
    public static char DECIMAL_POINT = DECIMAL_FORMAT.getDecimalSeparator();
    public static char DECIMAL_SEPARATOR = DECIMAL_FORMAT.getGroupingSeparator();

    public static String number = "A-F0-9" +
            Pattern.quote(String.valueOf(DECIMAL_POINT)) +
            Pattern.quote(String.valueOf(DECIMAL_SEPARATOR));

    private static String REGEX_NUMBER = "[" + number + "]";
    private static String REGEX_NOT_NUMBER = "[^" + number + "]";

    private static Base mBase = Base.DECIMAL;

    public static final char SELECTION_HANDLE = '\u2620';
    private static final int mDecSeparatorDistance = 3;

    public static char getDecimalPoint() {
        return DECIMAL_POINT;
    }

    public static String deleteText(String value) {
        String textString = value;
        String originalString;
        String afterText;
        if (textString.length() > 0) {
            originalString = textString.substring(0, textString.length() - 1);

            if (originalString.contains(",")) {
                originalString = originalString.replaceAll(",", "");
                afterText = groupSentence(originalString, SELECTION_HANDLE);
                return afterText;
            } else {
                return originalString;
            }
        }
        else if (textString.length() == 0) {
            return null;
        }
        return null;
    }

    private static Object[] removeWhitespace(String[] strings) {
        ArrayList<String> formatted = new ArrayList<String>(strings.length);
        for(String s : strings) {
            if(s != null && !s.isEmpty()) formatted.add(s);
        }
        return formatted.toArray();
    }

    public static String groupSentence(String originalText, int selectionHandle) {
        if(originalText.isEmpty() || originalText.matches(REGEX_NOT_NUMBER)) return originalText;

        String[] operations = originalText.split(REGEX_NUMBER);
        String[] numbers = originalText.split(REGEX_NOT_NUMBER);
        String[] translatedNumbers = new String[numbers.length];
        for(int i = 0; i < numbers.length; i++) {
            if(!numbers[i].isEmpty()) {
                translatedNumbers[i] = groupDigits(numbers[i], mBase);
            }
        }
        String text = "";
        Object[] o = removeWhitespace(operations);
        Object[] n = removeWhitespace(translatedNumbers);
        if(originalText.substring(0, 1).matches(REGEX_NUMBER)) {
            for(int i = 0; i < o.length && i < n.length; i++) {
                text += n[i];
                text += o[i];
            }
        } else {
            for(int i = 0; i < o.length && i < n.length; i++) {
                text += o[i];
                text += n[i];
            }
        }
        if(o.length > n.length) {
            text += o[o.length - 1];
        } else if(n.length > o.length) {
            text += n[n.length - 1];
        }

        return text;
    }

    public static String groupDigits(String number, Base base) {
        String sign = "";
        if(isNegative(number)) {
            sign = String.valueOf(MINUS);
            number = number.substring(1);
        }
        String wholeNumber = number;
        String remainder = "";
        // We only group the whole number
        if(number.contains(getDecimalPoint()+"")) {
            if(!number.startsWith(getDecimalPoint()+"")) {
                String[] temp = number.split(Pattern.quote(getDecimalPoint()+""));
                wholeNumber = temp[0];
                remainder = getDecimalPoint() + ((temp.length == 1) ? "" : temp[1]);
            } else {
                wholeNumber = "";
                remainder = number;
            }
        }

        String modifiedNumber = group(wholeNumber, getSeparatorDistance(base), getSeparator(base));

        return sign + modifiedNumber + remainder;
    }

    private static String group(String wholeNumber, int spacing, char separator) {
        StringBuilder sb = new StringBuilder();
        int digitsSeen = 0;
        for (int i=wholeNumber.length()-1; i>=0; --i) {
            char curChar = wholeNumber.charAt(i);
            if (curChar != SELECTION_HANDLE) {
                if (digitsSeen > 0 && digitsSeen % spacing == 0) {
                    sb.insert(0, separator);
                }
                ++digitsSeen;
            }
            sb.insert(0, curChar);
        }
        return sb.toString();
    }

    private static int getSeparatorDistance(Base base) {
        switch(base) {
            case DECIMAL:
                return getDecSeparatorDistance();
            default:
                return -1;
        }
    }

    public static int getDecSeparatorDistance() {
        return mDecSeparatorDistance;
    }


    public static char getSeparator(Base base) {
        switch(base) {
            case DECIMAL:
                return getDecSeparator();
            default:
                return 0;
        }
    }

    public static char getDecSeparator() {
        return DECIMAL_SEPARATOR;
    }

    public static boolean isNegative(String number) {
        return number.startsWith(String.valueOf(MINUS)) || number.startsWith("-");
    }

    public enum Base {
        DECIMAL(10);

        int quickSerializable;

        Base(int num) {
            this.quickSerializable = num;
        }

    }

}
