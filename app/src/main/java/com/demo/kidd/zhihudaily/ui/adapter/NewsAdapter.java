package com.demo.kidd.zhihudaily.ui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.kidd.zhihudaily.R;
import com.demo.kidd.zhihudaily.bean.Story;
import com.demo.kidd.zhihudaily.ui.activity.MainActivity;
import com.demo.kidd.zhihudaily.ui.activity.NewsDetailActivity;
import com.demo.kidd.zhihudaily.utils.Utility;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by niuwa on 2016/6/21.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.CardViewHolder> {


    private List<Story> mStoryList;
    private Context mContext;


    public NewsAdapter(Context context, List<Story> storyList) {
        this.mStoryList = storyList;
        this.mContext = context;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list_item, parent, false);
        return new CardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CardViewHolder viewHolder, int position) {
        Story story = mStoryList.get(position);
        if (TextUtils.isEmpty(story.getImage())){
            Picasso.with(mContext).load(R.drawable.zhihu_logo).into(viewHolder.mThumbnailImage);
        }else
            Picasso.with(mContext).load(story.getImage()).into(viewHolder.mThumbnailImage);

        viewHolder.mQuestionTitle.setText(story.getTitle());
        viewHolder.mCardView.setOnClickListener(getListener(viewHolder, story));
    }

    private View.OnClickListener getListener(final CardViewHolder holder, final Story story){
        return new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (Utility.checkNetworkConnection(mContext))
                    NewsDetailActivity.start(mContext, story);
                else
                    ((MainActivity)mContext).showSnackBar(R.string.unconnected);

            }
        };
    }

    @Override
    public int getItemCount(){
        return mStoryList == null ? 0 : mStoryList.size();
    }

    public void refreshStoryList(List<Story> list){
        mStoryList = list;
        notifyDataSetChanged();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.thumbnail_image)
        ImageView mThumbnailImage;
        @BindView(R.id.question_title)
        TextView mQuestionTitle;
        @BindView(R.id.card_view)
        CardView mCardView;

        public CardViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
