package com.nacho.cuadraticequations;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.KeyboardView;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by nacho on 06/05/2015.
 */
public class CustomKeyboardView extends KeyboardView {
    private Activity mTargetActivity;
    public CustomKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setTargetActivity(Activity activity) {
        mTargetActivity = activity;
    }

    public void showWithAnimation(Animation animation) {
        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setVisibility(View.VISIBLE);
            }


        });

        setAnimation(animation);
    }

    public void hideCustomKeyboard() {
        setVisibility(View.GONE);
        setEnabled(false);
    }
    public void showCustomKeyboard( View v ) {
        setVisibility(View.VISIBLE);
        setEnabled(true);
        if( v!= null ) ((InputMethodManager)mTargetActivity.getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public void registerEditText(int resid) {
        // Find the EditText 'resid'
        EditText edittext= (EditText)mTargetActivity.findViewById(resid);
        // Make the custom keyboard appear
        /*edittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override public void onFocusChange(View v, boolean hasFocus) {
                if( hasFocus ) showCustomKeyboard(v); else hideCustomKeyboard();
            }
        });*/
        edittext.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                showCustomKeyboard(v);
            }
        });
        // Disable standard keyboard hard way
        edittext.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                EditText edittext = (EditText) v;
                int inType = edittext.getInputType();       // Backup the input type
                edittext.setInputType(InputType.TYPE_NULL); // Disable standard keyboard
                edittext.onTouchEvent(event);               // Call native handler
                edittext.setInputType(inType);              // Restore input type
                return true; // Consume touch event
            }
        });
        // Disable spell check (hex strings look like words to Android)
        edittext.setInputType( edittext.getInputType() | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS );
    }

}