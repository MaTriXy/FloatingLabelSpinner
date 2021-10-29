package com.james602152002.floatinglabelspinner.adapter;

import android.content.Context;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnitRunner;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.james602152002.floatinglabelspinner.FloatingLabelSpinner;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by shiki60215 on 18-1-11.
 */
public class DropDownViewAdapterTest extends AndroidJUnitRunner {

    private DropDownViewAdapter dropDownViewAdapter;
    private HintAdapter hintAdapter;

    @Before
    public void setUp() throws Exception {
        final Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        FloatingLabelSpinner spinner = new FloatingLabelSpinner(context);
        spinner.setDropDownHintView(new View(context));
        spinner.setHint("hint");
        hintAdapter = new HintAdapter(context, spinner, new BaseAdapter() {
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
                return null;
            }
        });
        dropDownViewAdapter = new DropDownViewAdapter(hintAdapter);
    }

    @Test
    public void getCount() throws Exception {
        Assert.assertEquals(hintAdapter.getCount(), dropDownViewAdapter.getCount());
    }

    @Test
    public void testItem() throws Exception {
        final Object item = dropDownViewAdapter.getItem(0);
        Assert.assertEquals(hintAdapter.getItem(0), item);
    }

    @Test
    public void testItemId() throws Exception {
        final long itemID = dropDownViewAdapter.getItemId(0);
        Assert.assertEquals(hintAdapter.getItemId(0), itemID);
    }

    @Test
    public void testItemViewType() throws Exception {
        final int itemViewType = dropDownViewAdapter.getItemViewType(0);
        Assert.assertEquals(hintAdapter.getItemViewType(0), itemViewType);
    }

    @Test
    public void testGetView() throws Exception {
        final View convertView = dropDownViewAdapter.getView(0, null, null);
        Assert.assertNotNull(convertView);
    }


    @After
    public void tearDown() throws Exception {
        dropDownViewAdapter = null;
    }

}