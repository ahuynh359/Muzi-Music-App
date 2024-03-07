package com.ahuynh.muzimusic.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;

public class SimpleDialog {

    public static void show(Context context, String title, String message, final DialogClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", (dialogInterface, i) -> {
            if (listener != null) {
                listener.onPositiveClick();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public interface DialogClickListener {
        void onPositiveClick();
    }
}
