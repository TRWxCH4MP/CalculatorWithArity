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

    String input;
    Symbols symbols = new Symbols();
    TextView txtView;
    EditText edtText;
    Button btn0, btn1, btn2, btn3, btn4,
            btn5, btn6, btn7, btn8, btn9,
            btnPlus, btnMinus, btnMultiply,
            btnDivide, btnDelete, btnDot, btnEnter;


    private char CURRENT_ACTION;

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
                CURRENT_ACTION = NumberCalculateHelper.PLUS;
                changeOperator(CURRENT_ACTION);
                checkOperator(CURRENT_ACTION);
                setSelection();
                break;
            case R.id.btnMinus:
                CURRENT_ACTION = NumberCalculateHelper.MINUS;
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
                CURRENT_ACTION = NumberCalculateHelper.MULTIPLY;
                changeOperator(CURRENT_ACTION);
                checkOperator(CURRENT_ACTION);
                setSelection();
                break;
            case R.id.btnDivide:
                CURRENT_ACTION = NumberCalculateHelper.DIVIDE;
                changeOperator(CURRENT_ACTION);
                checkOperator(CURRENT_ACTION);
                setSelection();
                break;
            case R.id.btnDot:
                CURRENT_ACTION = NumberCalculateHelper.DOT;
                dotOperator(CURRENT_ACTION);
                setSelection();
                break;
            case R.id.btnDelete:
                onTextDelete();
                getResult();
                break;
            case R.id.btnEnter:
                getResult();

                String text = "1500+0.5+1000000";
                String result = NumberCalculateHelper.groupSentence(text, NumberCalculateHelper.SELECTION_HANDLE);
                Toast.makeText(this, "= "+ result, Toast.LENGTH_SHORT).show();
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
                onTextDelete();
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

    private void onTextDelete(){
        String value = edtText.getText().toString();
        String result = NumberCalculateHelper.deleteText(value);
        if (result != null) {
            edtText.setText(result);
        }
        else {
            edtText.setText(null);
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
        String afterText = NumberCalculateHelper.groupSentence(text, NumberCalculateHelper.SELECTION_HANDLE);
        edtText.setText(afterText);
        getResult();
        setSelection();
        return null;
    }









}
