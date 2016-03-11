package com.nacho.cuadraticequations;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {


    //CustomKeyboard mCustomKeyboard;
    //KeyboardView mKeyboardView;
    EditText enter;
    TextView out;
    Button calculate;
    RelativeLayout pantalla;
    private CustomKeyboardView mKeyboardView;
    private Keyboard mKeyboard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Create the Keyboard
        pantalla = (RelativeLayout)findViewById(R.id.pantalla);
        enter = (EditText)findViewById(R.id.inEquation);
        out = (TextView)findViewById(R.id.out);
        calculate = (Button)findViewById(R.id.calculate);

        mKeyboard = new Keyboard(this, R.xml.equationkeyboard);
        enter.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Dobbiamo intercettare l'evento onTouch in modo da aprire la
                // nostra tastiera e prevenire che venga aperta quella di
                // Android
                showKeyboardWithAnimation();
                return true;
            }
        });

        mKeyboardView = (CustomKeyboardView) findViewById(R.id.keyboard_view);
        mKeyboardView.setTargetActivity(this);
        mKeyboardView.setKeyboard(mKeyboard);
        mKeyboardView.setOnKeyboardActionListener(new BasicOnKeyboardActionListener(this, mKeyboardView));
        mKeyboardView.registerEditText(R.id.inEquation);

        /*TableLayout tabla = (TableLayout)findViewById(R.id.tabla);
        TableRow tr1 = (TableRow)findViewById(R.id.tr1);
        int ancho = tr1.getWidth()/10;
        for(int i = 0; i < 10; i ++) {
            Button b = new Button(this);
            b.setWidth(ancho);
            b.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
            b.setHeight(ActionBar.LayoutParams.MATCH_PARENT);
            b.setText("" + i);
            tr1.addView(b);
        }
*/
        /*final Dialog dialog = new Dialog(getApplicationContext());
        mCustomKeyboard= new CustomKeyboard(this, R.id.keyboard, R.xml.qwerty );
        mCustomKeyboard.mKeyboardView= (KeyboardView)dialog.findViewById(R.id.keyboard);
        mCustomKeyboard.mKeyboardView.setKeyboard( new Keyboard(this,R.layout.keyboard) );
        mCustomKeyboard.mKeyboardView.setOnKeyboardActionListener(mCustomKeyboard.mOnKeyboardActionListener);
        mCustomKeyboard.registerEditText(R.id.inEquation);
        dialog.setContentView(R.layout.keyboard);
    enter.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            dialog.show();
        }
    });*/


        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //makeCalc(enter.getText().toString());
                mKeyboardView.hideCustomKeyboard();
                out.setText(makeCalc(enter.getText().toString()));
            }
        });

        /*nputMethodManager inputMethodManager = (InputMethodManager)this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        InputMethodSubtype inputMethod = inputMethodManager.getCurrentInputMethodSubtype();
        Toast.makeText(getApplicationContext(), inputMethod.toString(), Toast.LENGTH_LONG).show();
*/
        //inputMethodManager.setInputMethodAndSubtype(null, "Nacho IME", );
        //inputMethodManager.setInputMethod(null, "NachoIME"


    }

    public void outResult() {
        out.setText(makeCalc(enter.getText().toString()));
    }

    private void showKeyboardWithAnimation() {
        if (mKeyboardView.getVisibility() == View.GONE) {
            Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_in_bottom);
            mKeyboardView.showWithAnimation(animation);
        }
    }


    private int number(char c) {
        int dif = (int) c - (int) '0';
        if((dif >= 0) && (dif < 10)) {
            return dif;
        }
        return -1;
    }
    private String makeCalc(String ent) {
        if((ent == null) || ent.equals("")) {return "input equation is empty";}
        char[] s = ent.toCharArray();
        // Bucle identificador del lenguaje...
        int estado = 0; // estado inicial
        int coefTemp = 0; // coeficiente temporal
        int b = 0, a = 0, c = 0;
        byte signo = 1;
        //int a = 0,b = 0, c = 0;
        int i = 0;
        //for(int i = 0; i < s.length; i ++) {
        while(i < s.length) {
            char sym = s[i];
            if(sym == ' ') {
                i++;
                continue;
            }
            if(estado == 0) {
                if(sym == '-') {
                    c += coefTemp * signo; // termino independiente
                    coefTemp = 0;
                    signo = -1;
                    estado = 1;
                    i++;
                    continue;
                }
                else if(sym == '+') {
                    c += coefTemp * signo; // termino independiente
                    coefTemp = 0;
                    signo = 1;
                    estado = 1;
                    i++;
                    continue;
                }
                else if(sym == 'x') {
                    if(coefTemp == 0) {
                        coefTemp = 1;
                    }
                    estado = 2;
                    i++;
                    continue;
                }
                int cifra = number(sym);
                if(cifra < 0) {
                    estado = 4;
                    continue;
                }
                else {
                    //sumandoNulo = true;
                    coefTemp = coefTemp * 10 + cifra;
                    i++;
                    continue;
                }
            }
            else if(estado == 1) {
                if(sym == 'x') {
                    if(coefTemp == 0) {
                        coefTemp = 1;
                    }
                    estado = 2;
                    i++;
                    continue;
                }
                int cifra = number(sym);
                if(cifra < 0) {
                    estado = 4;
                    continue;
                }
                else {
                    estado = 0;
                    coefTemp = coefTemp * 10 + cifra;
                    i++;
                    continue;
                }
            }

            else if(estado == 2) {
                if(sym == '2') {
                    estado = 3;
                    i++;
                    continue;
                }
                else if(sym == '+') {
                    b += coefTemp * signo; // termino independiente
                    coefTemp = 0;
                    signo = 1;
                    estado = 0;
                    i++;
                    continue;
                }
                else if(sym == '-') {
                    b += coefTemp * signo; // termino independiente
                    coefTemp = 0;
                    signo = -1;
                    estado = 0;
                    i++;
                    continue;
                }
                else {
                    estado = 4;
                    continue;
                }
            }

            else if(estado == 3) {
                if(sym == '+') {
                    a += coefTemp * signo; // termino independiente
                    coefTemp = 0;
                    signo = 1;
                    estado = 0;
                    i++;
                    continue;
                }
                else if(sym == '-') {
                    a += coefTemp * signo; // termino independiente
                    coefTemp = 0;
                    signo = -1;
                    estado = 0;
                    i++;
                    continue;
                }
                else {
                    estado = 4;
                    continue;
                }
            }

            else if(estado == 4) {
                return "error in input at position " + (i+1) + ": " + "\'" + sym + "\'";
            }
        }
        if(coefTemp != 0) {
            if(estado == 0) {
                c += coefTemp * signo;
            }
            else if(estado == 2) {
                b += coefTemp * signo;
            }
            else if(estado == 3) {
                a += coefTemp * signo;
            }
        }
        if((a == 0) && (b == 0)) {
            return "That is not an equation...";
        }
        Imaginary[] soluciones = equationSquare(a,b,c);
        StringBuilder sb = new StringBuilder();
        sb.append("Parameters: a = " + a + ", b = " + b + ", c = " + c + "\n");
        sb.append("Number of solutions: " + soluciones.length);
        sb.append("\nSolution 1: " + soluciones[0]);
        if(soluciones.length > 1) {
            sb.append("\nSolution 2: " + soluciones[1]);
        }
        return sb.toString();
        //return "" + a + ", " + b + ", " + c + "; " + estado +"; " + i;
    }

    static class Imaginary {
        double realPart, imagPart;
        public Imaginary(double r, double i) {
            realPart = r;
            imagPart = i;
        }
        public Imaginary(double det) {
            if(det < 0) {
                imagPart = Math.sqrt((-1)*det);
            }
            else {
                realPart = Math.sqrt(det);
            }
        }

        Imaginary substract(double d) {
            return new Imaginary(d-realPart, -imagPart);
        }

        Imaginary add(double d) {
            return new Imaginary(realPart + d, imagPart);
        }

        Imaginary divide(double d) {
            return new Imaginary(realPart/d, imagPart/d);
        }

        Imaginary mult(double d) {
            return new Imaginary(realPart*d, imagPart*d);
        }

        public String toString() {
            if(imagPart == 0) {
                return "" + realPart;
            }
            return "" + realPart + ((imagPart < 0) ? " - " : " + ") + Math.abs(imagPart) +"i";
        }
    }

    private Imaginary[] equationSquare(int a, int b, int c) {
        Imaginary[] img;
        if(a == 0) {
            img = new Imaginary[1];
            img[0] = new Imaginary((double)-c/b, 0);
            return img;
        }
        double det = Math.pow(b, 2) - 4*a*c;
        if(det != 0) {
            img = new Imaginary[2];
            Imaginary im = new Imaginary(det);
            img[0] = im.add(-b).divide(2*a);
            im = new Imaginary(det);
            img[1] = im.substract(-b).divide(2*a);
        }
        else {
            img = new Imaginary[1];
            img[0] = new Imaginary(-b/2/a, 0);
        }
        return img;
    }

    @Override
    public void onBackPressed() {
        if(mKeyboardView.getVisibility() == View.VISIBLE) {
            mKeyboardView.hideCustomKeyboard();
        }
        else {
            this.finish();
        }
    }
}