package com.demo.common.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;


import com.mic.etoast2.Toast;

import java.lang.ref.WeakReference;

/**
 * Created by zhudi on 18/6/1.
 */
public class ToastCompat {

    private static WeakReference<Toast> weakReference;

    public static void show(Context context, final CharSequence text, final int duration) {
        if(context == null) {
            return ;
        }
        final Toast toast;
        if (weakReference == null || weakReference.get() == null) {
            toast = Toast.makeText(context,text.toString(),duration);
            weakReference = new WeakReference<Toast>(toast);
        } else {
            toast = weakReference.get();
        }

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                toast.setText(text);
                toast.show();
            }
        });
    }
}
