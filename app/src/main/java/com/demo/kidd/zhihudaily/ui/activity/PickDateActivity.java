package com.demo.kidd.zhihudaily.ui.activity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.demo.kidd.zhihudaily.R;
import com.demo.kidd.zhihudaily.ui.PickDateFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by niuwa on 2016/6/21.
 */
public class PickDateActivity extends AppCompatActivity {


    @BindView(R.id.date_picker_toolbar)
    Toolbar mDatePickerToolbar;
    @BindView(R.id.fragment_container)
    FrameLayout mFragmentContainer;
    @BindView(R.id.pick_date_coordinator_layout)
    CoordinatorLayout mPickDateCoordinatorLayout;

    private PickDateFragment mDateFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_date);
        ButterKnife.bind(this);

        mDatePickerToolbar.setTitle(R.string.date_pick);
        setSupportActionBar(mDatePickerToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fm = getSupportFragmentManager();
        mDateFragment = (PickDateFragment) fm.findFragmentById(R.id.fragment_container);

        if (mDateFragment == null) {
            mDateFragment = new PickDateFragment();
            fm.beginTransaction().add(R.id.fragment_container, mDateFragment).commit();
        }
    }

    public void showSnackBar(int resourceId) {
        Snackbar.make(mPickDateCoordinatorLayout, resourceId, Snackbar.LENGTH_SHORT).show();
    }
}
