package com.james602152002.floatinglabelspinner;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.SystemClock;
import androidx.annotation.VisibleForTesting;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnitRunner;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Spinner;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by shiki60215 on 18-1-9.
 */
public class FloatingLabelSpinnerTest extends AndroidJUnitRunner {

//    @Rule
    public FloatingLabelSpinner customView;

    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        customView = new FloatingLabelSpinner(context, null, 0, Spinner.MODE_DROPDOWN);
        customView = new FloatingLabelSpinner(context, null);
        customView = new FloatingLabelSpinner(context, Spinner.MODE_DROPDOWN);
        customView = new FloatingLabelSpinner(context);
        customView.setMinimumWidth(100);
    }

    @Test
    public void testHintTextEqual() {
        final String hint = "hint";
        customView.setHint(hint);
        Assert.assertEquals(hint, customView.getHint());
    }

    @Test
    public void testHintTextColor() {
        final int color = Color.GRAY;
        customView.setHint_text_color(color);
        Assert.assertEquals(color, customView.getHint_text_color());
    }

    @Test
    public void testHintTestSize() {
        final float text_size = 10;
        customView.setHint_text_size(text_size);
        Assert.assertEquals(text_size, customView.getHint_text_size(), 0);
    }

    @Test
    public void testHighLightColor() {
        final int color = Color.BLUE;
        customView.setHighlight_color(color);
        Assert.assertEquals(color, customView.getHighlight_color());
    }

    @Test
    public void testLabelTextSize() {
        final float label_text_size = 8;
        customView.setLabel_text_size(label_text_size);
        Assert.assertEquals(label_text_size, customView.getLabel_text_size(),0);
    }

    @Test
    public void testDispatchDraw() throws NoSuchFieldException, IllegalAccessException {
        final Canvas canvas = new Canvas();
        customView.setError("error");
        Field field = FloatingLabelSpinner.class.getDeclaredField("errorAnimator");
        field.setAccessible(true);
        field.set(customView, new ObjectAnimator());
        field = FloatingLabelSpinner.class.getDeclaredField("hint_cell_height");
        field.setAccessible(true);
        field.set(customView, (short) 1);
        customView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 0;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return new View(InstrumentationRegistry.getInstrumentation().getTargetContext());
            }
        });
        customView.setSelection(1);
        field = FloatingLabelSpinner.class.getDeclaredField("selectedView");
        field.setAccessible(true);
        field.set(customView, new View(InstrumentationRegistry.getInstrumentation().getTargetContext()));
        customView.setHint("hint");
        customView.setError("error");
        customView.dispatchDraw(canvas);
        customView.setSelection(0);
        customView.dispatchDraw(canvas);
        field = FloatingLabelSpinner.class.getDeclaredField("float_label_anim_percentage");
        field.setAccessible(true);
        field.set(customView, 1);
        customView.dispatchDraw(canvas);
    }

    @Test
    public void testDispatchDrawWithErrorMargin() throws NoSuchFieldException, IllegalAccessException {
        customView.setErrorMargin(10, 10);
        customView.setError("error");
        Field field = FloatingLabelSpinner.class.getDeclaredField("errorAnimator");
        field.setAccessible(true);
        field.set(customView, new ObjectAnimator());
        customView.setHint("hint");
        customView.setError("error");
        customView.dispatchDraw(new Canvas());
    }

    @Test
    public void testDispatchDrawWithoutHintAndError() {
        customView.setHint(null);
        customView.setError(null);
        customView.dispatchDraw(new Canvas());
    }

    @Test
    public void testDrawSpannableString() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        SpannableString span = new SpannableString("*hint");
        span.setSpan(new ForegroundColorSpan(Color.RED), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        customView.setHint(span);
        Method method = FloatingLabelSpinner.class.getDeclaredMethod("drawSpannableString", Canvas.class, CharSequence.class, TextPaint.class, int.class, int.class);
        method.setAccessible(true);
        method.invoke(customView, new Canvas(), span, new TextPaint(), 0, 0);
    }

    @Test
    public void testError() {
        final String error = "error";
        customView.requestLayout();
        customView.setError(error);
        Assert.assertEquals(error, customView.getError());
        customView.setError(null);
    }

    @Test
    public void testErrorColor() {
        final int color = Color.RED;
        customView.setError_color(color);
        Assert.assertEquals(color, customView.getError_color());
    }

    @Test
    public void testErrorTextSize() {
        final float error_text_size = 6;
        customView.setError_text_size(error_text_size);
        Assert.assertEquals(error_text_size, customView.getError_text_size(), 0);
    }

    @Test
    public void testMargins() {
        customView.setLabelMargins(10, 10);
        customView.setErrorMargin(10, 10);
    }

    @Test
    public void testThickness() {
        final int thickness = 5;
        customView.setThickness(thickness);
        Assert.assertEquals(thickness, customView.getThickness());
    }

    @Test
    public void testErrorPercentage() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        final float test_percentage = .5f;
        Method method = FloatingLabelSpinner.class.getDeclaredMethod("setError_percentage", float.class);
        method.setAccessible(true);
        method.invoke(customView, test_percentage);
        Field field = FloatingLabelSpinner.class.getDeclaredField("error_percentage");
        field.setAccessible(true);
        Assert.assertEquals(test_percentage, field.get(customView));
    }

    @Test
    public void testLabelPercentage() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        final float test_percentage = .5f;
        Method method = FloatingLabelSpinner.class.getDeclaredMethod("setFloat_label_anim_percentage", float.class);
        method.setAccessible(true);
        method.invoke(customView, test_percentage);
        Field field = FloatingLabelSpinner.class.getDeclaredField("float_label_anim_percentage");
        field.setAccessible(true);
        Assert.assertEquals(test_percentage, field.get(customView));
    }

    @Test
    public void testAnimationDuration() {
        final short anim_duration = 80;
        customView.setAnimDuration(-1);
        customView.setAnimDuration(anim_duration);
        Assert.assertEquals(anim_duration, customView.getAnimDuration());
    }

    @Test
    public void testErrorAnimDuration() {
        final short error_anim_duration = 5000;
        customView.setErrorAnimDuration(-1);
        customView.setErrorAnimDuration(error_anim_duration);
        Assert.assertEquals(error_anim_duration, customView.getErrorAnimDuration());
    }

    @Test
    public void testAdapter() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        initAdapter();
        customView.setHint("hint");
        Method method = FloatingLabelSpinner.class.getDeclaredMethod("measureHintCellHeight");
        method.setAccessible(true);
        method.invoke(customView);
    }

    @VisibleForTesting
    public void testErrorAnimation() {
        try {
            customView.setError("error");
            Method method = FloatingLabelSpinner.class.getDeclaredMethod("startErrorAnimation");
            method.setAccessible(true);
            method.invoke(customView);
        } catch (Exception e) {

        }
    }

    @Test
    public void testDropDownHintView() {
        View hintView = new View(InstrumentationRegistry.getInstrumentation().getTargetContext());
        customView.setDropDownHintView(hintView);
        Assert.assertEquals(hintView, customView.getDropDownHintView());
        int dropDownHintView = (int) (Integer.MAX_VALUE * Math.random());
        customView.setDropDownHintView(dropDownHintView);
        Assert.assertEquals(dropDownHintView, customView.getDropDownHintViewID());
    }

    @VisibleForTesting
    public void testOnItemSelectedListener() {
        customView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        final AdapterView.OnItemSelectedListener listener = customView.getOnItemSelectedListener();
        listener.onItemSelected(null, null, 0, 0);
        listener.onNothingSelected(null);
    }

    @VisibleForTesting
    public void testPerformClick() {
        initAdapter();
        customView.performClick();
        customView.performClick();
    }

    @VisibleForTesting
    public void testPerformLongClick() {
        initAdapter();
        customView.performLongClick();
        customView.performLongClick(0, 0);
    }


    @Test
    public void testRecursiveMode() {
        customView.setRecursive_mode(true);
        Assert.assertTrue(customView.isRecursive_mode());
    }

    @Test
    public void testLayoutSpinnerView() {
        customView.layoutSpinnerView(1);
    }

    @Test
    public void testSetSelection() {
        final int position = 1;
        customView.setSelection(position, false);
        Assert.assertEquals(position, customView.getSelectedItemPosition());
    }

    @Test
    public void testRecursiveModeDismiss() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        initAdapter();
        Method method = FloatingLabelSpinner.class.getDeclaredMethod("togglePopupWindow");
        method.setAccessible(true);
        method.invoke(customView);
        customView.setRecursive_mode(true);
        customView.dismiss();
        Field field = FloatingLabelSpinner.class.getDeclaredField("popupWindow");
        field.setAccessible(true);
        Assert.assertNull(field.get(customView));
    }

    @Test
    public void testAllDismissCondition() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        initAdapter();
        customView.dismiss();
        customView.setRecursive_mode(true);
        customView.dismiss();
        customView.setRecursive_mode(false);
        Method method = FloatingLabelSpinner.class.getDeclaredMethod("togglePopupWindow");
        method.setAccessible(true);
        method.invoke(customView);
        customView.dismiss();
    }

    @Test
    public void testDataSetChanged() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        initAdapter();
        customView.notifyDataSetChanged();
        customView.setDropDownHintView(new View(InstrumentationRegistry.getInstrumentation().getTargetContext()));
        Method method = FloatingLabelSpinner.class.getDeclaredMethod("togglePopupWindow");
        method.setAccessible(true);
        method.invoke(customView);
        customView.notifyDataSetChanged();
    }

    @Test
    public void testTouchEvent() throws NoSuchFieldException, IllegalAccessException {
        // Obtain MotionEvent object
        long downTime = SystemClock.uptimeMillis();
        long eventTime = SystemClock.uptimeMillis() + 100;
        float x = 0.0f;
        float y = 0.0f;
        // List of meta states found here: developer.android.com/reference/android/view/KeyEvent.html#getMetaState()
        int metaState = 0;
        MotionEvent motionEvent = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_DOWN, x, y, metaState);
        customView.dispatchTouchEvent(motionEvent);
        motionEvent = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_MOVE, x, y, metaState);
        customView.dispatchTouchEvent(motionEvent);
        motionEvent = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_UP, x, y, metaState);
        customView.dispatchTouchEvent(motionEvent);
        Field field = FloatingLabelSpinner.class.getDeclaredField("long_click");
        field.setAccessible(true);
        field.set(customView, true);
        motionEvent = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_UP, x, y, metaState);
        customView.dispatchTouchEvent(motionEvent);
        motionEvent = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_DOWN, x, y, metaState);
        customView.dispatchTouchEvent(motionEvent);
        motionEvent = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_MOVE, x, y, metaState);
        customView.dispatchTouchEvent(motionEvent);
        motionEvent = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_MOVE, 1000, y, metaState);
        customView.dispatchTouchEvent(motionEvent);
        motionEvent = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_UP, x, y, metaState);
        customView.dispatchTouchEvent(motionEvent);
        motionEvent = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_DOWN, x, y, metaState);
        customView.dispatchTouchEvent(motionEvent);
        motionEvent = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_MOVE, x, y, metaState);
        customView.dispatchTouchEvent(motionEvent);
        motionEvent = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_MOVE, 0, 1000, metaState);
        customView.dispatchTouchEvent(motionEvent);
        motionEvent = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_UP, x, y, metaState);
        customView.dispatchTouchEvent(motionEvent);
        motionEvent = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_DOWN, x, y, metaState);
        customView.dispatchTouchEvent(motionEvent);
        motionEvent = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_MOVE, x, y, metaState);
        customView.dispatchTouchEvent(motionEvent);
        motionEvent = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_MOVE, 1000, 1000, metaState);
        customView.dispatchTouchEvent(motionEvent);
        motionEvent = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_MOVE, x, y, metaState);
        customView.dispatchTouchEvent(motionEvent);
        motionEvent = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_UP, x, y, metaState);
        customView.dispatchTouchEvent(motionEvent);
        motionEvent = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_CANCEL, x, y, metaState);
        customView.dispatchTouchEvent(motionEvent);
    }

    private void initAdapter() {
        customView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 1;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return new View(InstrumentationRegistry.getInstrumentation().getTargetContext());
            }
        });
    }

    @After
    public void tearDown() throws Exception {
        customView = null;
    }

}