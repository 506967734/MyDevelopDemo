package cn.zhudi.mvpdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by mengyangyang on 2016-08-09.
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
    public LayoutInflater mInflater;
    public Context mContext;
    public List<T> mDatas;

    public CommonAdapter(Context context, List<T> mDatas) {
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
