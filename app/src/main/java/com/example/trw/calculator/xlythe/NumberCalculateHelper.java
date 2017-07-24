package com.example.trw.calculator.xlythe;

import org.javia.arity.Symbols;
import org.javia.arity.SyntaxException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Created by TRW on 24/7/2560.
 */

public class NumberCalculateHelper {

    static Symbols symbols = new Symbols();

    static DecimalFormat formatterResult = new DecimalFormat("#,###.00");
    static DecimalFormat formatterResult2 = new DecimalFormat("#,###");

    public static final String PLUS = "+";
    public static final String MINUS = "-";
    public static final String MULTIPLY = "*";
    public static final String DIVIDE = "/";
    public static final String DOT = ".";

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

    public enum Base {
        DECIMAL(10);

        int quickSerializable;

        Base(int num) {
            this.quickSerializable = num;
        }

    }
    public static char getDecimalPoint() {
        return DECIMAL_POINT;
    }

    public static String changeOperator(String value, String operator) {
        ArrayList<String> split1 = new ArrayList<>(Arrays.asList(value.split("")));
        int size;
        int index;
        size = split1.size();
        index = size - 1;
        if (split1.get(index).equals("+") ||
                split1.get(index).equals("-") ||
                split1.get(index).equals("*") ||
                split1.get(index).equals("/")) {
            if (value.length() != 1) {
                value = deleteText(value);
                return String.format("%s%s", value, operator);
            }
        }
        return null;
    }

    public static String checkNumberInsert(String value, String operator) {
        int sizeSplit;
        int index;
        int index2;
        ArrayList<String> split1 = new ArrayList<>(Arrays.asList(value.split("")));
        try {
            sizeSplit = split1.size();
            index = sizeSplit - 1;
            index2 = Integer.parseInt(split1.get(index));

            if (index2 >= 0) {
                return String.format("%s%s", value, operator);
            }
        } catch (NumberFormatException ignored) {
        }
        return null;
    }

    public static String deleteText(String value) {
        String originalString;
        String formatText;
        try {
            if (value.length() > 0) {
                originalString = value.substring(0, value.length() - 1);

                if (originalString.contains(",")) {
                    originalString = originalString.replaceAll(",", "");
                    formatText = groupSentence(originalString, NumberCalculateHelper.SELECTION_HANDLE);
                    return formatText;
                } else {
                    return originalString;
                }
            } else if (value.length() == 0) {
                return null;
            }
        } catch (NullPointerException ignored) {
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

    public static String getResult(String value) {
        double decimal;
        String str = value.replaceAll(",", "");
        if (str.length() == 0) {
            return null;
        } else {
            try {
                decimal = symbols.eval(str);
            } catch (SyntaxException ex) {
                return null;
            }
            if (decimal % 1 != 0) {
                String result = formatterResult.format(decimal);
                return result;
            } else {
                String result = formatterResult2.format(decimal);
                return result;
            }
        }
    }

}
