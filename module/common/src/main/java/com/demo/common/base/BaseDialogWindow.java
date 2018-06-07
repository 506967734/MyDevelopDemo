package com.demo.common.base;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.common.R;
import com.demo.common.constant.GlobalVariable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseDialogWindow extends Dialog {
	public static TextView title;
	public static FrameLayout mDialogTitleLayout = null, mDialogContentLayout = null;
	public static ImageView iv_close = null;

	private int backgroundResource = R.color.transparent;
	private int gravity = Gravity.CENTER;
	private int mDialogWidth =  (int)(GlobalVariable.SCREEN_WIDTH* 0.9);
	private int dialogHeight = LayoutParams.WRAP_CONTENT;
	private static Logger logger = LoggerFactory.getLogger("BaseDialogWindow");

	public BaseDialogWindow(Context context, int theme) {
		super(context, theme);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		init();
	}

	public BaseDialogWindow(Context context) {
		super(context, R.style.FullHeightDialog);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		init();
	}

	public void setDialogTitle(String t) {
		if (t != null) {
			title.setText(t);
		}
	}

	public String getDialogTitle() {

		return title.getText().toString().trim();

	}

	private void init() {
		super.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				BaseDialog.Builder.remove(BaseDialogWindow.this);
				logger.info("size on show----->" + BaseDialog.Builder.getDialogList().size());
				if (mOnDismiss != null) {
					mOnDismiss.onDismiss(dialog);
				}
			}
		});
	}

	public void setBackGroundId(int id) {
		backgroundResource = id;
	}

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public void show() {
		try {
			setWindow();
			darkScreenLight();
			super.show();
			BaseDialog.Builder.put(this);
			logger.info("size on show----->" + BaseDialog.Builder.getDialogList().size());
		} catch (Exception e) {
			
		}
	}

	public void setGravity(int gravity) {
		this.gravity = gravity;
	}

	public void setWidth(int width) {
		mDialogWidth = width;

	}

	public int getWidth() {
		return mDialogWidth = (int)(GlobalVariable.SCREEN_WIDTH* 0.9);
	}

	public void setHeight(int height) {
		dialogHeight = height;
	}

	private void darkScreenLight() {
		LayoutParams m = getWindow().getAttributes();
		m.dimAmount = 0.5f;
		getWindow().setAttributes(m);
	}

	private void setWindow() {
		getWindow().setBackgroundDrawableResource(backgroundResource);

		// getWindow().setWindowAnimations(R.style.Dialog_Animation);

		getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		Window win = this.getWindow();
		LayoutParams params = win.getAttributes();
		if (gravity == Gravity.BOTTOM) {
			win.getDecorView().setPadding(0, 0, 0, 0);
			params.width = LayoutParams.FILL_PARENT;
			params.height = LayoutParams.WRAP_CONTENT;
		} else if (gravity == Gravity.CENTER) {
			params.width = mDialogWidth;
			params.height = dialogHeight;
		} else {
			params.width = mDialogWidth;
			params.height = dialogHeight;
		}
		params.dimAmount = 0;
		params.gravity = gravity;

		win.setAttributes(params);
	}

	private OnDismissListener mOnDismiss = null;

	@Override
	public void setOnDismissListener(OnDismissListener listener) {
		mOnDismiss = listener;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(isOutOfBounds(getContext(),event)){
			if(onTouchOutsideEvent!=null){
				onTouchOutsideEvent.touchOutside();
			}
		}
		return super.onTouchEvent(event);
	}

	private boolean isOutOfBounds(Context context, MotionEvent event) {
		final int x = (int) event.getX();
		final int y = (int) event.getY();
		final int slop = ViewConfiguration.get(context).getScaledWindowTouchSlop();
		final View decorView = getWindow().getDecorView();
		return (x < -slop) || (y < -slop) || (x > (decorView.getWidth() + slop))
				|| (y > (decorView.getHeight() + slop));
	}

	public interface OnTouchOutsideEvent{
		void touchOutside();
	}
	private OnTouchOutsideEvent onTouchOutsideEvent;

	public void setOnTouchOutsideEvent(OnTouchOutsideEvent onTouchOutsideEvent){
		this.onTouchOutsideEvent = onTouchOutsideEvent;
	}
}
