package com.example.trw.calculator.xlythe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.trw.calculator.R;
import org.javia.arity.Symbols;
import org.javia.arity.SyntaxException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private DecimalFormatSymbols DECIMAL_FORMAT;
    public char DECIMAL_POINT;
    public char DECIMAL_SEPARATOR;

    private String REGEX_NUMBER;
    private String REGEX_NOT_NUMBER;

    private Base mBase = Base.DECIMAL;

    public final char SELECTION_HANDLE = '\u2620';
    private final int mDecSeparatorDistance = 3;

    public char getDecimalPoint() {
        return DECIMAL_POINT;
    }

    String input;
    Symbols symbols = new Symbols();
    TextView txtView;
    EditText edtText;
    Button btn0, btn1, btn2, btn3, btn4,
            btn5, btn6, btn7, btn8, btn9,
            btnPlus, btnMinus, btnMultiply,
            btnDivide, btnDelete, btnDot, btnEnter;


    private static final char PLUS = '+';
    private static final char MINUS = '-';
    private static final char MULTIPLY = '*';
    private static final char DIVIDE = '/';
    private static final char DOT = '.';

    private char CURRENT_ACTION;

    DecimalFormat formatterResult = new DecimalFormat("#,###.00");
    DecimalFormat formatterResult2 = new DecimalFormat("#,###");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initInstance();
        rebuildConstants();
        
    }
    public void initInstance() {

        txtView = (TextView) (findViewById(R.id.txtView));
        edtText = (EditText) (findViewById(R.id.edtInput));
        btn0 = (Button) (findViewById(R.id.btn0));
        btn1 = (Button) (findViewById(R.id.btn1));
        btn2 = (Button) (findViewById(R.id.btn2));
        btn3 = (Button) (findViewById(R.id.btn3));
        btn4 = (Button) (findViewById(R.id.btn4));
        btn5 = (Button) (findViewById(R.id.btn5));
        btn6 = (Button) (findViewById(R.id.btn6));
        btn7 = (Button) (findViewById(R.id.btn7));
        btn8 = (Button) (findViewById(R.id.btn8));
        btn9 = (Button) (findViewById(R.id.btn9));
        btnPlus = (Button) (findViewById(R.id.btnPlus));
        btnMinus = (Button) (findViewById(R.id.btnMinus));
        btnMultiply = (Button) (findViewById(R.id.btnMultiply));
        btnDivide = (Button) (findViewById(R.id.btnDivide));
        btnDelete = (Button) (findViewById(R.id.btnDelete));
        btnDot = (Button) (findViewById(R.id.btnDot));
        btnEnter = (Button) (findViewById(R.id.btnEnter));

        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btnPlus.setOnClickListener(this);
        btnMinus.setOnClickListener(this);
        btnMultiply.setOnClickListener(this);
        btnDivide.setOnClickListener(this);
        btnDot.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnEnter.setOnClickListener(this);

        txtView.setText("0");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn0:
                input = "0";
                formatText(input);
                break;
            case R.id.btn1:
                input = "1";
                formatText(input);
                break;
            case R.id.btn2:
                input = "2";
                formatText(input);
                break;
            case R.id.btn3:
                input = "3";
                formatText(input);
                break;
            case R.id.btn4:
                input = "4";
                formatText(input);
                break;
            case R.id.btn5:
                input = "5";
                formatText(input);
                break;
            case R.id.btn6:
                input = "6";
                formatText(input);
                break;
            case R.id.btn7:
                input = "7";
                formatText(input);
                break;
            case R.id.btn8:
                input = "8";
                formatText(input);
                break;
            case R.id.btn9:
                input = "9";
                formatText(input);
                break;
            case R.id.btnPlus:
                CURRENT_ACTION = PLUS;
                changeOperator(CURRENT_ACTION);
                checkOperator(CURRENT_ACTION);
                setSelection();
                break;
            case R.id.btnMinus:
                CURRENT_ACTION = MINUS;
                if (edtText.getText().toString().length() <= 0) {
                    edtText.setText(edtText.getText().toString() + CURRENT_ACTION);
                }
                else {
                    changeOperator(CURRENT_ACTION);
                    checkOperator(CURRENT_ACTION);
                }
                setSelection();
                break;
            case R.id.btnMultiply:
                CURRENT_ACTION = MULTIPLY;
                changeOperator(CURRENT_ACTION);
                checkOperator(CURRENT_ACTION);
                setSelection();
                break;
            case R.id.btnDivide:
                CURRENT_ACTION = DIVIDE;
                changeOperator(CURRENT_ACTION);
                checkOperator(CURRENT_ACTION);
                setSelection();
                break;
            case R.id.btnDot:
                CURRENT_ACTION = DOT;
                dotOperator(CURRENT_ACTION);
                setSelection();
                break;
            case R.id.btnDelete:
                deleteText();
                getResult();
                break;
            case R.id.btnEnter:
                getResult();

                /*String text = "1500+0.5+1000000";
                String result = groupSentence(text, SELECTION_HANDLE);
                Toast.makeText(this, "= "+ result, Toast.LENGTH_SHORT).show();*/
                break;
        }
    }

    private void dotOperator(char operator) {
        char action = operator;
        ArrayList<String> ls = new ArrayList<>(Arrays.asList(edtText.getText().toString().split("")));
        if (edtText.getText().toString().length() <= 0) {
            edtText.setText(edtText.getText().toString() + "0"+action);
        }
        else if (ls.get(ls.size()-1).equals("+") ||
                ls.get(ls.size()-1).equals("-") ||
                ls.get(ls.size()-1).equals("*") ||
                ls.get(ls.size()-1).equals("/") ) {
            edtText.setText(edtText.getText().toString() + "0"+action);

        }
        else {
            checkOperator(action);
        }
    }

    private void changeOperator(char operator) {
        char action = operator;
        String str = edtText.getText().toString();
        ArrayList<String> split1 = new ArrayList<>(Arrays.asList(str.split("")));
        int size = 0;
        int index = 0;
        size = split1.size();
        index = size-1 ;
        if (split1.get(index).equals("+") ||
                split1.get(index).equals("-") ||
                split1.get(index).equals("*") ||
                split1.get(index).equals("/")) {
            if (str.length() != 1) {
                deleteText();
                edtText.setText(edtText.getText().toString() + action);
            }
        }
    }

    private void checkOperator(char operator) {
        char action = operator;
        String string1 = edtText.getText().toString();
        int sizeSplit = 0;
        int index = 0;
        int index2 = 0;
        ArrayList<String> split1 = new ArrayList<>(Arrays.asList(string1.split("")));

        try {
            sizeSplit = split1.size();
            index = sizeSplit-1;
            index2 = Integer.parseInt(split1.get(index));

            if (index2 >= 0) {
                edtText.setText(edtText.getText().toString() + action);
            }
            else {
                Toast.makeText(this, "cannot input !", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException ex) {}
    }

    private void getResult(){
        double decimal = 0;
        String str = edtText.getText().toString().replaceAll(",", "");
        if (str.length() == 0) {
            txtView.setText("0");
        }
        else {
            try {
                decimal = symbols.eval(str);
            } catch (SyntaxException e) {
                Toast.makeText(this, "cannot calculate !", Toast.LENGTH_SHORT).show();
            }
            if ( decimal % 1 != 0) {
                String result = formatterResult.format(decimal);
                txtView.setText(result);
            }
            else {
                String result = formatterResult2.format(decimal);
                txtView.setText(result);
            }
        }
    }

    private void deleteText() {
        String textString = edtText.getText().toString();
        String originalString;
        String afterText;
        if (textString.length() > 0) {
            originalString = textString.substring(0, textString.length() - 1);

            if (originalString.contains(",")) {
                originalString = originalString.replaceAll(",", "");
                afterText = groupSentence(originalString, SELECTION_HANDLE);
                edtText.setText(afterText);
            } else {
                edtText.setText(originalString);
            }
        }
        else if (textString.length() == 0) {
            txtView.setText("0");
        }
        setSelection();
    }

    public void setSelection() {
        edtText.setSelection(edtText.getText().length());
    }

    private String formatText(String input) {
        edtText.append(input);
        String text = edtText.getText().toString();
        if (text.contains(",")) {
            text = text.replaceAll(",", "");
        }
        String afterText = groupSentence(text, SELECTION_HANDLE);
        edtText.setText(afterText);
        getResult();
        setSelection();
        return null;
    }

    private Object[] removeWhitespace(String[] strings) {
        ArrayList<String> formatted = new ArrayList<String>(strings.length);
        for(String s : strings) {
            if(s != null && !s.isEmpty()) formatted.add(s);
        }
        return formatted.toArray();
    }

    public String groupSentence(String originalText, int selectionHandle) {
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

    public String groupDigits(String number, Base base) {
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

    private String group(String wholeNumber, int spacing, char separator) {
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

    private int getSeparatorDistance(Base base) {
        switch(base) {
            case DECIMAL:
                return getDecSeparatorDistance();
            default:
                return -1;
        }
    }

    public int getDecSeparatorDistance() {
        return mDecSeparatorDistance;
    }


    public char getSeparator(Base base) {
        switch(base) {
            case DECIMAL:
                return getDecSeparator();
            default:
                return 0;
        }
    }

    public char getDecSeparator() {
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
    public  void rebuildConstants() {
        DECIMAL_FORMAT = new DecimalFormatSymbols();

        // These will already be known by Java
        DECIMAL_POINT = DECIMAL_FORMAT.getDecimalSeparator();
        DECIMAL_SEPARATOR = DECIMAL_FORMAT.getGroupingSeparator();

        String number = "A-F0-9" +
                Pattern.quote(String.valueOf(DECIMAL_POINT)) +
                Pattern.quote(String.valueOf(DECIMAL_SEPARATOR));

        REGEX_NUMBER = "[" + number + "]";
        REGEX_NOT_NUMBER = "[^" + number + "]";
    }

}
