package com.demo.common.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.WeakReference;

/**
 * Created by zhudi on 18/6/1.
 */
public class ToastCompat {
    private static final Logger logger = LoggerFactory.getLogger("ToastCompat");

    private static WeakReference<Toast> weakReference;

//    public static void show(Context context, final CharSequence text, final int duration) {
//        if(context == null) {
//            return ;
//        }
//        final Toast toast;
//        if (weakReference == null || weakReference.get() == null) {
//            toast = Toast.makeText(context,text.toString(),duration);
//            weakReference = new WeakReference<Toast>(toast);
//        } else {
//            toast = weakReference.get();
//        }
//        logger.debug("toast.show() {} ， {}" ,text,toast);
//        toast.setText(text);
//        toast.show();
////        Handler handler = new Handler(Looper.getMainLooper());
////        handler.post(new Runnable() {
////            @Override
////            public void run() {
////                logger.debug("toast.show() {} ， {}" ,text,toast);
////                toast.setText(text);
////                toast.show();
////            }
////        });
//    }

    public static void show(Context context, final CharSequence text, final int duration) {
        if(context == null) {
            return ;
        }
        final Toast toast;
        if (weakReference == null || weakReference.get() == null) {
            toast = android.widget.Toast.makeText(context, text, duration);
            weakReference = new WeakReference<android.widget.Toast>(toast);
        } else {
            toast = weakReference.get();
        }

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                logger.debug("toast.show() {} ， {}" ,text,toast);
                toast.setText(text);
                toast.setDuration(duration);
                toast.show();
            }
        });
    }
}
