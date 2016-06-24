package com.demo.kidd.zhihudaily.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.kidd.zhihudaily.LoadNewsDetailTask;
import com.demo.kidd.zhihudaily.R;
import com.demo.kidd.zhihudaily.bean.Story;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by niuwa on 2016/6/23.
 */
public class NewsDetailActivity extends AppCompatActivity {

    public static final String KEY_NEWS = "key_news";

    private Story story;

    @BindView(R.id.image_header)
    ImageView mImageHeader;
    @BindView(R.id.text_title)
    TextView mTextTitle;
    @BindView(R.id.text_source)
    TextView mTextSource;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.web_view)
    WebView mWebView;
    @BindView(R.id.nested_view)
    NestedScrollView mNestedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);

        initView();

        story = getIntent().getParcelableExtra(KEY_NEWS);
        new LoadNewsDetailTask(this, mImageHeader, mTextTitle, mTextSource, mWebView).execute(story.getId());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_action_share:
                share();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView(){
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mCollapsingToolbarLayout.setTitleEnabled(true);

        mWebView.setDrawingCacheEnabled(true);
    }

    public static void start(Context context, Story story){
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra(KEY_NEWS, story);
        context.startActivity(intent);
    }

    private void share(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        intent.putExtra(Intent.EXTRA_TEXT,
                "来自「知报」的分享：" + story.getTitle() + ",http://daily.zhihu.com/story/" + story.getId());
        startActivity(Intent.createChooser(intent, story.getTitle()));
    }
}
