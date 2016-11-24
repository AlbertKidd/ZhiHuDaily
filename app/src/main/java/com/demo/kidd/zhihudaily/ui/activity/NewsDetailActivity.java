package com.demo.kidd.zhihudaily.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.kidd.zhihudaily.Constants;
import com.demo.kidd.zhihudaily.DB.FavoriteDB;
import com.demo.kidd.zhihudaily.R;
import com.demo.kidd.zhihudaily.bean.Question;
import com.demo.kidd.zhihudaily.bean.Story;
import com.demo.kidd.zhihudaily.utils.JsonHelper;
import com.demo.kidd.zhihudaily.utils.ObservableManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by niuwa on 2016/6/23.
 */
public class NewsDetailActivity extends AppCompatActivity {

    public static final String KEY_NEWS = "key_news";

    private Story story;
    private FavoriteDB mFavoriteDB;
    private boolean isFavorite;

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
//        new LoadNewsDetailTask(this, mImageHeader, mTextTitle, mTextSource, mWebView).execute(story.getId());
        ObservableManager.getDetailObservable(story.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        try{
                            Question question = JsonHelper.parseJsonToDetail(s);

                            String headerImage;
                            if (TextUtils.isEmpty(question.getImage()))
                                headerImage = Constants.URLs.DEFAULT_HEAD_IMAGE;
                            else
                                headerImage = question.getImage();
                            Picasso.with(NewsDetailActivity.this)
                                    .load(headerImage)
                                    .into(mImageHeader);

                            mTextTitle.setText(question.getTitle());
                            mTextSource.setText(question.getImage_source());

                            String newsContent = "<link rel=\"stylesheet\" type=\"text/css\" href=\"news_content_style.css\"/>"
                                    + question.getBody().replace("<div class=\"img-place-holder\">", "");
                            mWebView.loadDataWithBaseURL("file:///android_asset/", newsContent, "text/html", "utf-8", null);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                });
        mFavoriteDB = new FavoriteDB(this);
        isFavorite = mFavoriteDB.isFavorite(story);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        if (isFavorite)
            menu.findItem(R.id.menu_action_favorite).setIcon(R.drawable.ic_favorite_red_500_48dp);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_action_favorite:
                if (isFavorite){
                    mFavoriteDB.deleteFavorite(story);
                    item.setIcon(R.drawable.ic_favorite_white_48dp);
                    isFavorite = false;
                }else {
                    mFavoriteDB.addFavorite(story);
                    item.setIcon(R.drawable.ic_favorite_red_500_48dp);
                    isFavorite = true;
                }
                return true;
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
                "来自「知报」的分享：" + story.getTitle() + Constants.URLs.STORY_VIEW + story.getId());
        startActivity(Intent.createChooser(intent, story.getTitle()));
    }
}
