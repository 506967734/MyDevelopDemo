/*
 *
 *  * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 *
 */

package com.demo.common.dialog;

import android.app.Dialog;
import android.content.Context;

import com.demo.common.R;


/**
 * Created by cwf on 20170914
 */
public class LoadingProgressDialog {
    private LoadingProgressDrawable loadingProgressDrawable;
    private Dialog dialog;

    private LoadingProgressDialog(Context context) {
        dialog = new Dialog(context, R.style.confirm_dialog);
        dialog.setContentView(R.layout.dialog_loading_progress);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        loadingProgressDrawable = (LoadingProgressDrawable) dialog.findViewById(R.id.loading_progress_view);
    }

    public static void dismiss(LoadingProgressDialog progressDialog) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    public static LoadingProgressDialog show(Context context) {
        return new LoadingProgressDialog(context).show();
    }

    private LoadingProgressDialog show() {
        dialog.dismiss();
        dialog.show();
        loadingProgressDrawable.show();
        return this;
    }

    private void dismiss() {
        dialog.dismiss();
        loadingProgressDrawable.dismiss();
    }
}
