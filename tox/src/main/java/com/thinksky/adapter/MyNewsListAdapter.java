package com.thinksky.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.thinksky.info.NewsListInfo;
import com.thinksky.tox.R;
import com.thinksky.utils.imageloader.ImageLoader;
import java.util.ArrayList;
import org.kymjs.aframe.bitmap.KJBitmap;

/**
 * 我的资讯列表适配器
 */
public class MyNewsListAdapter extends BaseAdapter {

    private ArrayList<NewsListInfo> newsListInfos;
    ViewHolder viewHolder;
    KJBitmap kjBitmap;
    Context mContext;

    public MyNewsListAdapter(Context context,ArrayList<NewsListInfo> newsListInfos) {
        super();
        this.newsListInfos = newsListInfos;
        this.mContext = context;
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
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.news_info_item, parent, false);
            viewHolder.newsLogo = (ImageView)convertView.findViewById(R.id.news_logo);
            viewHolder.newsTitle = (TextView)convertView.findViewById(R.id.news_title);
//            viewHolder.newsAuthor = (TextView)convertView.findViewById(R.id.news_author_name);
//            viewHolder.newsCreateTime = (TextView)convertView.findViewById(R.id.news_create_time);
//            viewHolder.isOverdue = (TextView)convertView.findViewById(R.id.is_overdue);
            viewHolder.newsDescription = (TextView)convertView.findViewById(R.id.news_description);
//            viewHolder.newsViewCount = (TextView)convertView.findViewById(R.id.news_view_count);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

//        kjBitmap.display(viewHolder.newsLogo,newsListInfos.get(position).getCover(),280,260);
//        ImageLoader.getInstance().displayImage(newsListInfos.get(position).getCover(),viewHolder.newsLogo);

        try {
            ImageLoader.loadOptimizedHttpImage(mContext,newsListInfos.get(position).getCover()).into(viewHolder.newsLogo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        viewHolder.newsTitle.setText(newsListInfos.get(position).getTitle());
        viewHolder.newsAuthor.setVisibility(View.GONE);
        viewHolder.isOverdue.setVisibility(View.VISIBLE);
        viewHolder.isOverdue.setText(newsListInfos.get(position).getApproval());
        viewHolder.newsCreateTime.setText(newsListInfos.get(position).getCreate_time());
        viewHolder.newsDescription.setText(Html.fromHtml(newsListInfos.get(position).getDescription()));
        viewHolder.newsViewCount.setText(newsListInfos.get(position).getView());
        return convertView;
    }
    //控件缓存器
    private class ViewHolder{
        ImageView newsLogo;
        TextView newsTitle,newsAuthor,newsCreateTime,newsDescription,newsViewCount,isOverdue;
    }
}