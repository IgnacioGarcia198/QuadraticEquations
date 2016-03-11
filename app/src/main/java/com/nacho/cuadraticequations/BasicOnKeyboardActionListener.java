package com.nacho.cuadraticequations;

import android.app.Activity;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by nacho on 06/05/2015.
 */
public class BasicOnKeyboardActionListener implements KeyboardView.OnKeyboardActionListener {
    public final static int CodeDelete   = -5; // Keyboard.KEYCODE_DELETE
    public final static int CodeCancel   = -3; // Keyboard.KEYCODE_CANCEL
    public final static int CodePrev     = 55000;
    public final static int CodeAllLeft  = 55001;
    public final static int CodeLeft     = 55002;
    public final static int CodeRight    = 55003;
    public final static int CodeAllRight = 55004;
    public final static int CodeDone     = -4;
    public final static int CodeClear    = 55006;
    public final static int CodeSmile    = 0;
    private MainActivity mTargetActivity;
    private CustomKeyboardView ckbv;

    public BasicOnKeyboardActionListener(MainActivity targetActivity, CustomKeyboardView ckbv) {
        mTargetActivity = targetActivity;
        this.ckbv = ckbv;
    }

    //Altri metodo rimossi

    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        /*long eventTime = System.currentTimeMillis();
        KeyEvent event = new KeyEvent(eventTime, eventTime,
                KeyEvent.ACTION_DOWN, primaryCode, 0, 0, 0, 0,
                KeyEvent.FLAG_SOFT_KEYBOARD | KeyEvent.FLAG_KEEP_TOUCH_MODE);

        mTargetActivity.dispatchKeyEvent(event);*/
        // Get the EditText and its Editable
        View focusCurrent = mTargetActivity.getWindow().getCurrentFocus();
        if( focusCurrent==null || focusCurrent.getClass()!=EditText.class ) return;
        EditText edittext = (EditText) focusCurrent;
        Editable editable = edittext.getText();
        int start = edittext.getSelectionStart();
        // Handle key
        if( primaryCode==CodeCancel ) {
            ckbv.hideCustomKeyboard();
        } else if( primaryCode==CodeDelete ) {
            if( editable!=null && start>0 ) editable.delete(start - 1, start);
        } else if( primaryCode==CodeClear ) {
            if( editable!=null ) editable.clear();
        } else if( primaryCode==CodeLeft ) {
            if( start>0 ) edittext.setSelection(start - 1);
        } else if( primaryCode==CodeRight ) {
            if (start < edittext.length()) edittext.setSelection(start + 1);
        } else if( primaryCode==CodeAllLeft ) {
            edittext.setSelection(0);
        } else if( primaryCode==CodeAllRight ) {
            edittext.setSelection(edittext.length());
        } else if( primaryCode==CodePrev ) {
            View focusNew= edittext.focusSearch(View.FOCUS_LEFT);
            if( focusNew!=null ) focusNew.requestFocus();
        } else if( primaryCode==CodeDone ) {
            ckbv.hideCustomKeyboard();
            mTargetActivity.outResult();
        } else if(primaryCode==CodeSmile) {
            Toast.makeText(mTargetActivity.getApplicationContext(), "OMG! Be careful with backspace!", Toast.LENGTH_SHORT).show();
        }
        else {// Insert character
            editable.insert(start, Character.toString((char) primaryCode));
        }
    }



    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }
}
