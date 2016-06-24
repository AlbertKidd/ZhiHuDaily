package com.demo.kidd.zhihudaily;

import android.content.Context;
import android.os.AsyncTask;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.kidd.zhihudaily.bean.Question;
import com.demo.kidd.zhihudaily.utils.HttpUtil;
import com.demo.kidd.zhihudaily.utils.JsonHelper;
import com.squareup.picasso.Picasso;

/**
 * Created by niuwa on 2016/6/23.
 */
public class LoadNewsDetailTask extends AsyncTask<Integer, Void, Question>{

    private Context mContext;
    private ImageView mImageView;
    private TextView mTextView;
    private TextView mTextResourceView;
    private WebView mWebView;

    public LoadNewsDetailTask(Context context, ImageView imageView, TextView textView, TextView textResourceView, WebView webView){
        this.mContext = context;
        this.mImageView = imageView;
        this.mTextView = textView;
        this.mTextResourceView = textResourceView;
        this.mWebView = webView;
    }

    @Override
    protected Question doInBackground(Integer... params){
        Question question = null;
        try {
            question = JsonHelper.parseJsonToDetail(HttpUtil.getNewsDetail(params[0]));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return question;
        }
    }

    @Override
    protected void onPostExecute(Question question){
        String headerImage;
        if (question.getImage() == null || question.getImage() == ""){
            headerImage = "file:///android_asset/news_detail_header_image.jpg";
        }else {
            headerImage = question.getImage();
        }
        Picasso.with(mContext)
                .load(headerImage)
                .into(mImageView);
        mTextView.setText(question.getTitle());
        mTextResourceView.setText(question.getImage_source());

        String mNewsContent = "<link rel=\"stylesheet\" type=\"text/css\" href=\"news_content_style.css\"/>"
                + question.getBody().replace("<div class=\"img-place-holder\">", "");
        mWebView.loadDataWithBaseURL("file:///android_asset/", mNewsContent, "text/html", "UTF-8", null);
    }
}
