package cn.zhudi.mvpdemo.adapter;

import android.content.Context;
import android.widget.AdapterView;

import com.demo.common.adapter.BaseRecyclerAdapter;
import com.demo.common.adapter.SmartViewHolder;

import java.util.List;

import cn.zhudi.mvpdemo.R;
import cn.zhudi.mvpdemo.model.entities.User;

public class PersonAdapter<T> extends BaseRecyclerAdapter<T> {
    private Context mContext;

    public PersonAdapter(Context context, int layoutId) {
        super(layoutId);
        this.mContext = context;
    }

    public PersonAdapter(Context context, List<T> list, int layoutId) {
        super(list, layoutId);
        this.mContext = context;
    }

    public PersonAdapter(Context context, List<T> list, int layoutId, AdapterView.OnItemClickListener listener) {
        super(list, layoutId, listener);
        this.mContext = context;
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, T model, int position) {
        holder.text(android.R.id.text1, mContext.getString(R.string.item_example_number_title, ((User) model).getUserName()));
        holder.text(android.R.id.text2, mContext.getString(R.string.item_example_number_abstract, position));

    }
}
