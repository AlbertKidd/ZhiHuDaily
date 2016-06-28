package com.demo.kidd.zhihudaily.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.demo.kidd.zhihudaily.R;
import com.demo.kidd.zhihudaily.ui.fragment.FavoriteNewsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by niuwa on 2016/6/21.
 */
public class FavoriteActivity extends AppCompatActivity {
    @BindView(R.id.basic_toolbar)
    Toolbar mBasicToolbar;
    @BindView(R.id.fragment_container)
    FrameLayout mFragmentContainer;
    @BindView(R.id.basic_coordinator_layout)
    CoordinatorLayout mBasicCoordinatorLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_toobar_layout);
        ButterKnife.bind(this);

        setSupportActionBar(mBasicToolbar);
        getSupportActionBar().setTitle(R.string.favorite);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        FavoriteNewsFragment fragment = new FavoriteNewsFragment();
        transaction.add(R.id.fragment_container, fragment).commit();

    }
}
