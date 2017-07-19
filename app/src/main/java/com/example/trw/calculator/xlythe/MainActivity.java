package com.example.trw.calculator.xlythe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.trw.calculator.R;
import org.javia.arity.Symbols;
import org.javia.arity.SyntaxException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    int a;
    Symbols symbols = new Symbols();
    TextView txtView;
    EditText edtText;
    Button btn0, btn1, btn2, btn3, btn4,
            btn5, btn6, btn7, btn8, btn9,
            btnPlus, btnMinus, btnMultiply,
            btnDivide, btnDelete, btnDot, btnEnter;

    int num0, num1, num2, num3, num4,
            num5, num6, num7, num8, num9;

    private static final char PLUS = '+';
    private static final char MINUS = '-';
    private static final char MULTIPLY = '*';
    private static final char DIVIDE = '/';
    private static final char DOT = '.';

    private char CURRENT_ACTION;
    private final String REGEX_NUMBER = "\\d";
    private final String REGEX_NOT_NUMBER = "\\+|-|\\*|/";

    DecimalFormat formatterResult = new DecimalFormat("#,###.00");
    DecimalFormat formatterResult2 = new DecimalFormat("#,###");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initInstance();
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


        edtText.addTextChangedListener(onTextChangedListener());
        txtView.setText("0");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn0:
                num0 = 0;
                edtText.append(num0+"");
                getResult();
                break;
            case R.id.btn1:
                num1 = 1;
                edtText.append(num1+"");
                getResult();
                break;
            case R.id.btn2:
                num2 = 2;
                edtText.append(num2+"");
                getResult();
                break;
            case R.id.btn3:
                num3 = 3;
                edtText.append(num3+"");
                getResult();
                break;
            case R.id.btn4:
                num4 = 4;
                edtText.append(num4+"");
                getResult();
                break;
            case R.id.btn5:
                num5 = 5;
                edtText.append(num5+"");
                getResult();
                break;
            case R.id.btn6:
                num6 = 6;
                edtText.append(num6+"");
                getResult();
                break;
            case R.id.btn7:
                num7 = 7;
                edtText.append(num7+"");
                getResult();
                break;
            case R.id.btn8:
                num8 = 8;
                edtText.append(num8+"");
                getResult();
                break;
            case R.id.btn9:
                num9 = 9;
                edtText.append(num9+"");
                getResult();
                break;
            case R.id.btnPlus:
                CURRENT_ACTION = PLUS;
                changeOperator(CURRENT_ACTION);
                checkOperator(CURRENT_ACTION);
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
                break;
            case R.id.btnMultiply:
                CURRENT_ACTION = MULTIPLY;
                changeOperator(CURRENT_ACTION);
                checkOperator(CURRENT_ACTION);
                break;
            case R.id.btnDivide:
                CURRENT_ACTION = DIVIDE;
                changeOperator(CURRENT_ACTION);
                checkOperator(CURRENT_ACTION);
                break;
            case R.id.btnDot:
                CURRENT_ACTION = DOT;
                dotOperator(CURRENT_ACTION);
                break;
            case R.id.btnDelete:
                deleteText();
                getResult();
                break;
            case R.id.btnEnter:
                getResult();

                /*String text = edtText.getText().toString();
                Pattern pattern = Pattern.compile("\\d+(\\,\\d{1,100})?(\\.\\d{1,100})?|\\+|-|\\*|/");
                Matcher m = pattern.matcher("1+2-3*0.4/5");

                //Toast.makeText(this, "= " + m.find(), Toast.LENGTH_SHORT).show();
                int groupCount = m.groupCount();
                int myMatches = 0;

                while (m.find()) {
                    myMatches++;

                    for (int index = 0; index <= groupCount; index++) {
                        // Group index substring
                        if (index == 0) {
                            if (m.group(index).matches("\\+|-|\\*|/")) {
                                Toast.makeText(this, "= " + m.group(index), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }*/
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
                e.printStackTrace();
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
        if (textString.length() > 0) {
            edtText.setText(textString.substring(0, textString.length() - 1));
            setSelection();
        }
        else if (textString.length() == 0) {
            txtView.setText("0");
        }
    }

    private TextWatcher onTextChangedListener() {
        return new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                edtText.removeTextChangedListener(this);

                /*try {
                    String givenstring = s.toString();
                    Long longval;
                    if (givenstring.contains(",")) {
                        givenstring = givenstring.replaceAll(",", "");
                    }
                    longval = Long.parseLong(givenstring);
                    DecimalFormat formatter = new DecimalFormat("#,###,###");
                    String formattedString = formatter.format(longval);


                    edtText.setText(formattedString);
                    setSelection();
                    // to place the cursor at the end of text
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }*/

                String input = s.toString();
                if (input.contains(",")) {
                    input = input.replaceAll(",", "");
                }
                edtText.setText(formatText(input));
                setSelection();
                edtText.addTextChangedListener(this);
            }
        };
    }

    private String formatText(String input) {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String text = "";
        Pattern pattern = Pattern.compile("\\d+(\\.\\d{1,100})?|\\+|-|\\*|/|\\.?");
        Matcher m = pattern.matcher(input);
        //Toast.makeText(this, originalString + " = " + m.find(), Toast.LENGTH_SHORT).show();
        int groupCount = m.groupCount();
        int myMatches = 0;

        while (m.find()) {
            myMatches++;
            for (int index = 0; index <= groupCount; index++) {
                // Group index substring
                if (index == 0) {
                    if (m.group(index).matches("\\d+(\\.\\d{1,100})?")) {
                        Double numberFormat = Double.parseDouble(m.group(index));
                        text += formatter.format(numberFormat);
                        //Toast.makeText(MainActivity.this, " = "+ text, Toast.LENGTH_SHORT).show();
                    }
                    else if (m.group(index).matches("\\+|-|\\*|/")){
                        text += m.group(index);
                        //Toast.makeText(this, "Operations ! " + text, Toast.LENGTH_SHORT).show();
                    } else if (m.group(index).matches("\\.?")) {
                        text += m.group(index);
                    }
                    //Toast.makeText(this, "Group " + i + ": " + m.group(i), Toast.LENGTH_SHORT).show();
                }
            }
        }
        return text;
    }

    public void setSelection() {
        edtText.setSelection(edtText.getText().length());
    }


}
