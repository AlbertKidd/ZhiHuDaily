package com.demo.kidd.zhihudaily.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.CalendarView;

import com.demo.kidd.zhihudaily.R;

import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by niuwa on 2016/6/21.
 */
public class PickDateActivity extends AppCompatActivity {

    @BindView(R.id.date_picker_toolbar)
    Toolbar mDatePickerToolbar;
    @BindView(R.id.date_picker)
    CalendarView mDatePicker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_date);
        ButterKnife.bind(this);

        mDatePickerToolbar.setTitle(R.string.date_pick);
        setSupportActionBar(mDatePickerToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        mDatePicker.setMaxDate(calendar.getTimeInMillis());
    }
}
