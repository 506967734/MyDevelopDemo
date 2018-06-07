package cn.zhudi.mvpdemo.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by mengyangyang on 2016-08-09.
 */
public class ViewHolder {
    private final SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;

    public ViewHolder(Context context, ViewGroup parent, int layoutId,
                      int position) {
        this.mPosition = position;
        this.mViews = new SparseArray<>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        // setTag
        mConvertView.setTag(this);
    }

    /**
     * 拿到一个ViewHolder对象
     *
     * @param context
     * @param convertView
     * @param parent
     * @param layoutId
     * @param position
     * @return
     */
    @SuppressWarnings("JavaDoc")
    public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new ViewHolder(context, parent, layoutId, position);
        }
        return (ViewHolder) convertView.getTag();
    }

//    public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position, boolean flag) {
////		if (convertView == null) {
//        return new ViewHolder(context, parent, layoutId, position);
////		}
////		return (ViewHolder) convertView.getTag();
//    }

    public View getConvertView() {
        return mConvertView;
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId
     * @return
     */
    @SuppressWarnings("JavaDoc")
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewId
     * @param text
     * @return
     */
    @SuppressWarnings("JavaDoc")
    public ViewHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * 为EditText设置字符串
     *
     * @param viewId
     * @param text
     * @return
     */
    @SuppressWarnings("JavaDoc")
    public ViewHolder setEditText(int viewId, String text) {
        EditText view = getView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * View隐藏
     *
     * @param viewId
     * @return
     */
    @SuppressWarnings("JavaDoc")
    public ViewHolder gone(int viewId) {
        getView(viewId).setVisibility(View.GONE);
        return this;
    }

    /**
     * View显示
     *
     * @param viewId
     * @return
     */
    @SuppressWarnings("JavaDoc")
    public ViewHolder visible(int viewId) {
        getView(viewId).setVisibility(View.VISIBLE);
        return this;
    }

    /**
     * View invisible
     *
     * @param viewId
     * @return
     */
    @SuppressWarnings("JavaDoc")
    public ViewHolder invisible(int viewId) {
        getView(viewId).setVisibility(View.INVISIBLE);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    @SuppressWarnings("JavaDoc")
    public ViewHolder setImageResource(int viewId, int drawableId) {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);

        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    @SuppressWarnings("JavaDoc")
    public ViewHolder setBackgroundResource(int viewId, int drawableId) {
        ImageView view = getView(viewId);
        view.setBackgroundResource(drawableId);
        return this;
    }


    public int getPosition() {
        return mPosition;
    }

}
