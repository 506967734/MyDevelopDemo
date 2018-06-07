package cn.zhudi.mvpdemo.base;


import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import rx.subscriptions.CompositeSubscription;

public abstract class BasePresenter<T> {
//    public T mView;
//
//    public void attach(T mView){
//        this.mView = mView;
//    }
//
//    public void dettach(){
//        mView = null;
//    }

    public Reference<T> mViewRef;
    protected CompositeSubscription mCompositeSubscription;

    public void attach(T mView) {
        mCompositeSubscription = new CompositeSubscription();
        mViewRef = new WeakReference<>(mView);
    }

    public T getView() {
        if (mViewRef != null) {
            return mViewRef.get();
        }
        return null;
    }

    public void dettach() {
        if (mCompositeSubscription != null && mCompositeSubscription.isUnsubscribed()) {
            mCompositeSubscription.unsubscribe();
        }
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }
}
