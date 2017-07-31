package com.example.trw.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String input;
    TextView txtView;
    EditText edtText;
    Button btn0, btn1, btn2, btn3, btn4,
            btn5, btn6, btn7, btn8, btn9,
            btnPlus, btnMinus, btnMultiply,
            btnDivide, btnDelete, btnDot, btnEnter;

    public static String CURRENT_ACTION;

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
                onOperatorChanged(CURRENT_ACTION);
                onNumberValueChanged(CURRENT_ACTION);
                setSelection();
                break;
            case R.id.btnMinus:
                CURRENT_ACTION = NumberCalculateHelper.MINUS;
                onOperatorChanged(CURRENT_ACTION);
                onNumberValueChanged(CURRENT_ACTION);
                setSelection();
                break;
            case R.id.btnMultiply:
                CURRENT_ACTION = NumberCalculateHelper.MULTIPLY;
                onOperatorChanged(CURRENT_ACTION);
                onNumberValueChanged(CURRENT_ACTION);
                setSelection();
                break;
            case R.id.btnDivide:
                CURRENT_ACTION = NumberCalculateHelper.DIVIDE;
                onOperatorChanged(CURRENT_ACTION);
                onNumberValueChanged(CURRENT_ACTION);
                setSelection();
                break;
            case R.id.btnDot:
                CURRENT_ACTION = NumberCalculateHelper.DOT;
                dotOperator(CURRENT_ACTION);
                setSelection();
                break;
            case R.id.btnDelete:
                onTextDeleted();
                break;
            case R.id.btnEnter:
                String result = NumberCalculateHelper.getResult(edtText.getText().toString());
                showResult(result);
                break;
        }
    }

    private void formatText(String input) {
        String text = edtText.getText().toString() + input;
        if (text.contains(",")) {
            text = text.replaceAll(",", "");
        }
        text = NumberCalculateHelper.groupSentence(text);
        edtText.setText(text);
        String result = NumberCalculateHelper.getResult(text);
        showResult(result);
        setSelection();
    }

    private void dotOperator(String operator) {
        String value = edtText.getText().toString();
        String result = NumberCalculateHelper.checkInsertDot(value, operator);

        if (result != null) {
            edtText.setText(result);
        }
    }

    private void onOperatorChanged(String operator) {
        String value = edtText.getText().toString();
        String result = NumberCalculateHelper.changeOperator(value, operator);
        if (result != null) {
            edtText.setText(result);
        }
    }

    private void onNumberValueChanged(String operator) {
        String value = edtText.getText().toString();
        String result = NumberCalculateHelper.checkNumberInsert(value, operator);
        if (result != null) {
            edtText.setText(result);
        }
    }

    private void showResult(String result) {
        if (result != null) {
            txtView.setText(result);
        } else {
            Toast.makeText(this, R.string.CannotCalculate, Toast.LENGTH_SHORT).show();
        }
    }

    private void onTextDeleted() {
        String value = edtText.getText().toString();
        String result = NumberCalculateHelper.deleteText(value);
        if (result != null) {
            edtText.setText(result);
            String showResult = NumberCalculateHelper.getResult(result);
            showResult(showResult);
            setSelection();
        } else {
            txtView.setText("0");
        }
    }

    public void setSelection() {
        edtText.setSelection(edtText.getText().length());
    }

}
