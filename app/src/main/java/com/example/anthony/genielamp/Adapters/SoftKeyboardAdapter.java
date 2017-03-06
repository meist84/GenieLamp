package com.example.anthony.genielamp.Adapters;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.graphics.Rect;
import android.view.inputmethod.InputMethodManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

public class SoftKeyboardAdapter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {

        boolean handleReturn = super.dispatchTouchEvent(motionEvent);

        View view = getCurrentFocus();

        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();

        if(view instanceof EditText){
            View innerView = getCurrentFocus();

            if (motionEvent.getAction() == MotionEvent.ACTION_UP && !getLocationOnScreen(innerView).contains(x, y)) {
                InputMethodManager input = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                input.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
            }
        }
        return handleReturn;
    }

    protected Rect getLocationOnScreen(View view) {
        Rect rect = new Rect();
        int[] location = new int[2];

        view.getLocationOnScreen(location);

        rect.left = location[0];
        rect.top = location[1];
        rect.right = location[0] + view.getWidth();
        rect.bottom = location[1] + view.getHeight();

        return rect;
    }
}
