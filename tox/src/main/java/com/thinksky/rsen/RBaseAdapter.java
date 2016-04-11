package com.thinksky.rsen;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dajiao on 16-01-18-018.
 */
public abstract class RBaseAdapter<T> extends RecyclerView.Adapter<RViewHolder> {

    protected List<T> mAllDatas;
    protected Context mContext;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public RBaseAdapter(Context context) {
        mAllDatas = new ArrayList<>();
        this.mContext = context;
    }

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String data);
    }

    public RBaseAdapter(Context context, List<T> datas) {
        this.mAllDatas = datas;
        this.mContext = context;

//        new View(context).setSelected();
    }

    @Override
    public RViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = View.inflate(parent.getContext(), getItemLayoutId(viewType), null);
        return new RViewHolder(item);
    }

    protected abstract int getItemLayoutId(int viewType);

    @Override
    public void onBindViewHolder(RViewHolder holder, int position) {
        onBindView(holder, position, mAllDatas.get(position));
    }

    protected abstract void onBindView(RViewHolder holder, int position, T bean);

    @Override
    public int getItemCount() {
        return mAllDatas == null ? 0 : mAllDatas.size();
    }

    /**
     * 在最后的位置插入数据
     */
    public void addItemLast(T bean) {
        mAllDatas.add(bean);
        notifyItemInserted(mAllDatas.size() - 1);
    }

    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (String) v.getTag());
        }
    }

    /**
     * 重置数据
     */
    public void resetData(List<T> datas) {
        this.mAllDatas = datas;
        notifyDataSetChanged();
    }

    /**
     * 追加数据
     */
    public void appendData(List<T> datas) {
        if (datas == null || datas.size() == 0) {
            return;
        }
        if (this.mAllDatas == null) {
            this.mAllDatas = new ArrayList<>();
        }
        int startPosition = this.mAllDatas.size();
        this.mAllDatas.addAll(datas);
        notifyItemRangeInserted(startPosition, datas.size());
    }

    public List<T> getAllDatas() {
        return mAllDatas;
    }
}
