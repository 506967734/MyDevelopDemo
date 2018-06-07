package com.demo.common.base;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.demo.common.utils.ViewUtil;
import com.demo.common.R;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class BaseDialog extends BaseDialogWindow {
    public final static int CANCEL_BUTTON_STYLE = R.drawable.bg_ordermanager_action;// R.drawable.btn_cancel_bg;
    public final static int CONFIRM_BUTTON_STYLE = R.drawable.bg_ordermanager_action;// R.drawable.btn_ok_bg;
    public final static int DEFAULT_BUTTON_STYLE = R.drawable.bg_ordermanager_action;// R.drawable.btn_ok_bg;
    private static Logger logger = LoggerFactory.getLogger("BaseDialog");

    public BaseDialog(Context context) {
        super(context);

    }

    public static class Builder {
        public static boolean parentCanDismiss = true;

        private static List<BaseDialogWindow> mDialogList = new ArrayList<BaseDialogWindow>();

        private Context mContext = null;
        private BaseDialogWindow mDialog = null;
        // private final int MSG_TEXT_COLOR = Color.rgb(112, 146, 190);

        private View mDialogBaseLayout = null;

        private LinearLayout mDialogButtonLayout = null;

		/*
         * Below is dialog items
		 */

        private String mTitle = null;
        private int mGravity = Gravity.CENTER;
        private String mMessage = null;

        private boolean mAddCloseIcon = false;
        private boolean mShow;
        private boolean showTitle = true;
        private int theme = R.style.FullHeightDialog;
        private View mContent = null;
        private View mMoreView = null;
        private View mDialogLine;
        private ArrayList<Button> mButtons = null;

        private ListView mOptionsList = null;
        private String[] mOptions = new String[0];

        private static boolean mClearSelectionMode;

        public Builder(Context c) {
            mClearSelectionMode = false;
            mShow = true;
            mContext = c;
            init();
        }

        /**
         * @param c
         * @param showTitle false: 不显示标题 只有在不需要标题的时候才调用
         */
        public Builder(Context c, boolean showTitle) {
            mClearSelectionMode = false;
            this.showTitle = showTitle;
            mShow = true;
            mContext = c;
            init();
        }

        /**
         * @param c
         * @param showTitle false: 不显示标题 只有在不需要标题的时候才调用
         */
        public Builder(Context c, int theme, boolean showTitle) {
            mClearSelectionMode = false;
            this.theme = theme;
            this.showTitle = showTitle;
            mShow = true;
            mContext = c;
            init();
        }

        private void init() {
            mDialog = new BaseDialogWindow(mContext, theme);
            mDialogBaseLayout = LayoutInflater.from(mContext).inflate(R.layout.dialog_base_layout, null);
            mDialogBaseLayout.setMinimumWidth(mDialog.getWidth());

            mDialogTitleLayout = (FrameLayout) mDialogBaseLayout.findViewById(R.id.dialog_title_layout);
            mDialogContentLayout = (FrameLayout) mDialogBaseLayout.findViewById(R.id.dialog_content_layout);
            mDialogButtonLayout = (LinearLayout) mDialogBaseLayout.findViewById(R.id.dialog_buttons_layout);
            mDialogLine = (View) mDialogBaseLayout.findViewById(R.id.base_dialog_line);
            if (!showTitle) {
                mDialogTitleLayout.setVisibility(View.GONE);
            }
            mButtons = new ArrayList<Button>();
            iv_close = (ImageView) mDialogBaseLayout.findViewById(R.id.dialog_title_close_icon);
            iv_close.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    removeDialog(false);
                }
            });
            mDialog.setCanceledOnTouchOutside(false);
        }

        public Builder setTitle(String title) {
            mTitle = title;

            return this;
        }

        public Builder setGravity(int gravity) {
            mDialog.setGravity(gravity);
            return this;
        }

        public Builder setTitle(int titleResID) {
            mTitle = mContext.getString(titleResID);

            return this;
        }

        public Builder addCloseIcon() {
            mAddCloseIcon = true;

            return this;
        }

        public Builder notShowTitle() {
            showTitle = false;

            return this;
        }

        public Builder setMessage(String message) {
            mMessage = message;

            return this;
        }

        public Builder setMessage(int msgResID) {
            mMessage = mContext.getString(msgResID);

            return this;
        }

        public Builder setDialogContentView(View contentView) {
            mContent = contentView;
            if(!showTitle){
                mDialogContentLayout.setPadding(0, 20, 0, 0);
            }
            return this;
        }

        public Builder setDialogContentView(View contentView, boolean hasSpance) {
            mContent = contentView;
            if (hasSpance)
                mDialogContentLayout.setPadding(0, 0, 0, 20);
            return this;
        }

        public Builder setDialogContentView(int contentViewID) {
            mContent = LayoutInflater.from(mContext).inflate(contentViewID, null);

            return this;
        }

        public View getContentView() {
            return mContent;
        }

        public Builder addView(View view) {
            mMoreView = view;
            if(!showTitle) {
                mDialogContentLayout.setPadding(0, 50, 0, 0);
            }
            return this;
        }

        public Builder addView(View view, boolean hasSpance) {
            mMoreView = view;
            if(hasSpance) {
                mDialogContentLayout.setPadding(0, 50, 0, 0);
            }
            return this;
        }
        /**
         * Make a options list on the dialog with the String array.
         *
         * @param options           The options to be selected
         * @param addClearSelection If this argument set to true, the options list will have
         *                          one more item that used to clear the selection, and when
         *                          you click the clear selection, the position will be set to
         *                          -1.
         * @param onDialogItemClick
         * @return
         */
        public Builder setItems(String[] options, boolean addClearSelection, OnDialogItemClickListener onDialogItemClick) {
            mClearSelectionMode = addClearSelection;
            if (!mClearSelectionMode) {
                return setItems(options, onDialogItemClick);
            }
            /**
             * Add clear selection
             */
            String[] newOptions = new String[options.length + 1];
            newOptions[0] = mContext.getString(R.string.bi_clear_selection);

            for (int i = 1; i < newOptions.length; i++) {
                newOptions[i] = options[i - 1];
            }

            return setItems(newOptions, onDialogItemClick);
        }

        public Builder setItems(String[] options, OnDialogItemClickListener onDialogItemClick) {
            if (options == null || options.length == 0) {
                mShow = false;
                return this;
            }

            mOptions = options;

            mOptionsList = new ListView(mContext);
            // mOptionsList.setDividerHeight(0);
            mOptionsList.setAdapter(new BaseDialogAdapter(onDialogItemClick));
            mOptionsList.setOnItemClickListener(onDialogItemClick);
            mOptionsList.setBackgroundColor(Color.TRANSPARENT);
            mOptionsList.setCacheColorHint(Color.TRANSPARENT);
            mOptionsList.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

            return this;
        }

        public Builder addButton(Button button) {
            mButtons.add(button);

            return this;
        }

        public Builder addPositiveButton(String name, OnDialogButtonClickListener onButtonClick) {
            addButton(name, CONFIRM_BUTTON_STYLE, onButtonClick, true);

            return this;
        }

        public Builder addNegativeButton(String name, OnDialogButtonClickListener onButtonClick) {
            addButton(name, CANCEL_BUTTON_STYLE, onButtonClick, false);

            return this;
        }

        public Builder addButton(int nameResID, int bgResID, OnDialogButtonClickListener onButtonClick) {
            addButton(mContext.getString(nameResID), bgResID, onButtonClick, false);

            return this;
        }

        public Builder addButton(String name, int bgResID, OnDialogButtonClickListener onButtonClick, boolean isConfirm) {
            Button button = buildButton(mContext, name, bgResID, isConfirm);
            button.setTag(mDialog);
            if (onButtonClick == null) {
                button.setOnClickListener(new OnDialogButtonClickListener() {

                    @Override
                    public boolean onDialogButtonClick(View view) {
                        return false;
                    }

                });
            } else {
                button.setOnClickListener(onButtonClick);
            }

            mButtons.add(button);

            return this;
        }

        public static Button buildButton(Context c, String name, int backgroundResource, boolean isConfirm) {
            Button newbtn = new Button(c);
            newbtn.setId(isConfirm ? R.id.dialog_positive_button_id : R.id.dialog_negative_button_id);
            newbtn.setText(name);
            newbtn.setHeight(ViewUtil.dip2px(c, 50));
            newbtn.setTextSize(TypedValue.COMPLEX_UNIT_PX, c.getResources().getDimensionPixelSize(R.dimen.text_medium));
            if (isConfirm) {
                newbtn.setTextColor(c.getResources().getColor(R.color.theme_btn_color));
            } else {
                newbtn.setTextColor(c.getResources().getColor(R.color.indication_text_color));
            }
            // newbtn.setTextColor(Color.BLACK);

            // newbtn.setShadowLayer(2, 2, 2, 0xaaaaaaaa);
            newbtn.setBackgroundResource(backgroundResource);
            newbtn.setGravity(Gravity.CENTER);

            return newbtn;
        }

        public Builder setDialogWidth(int width) {
            mDialog.setWidth(width + 20);

            return this;
        }

        public Builder setDialogHeight(int height) {
            mDialog.setHeight(height);

            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            mDialog.setCancelable(cancelable);

            return this;
        }

        public Builder setCancelableOutside(boolean cancelableOutside) {
            mDialog.setCanceledOnTouchOutside(cancelableOutside);
            return this;
        }

        public BaseDialogWindow create() {
            if (mTitle != null) {
                title = (TextView) mDialogTitleLayout.findViewById(R.id.dialog_title);
                // title.setShadowLayer(2, 2, 2, 0xaaaaaaaa);//字体阴影 效果不好注释掉了
                title.setVisibility(View.VISIBLE);
                // title.setPadding(0, 5, 0, 5);
                title.setTextSize(20);
                // TextPaint tPaint = title.getPaint();
                // tPaint.setFakeBoldText(true);
                // tPaint.setAntiAlias(true);

                title.setText(mTitle);
            }

            if (mAddCloseIcon) {
                mDialogTitleLayout.findViewById(R.id.dialog_title_close_icon).setVisibility(View.VISIBLE);
            }

            if (mMessage != null) {
                TextView message = (TextView) mDialogContentLayout.findViewById(R.id.dialog_message);
                message.setText(mMessage);
                // message.setPadding(0, 60, 0, 60);
                message.setVisibility(View.VISIBLE);
            }

            if (mOptionsList != null) {
                mDialogContentLayout.removeAllViews();
                mDialogContentLayout.setPadding(2, 2, 2, 2);
                mDialogContentLayout.addView(mOptionsList);
            }

            if (mContent != null) {
                mDialogContentLayout.removeAllViews();
                mDialogContentLayout.addView(mContent, new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT));
            }

            if (mMoreView != null) {
                mDialogContentLayout.addView(mMoreView);
            }

            if (mGravity == Gravity.CENTER) {
                mDialogContentLayout.setBackgroundResource(R.drawable.base_bg_dialog);
            } else {
                mDialogContentLayout.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            }
            if (mButtons.size() != 0) {
                mDialogLine.setVisibility(View.VISIBLE);
                addButtonInLayout();
            } else {
                mDialogContentLayout.setBackgroundResource(R.drawable.base_bg_dialog);
                mDialogLine.setVisibility(View.GONE);
            }

            mDialog.setContentView(mDialogBaseLayout, new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT));

            return mDialog;
        }

        public void show() {
            if (mShow)
                create().show();
        }

        public static void put(BaseDialogWindow dialog) {
            mDialogList.add(dialog);
        }

        public static void remove(BaseDialogWindow dialog) {
            mDialogList.remove(dialog);
        }

        public static List<BaseDialogWindow> getDialogList() {
            return mDialogList;
        }

        private void addButtonInLayout() {
            Button button = null;
            if (mButtons.size() == 1) {
                button = mButtons.get(0);
                button.setMinWidth(mDialog.getWidth());
                button.setLayoutParams(new LinearLayout.LayoutParams(mDialog.getWidth(), LayoutParams.WRAP_CONTENT));
                mDialogButtonLayout.addView(button);

            } else {
                int maxWidth = mDialog.getWidth() / mButtons.size();// (mButtons.size()
                // + 1);
                int margin = 0;

                for (int i = 0; i < mButtons.size(); i++) {
                    button = mButtons.get(i);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(maxWidth, LayoutParams.WRAP_CONTENT);
                    if (i != mButtons.size() - 1) {
                        lp.rightMargin = margin;
                    }
                    button.setLayoutParams(lp);

                    mDialogButtonLayout.addView(button);
                    View mline = new View(mContext);
                    if (mline != null) {
                        LinearLayout.LayoutParams lp1 = null;
                        lp1 = new LinearLayout.LayoutParams(1, LayoutParams.MATCH_PARENT);
                        //lp1.setMargins(0, 25, 0, 25);
                        mline.setLayoutParams(lp1);

                        mline.setBackgroundColor(mContext.getResources().getColor(R.color.item_dividing_line));
                    }
                    mDialogButtonLayout.addView(mline);
                }

            }

        }

        public void dismiss() {
            removeDialog(false);
        }

        private class BaseDialogAdapter extends BaseAdapter {
            int mTouchSlop = 0;
            float startY = 0;

            private OnDialogItemClickListener mOnItemClick = null;

            public BaseDialogAdapter(OnDialogItemClickListener ll) {
                mOnItemClick = ll;
                mTouchSlop = ViewConfiguration.get(mContext).getScaledTouchSlop();
            }

            @Override
            public int getCount() {
                return mOptions.length;
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                final int index = position;

                LinearLayout ll = new LinearLayout(mContext);
                ll.setGravity(Gravity.CENTER);
                ll.setBackgroundResource(R.drawable.item_bg_bg1);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

                TextView option = new TextView(mContext);
                option.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContext.getResources().getDimensionPixelSize(R.dimen.text_medium));
                option.setText(mOptions[position]);
                option.setGravity(Gravity.CENTER_VERTICAL);
                option.setTextColor(Color.BLACK);
                option.setBackgroundResource(R.drawable.item_front_bg1);

                option.setOnTouchListener(new OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        final View itemView = v;
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            v.startAnimation(getAnimation(true));
                            startY = event.getY();
                        }

                        if (event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP) {
                            final int yDetal = (int) Math.abs(event.getY() - startY);

                            if (v.getAnimation().hasEnded()) {
                                v.startAnimation(getAnimation(false));
                            } else {
                                v.setAnimation(getAnimation(false));
                            }

                            v.getAnimation().setAnimationListener(new AnimationListener() {

                                @Override
                                public void onAnimationStart(Animation animation) {
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {
                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    if (yDetal < mTouchSlop) {
                                        if (mOnItemClick != null)
                                            mOnItemClick.onItemClick(mOptionsList, itemView, index, getItemId(index));
                                    }

                                    itemView.clearAnimation();
                                }
                            });
                        }

                        return true;
                    }
                });

                ll.addView(option, lp);
                return ll;
            }

            private ScaleAnimation getAnimation(boolean actionDown) {
                ScaleAnimation scale = null;
                if (actionDown)
                    scale = new ScaleAnimation(1.0f, 0.95f, 1.0f, 0.8f, Animation.RELATIVE_TO_SELF, 0.5f,
                            Animation.RELATIVE_TO_SELF, 0.5f);
                else
                    scale = new ScaleAnimation(0.95f, 1.0f, 0.8f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f,
                            Animation.RELATIVE_TO_SELF, 0.5f);

                scale.setDuration(200);
                scale.setFillEnabled(true);
                scale.setFillAfter(true);

                return scale;
            }

        }

        /**
         * The interface for dialog button and dialog item
         *
         * @author Administrator
         */
        public static abstract class OnDialogButtonClickListener implements View.OnClickListener {

            @Override
            public void onClick(View v) {
                if (!onDialogButtonClick(v)) {
                    Object tag = v.getTag();
                    if (tag != null) {
                        BaseDialogWindow dialog = (BaseDialogWindow) tag;
                        dialog.dismiss();
                    } else {
                        removeDialog(false);
                    }

                }

                // onDialogButtonClick(v);
            }

            /**
             * @param view
             * @return false means dismiss the dialog while clicking the button,
             * otherwise no action
             */
            public abstract boolean onDialogButtonClick(View view);
        }

        public static abstract class OnDialogItemClickListener implements OnItemClickListener {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg3) {
                removeDialog(false);

                if (mClearSelectionMode) {
                    boolean isClearSelection = false;
                    if (position == 0) {
                        isClearSelection = true;
                        position = -1;
                    } else {
                        position = position - 1;
                    }
                    onDialogItemClick(adapterView, view, position, isClearSelection);
                } else {
                    onDialogItemClick(adapterView, view, position, false);
                }
            }

            /**
             * @param adapterView
             * @param view            The view that was clicked
             * @param position        If isClearPosition is true, the position's value is -1
             *                        and that means its unavailable,
             * @param isClearPosition If true means the item you click is clear position,
             *                        false otherwise.Note that you do not need to correct
             *                        the value of the position, it has been corrected in
             *                        fact.
             * @return The return value currently unavailable
             */
            public abstract boolean onDialogItemClick(AdapterView<?> adapterView, View view, int position, boolean isClearPosition);

        }

        /**
         * Remove dialog in the BaseDialog list.
         *
         * @param removeAll true if you want to remove all dialogs, false otherwise
         */
        public static void removeDialog(boolean removeAll) {
            try {
                while (mDialogList.size() > 0) {
                    BaseDialogWindow dialog = mDialogList.remove(mDialogList.size() - 1);
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();

                        if (!removeAll) {
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("" + e);
                return;
            }
        }
    }
}
