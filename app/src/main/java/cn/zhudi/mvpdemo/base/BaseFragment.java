package cn.zhudi.mvpdemo.base;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhudi on 2018/5/28.
 */
public abstract class BaseFragment extends Fragment {
    private Unbinder unbinder;

    protected abstract int getLayoutView();

    private static Logger logger = LoggerFactory.getLogger("BaseFragment");

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logger.info("onCreate={}, Bundle={}", this, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        logger.info("onCreateView={}, container={}, Bundle={}", this, container, savedInstanceState);
        View view = inflater.inflate(getLayoutView(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        logger.info("onStart={}", this);
    }

    @Override
    public void onResume() {
        super.onResume();
        logger.info("onResume={}", this);
    }

    @Override
    public void onPause() {
        super.onPause();
        logger.info("onPause={}", this);
    }

    @Override
    public void onStop() {
        super.onStop();
        logger.info("onStop={}", this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        logger.info("onDestroyView={}", this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        logger.info("onStop={}", this);
    }

    /**
     * 网络回调后是否能继续操作UI<br/>
     * 取名叫canContinue,主要的判断是否Fragment处于正在销毁的过程中<br/>
     * 如果Fragment处于销毁过程中明显操作UI应该被终止,否则容易出现异常
     *
     * @return
     */
    public boolean canContinue() {
        if (isDetached() || isRemoving()) {
            logger.warn("canContinue return false, Fragment isDetached OR isRemoving.");
            return false;
        }
        Activity activity = getActivity();
        if (activity == null || activity.isFinishing()) {
            logger.warn("canContinue return false, activity is null Or isFinishing.");
            return false;
        }
        if (Build.VERSION_CODES.JELLY_BEAN_MR1 <= Build.VERSION.SDK_INT
                && activity.isDestroyed()) {
            logger.warn("canContinue return false, SDK_INT={}, activity is isDestroyed.", Build.VERSION.SDK_INT);
            return false;
        }
        return true;
    }

}
