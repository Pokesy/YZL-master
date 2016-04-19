package com.thinksky.adapter;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.thinksky.info.NewsListInfo;
import com.thinksky.tox.R;

import org.kymjs.aframe.bitmap.KJBitmap;

import java.util.ArrayList;

/**
 * 资讯列表适配器
 */
public class NewsListAdapter extends BaseAdapter {

    private ArrayList<NewsListInfo> newsListInfos;
    ViewHolder viewHolder;
    KJBitmap kjBitmap;
    private Context mContent;

    public NewsListAdapter(Context context, ArrayList<NewsListInfo> newsListInfos) {
        super();
        this.newsListInfos = newsListInfos;
        this.mContent = context;
    }

    @Override
    public int getCount() {
        return newsListInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return newsListInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Integer.parseInt(newsListInfos.get(position).getId());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        kjBitmap = KJBitmap.create();
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContent).inflate(R.layout.news_info_item, parent, false);
            viewHolder.mSnapshotsView = (ImageView) convertView.findViewById(R.id.snapshots);
            viewHolder.newsTitle = (TextView) convertView.findViewById(R.id.news_title);
            viewHolder.newsDescription = (TextView) convertView.findViewById(R.id.news_description);
            viewHolder.mTimeView = (TextView) convertView.findViewById(R.id.time);
            viewHolder.mCommentCount = (TextView) convertView.findViewById(R.id.comment_count);
            viewHolder.mViewCount = (TextView) convertView.findViewById(R.id.view_count);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        NewsListInfo info = newsListInfos.get(position);
        viewHolder.mSnapshotsView.setVisibility(TextUtils.isEmpty(info.getCover()) ? View.GONE : View.VISIBLE);
        kjBitmap.display(viewHolder.mSnapshotsView, info.getCover().replace("opensns//opensns","opensns"));
        viewHolder.newsTitle.setText(info.getTitle());
        viewHolder.mTimeView.setText(info.getCreate_time());
        viewHolder.newsDescription.setText(Html.fromHtml(info.getDescription()));
        viewHolder.mCommentCount.setText(info.getComment());
        viewHolder.mViewCount.setText(info.getView());
        return convertView;
    }

    //控件缓存器
    private class ViewHolder {
        ImageView mSnapshotsView;
        TextView newsTitle, newsDescription, mCommentCount, mViewCount, mTimeView;
    }
}