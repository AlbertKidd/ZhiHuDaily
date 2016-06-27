package com.demo.kidd.zhihudaily.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.kidd.zhihudaily.Constants;
import com.demo.kidd.zhihudaily.R;
import com.squareup.timessquare.CalendarPickerView;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by niuwa on 2016/6/25.
 */
public class PickDateFragment extends Fragment {

    @BindView(R.id.calendar_picker)
    CalendarPickerView mCalendarPicker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle saveInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pick_date, parent, false);
        ButterKnife.bind(this, v);

        Calendar nextDay = Calendar.getInstance(Locale.CHINA);
        nextDay.add(Calendar.DAY_OF_YEAR, 1);
        mCalendarPicker.init(Constants.Dates.birthday, nextDay.getTime())
                .withSelectedDate(Calendar.getInstance(Locale.CHINA).getTime());
        mCalendarPicker.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.BundleKeys.DATE, date);

                SpecifiedDateNewsFragment newsFragment = new SpecifiedDateNewsFragment();
                newsFragment.setArguments(bundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container,newsFragment).commit();
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });

        return v;
    }
}
